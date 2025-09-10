package syrenyx.distantmoons.initializers;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.*;
import syrenyx.distantmoons.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.affliction.effect.location_based.AttributeEffect;
import syrenyx.distantmoons.affliction.effect.miscellaneous.DamageImmunityEffect;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AfflictionEffectComponents {

  //ENTITY EFFECT ENTRIES
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> HIT_BLOCK = register(
      "hit_block", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_BLOCK).listOf())
  );
  public static final ComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> POST_ATTACK = register(
      "post_attack", builder -> builder.codec(TargetedAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final ComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> POST_DEATH = register(
      "post_death", builder -> builder.codec(TargetedAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final ComponentType<List<ProgressionThresholdAfflictionEffectEntry<AfflictionEntityEffect>>> PROGRESSION_THRESHOLD = register(
      "progression_threshold", builder -> builder.codec(ProgressionThresholdAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ENTITY).listOf())
  );
  public static final ComponentType<List<SpawnedEntityAfflictionEffectEntry<AfflictionEntityEffect>>> PROJECTILE_SPAWNED = register(
      "projectile_spawned", builder -> builder.codec(SpawnedEntityAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_PROJECTILE).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> STAGE_CHANGED = register(
      "stage_changed", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ENTITY).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> TICK = register(
      "tick", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ENTITY).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> USED_ITEM = register(
      "used_item", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ITEM).listOf())
  );

  //LOCATION BASED EFFECT ENTRIES
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionLocationBasedEffect>>> LOCATION_CHANGED = register(
      "location_changed", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionLocationBasedEffect.CODEC, LootContextTypes.AFFLICTED_ENTITY).listOf())
  );

  //MISCELLANEOUS EFFECT ENTRIES
  public static final ComponentType<List<AttributeEffect>> ATTRIBUTES = register(
      "attributes", builder -> builder.codec(AttributeEffect.CODEC.codec().listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<DamageImmunityEffect>>> DAMAGE_IMMUNITY = register(
      "damage_immunity", builder -> builder.codec(AfflictionEffectEntry.createCodec(DamageImmunityEffect.CODEC, LootContextTypes.AFFLICTED_ATTACK).listOf())
  );

  private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
    return Registry.register(Registries.AFFLICTION_EFFECT_COMPONENT_REGISTRY, UnderDistantMoons.identifierOf(id), builderOperator.apply(ComponentType.builder()).build());
  }

  public static void initialize() {}
}
