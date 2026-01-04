package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsItemTagProvider extends FabricTagProvider.ItemTagProvider {

  public DistantMoonsItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, @Nullable BlockTagProvider blockTagProvider) {
    super(output, registriesFuture, blockTagProvider);
  }

  @Override
  protected void addTags(HolderLookup.Provider wrapperLookup) {

    //DYED ITEMS
    this.valueLookupBuilder(DistantMoonsItemTags.DYED_PILLOW).addAll(
        DistantMoonsBlocks.DYED_PILLOWS.values().stream().map(Block::asItem)
    );

    //FUEL
    this.valueLookupBuilder(DistantMoonsItemTags.SMELTING_FUEL_WOOD_BLOCK).add(
        DistantMoonsBlocks.ACACIA_BEAM.asItem(),
        DistantMoonsBlocks.BIRCH_BEAM.asItem(),
        DistantMoonsBlocks.CHERRY_BEAM.asItem(),
        DistantMoonsBlocks.DARK_OAK_BEAM.asItem(),
        DistantMoonsBlocks.JUNGLE_BEAM.asItem(),
        DistantMoonsBlocks.MANGROVE_BEAM.asItem(),
        DistantMoonsBlocks.OAK_BEAM.asItem(),
        DistantMoonsBlocks.PALE_OAK_BEAM.asItem(),
        DistantMoonsBlocks.SPRUCE_BEAM.asItem()
    );
    this.valueLookupBuilder(DistantMoonsItemTags.SMELTING_FUEL_WOOD_HALF_BLOCK).add(
        DistantMoonsBlocks.ACACIA_POLE.asItem(),
        DistantMoonsBlocks.ACACIA_WALL_SLAB.asItem(),
        DistantMoonsBlocks.BAMBOO_POLE.asItem(),
        DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB.asItem(),
        DistantMoonsBlocks.BAMBOO_WALL_SLAB.asItem(),
        DistantMoonsBlocks.BIRCH_POLE.asItem(),
        DistantMoonsBlocks.BIRCH_WALL_SLAB.asItem(),
        DistantMoonsBlocks.CHERRY_POLE.asItem(),
        DistantMoonsBlocks.CHERRY_WALL_SLAB.asItem(),
        DistantMoonsBlocks.CUT_ACACIA_LOG.asItem(),
        DistantMoonsBlocks.CUT_ACACIA_WOOD.asItem(),
        DistantMoonsBlocks.CUT_BAMBOO_BLOCK.asItem(),
        DistantMoonsBlocks.CUT_BIRCH_LOG.asItem(),
        DistantMoonsBlocks.CUT_BIRCH_WOOD.asItem(),
        DistantMoonsBlocks.CUT_CHERRY_LOG.asItem(),
        DistantMoonsBlocks.CUT_CHERRY_WOOD.asItem(),
        DistantMoonsBlocks.CUT_DARK_OAK_LOG.asItem(),
        DistantMoonsBlocks.CUT_DARK_OAK_WOOD.asItem(),
        DistantMoonsBlocks.CUT_JUNGLE_LOG.asItem(),
        DistantMoonsBlocks.CUT_JUNGLE_WOOD.asItem(),
        DistantMoonsBlocks.CUT_MANGROVE_LOG.asItem(),
        DistantMoonsBlocks.CUT_MANGROVE_WOOD.asItem(),
        DistantMoonsBlocks.CUT_OAK_LOG.asItem(),
        DistantMoonsBlocks.CUT_OAK_WOOD.asItem(),
        DistantMoonsBlocks.CUT_PALE_OAK_LOG.asItem(),
        DistantMoonsBlocks.CUT_PALE_OAK_WOOD.asItem(),
        DistantMoonsBlocks.CUT_SPRUCE_LOG.asItem(),
        DistantMoonsBlocks.CUT_SPRUCE_WOOD.asItem(),
        DistantMoonsBlocks.DARK_OAK_POLE.asItem(),
        DistantMoonsBlocks.DARK_OAK_WALL_SLAB.asItem(),
        DistantMoonsBlocks.JUNGLE_POLE.asItem(),
        DistantMoonsBlocks.JUNGLE_WALL_SLAB.asItem(),
        DistantMoonsBlocks.MANGROVE_POLE.asItem(),
        DistantMoonsBlocks.MANGROVE_WALL_SLAB.asItem(),
        DistantMoonsBlocks.OAK_POLE.asItem(),
        DistantMoonsBlocks.OAK_WALL_SLAB.asItem(),
        DistantMoonsBlocks.PALE_OAK_POLE.asItem(),
        DistantMoonsBlocks.PALE_OAK_WALL_SLAB.asItem(),
        DistantMoonsBlocks.ROPE_LADDER.asItem(),
        DistantMoonsBlocks.SPRUCE_POLE.asItem(),
        DistantMoonsBlocks.SPRUCE_WALL_SLAB.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG.asItem(),
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD.asItem()
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
