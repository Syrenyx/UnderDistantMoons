package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.predicate.number_provider.AfflictionProgression;
import syrenyx.distantmoons.content.predicate.number_provider.AfflictionStage;

public abstract class DistantMoonsNumberProviders {

  static {
    register("affliction_progression", AfflictionProgression.CODEC);
    register("affliction_stage", AfflictionStage.CODEC);
  }

  private static void register(String id, MapCodec<? extends NumberProvider> codec) {
    Registry.register(BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
