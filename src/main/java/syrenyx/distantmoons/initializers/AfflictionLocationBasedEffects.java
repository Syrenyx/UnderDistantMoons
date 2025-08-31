package syrenyx.distantmoons.initializers;

import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.entity.ChangeAfflictionProgressionEffect;

public class AfflictionLocationBasedEffects {

  public static void initialize() {
    Registry.register(Registries.AFFLICTION_LOCATION_BASED_EFFECT_TYPE, UnderDistantMoons.identifierOf("change_affliction_progression"), ChangeAfflictionProgressionEffect.CODEC);
  }
}
