package syrenyx.distantmoons.initializers.component;

import net.minecraft.component.ComponentType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.AfflictionEffectEntry;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;
import syrenyx.distantmoons.initializers.Registries;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class AfflictionEffectComponents {

  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> TICK = register(
      "tick", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.SELECTOR).listOf())
  );

  private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
    return Registry.register(Registries.AFFLICTION_EFFECT_COMPONENT_REGISTRY, UnderDistantMoons.identifierOf(id), builderOperator.apply(ComponentType.builder()).build());
  }

  public static void initialize() {}
}
