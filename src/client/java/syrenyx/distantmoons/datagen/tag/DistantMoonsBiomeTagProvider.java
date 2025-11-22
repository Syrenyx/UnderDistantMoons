package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import syrenyx.distantmoons.references.tag.DistantMoonsBiomeTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBiomeTagProvider extends FabricTagProvider<Biome> {

  public DistantMoonsBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, RegistryKeys.BIOME, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

    //HAS FEATURE
    this.builder(DistantMoonsBiomeTags.HAS_DEEP_IRON_BUBBLE_FEATURE)
        .add(BiomeKeys.BASALT_DELTAS)
        .add(BiomeKeys.CRIMSON_FOREST)
        .add(BiomeKeys.NETHER_WASTES)
        .add(BiomeKeys.SOUL_SAND_VALLEY)
        .add(BiomeKeys.WARPED_FOREST);
    this.builder(DistantMoonsBiomeTags.HAS_DEEP_IRON_ORE_FEATURE)
        .add(BiomeKeys.BASALT_DELTAS);
  }
}
