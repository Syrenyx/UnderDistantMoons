package syrenyx.distantmoons.initializers;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.worldgen.feature.BranchingOreVeinFeature;
import syrenyx.distantmoons.content.worldgen.feature.OreGeodeFeature;

public abstract class DistantMoonsFeatures {

  public static final Feature<BranchingOreVeinFeature.Config> BRANCHING_ORE_VEIN = register("branching_ore_vein", new BranchingOreVeinFeature(BranchingOreVeinFeature.Config.CODEC));
  public static final Feature<OreGeodeFeature.Config> ORE_GEODE = register("ore_geode", new OreGeodeFeature(OreGeodeFeature.Config.CODEC));

  private static <T extends FeatureConfig> Feature<T> register(String id, Feature<T> feature) {
    return Registry.register(Registries.FEATURE, UnderDistantMoons.identifierOf(id), feature);
  }

  public static void initialize() {}
}
