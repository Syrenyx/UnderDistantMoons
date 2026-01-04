package syrenyx.distantmoons.initializers;

import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.affliction.effect.AfflictionEffectEntry;
import syrenyx.distantmoons.content.affliction.effect.ProgressionThresholdAfflictionEffectEntry;
import syrenyx.distantmoons.content.affliction.effect.SpawnedEntityAfflictionEffectEntry;
import syrenyx.distantmoons.content.affliction.effect.TargetedAfflictionEffectEntry;
import syrenyx.distantmoons.content.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.content.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.content.affliction.effect.location_based.AttributeEffect;
import syrenyx.distantmoons.content.affliction.effect.miscellaneous.DamageImmunityEffect;
import syrenyx.distantmoons.content.affliction.effect.value.AfflictionValueEffect;

import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;

public abstract class DistantMoonsAfflictionEffectComponents {

  //ENTITY EFFECT ENTRIES
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> HIT_BLOCK = register(
      "hit_block", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_BLOCK).listOf())
  );
  public static final DataComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> POST_ATTACK = register(
      "post_attack", builder -> builder.persistent(TargetedAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final DataComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> POST_DEATH = register(
      "post_death", builder -> builder.persistent(TargetedAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final DataComponentType<List<ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect>>> PROGRESSION_THRESHOLD = register(
      "progression_threshold", builder -> builder.persistent(ProgressionThresholdAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ENTITY).listOf())
  );
  public static final DataComponentType<List<SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect>>> PROJECTILE_SPAWNED = register(
      "projectile_spawned", builder -> builder.persistent(SpawnedEntityAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_PROJECTILE).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> STAGE_CHANGED = register(
      "stage_changed", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ENTITY).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> TICK = register(
      "tick", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ENTITY).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> USED_ITEM = register(
      "used_item", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ITEM).listOf())
  );

  //LOCATION BASED EFFECT ENTRIES
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionLocationBasedEffect>>> LOCATION_CHANGED = register(
      "location_changed", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionLocationBasedEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ENTITY).listOf())
  );

  //VALUE EFFECT ENTRIES
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> ARMOR_EFFECTIVENESS = register(
      "armor_effectiveness", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> DAMAGE = register(
      "damage", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> DAMAGE_PROTECTION = register(
      "damage_protection", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> FISHING_LUCK_BONUS = register(
      "fishing_luck_bonus", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ITEM).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> FISHING_TIME_REDUCTION = register(
      "fishing_time_reduction", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ITEM).listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> KNOCKBACK = register(
      "knockback", builder -> builder.persistent(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ATTACK).listOf())
  );

  //MISCELLANEOUS EFFECT ENTRIES
  public static final DataComponentType<List<AttributeEffect>> ATTRIBUTES = register(
      "attributes", builder -> builder.persistent(AttributeEffect.CODEC.codec().listOf())
  );
  public static final DataComponentType<List<AfflictionEffectEntry<DamageImmunityEffect>>> DAMAGE_IMMUNITY = register(
      "damage_immunity", builder -> builder.persistent(AfflictionEffectEntry.createCodec(DamageImmunityEffect.CODEC, DistantMoonsLootContextTypes.AFFLICTED_ATTACK).listOf())
  );

  private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
    return Registry.register(DistantMoonsRegistries.AFFLICTION_EFFECT_COMPONENT_REGISTRY, UnderDistantMoons.identifierOf(id), builderOperator.apply(DataComponentType.builder()).build());
  }

  public static void initialize() {}
}
