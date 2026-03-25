package syrenyx.distantmoons.references.data.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsConfiguredFeatures {

  public static final ResourceKey<ConfiguredFeature<?, ?>> DEEP_IRON_BUBBLE = keyOf("deep_iron_bubble");
  public static final ResourceKey<ConfiguredFeature<?, ?>> DEEP_IRON_ORE = keyOf("deep_iron_ore");
  public static final ResourceKey<ConfiguredFeature<?, ?>> UNDERWORLD_ANCHOR_CHAMBER = keyOf("underworld_anchor_chamber");

  private static ResourceKey<ConfiguredFeature<?, ?>> keyOf(String id) {
    return UnderDistantMoons.resourceKeyOf(id, Registries.CONFIGURED_FEATURE);
  }
}
