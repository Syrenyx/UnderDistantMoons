package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import syrenyx.distantmoons.utility.ColorUtil;

import java.util.List;
import java.util.Map;

public abstract class DistantMoonsItemGroups {

  static {

    //BUILDING BLOCKS
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.OAK_WOOD, List.of(
        DistantMoonsBlocks.CUT_OAK_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_OAK_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_OAK_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.OAK_SLAB, List.of(
        DistantMoonsBlocks.OAK_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.OAK_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.OAK_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.SPRUCE_WOOD, List.of(
        DistantMoonsBlocks.CUT_SPRUCE_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_SPRUCE_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_SPRUCE_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.SPRUCE_SLAB, List.of(
        DistantMoonsBlocks.SPRUCE_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.SPRUCE_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.SPRUCE_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.BIRCH_WOOD, List.of(
        DistantMoonsBlocks.CUT_BIRCH_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_BIRCH_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_BIRCH_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.BIRCH_SLAB, List.of(
        DistantMoonsBlocks.BIRCH_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.BIRCH_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.BIRCH_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.JUNGLE_WOOD, List.of(
        DistantMoonsBlocks.CUT_JUNGLE_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_JUNGLE_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_JUNGLE_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.JUNGLE_SLAB, List.of(
        DistantMoonsBlocks.JUNGLE_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.JUNGLE_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.JUNGLE_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.ACACIA_WOOD, List.of(
        DistantMoonsBlocks.CUT_ACACIA_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_ACACIA_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_ACACIA_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.ACACIA_SLAB, List.of(
        DistantMoonsBlocks.ACACIA_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.ACACIA_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.ACACIA_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.DARK_OAK_WOOD, List.of(
        DistantMoonsBlocks.CUT_DARK_OAK_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_DARK_OAK_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_DARK_OAK_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.DARK_OAK_SLAB, List.of(
        DistantMoonsBlocks.DARK_OAK_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DARK_OAK_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DARK_OAK_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.MANGROVE_WOOD, List.of(
        DistantMoonsBlocks.CUT_MANGROVE_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_MANGROVE_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_MANGROVE_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.MANGROVE_SLAB, List.of(
        DistantMoonsBlocks.MANGROVE_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.MANGROVE_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.MANGROVE_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.CHERRY_WOOD, List.of(
        DistantMoonsBlocks.CUT_CHERRY_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_CHERRY_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_CHERRY_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.CHERRY_SLAB, List.of(
        DistantMoonsBlocks.CHERRY_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CHERRY_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CHERRY_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.PALE_OAK_WOOD, List.of(
        DistantMoonsBlocks.CUT_PALE_OAK_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_PALE_OAK_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_PALE_OAK_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.PALE_OAK_SLAB, List.of(
        DistantMoonsBlocks.PALE_OAK_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_OAK_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_OAK_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.BAMBOO_BLOCK, List.of(DistantMoonsBlocks.CUT_BAMBOO_BLOCK.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_BAMBOO_BLOCK, List.of(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.BAMBOO_MOSAIC_SLAB, List.of(
        DistantMoonsBlocks.BAMBOO_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.BAMBOO_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.CRIMSON_HYPHAE, List.of(
        DistantMoonsBlocks.CUT_CRIMSON_STEM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_CRIMSON_HYPHAE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_CRIMSON_HYPHAE, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.CRIMSON_SLAB, List.of(
        DistantMoonsBlocks.CRIMSON_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CRIMSON_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CRIMSON_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.WARPED_HYPHAE, List.of(
        DistantMoonsBlocks.CUT_WARPED_STEM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CUT_WARPED_HYPHAE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STRIPPED_WARPED_HYPHAE, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.WARPED_SLAB, List.of(
        DistantMoonsBlocks.WARPED_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WARPED_BEAM.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WARPED_POLE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STONE_SLAB, List.of(DistantMoonsBlocks.STONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.COBBLESTONE_SLAB, List.of(DistantMoonsBlocks.COBBLESTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.MOSSY_COBBLESTONE_SLAB, List.of(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_STONE_SLAB, List.of(DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.STONE_BRICK_SLAB, List.of(DistantMoonsBlocks.STONE_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.MOSSY_STONE_BRICK_SLAB, List.of(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.GRANITE_SLAB, List.of(DistantMoonsBlocks.GRANITE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_GRANITE_SLAB, List.of(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.DIORITE_SLAB, List.of(DistantMoonsBlocks.DIORITE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_DIORITE_SLAB, List.of(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.ANDESITE_SLAB, List.of(DistantMoonsBlocks.ANDESITE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_ANDESITE_SLAB, List.of(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.DEEPSLATE, List.of(DistantMoonsBlocks.CUT_DEEPSLATE.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.COBBLED_DEEPSLATE_SLAB, List.of(DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_DEEPSLATE_SLAB, List.of(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.DEEPSLATE_BRICK_SLAB, List.of(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.DEEPSLATE_TILE_SLAB, List.of(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.TUFF_SLAB, List.of(DistantMoonsBlocks.TUFF_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_TUFF_SLAB, List.of(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.TUFF_BRICK_SLAB, List.of(DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.BRICK_SLAB, List.of(DistantMoonsBlocks.BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.MUD_BRICK_SLAB, List.of(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.RESIN_BRICK_SLAB, List.of(DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.SANDSTONE_SLAB, List.of(DistantMoonsBlocks.SANDSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.CUT_STANDSTONE_SLAB, List.of(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.RED_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_RED_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.CUT_RED_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.PRISMARINE_SLAB, List.of(DistantMoonsBlocks.PRISMARINE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.PRISMARINE_BRICK_SLAB, List.of(
        DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PRISMARINE_TILES.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PRISMARINE_TILE_STAIRS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PRISMARINE_TILE_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.DARK_PRISMARINE_SLAB, List.of(
        DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_SEA_LANTERN.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_STAIRS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_WALL.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_BRICKS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_TILES.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.GRAY_PRISMARINE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.GRAY_PRISMARINE_SLAB.asItem().getDefaultInstance(),
        DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.NETHER_BRICK_SLAB, List.of(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.RED_NETHER_BRICK_SLAB, List.of(DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.BASALT, List.of(DistantMoonsBlocks.CUT_BASALT.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_BASALT, List.of(DistantMoonsBlocks.POLISHED_CUT_BASALT.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.BLACKSTONE_SLAB, List.of(DistantMoonsBlocks.BLACKSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_SLAB, List.of(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_BRICK_SLAB, List.of(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.END_STONE_BRICK_SLAB, List.of(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.PURPUR_PILLAR, List.of(DistantMoonsBlocks.CUT_PURPUR_PILLAR.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.PURPUR_SLAB, List.of(
        DistantMoonsBlocks.PURPUR_WALL_SLAB.asItem().getDefaultInstance(),
        Items.GLASS.getDefaultInstance(),
        Items.TINTED_GLASS.getDefaultInstance(),
        Items.BONE_BLOCK.getDefaultInstance(),
        DistantMoonsBlocks.CUT_BONE_BLOCK.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.COAL_BLOCK, List.of(
        DistantMoonsBlocks.CHARCOAL_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.COKE_BLOCK.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.IRON_BARS, List.of(DistantMoonsBlocks.IRON_FENCE.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.IRON_DOOR, List.of(DistantMoonsBlocks.IRON_BAR_DOOR.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, List.of(
        DistantMoonsBlocks.IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.FIXED_IRON_LADDER.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.IRON_CHAIN, List.of(
        DistantMoonsBlocks.EXPOSED_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WEATHERED_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.RUSTED_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WAXED_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WROUGHT_IRON_BARS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WROUGHT_IRON_FENCE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_BARS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_FENCE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_DOOR.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_TRAPDOOR.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_CHAIN.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.QUARTZ_SLAB, List.of(DistantMoonsBlocks.QUARTZ_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.QUARTZ_PILLAR, List.of(DistantMoonsBlocks.CUT_QUARTZ_PILLAR.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.SMOOTH_QUARTZ_SLAB, List.of(DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.EXPOSED_CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.WEATHERED_CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.OXIDIZED_CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_EXPOSED_CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_WEATHERED_CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, List.of(DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB.asItem().getDefaultInstance()));

    //COLORED BLOCKS
    addToGroup(CreativeModeTabs.COLORED_BLOCKS, Items.PINK_BED, DistantMoonsBlocks.DYED_PILLOWS);

    //NATURAL
    addToGroup(CreativeModeTabs.NATURAL_BLOCKS, Items.NETHER_QUARTZ_ORE, List.of(
        DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.NATURAL_BLOCKS, Items.RAW_GOLD_BLOCK, List.of(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK.asItem().getDefaultInstance()));

    //FUNCTIONAL
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SOUL_LANTERN, List.of(DistantMoonsBlocks.UNDERWORLD_LANTERN.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.IRON_CHAIN, List.of(DistantMoonsBlocks.DEEP_IRON_CHAIN.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SEA_LANTERN, List.of(DistantMoonsBlocks.PALE_SEA_LANTERN.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.BLAST_FURNACE, List.of(DistantMoonsBlocks.BLAST_FURNACE.asItem().getDefaultInstance()));
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.LADDER, List.of(
        DistantMoonsBlocks.IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.DEEP_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.FIXED_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem().getDefaultInstance(),
        DistantMoonsBlocks.ROPE_LADDER.asItem().getDefaultInstance(),
        DistantMoonsItems.COILED_ROPE_LADDER.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.DRAGON_EGG, List.of(
        Items.SPAWNER.getDefaultInstance(),
        Items.TRIAL_SPAWNER.getDefaultInstance(),
        Items.CREAKING_HEART.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.INFESTED_COBBLESTONE, List.of(
        DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_SMOOTH_STONE.asItem().getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.INFESTED_DEEPSLATE, List.of(
        DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE .asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES.asItem().getDefaultInstance()
    ));

    //REDSTONE

    //TOOLS
    addToGroup(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.IRON_HOE, List.of(
        DistantMoonsItems.DEEP_IRON_SHOVEL.getDefaultInstance(),
        DistantMoonsItems.DEEP_IRON_PICKAXE.getDefaultInstance(),
        DistantMoonsItems.DEEP_IRON_AXE.getDefaultInstance(),
        DistantMoonsItems.DEEP_IRON_HOE.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.TNT_MINECART, List.of(DistantMoonsItems.COILED_ROPE_LADDER.getDefaultInstance()));

    //COMBAT
    addToGroup(CreativeModeTabs.COMBAT, Items.IRON_SWORD, List.of(DistantMoonsItems.DEEP_IRON_SWORD.getDefaultInstance()));
    addToGroup(CreativeModeTabs.COMBAT, Items.IRON_AXE, List.of(
        DistantMoonsItems.DEEP_IRON_AXE.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.COMBAT, Items.IRON_BOOTS, List.of(
        DistantMoonsItems.DEEP_IRON_HELMET.getDefaultInstance(),
        DistantMoonsItems.DEEP_IRON_CHESTPLATE.getDefaultInstance(),
        DistantMoonsItems.DEEP_IRON_LEGGINGS.getDefaultInstance(),
        DistantMoonsItems.DEEP_IRON_BOOTS.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.COMBAT, Items.IRON_HORSE_ARMOR, List.of(DistantMoonsItems.DEEP_IRON_HORSE_ARMOR.getDefaultInstance()));

    //FOOD AND DRINK
    addToGroup(CreativeModeTabs.FOOD_AND_DRINKS, Items.MELON_SLICE, List.of(Items.GLISTERING_MELON_SLICE.getDefaultInstance()));
    addToGroup(CreativeModeTabs.FOOD_AND_DRINKS, Items.DRIED_KELP, List.of(
        Items.BROWN_MUSHROOM.getDefaultInstance(),
        DistantMoonsItems.ROASTED_BROWN_MUSHROOM.getDefaultInstance(),
        Items.RED_MUSHROOM.getDefaultInstance(),
        Items.CRIMSON_FUNGUS.getDefaultInstance(),
        Items.WARPED_FUNGUS.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.FOOD_AND_DRINKS, Items.ROTTEN_FLESH, List.of(DistantMoonsItems.ROTTEN_FISH.getDefaultInstance()));

    //INGREDIENTS
    addToGroup(CreativeModeTabs.INGREDIENTS, Items.CHARCOAL, List.of(DistantMoonsItems.COKE.getDefaultInstance()));
    addToGroup(CreativeModeTabs.INGREDIENTS, Items.RAW_GOLD, List.of(DistantMoonsItems.RAW_DEEP_IRON.getDefaultInstance()));
    addToGroup(CreativeModeTabs.INGREDIENTS, Items.GOLD_NUGGET, List.of(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET.getDefaultInstance()));
    addToGroup(CreativeModeTabs.INGREDIENTS, Items.GOLD_INGOT, List.of(
        DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK.getDefaultInstance(),
        DistantMoonsItems.REFINED_DEEP_IRON_INGOT.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.INGREDIENTS, Items.NETHERITE_INGOT, List.of(
        DistantMoonsItems.COPPER_ROD.getDefaultInstance(),
        DistantMoonsItems.IRON_ROD.getDefaultInstance(),
        DistantMoonsItems.WROUGHT_IRON_ROD.getDefaultInstance(),
        DistantMoonsItems.REFINED_DEEP_IRON_ROD.getDefaultInstance()
    ));
    addToGroup(CreativeModeTabs.INGREDIENTS, Items.PRISMARINE_SHARD, List.of(DistantMoonsItems.PALE_PRISMARINE_SHARD.getDefaultInstance()));
    addToGroup(CreativeModeTabs.INGREDIENTS, Items.BOOK, List.of(DistantMoonsItems.UNDERWORLD_DUST.getDefaultInstance()));

    //SPAWN EGGS
    addToGroup(CreativeModeTabs.SPAWN_EGGS, Items.CREAKING_HEART, List.of(
        Items.SCULK_SHRIEKER.getDefaultInstance(),
        Items.INFESTED_STONE.getDefaultInstance(),
        Items.INFESTED_COBBLESTONE.getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_SMOOTH_STONE.asItem().getDefaultInstance(),
        Items.INFESTED_STONE_BRICKS.getDefaultInstance(),
        Items.INFESTED_MOSSY_STONE_BRICKS.getDefaultInstance(),
        Items.INFESTED_CRACKED_STONE_BRICKS.getDefaultInstance(),
        Items.INFESTED_CHISELED_STONE_BRICKS.getDefaultInstance(),
        Items.INFESTED_DEEPSLATE.getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE .asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES.asItem().getDefaultInstance(),
        DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES.asItem().getDefaultInstance()
    ));

    //OPERATOR
  }

  private static void addToGroup(ResourceKey<CreativeModeTab> groupKey, Item anchor, List<ItemStack> itemStacks) {
    ItemGroupEvents.modifyEntriesEvent(groupKey).register(group -> group.addAfter(anchor, itemStacks));
  }

  private static void addToGroup(ResourceKey<CreativeModeTab> groupKey, Item anchor, Map<DyeColor, Block> dyedBlocks) {
    ColorUtil.SORTED_DYE_COLORS.forEach(color -> ItemGroupEvents.modifyEntriesEvent(groupKey).register(group -> group.addAfter(anchor, dyedBlocks.get(color))));
  }

  public static void initialize() {}
}
