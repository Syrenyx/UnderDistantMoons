package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.value.*;

public abstract class AfflictionValueEffects {

  static {
    register("add", AddEffect.CODEC);
    register("multiply", MultiplyEffect.CODEC);
    register("remove_binomial", RemoveBinomialEffect.CODEC);
    register("set", SetEffect.CODEC);
  }

  private static <T extends AfflictionValueEffect> void register(String id, MapCodec<T> codec) {
    Registry.register(Registries.AFFLICTION_VALUE_EFFECT_REGISTRY, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
