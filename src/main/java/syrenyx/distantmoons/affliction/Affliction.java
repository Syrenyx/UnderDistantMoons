package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.effect.*;
import syrenyx.distantmoons.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.affliction.effect.miscellaneous.DamageImmunityEffect;
import syrenyx.distantmoons.initializers.AfflictionEffectComponents;
import syrenyx.distantmoons.initializers.LootContextTypes;
import syrenyx.distantmoons.initializers.Registries;
import syrenyx.distantmoons.references.RegistryKeys;

import java.util.List;
import java.util.Optional;

public record Affliction(
    Text description,
    Optional<AfflictionDisplay> display,
    Optional<RegistryEntryList<EntityType<?>>> immuneEntities,
    int maxStage,
    boolean persistent,
    Optional<EnchantmentLevelBasedValue> tickProgression,
    ComponentMap effects
) {

  public static final Codec<ComponentMap> COMPONENT_MAP_CODEC = ComponentMap.createCodec(Codec.lazyInitialized(Registries.AFFLICTION_EFFECT_COMPONENT_REGISTRY::getCodec));
  public static final Codec<Affliction> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          TextCodecs.CODEC.fieldOf("description").forGetter(Affliction::description),
          AfflictionDisplay.CODEC.optionalFieldOf("display").forGetter(Affliction::display),
          RegistryCodecs.entryList(net.minecraft.registry.RegistryKeys.ENTITY_TYPE).optionalFieldOf("immune_entities").forGetter(Affliction::immuneEntities),
          Codecs.rangedInt(1, Affliction.MAX_STAGE).fieldOf("max_stage").forGetter(Affliction::maxStage),
          Codec.BOOL.optionalFieldOf("persistent", false).forGetter(Affliction::persistent),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("tick_progression").forGetter(Affliction::tickProgression),
          COMPONENT_MAP_CODEC.optionalFieldOf("effects", ComponentMap.EMPTY).forGetter(Affliction::effects)
      )
      .apply(instance, Affliction::new)
  );
  public static final Codec<RegistryEntry<Affliction>> FIXED_ENTRY_CODEC = RegistryFixedCodec.of(RegistryKeys.AFFLICTION_REGISTRY_KEY);
  public static final int MAX_PROGRESSION = 100;
  public static final int MAX_STAGE = 255;
  public static final int DEFAULT_STAGE = 1;

  public static boolean processDamageImmunityEffects(
      Entity entity,
      DamageSource damageSource,
      AfflictionInstance afflictionInstance
  ) {
    List<AfflictionEffectEntry<DamageImmunityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(AfflictionEffectComponents.DAMAGE_IMMUNITY, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(entity, damageSource, afflictionInstance.stage(), afflictionInstance.progression());
    for (AfflictionEffectEntry<DamageImmunityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) return true;
    }
    return false;
  }

  public static void processHitBlockEffects(
      Entity entity,
      Vec3d pos,
      AfflictionInstance afflictionInstance,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedBlockLootContext(entity, pos, afflictionInstance.stage(), afflictionInstance.progression());
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, pos);
    }
  }

  public static void processLocationChangedEffects(
      Entity entity,
      boolean remove,
      AfflictionInstance afflictionInstance,
      ComponentType<List<AfflictionEffectEntry<AfflictionLocationBasedEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionLocationBasedEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedEntityLootContext(entity, afflictionInstance.stage(), afflictionInstance.progression());
    for (AfflictionEffectEntry<AfflictionLocationBasedEffect> effectEntry : effectEntries) {
      if (remove) effectEntry.effect().remove((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, entity.getPos(), afflictionInstance);
      else if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, entity.getPos(), afflictionInstance);
    }
  }

  public static void processPostDamageEffects(
      Entity victim,
      DamageSource damageSource,
      EnchantmentEffectTarget afflicted,
      AfflictionInstance afflictionInstance,
      ComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(victim, damageSource, afflictionInstance.stage(), afflictionInstance.progression());
    for (TargetedAfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.afflicted() != afflicted) continue;
      Entity target = switch (effectEntry.affected()) {
        case ATTACKER -> damageSource.getAttacker();
        case DAMAGING_ENTITY -> damageSource.getSource();
        case VICTIM -> victim;
      };
      if (target == null) continue;
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) target.getWorld(), afflictionInstance.stage(), target, target.getPos());
    }
  }

  public static void processProgressionThresholdEffects(
      Entity entity,
      float previousProgression,
      AfflictionInstance afflictionInstance,
      ComponentType<List<ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    if (afflictionInstance.affliction().value().tickProgression().isEmpty() || afflictionInstance.affliction().value().tickProgression().get().getValue(afflictionInstance.stage()) == 0.0) return;
    List<ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    float currentProgression = afflictionInstance.progression();
    LootContext lootContext = getAfflictedEntityLootContext(entity, afflictionInstance.stage(), currentProgression);
    for (ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (
          currentProgression == previousProgression
              || effectEntry.type() != ProgressionThresholdPassingType.INCREASING && currentProgression < previousProgression && (effectEntry.threshold() < currentProgression || effectEntry.threshold() >= previousProgression)
              || effectEntry.type() != ProgressionThresholdPassingType.DECREASING && currentProgression > previousProgression && (effectEntry.threshold() > currentProgression || effectEntry.threshold() <= previousProgression)
      ) continue;
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, entity.getPos());
    }
  }

  public static void processProjectileSpawnedEffects(
      Entity owner,
      Entity projectile,
      AfflictionInstance afflictionInstance,
      ComponentType<List<SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedProjectileLootContext(owner, projectile, afflictionInstance.stage(), afflictionInstance.progression());
    for (SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      Entity target = effectEntry.target() == SpawnedEntityEffectTarget.ORIGINATOR ? owner : projectile;
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) target.getWorld(), afflictionInstance.stage(), target, target.getPos());
    }
  }

  public static void processStageChangedEffects(
      Entity entity,
      AfflictionInstance afflictionInstance,
      boolean cleared,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedEntityLootContext(entity, cleared ? 0 : afflictionInstance.stage(), afflictionInstance.progression());
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, entity.getPos());
    }
  }

  public static void processTickEffects(
      Entity entity,
      AfflictionInstance afflictionInstance,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedEntityLootContext(entity, afflictionInstance.stage(), afflictionInstance.progression());
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, entity.getPos());
    }
  }

  public static void processUsedItemEffects(
      Entity entity,
      ItemStack item,
      AfflictionInstance afflictionInstance,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedItemLootContext(entity, item, afflictionInstance.stage(), afflictionInstance.progression());
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, entity.getPos());
    }
  }

  private static LootContext getAfflictedAttackLootContext(Entity victim, DamageSource damageSource, int stage, float progression) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) victim.getWorld())
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_PROGRESSION, progression)
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_STAGE, stage)
            .add(LootContextParameters.ATTACKING_ENTITY, damageSource.getAttacker())
            .add(LootContextParameters.DAMAGE_SOURCE, damageSource)
            .add(LootContextParameters.DIRECT_ATTACKING_ENTITY, damageSource.getSource())
            .add(LootContextParameters.ORIGIN, victim.getPos())
            .add(LootContextParameters.THIS_ENTITY, victim)
            .build(LootContextTypes.AFFLICTED_ATTACK)
    ).build(Optional.empty());
  }

  private static LootContext getAfflictedBlockLootContext(Entity victim, Vec3d pos, int stage, float progression) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) victim.getWorld())
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_PROGRESSION, progression)
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_STAGE, stage)
            .add(LootContextParameters.BLOCK_STATE, victim.getWorld().getBlockState(BlockPos.ofFloored(pos)))
            .add(LootContextParameters.ORIGIN, pos)
            .add(LootContextParameters.THIS_ENTITY, victim)
            .build(LootContextTypes.AFFLICTED_BLOCK)
    ).build(Optional.empty());
  }

  private static LootContext getAfflictedEntityLootContext(Entity entity, int stage, float progression) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) entity.getWorld())
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_PROGRESSION, progression)
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_STAGE, stage)
            .add(LootContextParameters.ORIGIN, entity.getPos())
            .add(LootContextParameters.THIS_ENTITY, entity)
            .build(LootContextTypes.AFFLICTED_ENTITY)
    ).build(Optional.empty());
  }

  private static LootContext getAfflictedItemLootContext(Entity entity, ItemStack item, int stage, float progression) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) entity.getWorld())
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_PROGRESSION, progression)
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_STAGE, stage)
            .add(LootContextParameters.ORIGIN, entity.getPos())
            .add(LootContextParameters.THIS_ENTITY, entity)
            .add(LootContextParameters.TOOL, item)
            .build(LootContextTypes.AFFLICTED_ITEM)
    ).build(Optional.empty());
  }

  private static LootContext getAfflictedProjectileLootContext(Entity owner, Entity projectile, int stage, float progression) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) owner.getWorld())
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_PROGRESSION, progression)
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_STAGE, stage)
            .add(syrenyx.distantmoons.references.LootContextParameters.SPAWNED_ENTITY, projectile)
            .add(LootContextParameters.ORIGIN, projectile.getPos())
            .add(LootContextParameters.THIS_ENTITY, owner)
            .build(LootContextTypes.AFFLICTED_PROJECTILE)
    ).build(Optional.empty());
  }
}
