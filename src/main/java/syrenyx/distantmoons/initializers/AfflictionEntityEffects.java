package syrenyx.distantmoons.initializers;

import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.entity.IgniteEffect;
import syrenyx.distantmoons.affliction.effect.entity.RunFunctionEffect;

public class AfflictionEntityEffects {

  static {
    Registry.register(Registries.AFFLICTION_ENTITY_EFFECT_REGISTRY, UnderDistantMoons.identifierOf("ignite"), IgniteEffect.CODEC);
    Registry.register(Registries.AFFLICTION_ENTITY_EFFECT_REGISTRY, UnderDistantMoons.identifierOf("run_function"), RunFunctionEffect.CODEC);
  }

  public static void initialize() {}
}
