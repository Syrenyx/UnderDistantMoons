package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;

import java.util.List;

public abstract class DistantMoonsItemGroups {

  static {

    //BUILDING BLOCKS
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.OAK_WOOD, List.of(
        DistantMoonsBlocks.CUT_OAK_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_OAK_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_OAK_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.OAK_SLAB, List.of(
        DistantMoonsBlocks.OAK_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.OAK_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.OAK_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.SPRUCE_WOOD, List.of(
        DistantMoonsBlocks.CUT_SPRUCE_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_SPRUCE_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_SPRUCE_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.SPRUCE_SLAB, List.of(
        DistantMoonsBlocks.SPRUCE_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.SPRUCE_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.SPRUCE_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.BIRCH_WOOD, List.of(
        DistantMoonsBlocks.CUT_BIRCH_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_BIRCH_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_BIRCH_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.BIRCH_SLAB, List.of(
        DistantMoonsBlocks.BIRCH_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.BIRCH_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.BIRCH_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.JUNGLE_WOOD, List.of(
        DistantMoonsBlocks.CUT_JUNGLE_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_JUNGLE_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_JUNGLE_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.JUNGLE_SLAB, List.of(
        DistantMoonsBlocks.JUNGLE_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.JUNGLE_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.JUNGLE_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.ACACIA_WOOD, List.of(
        DistantMoonsBlocks.CUT_ACACIA_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_ACACIA_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_ACACIA_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.ACACIA_SLAB, List.of(
        DistantMoonsBlocks.ACACIA_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.ACACIA_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.ACACIA_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.DARK_OAK_WOOD, List.of(
        DistantMoonsBlocks.CUT_DARK_OAK_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_DARK_OAK_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_DARK_OAK_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.DARK_OAK_SLAB, List.of(
        DistantMoonsBlocks.DARK_OAK_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.DARK_OAK_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.DARK_OAK_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.MANGROVE_WOOD, List.of(
        DistantMoonsBlocks.CUT_MANGROVE_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_MANGROVE_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_MANGROVE_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.MANGROVE_SLAB, List.of(
        DistantMoonsBlocks.MANGROVE_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.MANGROVE_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.MANGROVE_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.CHERRY_WOOD, List.of(
        DistantMoonsBlocks.CUT_CHERRY_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_CHERRY_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_CHERRY_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.CHERRY_SLAB, List.of(
        DistantMoonsBlocks.CHERRY_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.CHERRY_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.CHERRY_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.PALE_OAK_WOOD, List.of(
        DistantMoonsBlocks.CUT_PALE_OAK_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_PALE_OAK_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_PALE_OAK_WOOD, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.PALE_OAK_SLAB, List.of(
        DistantMoonsBlocks.PALE_OAK_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.PALE_OAK_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.PALE_OAK_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.BAMBOO_BLOCK, List.of(DistantMoonsBlocks.CUT_BAMBOO_BLOCK.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_BAMBOO_BLOCK, List.of(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.BAMBOO_MOSAIC_SLAB, List.of(
        DistantMoonsBlocks.BAMBOO_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.BAMBOO_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.CRIMSON_HYPHAE, List.of(
        DistantMoonsBlocks.CUT_CRIMSON_STEM.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_CRIMSON_HYPHAE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_CRIMSON_HYPHAE, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.CRIMSON_SLAB, List.of(
        DistantMoonsBlocks.CRIMSON_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.CRIMSON_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.CRIMSON_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.WARPED_HYPHAE, List.of(
        DistantMoonsBlocks.CUT_WARPED_STEM.asItem().getDefaultStack(),
        DistantMoonsBlocks.CUT_WARPED_HYPHAE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STRIPPED_WARPED_HYPHAE, List.of(
        DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM.asItem().getDefaultStack(),
        DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.WARPED_SLAB, List.of(
        DistantMoonsBlocks.WARPED_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.WARPED_BEAM.asItem().getDefaultStack(),
        DistantMoonsBlocks.WARPED_POLE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STONE_SLAB, List.of(DistantMoonsBlocks.STONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.COBBLESTONE_SLAB, List.of(DistantMoonsBlocks.COBBLESTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.MOSSY_COBBLESTONE_SLAB, List.of(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.SMOOTH_STONE_SLAB, List.of(DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.STONE_BRICK_SLAB, List.of(DistantMoonsBlocks.STONE_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.MOSSY_STONE_BRICK_SLAB, List.of(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.GRANITE_SLAB, List.of(DistantMoonsBlocks.GRANITE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_GRANITE_SLAB, List.of(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.GRANITE_SLAB, List.of(DistantMoonsBlocks.DIORITE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_DIORITE_SLAB, List.of(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.ANDESITE_SLAB, List.of(DistantMoonsBlocks.ANDESITE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_ANDESITE_SLAB, List.of(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.DEEPSLATE, List.of(DistantMoonsBlocks.CUT_DEEPSLATE.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.COBBLED_DEEPSLATE_SLAB, List.of(DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_DEEPSLATE_SLAB, List.of(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.DEEPSLATE_BRICK_SLAB, List.of(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.DEEPSLATE_TILE_SLAB, List.of(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.TUFF_SLAB, List.of(DistantMoonsBlocks.TUFF_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_TUFF_SLAB, List.of(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.TUFF_BRICK_SLAB, List.of(DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.BRICK_SLAB, List.of(DistantMoonsBlocks.BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.MUD_BRICK_SLAB, List.of(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.RESIN_BRICK_SLAB, List.of(DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.SANDSTONE_SLAB, List.of(DistantMoonsBlocks.SANDSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.SMOOTH_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.CUT_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.RED_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.SMOOTH_RED_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.CUT_RED_SANDSTONE_SLAB, List.of(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.PRISMARINE_SLAB, List.of(DistantMoonsBlocks.PRISMARINE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.PRISMARINE_BRICK_SLAB, List.of(
        DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.PRISMARINE_TILES.asItem().getDefaultStack(),
        DistantMoonsBlocks.PRISMARINE_TILE_STAIRS.asItem().getDefaultStack(),
        DistantMoonsBlocks.PRISMARINE_TILE_SLAB.asItem().getDefaultStack(),
        DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.DARK_PRISMARINE_SLAB, List.of(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.NETHER_BRICK_SLAB, List.of(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.RED_NETHER_BRICK_SLAB, List.of(DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.BASALT, List.of(DistantMoonsBlocks.CUT_BASALT.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_BASALT, List.of(DistantMoonsBlocks.POLISHED_CUT_BASALT.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.BLACKSTONE_SLAB, List.of(DistantMoonsBlocks.BLACKSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_SLAB, List.of(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.POLISHED_BLACKSTONE_BRICK_SLAB, List.of(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.END_STONE_BRICK_SLAB, List.of(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.PURPUR_PILLAR, List.of(DistantMoonsBlocks.CUT_PURPUR_PILLAR.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.PURPUR_SLAB, List.of(
        DistantMoonsBlocks.PURPUR_WALL_SLAB.asItem().getDefaultStack(),
        Items.GLASS.getDefaultStack(),
        Items.TINTED_GLASS.getDefaultStack(),
        Items.BONE_BLOCK.getDefaultStack(),
        DistantMoonsBlocks.CUT_BONE_BLOCK.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.COAL_BLOCK, List.of(
        DistantMoonsBlocks.CHARCOAL_BLOCK.asItem().getDefaultStack(),
        DistantMoonsBlocks.COKE_BLOCK.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.IRON_BARS, List.of(DistantMoonsBlocks.IRON_FENCE.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.IRON_DOOR, List.of(DistantMoonsBlocks.IRON_BAR_DOOR.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, List.of(
        DistantMoonsBlocks.IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_IRON_LADDER.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.IRON_CHAIN, List.of(
        DistantMoonsBlocks.WROUGHT_IRON_BARS.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_FENCE.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK.asItem().getDefaultStack(),
        DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_BARS.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_FENCE.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_DOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_TRAPDOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_CHAIN.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.QUARTZ_SLAB, List.of(DistantMoonsBlocks.QUARTZ_WALL_SLAB.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.QUARTZ_PILLAR, List.of(DistantMoonsBlocks.CUT_QUARTZ_PILLAR.asItem().getDefaultStack()));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.SMOOTH_QUARTZ_SLAB, List.of(DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB.asItem().getDefaultStack()));

    //COLORED BLOCKS

    //NATURAL
    addToGroup(ItemGroups.NATURAL, Items.NETHER_QUARTZ_ORE, List.of(
        DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE.asItem().getDefaultStack(),
        DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.NATURAL, Items.RAW_GOLD_BLOCK, List.of(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK.asItem().getDefaultStack()));

    //FUNCTIONAL
    addToGroup(ItemGroups.FUNCTIONAL, Items.LADDER, List.of(
        DistantMoonsBlocks.IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.ROPE_LADDER.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.FUNCTIONAL, Items.DRAGON_EGG, List.of(
        Items.SPAWNER.getDefaultStack(),
        Items.TRIAL_SPAWNER.getDefaultStack(),
        Items.CREAKING_HEART.getDefaultStack()
    ));

    //REDSTONE

    //TOOLS
    addToGroup(ItemGroups.TOOLS, Items.IRON_HOE, List.of(
        DistantMoonsItems.DEEP_IRON_SHOVEL.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_PICKAXE.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_AXE.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_HOE.getDefaultStack()
    ));

    //COMBAT
    addToGroup(ItemGroups.COMBAT, Items.IRON_SWORD, List.of(DistantMoonsItems.DEEP_IRON_SWORD.getDefaultStack()));
    addToGroup(ItemGroups.COMBAT, Items.IRON_AXE, List.of(
        DistantMoonsItems.DEEP_IRON_AXE.getDefaultStack()
    ));
    addToGroup(ItemGroups.COMBAT, Items.IRON_BOOTS, List.of(
        DistantMoonsItems.DEEP_IRON_HELMET.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_CHESTPLATE.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_LEGGINGS.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_BOOTS.getDefaultStack()
    ));
    addToGroup(ItemGroups.COMBAT, Items.IRON_HORSE_ARMOR, List.of(DistantMoonsItems.DEEP_IRON_HORSE_ARMOR.getDefaultStack()));

    //FOOD AND DRINK
    addToGroup(ItemGroups.FOOD_AND_DRINK, Items.MELON_SLICE, List.of(Items.GLISTERING_MELON_SLICE.getDefaultStack()));
    addToGroup(ItemGroups.FOOD_AND_DRINK, Items.DRIED_KELP, List.of(
        Items.BROWN_MUSHROOM.getDefaultStack(),
        DistantMoonsItems.ROASTED_BROWN_MUSHROOM.getDefaultStack(),
        Items.RED_MUSHROOM.getDefaultStack(),
        Items.CRIMSON_FUNGUS.getDefaultStack(),
        Items.WARPED_FUNGUS.getDefaultStack()
    ));
    addToGroup(ItemGroups.FOOD_AND_DRINK, Items.ROTTEN_FLESH, List.of(DistantMoonsItems.ROTTEN_FISH.getDefaultStack()));

    //INGREDIENTS
    addToGroup(ItemGroups.INGREDIENTS, Items.CHARCOAL, List.of(DistantMoonsItems.COKE.getDefaultStack()));
    addToGroup(ItemGroups.INGREDIENTS, Items.RAW_GOLD, List.of(DistantMoonsItems.RAW_DEEP_IRON.getDefaultStack()));
    addToGroup(ItemGroups.INGREDIENTS, Items.GOLD_NUGGET, List.of(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET.getDefaultStack()));
    addToGroup(ItemGroups.INGREDIENTS, Items.GOLD_INGOT, List.of(
        DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK.getDefaultStack(),
        DistantMoonsItems.REFINED_DEEP_IRON_INGOT.getDefaultStack()
    ));
    addToGroup(ItemGroups.INGREDIENTS, Items.NETHERITE_INGOT, List.of(
        DistantMoonsItems.COPPER_ROD.getDefaultStack(),
        DistantMoonsItems.IRON_ROD.getDefaultStack(),
        DistantMoonsItems.WROUGHT_IRON_ROD.getDefaultStack(),
        DistantMoonsItems.REFINED_DEEP_IRON_ROD.getDefaultStack()
    ));
    addToGroup(ItemGroups.INGREDIENTS, Items.BOOK, List.of(DistantMoonsItems.UNDERWORLD_DUST.getDefaultStack()));

    //SPAWN EGGS
    addToGroup(ItemGroups.SPAWN_EGGS, Items.CREAKING_HEART, List.of(
        Items.SCULK_SHRIEKER.getDefaultStack(),
        Items.INFESTED_STONE.getDefaultStack(),
        Items.INFESTED_COBBLESTONE.getDefaultStack(),
        Items.INFESTED_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_MOSSY_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_CRACKED_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_CHISELED_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_DEEPSLATE.getDefaultStack()
    ));

    //OPERATOR
  }

  private static void addToGroup(
      RegistryKey<ItemGroup> groupKey,
      Item anchor,
      List<ItemStack> itemStacks
  ) {
    ItemGroupEvents
        .modifyEntriesEvent(groupKey)
        .register(group -> group.addAfter(anchor, itemStacks));
  }

  public static void initialize() {}
}
