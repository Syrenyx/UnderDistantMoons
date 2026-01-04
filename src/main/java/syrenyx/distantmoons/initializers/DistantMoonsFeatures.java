package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.worldgen.feature.BranchingOreVeinFeature;
import syrenyx.distantmoons.content.worldgen.feature.OreGeodeFeature;

public abstract class DistantMoonsFeatures {

  public static final Feature<BranchingOreVeinFeature.Config> BRANCHING_ORE_VEIN = register("branching_ore_vein", new BranchingOreVeinFeature(BranchingOreVeinFeature.Config.CODEC));
  public static final Feature<OreGeodeFeature.Config> ORE_GEODE = register("ore_geode", new OreGeodeFeature(OreGeodeFeature.Config.CODEC));

  private static <T extends FeatureConfiguration> Feature<T> register(String id, Feature<T> feature) {
    return Registry.register(BuiltInRegistries.FEATURE, UnderDistantMoons.identifierOf(id), feature);
  }

  public static void initialize() {}
}
