package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsItemTagProvider extends FabricTagProvider.ItemTagProvider {

  public DistantMoonsItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, @Nullable BlockTagProvider blockTagProvider) {
    super(output, registriesFuture, blockTagProvider);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

    //FUEL
    this.valueLookupBuilder(DistantMoonsItemTags.SMELTING_FUEL_WOOD);
    this.valueLookupBuilder(DistantMoonsItemTags.SMELTING_FUEL_WOOD_SLAB).add(
        DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE.asItem(),
        DistantMoonsBlocks.CHARCOAL_BLOCK.asItem(),
        DistantMoonsBlocks.COKE_BLOCK.asItem(),
        DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK.asItem(),
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR.asItem(),
        DistantMoonsBlocks.DEEP_IRON_BARS.asItem(),
        DistantMoonsBlocks.DEEP_IRON_CHAIN.asItem(),
        DistantMoonsBlocks.DEEP_IRON_DOOR.asItem(),
        DistantMoonsBlocks.DEEP_IRON_FENCE.asItem(),
        DistantMoonsBlocks.DEEP_IRON_LADDER.asItem(),
        DistantMoonsBlocks.DEEP_IRON_TRAPDOOR.asItem(),
        DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE.asItem(),
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem(),
        DistantMoonsBlocks.FIXED_IRON_LADDER.asItem(),
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem(),
        DistantMoonsBlocks.IRON_BAR_DOOR.asItem(),
        DistantMoonsBlocks.IRON_FENCE.asItem(),
        DistantMoonsBlocks.IRON_LADDER.asItem(),
        DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE.asItem(),
        DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK.asItem(),
        DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK.asItem(),
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR.asItem(),
        DistantMoonsBlocks.WROUGHT_IRON_BARS.asItem(),
        DistantMoonsBlocks.WROUGHT_IRON_FENCE.asItem(),
        DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem()
    );

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
