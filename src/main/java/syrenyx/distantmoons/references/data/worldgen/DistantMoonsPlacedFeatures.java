package syrenyx.distantmoons.references.data.worldgen;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.PlacedFeature;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsPlacedFeatures {

  public static final RegistryKey<PlacedFeature> DEEP_IRON_BUBBLE = keyOf("deep_iron_bubble");
  public static final RegistryKey<PlacedFeature> DEEP_IRON_ORE = keyOf("deep_iron_ore");

  private static RegistryKey<PlacedFeature> keyOf(String id) {
    return UnderDistantMoons.registryKeyOf(id, RegistryKeys.PLACED_FEATURE);
  }
}
