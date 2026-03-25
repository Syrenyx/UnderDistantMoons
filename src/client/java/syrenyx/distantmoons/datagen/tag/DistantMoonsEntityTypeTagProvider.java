package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.tag.DistantMoonsEntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {

  public DistantMoonsEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.@NonNull Provider provider) {

    this.builder(DistantMoonsEntityTypeTags.IGNORED_BY_UNDERWORLD_CONFLUX)
        .add(EntityType.AREA_EFFECT_CLOUD.builtInRegistryHolder().key());
  }
}
