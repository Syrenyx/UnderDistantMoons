package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import syrenyx.distantmoons.utility.ColorUtil;

import java.util.List;
import java.util.Map;

public abstract class DistantMoonsItemGroups {

  static {

    //BUILDING BLOCKS
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.OAK_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_OAK_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_OAK_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_OAK_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.OAK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.OAK_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.OAK_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.OAK_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.OAK_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SPRUCE_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_SPRUCE_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_SPRUCE_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_SPRUCE_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SPRUCE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.SPRUCE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SPRUCE_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SPRUCE_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SPRUCE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.BIRCH_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_BIRCH_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_BIRCH_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_BIRCH_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.BIRCH_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.BIRCH_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.BIRCH_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.BIRCH_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.BIRCH_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.JUNGLE_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_JUNGLE_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_JUNGLE_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_JUNGLE_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.JUNGLE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.JUNGLE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.JUNGLE_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.JUNGLE_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.JUNGLE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.ACACIA_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_ACACIA_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_ACACIA_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_ACACIA_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.ACACIA_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.ACACIA_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.ACACIA_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.ACACIA_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.ACACIA_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DARK_OAK_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_DARK_OAK_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_DARK_OAK_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_DARK_OAK_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DARK_OAK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.DARK_OAK_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DARK_OAK_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DARK_OAK_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DARK_OAK_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.MANGROVE_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_MANGROVE_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_MANGROVE_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_MANGROVE_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.MANGROVE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.MANGROVE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.MANGROVE_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.MANGROVE_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.MANGROVE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.CHERRY_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_CHERRY_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_CHERRY_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_CHERRY_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.CHERRY_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CHERRY_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CHERRY_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CHERRY_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CHERRY_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.PALE_OAK_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_PALE_OAK_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_PALE_OAK_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_PALE_OAK_WOOD, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.PALE_OAK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.PALE_OAK_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_OAK_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_OAK_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_OAK_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.BAMBOO_BLOCK, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_BAMBOO_BLOCK.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_BAMBOO_BLOCK, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.BAMBOO_MOSAIC_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.BAMBOO_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.BAMBOO_POLE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.CRIMSON_HYPHAE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_CRIMSON_STEM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_CRIMSON_HYPHAE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.CRIMSON_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CRIMSON_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CRIMSON_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CRIMSON_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CRIMSON_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.WARPED_HYPHAE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_WARPED_STEM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_WARPED_HYPHAE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_WARPED_HYPHAE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.WARPED_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.WARPED_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WARPED_BEAM.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WARPED_POLE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WARPED_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STONE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.STONE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.COBBLESTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.COBBLESTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.MOSSY_COBBLESTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_STONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.STONE_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.STONE_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.MOSSY_STONE_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.GRANITE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.GRANITE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.GRANITE_WALL, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.GRANITE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_GRANITE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DIORITE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.DIORITE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DIORITE_WALL, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.DIORITE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_DIORITE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.ANDESITE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.ANDESITE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.ANDESITE_WALL, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.ANDESITE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_ANDESITE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DEEPSLATE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_DEEPSLATE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.COBBLED_DEEPSLATE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_DEEPSLATE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DEEPSLATE_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DEEPSLATE_TILE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.REINFORCED_DEEPSLATE, List.of(
        new ItemStackTemplate(Items.CALCITE),
        new ItemStackTemplate(DistantMoonsBlocks.CALCITE_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CALCITE_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CALCITE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.TUFF_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.TUFF_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.TUFF_WALL, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.TUFF_BALUSTRADE.asItem()),
        new ItemStackTemplate(Items.DRIPSTONE_BLOCK)
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_TUFF_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.TUFF_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.MUD_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.RESIN_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SANDSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.SANDSTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_SANDSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_SANDSTONE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.CUT_STANDSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.RED_SANDSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_RED_SANDSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_BALUSTRADE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.CUT_RED_SANDSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.PRISMARINE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.PRISMARINE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.PRISMARINE_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PRISMARINE_TILES.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PRISMARINE_TILE_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PRISMARINE_TILE_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DARK_PRISMARINE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_SEA_LANTERN.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_WALL.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_TILES.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.GRAY_PRISMARINE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.GRAY_PRISMARINE_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.NETHERRACK, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.NETHERRACK_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.NETHERRACK_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.NETHERRACK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.NETHER_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.RED_NETHER_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.BASALT, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_BASALT.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_BASALT, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_BASALT_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_BASALT_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_BASALT_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_BASALT, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_CUT_BASALT.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.BLACKSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.BLACKSTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_BRICK_WALL, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.FIRE_BRICKS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIRE_BRICK_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIRE_BRICK_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIRE_BRICK_WALL_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIRE_BRICK_WALL.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.END_STONE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.END_STONE_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.END_STONE_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.END_STONE_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.END_STONE_BRICK_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.PURPUR_PILLAR, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_PURPUR_PILLAR.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.PURPUR_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.PURPUR_WALL_SLAB.asItem()),
        new ItemStackTemplate(Items.GLASS),
        new ItemStackTemplate(Items.TINTED_GLASS),
        new ItemStackTemplate(Items.BONE_BLOCK),
        new ItemStackTemplate(DistantMoonsBlocks.CUT_BONE_BLOCK.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.COAL_BLOCK, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CHARCOAL_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.COKE_BLOCK.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.IRON_BARS, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.IRON_FENCE.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.IRON_DOOR, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.IRON_BAR_DOOR.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIXED_IRON_LADDER.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.IRON_CHAIN, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.EXPOSED_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WEATHERED_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.RUSTED_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WROUGHT_IRON_BARS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WROUGHT_IRON_FENCE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_BARS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_FENCE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_DOOR.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_BAR_DOOR.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_CHAIN.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.DIAMOND_BLOCK, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.RUBY_BLOCK.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.SAPPHIRE_BLOCK.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.QUARTZ_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.QUARTZ_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.QUARTZ_BRICKS, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.QUARTZ_BRICK_STAIRS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.QUARTZ_BRICK_SLAB.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.QUARTZ_BRICK_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.QUARTZ_PILLAR, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_QUARTZ_PILLAR.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_QUARTZ_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.CUT_COPPER_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.EXPOSED_CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.WEATHERED_CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.OXIDIZED_CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_EXPOSED_CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_WEATHERED_CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB.asItem())
    ));

    //COLORED BLOCKS
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_BED, DistantMoonsBlocks.DYED_PILLOWS);
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_TERRACOTTA, DistantMoonsBlocks.DYED_TERRACOTTA_WALL_SLABS);
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_TERRACOTTA, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.TERRACOTTA_WALL_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_TERRACOTTA, DistantMoonsBlocks.DYED_TERRACOTTA_SLABS);
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_TERRACOTTA, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.TERRACOTTA_SLAB.asItem())
    ));
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_TERRACOTTA, DistantMoonsBlocks.DYED_TERRACOTTA_STAIRS);
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_TERRACOTTA, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.TERRACOTTA_STAIRS.asItem())
    ));
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_CONCRETE, DistantMoonsBlocks.DYED_CONCRETE_WALL_SLABS);
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_CONCRETE, DistantMoonsBlocks.DYED_CONCRETE_SLABS);
    addToTab(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_CONCRETE, DistantMoonsBlocks.DYED_CONCRETE_STAIRS);

    //NATURAL
    addToTab(CreativeModeTabs.NATURAL_BLOCKS, Items.NETHER_QUARTZ_ORE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE.asItem())
    ));
    addToTab(CreativeModeTabs.NATURAL_BLOCKS, Items.RAW_GOLD_BLOCK, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK.asItem())
    ));

    //FUNCTIONAL
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SOUL_LANTERN, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.UNDERWORLD_LANTERN.asItem())
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.IRON_CHAIN, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_CHAIN.asItem())
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SEA_LANTERN, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.PALE_SEA_LANTERN.asItem())
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLAST_FURNACE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.BLAST_FURNACE.asItem())
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.LADDER, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.DEEP_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIXED_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.ROPE_LADDER.asItem()),
        new ItemStackTemplate(DistantMoonsItems.COILED_ROPE_LADDER)
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.PINK_BED, DistantMoonsBlocks.DYED_PILLOWS);
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.DRAGON_EGG, List.of(
        new ItemStackTemplate(Items.SPAWNER),
        new ItemStackTemplate(Items.TRIAL_SPAWNER),
        new ItemStackTemplate(Items.CREAKING_HEART)
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.END_PORTAL_FRAME, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.UNDERWORLD_CONFLUX.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.UNDERWORLD_ANCHOR.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.BROKEN_UNDERWORLD_ANCHOR.asItem())
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.ENDER_EYE, List.of(
        new ItemStackTemplate(DistantMoonsItems.ABYSS_KEYSTONE),
        new ItemStackTemplate(DistantMoonsItems.NETHER_KEYSTONE)
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.INFESTED_COBBLESTONE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_SMOOTH_STONE.asItem())
    ));
    addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.INFESTED_DEEPSLATE, List.of(
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES.asItem())
    ));

    //REDSTONE

    //TOOLS
    addToTab(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.IRON_HOE, List.of(
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_SHOVEL),
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_PICKAXE),
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_AXE),
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_HOE)
    ));
    addToTab(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.RECOVERY_COMPASS, List.of(
        new ItemStackTemplate(DistantMoonsItems.UNDERWORLD_COMPASS)
    ));
    addToTab(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.ENDER_EYE, List.of(
        new ItemStackTemplate(DistantMoonsItems.ABYSS_KEYSTONE),
        new ItemStackTemplate(DistantMoonsItems.NETHER_KEYSTONE)
    ));
    addToTab(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.TNT_MINECART, List.of(
        new ItemStackTemplate(DistantMoonsItems.COILED_ROPE_LADDER)
    ));

    //COMBAT
    addToTab(CreativeModeTabs.COMBAT, Items.IRON_SWORD, List.of(
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_SWORD)
    ));
    addToTab(CreativeModeTabs.COMBAT, Items.IRON_SPEAR, List.of(
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_SPEAR)
    ));
    addToTab(CreativeModeTabs.COMBAT, Items.IRON_AXE, List.of(
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_AXE)
    ));
    addToTab(CreativeModeTabs.COMBAT, Items.IRON_BOOTS, List.of(
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_HELMET),
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_CHESTPLATE),
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_LEGGINGS),
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_BOOTS)
    ));
    addToTab(CreativeModeTabs.COMBAT, Items.IRON_HORSE_ARMOR, List.of(
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_HORSE_ARMOR)
    ));
    addToTab(CreativeModeTabs.COMBAT, Items.IRON_NAUTILUS_ARMOR, List.of(
        new ItemStackTemplate(DistantMoonsItems.DEEP_IRON_NAUTILUS_ARMOR)
    ));

    //FOOD AND DRINK
    addToTab(CreativeModeTabs.FOOD_AND_DRINKS, Items.MELON_SLICE, List.of(
        new ItemStackTemplate(Items.GLISTERING_MELON_SLICE)
    ));
    addToTab(CreativeModeTabs.FOOD_AND_DRINKS, Items.DRIED_KELP, List.of(
        new ItemStackTemplate(Items.BROWN_MUSHROOM),
        new ItemStackTemplate(DistantMoonsItems.ROASTED_BROWN_MUSHROOM),
        new ItemStackTemplate(Items.RED_MUSHROOM),
        new ItemStackTemplate(Items.CRIMSON_FUNGUS),
        new ItemStackTemplate(Items.WARPED_FUNGUS)
    ));
    addToTab(CreativeModeTabs.FOOD_AND_DRINKS, Items.ROTTEN_FLESH, List.of(
        new ItemStackTemplate(DistantMoonsItems.ROTTEN_FISH)
    ));

    //INGREDIENTS
    addToTab(CreativeModeTabs.INGREDIENTS, Items.CHARCOAL, List.of(
        new ItemStackTemplate(DistantMoonsItems.COKE)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.RAW_IRON, List.of(
        new ItemStackTemplate(DistantMoonsItems.RAW_DEEP_IRON)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.DIAMOND, List.of(
        new ItemStackTemplate(DistantMoonsItems.RUBY),
        new ItemStackTemplate(DistantMoonsItems.SAPPHIRE)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.IRON_NUGGET, List.of(
        new ItemStackTemplate(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.IRON_INGOT, List.of(
        new ItemStackTemplate(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK),
        new ItemStackTemplate(DistantMoonsItems.REFINED_DEEP_IRON_INGOT)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.NETHERITE_INGOT, List.of(
        new ItemStackTemplate(DistantMoonsItems.COPPER_ROD),
        new ItemStackTemplate(DistantMoonsItems.IRON_ROD),
        new ItemStackTemplate(DistantMoonsItems.WROUGHT_IRON_ROD),
        new ItemStackTemplate(DistantMoonsItems.REFINED_DEEP_IRON_ROD)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.PRISMARINE_SHARD, List.of(
        new ItemStackTemplate(DistantMoonsItems.PALE_PRISMARINE_SHARD)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.NETHER_BRICK, List.of(
        new ItemStackTemplate(DistantMoonsItems.FIRE_BRICK)
    ));
    addToTab(CreativeModeTabs.INGREDIENTS, Items.BOOK, List.of(
        new ItemStackTemplate(DistantMoonsItems.UNDERWORLD_DUST),
        new ItemStackTemplate(DistantMoonsItems.UNDERWORLD_PEARL)
    ));

    //SPAWN EGGS
    addToTab(CreativeModeTabs.SPAWN_EGGS, Items.CREAKING_HEART, List.of(
        new ItemStackTemplate(Items.SCULK_SHRIEKER),
        new ItemStackTemplate(Items.INFESTED_STONE),
        new ItemStackTemplate(Items.INFESTED_COBBLESTONE),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_SMOOTH_STONE.asItem()),
        new ItemStackTemplate(Items.INFESTED_STONE_BRICKS),
        new ItemStackTemplate(Items.INFESTED_MOSSY_STONE_BRICKS),
        new ItemStackTemplate(Items.INFESTED_CRACKED_STONE_BRICKS),
        new ItemStackTemplate(Items.INFESTED_CHISELED_STONE_BRICKS),
        new ItemStackTemplate(Items.INFESTED_DEEPSLATE),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES.asItem()),
        new ItemStackTemplate(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES.asItem())
    ));

    //OPERATOR
  }

  private static void addToTab(ResourceKey<CreativeModeTab> groupKey, Item anchor, List<ItemStackTemplate> itemStacks) {
    CreativeModeTabEvents.modifyOutputEvent(groupKey).register(creativeTab ->
        creativeTab.insertAfter(anchor, itemStacks.stream().map(ItemStackTemplate::create).toList())
    );
  }

  private static void addToTab(ResourceKey<CreativeModeTab> groupKey, Item anchor, Map<DyeColor, Block> dyedBlocks) {
    ColorUtil.SORTED_DYE_COLORS.reversed().forEach(color -> CreativeModeTabEvents.modifyOutputEvent(groupKey).register(group -> group.insertAfter(anchor, dyedBlocks.get(color))));
  }

  public static void initialize() {}
}
