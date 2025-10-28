package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsItems;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsItemTagProvider extends FabricTagProvider.ItemTagProvider {

  public DistantMoonsItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, @Nullable BlockTagProvider blockTagProvider) {
    super(output, registriesFuture, blockTagProvider);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

    //REPAIR MATERIALS
    this.valueLookupBuilder(DistantMoonsItemTags.REPAIRS_DEEP_IRON_EQUIPMENT).add(
        DistantMoonsItems.REFINED_DEEP_IRON_INGOT
    );

    //MISCELLANEOUS
    this.valueLookupBuilder(DistantMoonsItemTags.SWORD).add(
        DistantMoonsItems.DEEP_IRON_SWORD
    );

    //VANILLA TAG REDIRECTS
    this.valueLookupBuilder(ItemTags.SWORDS).addTag(DistantMoonsItemTags.SWORD);
  }
}
