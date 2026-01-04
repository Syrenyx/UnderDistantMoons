package syrenyx.distantmoons.references.data.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsPlacedFeatures {

  public static final ResourceKey<PlacedFeature> DEEP_IRON_BUBBLE = keyOf("deep_iron_bubble");
  public static final ResourceKey<PlacedFeature> DEEP_IRON_ORE = keyOf("deep_iron_ore");

  private static ResourceKey<PlacedFeature> keyOf(String id) {
    return UnderDistantMoons.registryKeyOf(id, Registries.PLACED_FEATURE);
  }
}
