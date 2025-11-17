package syrenyx.distantmoons.initializers;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.worldgen.feature.BranchingOreVeinFeature;

public abstract class DistantMoonsFeatures {

  public static final Feature<BranchingOreVeinFeature.Config> BRANCHING_ORE_VEIN = register("branching_ore_vein", new BranchingOreVeinFeature(BranchingOreVeinFeature.Config.CODEC));

  private static <T extends FeatureConfig> Feature<T> register(String id, Feature<T> feature) {
    return Registry.register(Registries.FEATURE, UnderDistantMoons.identifierOf(id), feature);
  }

  public static void initialize() {}
}
