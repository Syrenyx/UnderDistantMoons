package syrenyx.distantmoons.initializers;

import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.value.AddAfflictionEffect;

public class AfflictionValueEffects {

  public static void initialize() {
    Registry.register(Registries.AFFLICTION_VALUE_EFFECT_TYPE, UnderDistantMoons.identifierOf("add"), AddAfflictionEffect.CODEC);
  }
}
