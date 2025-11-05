package syrenyx.distantmoons.content.affliction;

import com.google.common.collect.ComparisonChain;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.content.affliction.effect.*;
import syrenyx.distantmoons.content.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.content.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.content.affliction.effect.location_based.AttributeEffect;
import syrenyx.distantmoons.content.affliction.effect.miscellaneous.DamageImmunityEffect;
import syrenyx.distantmoons.content.affliction.effect.value.AfflictionValueEffect;
import syrenyx.distantmoons.initializers.DistantMoonsAfflictionEffectComponents;
import syrenyx.distantmoons.initializers.DistantMoonsLootContextTypes;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

import java.util.*;

public class AfflictionInstance implements Comparable<AfflictionInstance> {

  public static final Codec<AfflictionInstance> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Affliction.FIXED_ENTRY_CODEC.fieldOf("id").forGetter(AfflictionInstance::affliction),
          Codecs.rangedInt(1, Affliction.MAX_STAGE).fieldOf("stage").forGetter(AfflictionInstance::stage),
          Codecs.rangedInclusiveFloat(0, Affliction.MAX_PROGRESSION).optionalFieldOf("progression", 0.0F).forGetter(AfflictionInstance::progression)
      )
      .apply(instance, AfflictionInstance::new)
  );
  private final RegistryEntry<Affliction> affliction;
  private float progression;
  private int stage;
  public Set<AttributeEffect> activeAttributeEffects = new HashSet<>();

  public AfflictionInstance(RegistryEntry<Affliction> affliction, int stage, float progression) {
    this.affliction = affliction;
    this.progression = progression;
    this.stage = stage;
  }

  public AfflictionInstance(RegistryEntry<Affliction> affliction, int stage) {
    this.affliction = affliction;
    this.progression = 0.0F;
    this.stage = stage;
  }

  public AfflictionInstance(RegistryEntry<Affliction> affliction) {
    this.affliction = affliction;
    this.progression = 0.0F;
    this.stage = Affliction.DEFAULT_STAGE;
  }

  public RegistryEntry<Affliction> affliction() {
    return this.affliction;
  }

  public float progression() {
    return this.progression;
  }

  public int stage() {
    return this.stage;
  }

  public void setStage(int value) {
    this.stage = value;
  }

  public void addToStage(int value) {
    this.stage += value;
  }

  public void setProgression(float value) {
    this.progression = value;
  }

  public void addToProgression(float value) {
    this.progression += value;
  }

  public void limitToAllowedValues() {
    this.progression = Math.max(0.0F, Math.min(this.progression, Affliction.MAX_PROGRESSION));
    this.stage = Math.max(1, Math.min(this.stage, this.affliction.value().maxStage()));
  }

  public Text getDescription() {
    Affliction affliction = this.affliction.value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).description().orElseThrow();
    } catch (NoSuchElementException e) {
      return affliction.description().copy().append(ScreenTexts.SPACE).append(Text.translatable("enchantment.level." + (this.stage())));
    }
  }

  public @Nullable Identifier getIcon() {
    Affliction affliction = this.affliction.value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).icon().orElseThrow();
    } catch (NoSuchElementException e) {
      if (affliction.display().isPresent()) return affliction.display().get().icon();
      return null;
    }
  }

  public ProgressionBarStyle getProgressionBarStyle() {
    Affliction affliction = this.affliction.value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).progressionBarStyle().orElseThrow();
    } catch (NoSuchElementException e) {
      if (affliction.display().isPresent()) return affliction.tickProgression().isPresent() ? ProgressionBarStyle.DEFAULT : ProgressionBarStyle.INFINITE;
      return null;
    }
  }

  public @Nullable Identifier getTooltipStyle() {
    Affliction affliction = this.affliction.value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).tooltipStyle().orElseThrow();
    } catch (NoSuchElementException e) {
      if (affliction.display().isPresent()) return affliction.display().get().tooltipStyle().orElse(null);
      return null;
    }
  }

  @Override
  public int compareTo(@NotNull AfflictionInstance other) {
    Affliction thisAffliction = this.affliction.value();
    Affliction otherAffliction = other.affliction.value();
    return ComparisonChain
        .start()
        .compareTrueFirst(thisAffliction.persistent(), otherAffliction.persistent())
        .compare(this.stage, other.stage)
        .compare(thisAffliction.maxStage(), otherAffliction.maxStage())
        .result();
  }

  public boolean isVisible() {
    Affliction affliction = this.affliction.value();
    try {
      return !affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage)).hidden().orElseThrow();
    } catch (NoSuchElementException e) {
      return affliction.display().isPresent();
    }
  }

  public void processAttributeEffects(
      LivingEntity entity,
      boolean remove
  ) {
    List<AttributeEffect> effects = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.ATTRIBUTES, List.of());
    for (AttributeEffect effect : effects) {
      effect.remove((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos(), this);
      if (!remove) effect.apply((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos(), this);
    }
  }

  public float processArmorEffectiveness(
      LivingEntity entity,
      DamageSource damageSource,
      float armorEffectiveness
  ) {
    List<AfflictionEffectEntry<AfflictionValueEffect>> effectEntries = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.ARMOR_EFFECTIVENESS, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(entity, damageSource);
    for (var effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) armorEffectiveness = effectEntry.effect().apply(this.stage(), entity.getRandom(), armorEffectiveness);
    }
    return armorEffectiveness;
  }

  public float processDamage(
      LivingEntity entity,
      Entity victim,
      DamageSource damageSource,
      float damage
  ) {
    List<AfflictionEffectEntry<AfflictionValueEffect>> effectEntries = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.DAMAGE, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(victim, damageSource);
    for (var effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) damage = effectEntry.effect().apply(this.stage(), entity.getRandom(), damage);
    }
    return damage;
  }

  public float processDamageProtection(
      LivingEntity entity,
      DamageSource damageSource,
      float damageProtection
  ) {
    List<AfflictionEffectEntry<AfflictionValueEffect>> effectEntries = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.FISHING_LUCK_BONUS, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(entity, damageSource);
    for (var effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) damageProtection = effectEntry.effect().apply(this.stage(), entity.getRandom(), damageProtection);
    }
    return damageProtection;
  }

  public float processFishingLuckBonus(
      LivingEntity entity,
      ItemStack stack,
      float fishingLuckBonus
  ) {
    List<AfflictionEffectEntry<AfflictionValueEffect>> effectEntries = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.FISHING_TIME_REDUCTION, List.of());
    LootContext lootContext = getAfflictedItemLootContext(entity, stack);
    for (var effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) fishingLuckBonus = effectEntry.effect().apply(this.stage(), entity.getRandom(), fishingLuckBonus);
    }
    return fishingLuckBonus;
  }

  public float processFishingTimeReduction(
      LivingEntity entity,
      ItemStack stack,
      float fishingTimeReduction
  ) {
    List<AfflictionEffectEntry<AfflictionValueEffect>> effectEntries = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.KNOCKBACK, List.of());
    LootContext lootContext = getAfflictedItemLootContext(entity, stack);
    for (var effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) fishingTimeReduction = effectEntry.effect().apply(this.stage(), entity.getRandom(), fishingTimeReduction);
    }
    return fishingTimeReduction;
  }

  public float processKnockback(
      LivingEntity entity,
      Entity victim,
      DamageSource damageSource,
      float knockback
  ) {
    List<AfflictionEffectEntry<AfflictionValueEffect>> effectEntries = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.KNOCKBACK, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(victim, damageSource);
    for (var effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) knockback = effectEntry.effect().apply(this.stage(), entity.getRandom(), knockback);
    }
    return knockback;
  }

  public boolean processDamageImmunityEffects(
      Entity entity,
      DamageSource damageSource
  ) {
    List<AfflictionEffectEntry<DamageImmunityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(DistantMoonsAfflictionEffectComponents.DAMAGE_IMMUNITY, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(entity, damageSource);
    for (AfflictionEffectEntry<DamageImmunityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) return true;
    }
    return false;
  }

  public void processHitBlockEffects(
      Entity entity,
      Vec3d pos,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedBlockLootContext(entity, pos);
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getEntityWorld(), this.stage(), entity, pos);
    }
  }

  public void processLocationChangedEffects(
      Entity entity,
      boolean remove,
      ComponentType<List<AfflictionEffectEntry<AfflictionLocationBasedEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionLocationBasedEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedEntityLootContext(entity);
    for (AfflictionEffectEntry<AfflictionLocationBasedEffect> effectEntry : effectEntries) {
      if (remove) effectEntry.effect().remove((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos(), this);
      else if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos(), this);
    }
  }

  public void processPostDamageEffects(
      Entity victim,
      DamageSource damageSource,
      EnchantmentEffectTarget afflicted,
      ComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedAttackLootContext(victim, damageSource);
    for (TargetedAfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.afflicted() != afflicted) continue;
      Entity target = switch (effectEntry.affected()) {
        case ATTACKER -> damageSource.getAttacker();
        case DAMAGING_ENTITY -> damageSource.getSource();
        case VICTIM -> victim;
      };
      if (target == null) continue;
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) target.getEntityWorld(), this.stage(), target, target.getEntityPos());
    }
  }

  public void processProgressionThresholdEffects(
      Entity entity,
      float previousProgression,
      ComponentType<List<ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    if (this.affliction().value().tickProgression().isEmpty() || this.affliction().value().tickProgression().get().getValue(this.stage()) == 0.0) return;
    List<ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedEntityLootContext(entity);
    for (ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (
          this.progression == previousProgression
              || effectEntry.type() != ProgressionThresholdPassingType.INCREASING && this.progression < previousProgression && (effectEntry.threshold() < this.progression || effectEntry.threshold() >= previousProgression)
              || effectEntry.type() != ProgressionThresholdPassingType.DECREASING && this.progression > previousProgression && (effectEntry.threshold() > this.progression || effectEntry.threshold() <= previousProgression)
      ) continue;
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos());
    }
  }

  public void processProjectileSpawnedEffects(
      Entity owner,
      Entity projectile,
      ComponentType<List<SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedProjectileLootContext(owner, projectile);
    for (SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      Entity target = effectEntry.target() == SpawnedEntityEffectTarget.ORIGINATOR ? owner : projectile;
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) target.getEntityWorld(), this.stage(), target, target.getEntityPos());
    }
  }

  public void processStageChangedEffects(
      Entity entity,
      boolean cleared,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedEntityLootContext(entity, cleared ? 0 : this.stage());
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos());
    }
  }

  public void processTickEffects(
      Entity entity,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedEntityLootContext(entity);
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos());
    }
  }

  public void processUsedItemEffects(
      Entity entity,
      ItemStack item,
      ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType
  ) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = this.affliction().value().effects().getOrDefault(componentType, List.of());
    LootContext lootContext = getAfflictedItemLootContext(entity, item);
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getEntityWorld(), this.stage(), entity, entity.getEntityPos());
    }
  }

  private LootContext getAfflictedAttackLootContext(Entity victim, DamageSource damageSource) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) victim.getEntityWorld())
            .add(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION, this.progression)
            .add(DistantMoonsLootContextParameters.AFFLICTION_STAGE, this.stage)
            .add(LootContextParameters.ATTACKING_ENTITY, damageSource.getAttacker())
            .add(LootContextParameters.DAMAGE_SOURCE, damageSource)
            .add(LootContextParameters.DIRECT_ATTACKING_ENTITY, damageSource.getSource())
            .add(LootContextParameters.ORIGIN, victim.getEntityPos())
            .add(LootContextParameters.THIS_ENTITY, victim)
            .build(DistantMoonsLootContextTypes.AFFLICTED_ATTACK)
    ).build(Optional.empty());
  }

  private LootContext getAfflictedBlockLootContext(Entity victim, Vec3d pos) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) victim.getEntityWorld())
            .add(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION, this.progression)
            .add(DistantMoonsLootContextParameters.AFFLICTION_STAGE, this.stage)
            .add(LootContextParameters.BLOCK_STATE, victim.getEntityWorld().getBlockState(BlockPos.ofFloored(pos)))
            .add(LootContextParameters.ORIGIN, pos)
            .add(LootContextParameters.THIS_ENTITY, victim)
            .build(DistantMoonsLootContextTypes.AFFLICTED_BLOCK)
    ).build(Optional.empty());
  }

  private LootContext getAfflictedEntityLootContext(Entity entity) {
    return getAfflictedEntityLootContext(entity, this.stage);
  }

  private LootContext getAfflictedEntityLootContext(Entity entity, int stage) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) entity.getEntityWorld())
            .add(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION, this.progression)
            .add(DistantMoonsLootContextParameters.AFFLICTION_STAGE, stage)
            .add(LootContextParameters.ORIGIN, entity.getEntityPos())
            .add(LootContextParameters.THIS_ENTITY, entity)
            .build(DistantMoonsLootContextTypes.AFFLICTED_ENTITY)
    ).build(Optional.empty());
  }

  private LootContext getAfflictedItemLootContext(Entity entity, ItemStack item) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) entity.getEntityWorld())
            .add(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION, this.progression)
            .add(DistantMoonsLootContextParameters.AFFLICTION_STAGE, this.stage)
            .add(LootContextParameters.ORIGIN, entity.getEntityPos())
            .add(LootContextParameters.THIS_ENTITY, entity)
            .add(LootContextParameters.TOOL, item)
            .build(DistantMoonsLootContextTypes.AFFLICTED_ITEM)
    ).build(Optional.empty());
  }

  private LootContext getAfflictedProjectileLootContext(Entity owner, Entity projectile) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) owner.getEntityWorld())
            .add(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION, this.progression)
            .add(DistantMoonsLootContextParameters.AFFLICTION_STAGE, this.stage)
            .add(DistantMoonsLootContextParameters.SPAWNED_ENTITY, projectile)
            .add(LootContextParameters.ORIGIN, projectile.getEntityPos())
            .add(LootContextParameters.THIS_ENTITY, owner)
            .build(DistantMoonsLootContextTypes.AFFLICTED_PROJECTILE)
    ).build(Optional.empty());
  }
}
