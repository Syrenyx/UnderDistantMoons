package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import syrenyx.distantmoons.references.tag.DistantMoonsBiomeTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBiomeTagProvider extends FabricTagProvider<Biome> {

  public DistantMoonsBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, Registries.BIOME, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider wrapperLookup) {

    //HAS FEATURE
    this.builder(DistantMoonsBiomeTags.HAS_DEEP_IRON_BUBBLE_FEATURE)
        .add(Biomes.BASALT_DELTAS)
        .add(Biomes.CRIMSON_FOREST)
        .add(Biomes.NETHER_WASTES)
        .add(Biomes.SOUL_SAND_VALLEY)
        .add(Biomes.WARPED_FOREST);
    this.builder(DistantMoonsBiomeTags.HAS_DEEP_IRON_ORE_FEATURE)
        .add(Biomes.BASALT_DELTAS);
  }
}
