package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.dimension.v1.DimensionEvents;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.levelgen.GenerationStep;
import syrenyx.distantmoons.references.data.worldgen.DistantMoonsPlacedFeatures;
import syrenyx.distantmoons.references.tag.DistantMoonsBiomeTags;

public abstract class DistantMoonsEnvironmentModifications {

  static {
    BiomeModifications.addFeature(context -> context.hasTag(DistantMoonsBiomeTags.HAS_DEEP_IRON_BUBBLE_FEATURE), GenerationStep.Decoration.UNDERGROUND_ORES, DistantMoonsPlacedFeatures.DEEP_IRON_BUBBLE);
    BiomeModifications.addFeature(context -> context.hasTag(DistantMoonsBiomeTags.HAS_DEEP_IRON_ORE_FEATURE), GenerationStep.Decoration.UNDERGROUND_ORES, DistantMoonsPlacedFeatures.DEEP_IRON_ORE);

    DimensionEvents.MODIFY_ATTRIBUTES.register((dimension, attributes, registries) -> {
      if (dimension.is(BuiltinDimensionTypes.NETHER)) {
        attributes.set(DistantMoonsEnvironmentAttributes.UNDERWORLD, true);
      }
    });
  }

  public static void initialize() {}
}
