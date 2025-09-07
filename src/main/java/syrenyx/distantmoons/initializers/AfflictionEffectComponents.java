package syrenyx.distantmoons.initializers;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.*;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AfflictionEffectComponents {

  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> HIT_BLOCK = register(
      "hit_block", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_BLOCK).listOf())
  );
  public static final ComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> POST_ATTACK = register(
      "post_attack", builder -> builder.codec(TargetedAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ATTACK).listOf())
  );
  public static final ComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> POST_DEATH = register(
      "post_death", builder -> builder.codec(TargetedAfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.AFFLICTED_ATTACK).listOf())
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

  private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
    return Registry.register(Registries.AFFLICTION_EFFECT_COMPONENT_REGISTRY, UnderDistantMoons.identifierOf(id), builderOperator.apply(ComponentType.builder()).build());
  }

  public static void initialize() {}
}
