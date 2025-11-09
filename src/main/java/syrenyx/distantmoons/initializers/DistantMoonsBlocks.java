package syrenyx.distantmoons.initializers;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.block.*;
import syrenyx.distantmoons.references.DistantMoonsBlockSetTypes;

import java.util.function.Function;

public abstract class DistantMoonsBlocks {

  //SIMPLE BLOCKS
  public static final Block ACACIA_BEAM = register(
      "acacia_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_ACACIA_LOG),
      new Item.Settings()
  );
  public static final Block ACACIA_POLE = register(
      "acacia_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS),
      new Item.Settings()
  );
  public static final Block ACACIA_WALL_SLAB = register(
      "acacia_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS),
      new Item.Settings()
  );
  public static final Block ANDESITE_POST = register(
      "andesite_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.ANDESITE),
      new Item.Settings()
  );
  public static final Block ANDESITE_WALL_SLAB = register(
      "andesite_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.ANDESITE),
      new Item.Settings()
  );
  public static final Block BAMBOO_POLE = register(
      "bamboo_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS),
      new Item.Settings()
  );
  public static final Block BAMBOO_MOSAIC_WALL_SLAB = register(
      "bamboo_mosaic_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BAMBOO_MOSAIC),
      new Item.Settings()
  );
  public static final Block BAMBOO_WALL_SLAB = register(
      "bamboo_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS),
      new Item.Settings()
  );
  public static final Block BIRCH_BEAM = register(
      "birch_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_BIRCH_LOG),
      new Item.Settings()
  );
  public static final Block BIRCH_POLE = register(
      "birch_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS),
      new Item.Settings()
  );
  public static final Block BIRCH_WALL_SLAB = register(
      "birch_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS),
      new Item.Settings()
  );
  public static final Block BLACKSTONE_DEEP_IRON_ORE = register(
      "blackstone_deep_iron_ore",
      settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
      AbstractBlock.Settings.copy(Blocks.BLACKSTONE)
          .strength(3.0F, 3.0F),
      new Item.Settings().fireproof()
  );
  public static final Block BLACKSTONE_WALL_SLAB = register(
      "blackstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BLACKSTONE),
      new Item.Settings()
  );
  public static final Block BRICK_WALL_SLAB = register(
      "brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BRICKS),
      new Item.Settings()
  );
  public static final Block CHARCOAL_BLOCK = register(
      "charcoal_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.COAL_BLOCK),
      new Item.Settings()
  );
  public static final Block CHERRY_BEAM = register(
      "cherry_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_CHERRY_LOG),
      new Item.Settings()
  );
  public static final Block CHERRY_POLE = register(
      "cherry_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS),
      new Item.Settings()
  );
  public static final Block CHERRY_WALL_SLAB = register(
      "cherry_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS),
      new Item.Settings()
  );
  public static final Block COBBLED_DEEPSLATE_POST = register(
      "cobbled_deepslate_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.COBBLED_DEEPSLATE),
      new Item.Settings()
  );
  public static final Block COBBLED_DEEPSLATE_WALL_SLAB = register(
      "cobbled_deepslate_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.COBBLED_DEEPSLATE),
      new Item.Settings()
  );
  public static final Block COBBLESTONE_POST = register(
      "cobblestone_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.COBBLESTONE),
      new Item.Settings()
  );
  public static final Block COBBLESTONE_WALL_SLAB = register(
      "cobblestone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.COBBLESTONE),
      new Item.Settings()
  );
  public static final Block COKE_BLOCK = register(
      "coke_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.COAL_BLOCK),
      new Item.Settings()
  );
  public static final Block CRACKED_STONE_BRICK_POST = register(
      "cracked_stone_brick_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block CRACKED_STONE_BRICK_SLAB = register(
      "cracked_stone_brick_slab",
      SlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block CRACKED_STONE_BRICK_STAIRS = register(
      "cracked_stone_brick_stairs",
      SimplifiedStairsBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block CRACKED_STONE_BRICK_WALL = register(
      "cracked_stone_brick_wall",
      WallBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block CRACKED_STONE_BRICK_WALL_SLAB = register(
      "cracked_stone_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRACKED_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block CRIMSON_BEAM = register(
      "crimson_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_STEM),
      new Item.Settings()
  );
  public static final Block CRIMSON_POLE = register(
      "crimson_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS),
      new Item.Settings()
  );
  public static final Block CRIMSON_WALL_SLAB = register(
      "crimson_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS),
      new Item.Settings()
  );
  public static final Block CRUDE_DEEP_IRON_BLOCK = register(
      "crude_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings().fireproof()
  );
  public static final Block CUT_ACACIA_LOG = register(
      "cut_acacia_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.ACACIA_LOG),
      new Item.Settings()
  );
  public static final Block CUT_ACACIA_WOOD = register(
      "cut_acacia_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.ACACIA_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_BAMBOO_BLOCK = register(
      "cut_bamboo_block",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BAMBOO_BLOCK),
      new Item.Settings()
  );
  public static final Block CUT_BASALT = register(
      "cut_basalt",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BASALT),
      new Item.Settings()
  );
  public static final Block CUT_BIRCH_LOG = register(
      "cut_birch_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BIRCH_LOG),
      new Item.Settings()
  );
  public static final Block CUT_BIRCH_WOOD = register(
      "cut_birch_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BIRCH_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_BONE_BLOCK = register(
      "cut_bone_block",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.BONE_BLOCK),
      new Item.Settings()
  );
  public static final Block CUT_CHERRY_LOG = register(
      "cut_cherry_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CHERRY_LOG),
      new Item.Settings()
  );
  public static final Block CUT_CHERRY_WOOD = register(
      "cut_cherry_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CHERRY_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_CRIMSON_HYPHAE = register(
      "cut_crimson_hyphae",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRIMSON_HYPHAE),
      new Item.Settings()
  );
  public static final Block CUT_CRIMSON_STEM = register(
      "cut_crimson_stem",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CRIMSON_STEM),
      new Item.Settings()
  );
  public static final Block CUT_DARK_OAK_LOG = register(
      "cut_dark_oak_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_OAK_LOG),
      new Item.Settings()
  );
  public static final Block CUT_DARK_OAK_WOOD = register(
      "cut_dark_oak_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_OAK_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_DEEPSLATE = register(
      "cut_deepslate",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DEEPSLATE),
      new Item.Settings()
  );
  public static final Block CUT_JUNGLE_LOG = register(
      "cut_jungle_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.JUNGLE_LOG),
      new Item.Settings()
  );
  public static final Block CUT_JUNGLE_WOOD = register(
      "cut_jungle_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.JUNGLE_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_MANGROVE_LOG = register(
      "cut_mangrove_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.MANGROVE_LOG),
      new Item.Settings()
  );
  public static final Block CUT_MANGROVE_WOOD = register(
      "cut_mangrove_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.MANGROVE_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_OAK_LOG = register(
      "cut_oak_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.OAK_LOG),
      new Item.Settings()
  );
  public static final Block CUT_OAK_WOOD = register(
      "cut_oak_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.OAK_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_PALE_OAK_LOG = register(
      "cut_pale_oak_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PALE_OAK_LOG),
      new Item.Settings()
  );
  public static final Block CUT_PALE_OAK_WOOD = register(
      "cut_pale_oak_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PALE_OAK_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_PURPUR_PILLAR = register(
      "cut_purpur_pillar",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PURPUR_PILLAR),
      new Item.Settings()
  );
  public static final Block CUT_QUARTZ_PILLAR = register(
      "cut_quartz_pillar",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.QUARTZ_PILLAR),
      new Item.Settings()
  );
  public static final Block CUT_RED_SANDSTONE_WALL_SLAB = register(
      "cut_red_sandstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CUT_RED_SANDSTONE),
      new Item.Settings()
  );
  public static final Block CUT_SANDSTONE_WALL_SLAB = register(
      "cut_sandstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.CUT_SANDSTONE),
      new Item.Settings()
  );
  public static final Block CUT_SPRUCE_LOG = register(
      "cut_spruce_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SPRUCE_LOG),
      new Item.Settings()
  );
  public static final Block CUT_SPRUCE_WOOD = register(
      "cut_spruce_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD),
      new Item.Settings()
  );
  public static final Block CUT_WARPED_HYPHAE = register(
      "cut_warped_hyphae",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.WARPED_HYPHAE),
      new Item.Settings()
  );
  public static final Block CUT_WARPED_STEM = register(
      "cut_warped_stem",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.WARPED_STEM),
      new Item.Settings()
  );
  public static final Block DARK_OAK_BEAM = register(
      "dark_oak_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_DARK_OAK_LOG),
      new Item.Settings()
  );
  public static final Block DARK_OAK_POLE = register(
      "dark_oak_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS),
      new Item.Settings()
  );
  public static final Block DARK_OAK_WALL_SLAB = register(
      "dark_oak_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS),
      new Item.Settings()
  );
  public static final Block DARK_PRISMARINE_WALL_SLAB = register(
      "dark_prismarine_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_PRISMARINE),
      new Item.Settings()
  );
  public static final Block DEEP_IRON_BAR_DOOR = register(
      "deep_iron_bar_door",
      settings -> new MetalBarDoorBlock(BlockSetType.IRON, settings),
      AbstractBlock.Settings.copy(Blocks.IRON_DOOR)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings().fireproof()
  );
  public static final Block DEEP_IRON_BARS = register(
      "deep_iron_bars",
      PaneBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings().fireproof()
  );
  public static final Block DEEP_IRON_CHAIN = register(
      "deep_iron_chain",
      ChainBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_CHAIN),
      new Item.Settings().fireproof()
  );
  public static final Block DEEP_IRON_DOOR = register(
      "deep_iron_door",
      settings -> new DoorBlock(BlockSetType.IRON, settings),
      AbstractBlock.Settings.copy(Blocks.IRON_DOOR)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings().fireproof()
  );
  public static final Block DEEP_IRON_FENCE = register(
      "deep_iron_fence",
      SpikedFenceBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings().fireproof()
  );
  public static final Block DEEP_IRON_LADDER = register(
      "deep_iron_ladder",
      MetalLadderBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings().fireproof()
  );
  public static final Block DEEP_IRON_TRAPDOOR = register(
      "deep_iron_trapdoor",
      settings -> new TrapdoorBlock(BlockSetType.IRON, settings),
      AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings().fireproof()
  );
  public static final Block DEEPSLATE_BRICK_WALL_SLAB = register(
      "deepslate_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS),
      new Item.Settings()
  );
  public static final Block DEEPSLATE_DEEP_IRON_ORE = register(
      "deepslate_deep_iron_ore",
      settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
      AbstractBlock.Settings.copy(Blocks.DEEPSLATE)
          .strength(4.5F, 3.0F),
      new Item.Settings().fireproof()
  );
  public static final Block DEEPSLATE_TILE_WALL_SLAB = register(
      "deepslate_tile_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DEEPSLATE_TILES),
      new Item.Settings()
  );
  public static final Block DIORITE_POST = register(
      "diorite_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.DIORITE),
      new Item.Settings()
  );
  public static final Block DIORITE_WALL_SLAB = register(
      "diorite_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DIORITE),
      new Item.Settings()
  );
  public static final Block END_STONE_BRICK_WALL_SLAB = register(
      "end_stone_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.END_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block FIXED_DEEP_IRON_LADDER = register(
      "fixed_deep_iron_ladder",
      FixedLadderBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings().fireproof()
  );
  public static final Block FIXED_IRON_LADDER = register(
      "fixed_iron_ladder",
      FixedLadderBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings()
  );
  public static final Block FIXED_WROUGHT_IRON_LADDER = register(
      "fixed_wrought_iron_ladder",
      FixedLadderBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings()
  );
  public static final Block GRANITE_POST = register(
      "granite_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.GRANITE),
      new Item.Settings()
  );
  public static final Block GRANITE_WALL_SLAB = register(
      "granite_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.GRANITE),
      new Item.Settings()
  );
  public static final Block GRAY_PRISMARINE = register(
      "gray_prismarine",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.DARK_PRISMARINE)
          .mapColor(MapColor.TERRACOTTA_BROWN),
      new Item.Settings()
  );
  public static final Block GRAY_PRISMARINE_SLAB = register(
      "gray_prismarine_slab",
      SlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_PRISMARINE_SLAB)
          .mapColor(MapColor.TERRACOTTA_BROWN),
      new Item.Settings()
  );
  public static final Block GRAY_PRISMARINE_STAIRS = register(
      "gray_prismarine_stairs",
      SimplifiedStairsBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_PRISMARINE_STAIRS)
          .mapColor(MapColor.TERRACOTTA_BROWN),
      new Item.Settings()
  );
  public static final Block GRAY_PRISMARINE_WALL_SLAB = register(
      "gray_prismarine_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.DARK_PRISMARINE_SLAB)
          .mapColor(MapColor.TERRACOTTA_BROWN),
      new Item.Settings()
  );
  public static final Block IRON_BAR_DOOR = register(
      "iron_bar_door",
      settings -> new MetalBarDoorBlock(BlockSetType.IRON, settings),
      AbstractBlock.Settings.copy(Blocks.IRON_DOOR),
      new Item.Settings()
  );
  public static final Block IRON_FENCE = register(
      "iron_fence",
      SpikedFenceBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings()
  );
  public static final Block IRON_LADDER = register(
      "iron_ladder",
      MetalLadderBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings()
  );
  public static final Block JUNGLE_BEAM = register(
      "jungle_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_JUNGLE_LOG),
      new Item.Settings()
  );
  public static final Block JUNGLE_POLE = register(
      "jungle_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS),
      new Item.Settings()
  );
  public static final Block JUNGLE_WALL_SLAB = register(
      "jungle_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS),
      new Item.Settings()
  );
  public static final Block MANGROVE_BEAM = register(
      "mangrove_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_MANGROVE_LOG),
      new Item.Settings()
  );
  public static final Block MANGROVE_POLE = register(
      "mangrove_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS),
      new Item.Settings()
  );
  public static final Block MANGROVE_WALL_SLAB = register(
      "mangrove_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS),
      new Item.Settings()
  );
  public static final Block MOSSY_COBBLESTONE_POST = register(
      "mossy_cobblestone_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.MOSSY_COBBLESTONE),
      new Item.Settings()
  );
  public static final Block MOSSY_COBBLESTONE_WALL_SLAB = register(
      "mossy_cobblestone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.MOSSY_COBBLESTONE),
      new Item.Settings()
  );
  public static final Block MOSSY_STONE_BRICK_POST = register(
      "mossy_stone_brick_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.MOSSY_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block MOSSY_STONE_BRICK_WALL_SLAB = register(
      "mossy_stone_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.MOSSY_STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block MUD_BRICK_WALL_SLAB = register(
      "mud_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.MUD_BRICKS),
      new Item.Settings()
  );
  public static final Block NETHER_BRICK_WALL_SLAB = register(
      "nether_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.NETHER_BRICKS),
      new Item.Settings()
  );
  public static final Block NETHERRACK_DEEP_IRON_ORE = register(
      "netherrack_deep_iron_ore",
      settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
      AbstractBlock.Settings.copy(Blocks.NETHERRACK)
          .strength(3.0F, 3.0F),
      new Item.Settings().fireproof()
  );
  public static final Block OAK_BEAM = register(
      "oak_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG),
      new Item.Settings()
  );
  public static final Block OAK_POLE = register(
      "oak_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.OAK_PLANKS),
      new Item.Settings()
  );
  public static final Block OAK_WALL_SLAB = register(
      "oak_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.OAK_PLANKS),
      new Item.Settings()
  );
  public static final Block PALE_OAK_BEAM = register(
      "pale_oak_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_PALE_OAK_LOG),
      new Item.Settings()
  );
  public static final Block PALE_OAK_POLE = register(
      "pale_oak_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.PALE_OAK_PLANKS),
      new Item.Settings()
  );
  public static final Block PALE_OAK_WALL_SLAB = register(
      "pale_oak_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PALE_OAK_PLANKS),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE = register(
      "pale_prismarine",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_BRICK_SLAB = register(
      "pale_prismarine_brick_slab",
      SlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_BRICK_STAIRS = register(
      "pale_prismarine_brick_stairs",
      SimplifiedStairsBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_STAIRS)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_BRICK_WALL_SLAB = register(
      "pale_prismarine_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_BRICKS = register(
      "pale_prismarine_bricks",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICKS)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_SLAB = register(
      "pale_prismarine_slab",
      SlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_SLAB)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_STAIRS = register(
      "pale_prismarine_stairs",
      SimplifiedStairsBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_STAIRS)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_TILE_SLAB = register(
      "pale_prismarine_tile_slab",
      SlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_TILE_STAIRS = register(
      "pale_prismarine_tile_stairs",
      SimplifiedStairsBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_STAIRS)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_TILE_WALL_SLAB = register(
      "pale_prismarine_tile_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_TILES = register(
      "pale_prismarine_tiles",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICKS)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_WALL = register(
      "pale_prismarine_wall",
      WallBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_WALL)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_PRISMARINE_WALL_SLAB = register(
      "pale_prismarine_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_SLAB)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings()
  );
  public static final Block PALE_SEA_LANTERN = register(
      "pale_sea_lantern",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.SEA_LANTERN)
          .luminance(state -> 10),
      new Item.Settings()
  );
  public static final Block POLISHED_ANDESITE_POST = register(
      "polished_andesite_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_ANDESITE),
      new Item.Settings()
  );
  public static final Block POLISHED_ANDESITE_WALL = register(
      "polished_andesite_wall",
      WallBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_ANDESITE),
      new Item.Settings()
  );
  public static final Block POLISHED_ANDESITE_WALL_SLAB = register(
      "polished_andesite_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_ANDESITE),
      new Item.Settings()
  );
  public static final Block POLISHED_BLACKSTONE_BRICK_WALL_SLAB = register(
      "polished_blackstone_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_BLACKSTONE_BRICKS),
      new Item.Settings()
  );
  public static final Block POLISHED_BLACKSTONE_WALL_SLAB = register(
      "polished_blackstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_BLACKSTONE),
      new Item.Settings()
  );
  public static final Block POLISHED_CUT_BASALT = register(
      "polished_cut_basalt",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_BASALT),
      new Item.Settings()
  );
  public static final Block POLISHED_DEEPSLATE_WALL_SLAB = register(
      "polished_deepslate_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_DEEPSLATE),
      new Item.Settings()
  );
  public static final Block POLISHED_DIORITE_POST = register(
      "polished_diorite_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE),
      new Item.Settings()
  );
  public static final Block POLISHED_DIORITE_WALL = register(
      "polished_diorite_wall",
      WallBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE),
      new Item.Settings()
  );
  public static final Block POLISHED_DIORITE_WALL_SLAB = register(
      "polished_diorite_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE),
      new Item.Settings()
  );
  public static final Block POLISHED_GRANITE_POST = register(
      "polished_granite_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_GRANITE),
      new Item.Settings()
  );
  public static final Block POLISHED_GRANITE_WALL = register(
      "polished_granite_wall",
      WallBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_GRANITE),
      new Item.Settings()
  );
  public static final Block POLISHED_GRANITE_WALL_SLAB = register(
      "polished_granite_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_GRANITE),
      new Item.Settings()
  );
  public static final Block POLISHED_TUFF_WALL_SLAB = register(
      "polished_tuff_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF),
      new Item.Settings()
  );
  public static final Block PRISMARINE_BRICK_WALL_SLAB = register(
      "prismarine_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICKS),
      new Item.Settings()
  );
  public static final Block PRISMARINE_TILE_SLAB = register(
      "prismarine_tile_slab",
      SlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB),
      new Item.Settings()
  );
  public static final Block PRISMARINE_TILE_STAIRS = register(
      "prismarine_tile_stairs",
      SimplifiedStairsBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_STAIRS),
      new Item.Settings()
  );
  public static final Block PRISMARINE_TILE_WALL_SLAB = register(
      "prismarine_tile_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB),
      new Item.Settings()
  );
  public static final Block PRISMARINE_TILES = register(
      "prismarine_tiles",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICKS),
      new Item.Settings()
  );
  public static final Block PRISMARINE_WALL_SLAB = register(
      "prismarine_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PRISMARINE),
      new Item.Settings()
  );
  public static final Block PURPUR_WALL_SLAB = register(
      "purpur_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.PURPUR_BLOCK),
      new Item.Settings()
  );
  public static final Block QUARTZ_WALL_SLAB = register(
      "quartz_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK),
      new Item.Settings()
  );
  public static final Block RAW_DEEP_IRON_BLOCK = register(
      "raw_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)
          .mapColor(MapColor.TERRACOTTA_MAGENTA),
      new Item.Settings().fireproof()
  );
  public static final Block RED_NETHER_BRICK_WALL_SLAB = register(
      "red_nether_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.RED_NETHER_BRICKS),
      new Item.Settings()
  );
  public static final Block RED_SANDSTONE_WALL_SLAB = register(
      "red_sandstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.RED_SANDSTONE),
      new Item.Settings()
  );
  public static final Block REFINED_DEEP_IRON_BLOCK = register(
      "refined_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings().fireproof()
  );
  public static final Block RESIN_BRICK_WALL_SLAB = register(
      "resin_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.RESIN_BRICKS),
      new Item.Settings()
  );
  public static final Block ROPE_LADDER = register(
      "rope_ladder",
      RopeLadderBlock::new,
      AbstractBlock.Settings.copy(Blocks.LADDER)
          .sounds(BlockSoundGroup.WOOL)
          .strength(0.5F),
      new Item.Settings()
  );
  public static final Block SANDSTONE_WALL_SLAB = register(
      "sandstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SANDSTONE),
      new Item.Settings()
  );
  public static final Block SMOOTH_QUARTZ_WALL_SLAB = register(
      "smooth_quartz_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SMOOTH_QUARTZ),
      new Item.Settings()
  );
  public static final Block SMOOTH_RED_SANDSTONE_WALL_SLAB = register(
      "smooth_red_sandstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SMOOTH_RED_SANDSTONE),
      new Item.Settings()
  );
  public static final Block SMOOTH_SANDSTONE_WALL_SLAB = register(
      "smooth_sandstone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SMOOTH_SANDSTONE),
      new Item.Settings()
  );
  public static final Block SMOOTH_STONE_POST = register(
      "smooth_stone_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.SMOOTH_STONE),
      new Item.Settings()
  );
  public static final Block SMOOTH_STONE_STAIRS = register(
      "smooth_stone_stairs",
      SimplifiedStairsBlock::new,
      AbstractBlock.Settings.copy(Blocks.SMOOTH_STONE),
      new Item.Settings()
  );
  public static final Block SMOOTH_STONE_WALL = register(
      "smooth_stone_wall",
      WallBlock::new,
      AbstractBlock.Settings.copy(Blocks.SMOOTH_STONE),
      new Item.Settings()
  );
  public static final Block SMOOTH_STONE_WALL_SLAB = register(
      "smooth_stone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SMOOTH_STONE),
      new Item.Settings()
  );
  public static final Block SPRUCE_BEAM = register(
      "spruce_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_SPRUCE_LOG),
      new Item.Settings()
  );
  public static final Block SPRUCE_POLE = register(
      "spruce_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS),
      new Item.Settings()
  );
  public static final Block SPRUCE_WALL_SLAB = register(
      "spruce_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS),
      new Item.Settings()
  );
  public static final Block STONE_POST = register(
      "stone_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STONE),
      new Item.Settings()
  );
  public static final Block STONE_BRICK_POST = register(
      "stone_brick_post",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block STONE_BRICK_WALL_SLAB = register(
      "stone_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STONE_BRICKS),
      new Item.Settings()
  );
  public static final Block STONE_WALL = register(
      "stone_wall",
      WallBlock::new,
      AbstractBlock.Settings.copy(Blocks.STONE),
      new Item.Settings()
  );
  public static final Block STONE_WALL_SLAB = register(
      "stone_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STONE),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_ACACIA_LOG = register(
      "stripped_cut_acacia_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_ACACIA_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_ACACIA_WOOD = register(
      "stripped_cut_acacia_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_ACACIA_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_BAMBOO_BLOCK = register(
      "stripped_cut_bamboo_block",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_BAMBOO_BLOCK),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_BIRCH_LOG = register(
      "stripped_cut_birch_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_BIRCH_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_BIRCH_WOOD = register(
      "stripped_cut_birch_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_BIRCH_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_CHERRY_LOG = register(
      "stripped_cut_cherry_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_CHERRY_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_CHERRY_WOOD = register(
      "stripped_cut_cherry_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_CHERRY_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_CRIMSON_HYPHAE = register(
      "stripped_cut_crimson_hyphae",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_HYPHAE),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_CRIMSON_STEM = register(
      "stripped_cut_crimson_stem",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_STEM),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_DARK_OAK_LOG = register(
      "stripped_cut_dark_oak_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_DARK_OAK_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_DARK_OAK_WOOD = register(
      "stripped_cut_dark_oak_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_DARK_OAK_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_JUNGLE_LOG = register(
      "stripped_cut_jungle_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_JUNGLE_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_JUNGLE_WOOD = register(
      "stripped_cut_jungle_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_JUNGLE_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_MANGROVE_LOG = register(
      "stripped_cut_mangrove_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_MANGROVE_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_MANGROVE_WOOD = register(
      "stripped_cut_mangrove_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_MANGROVE_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_OAK_LOG = register(
      "stripped_cut_oak_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_OAK_WOOD = register(
      "stripped_cut_oak_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_PALE_OAK_LOG = register(
      "stripped_cut_pale_oak_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_PALE_OAK_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_PALE_OAK_WOOD = register(
      "stripped_cut_pale_oak_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_PALE_OAK_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_SPRUCE_LOG = register(
      "stripped_cut_spruce_log",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_SPRUCE_LOG),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_SPRUCE_WOOD = register(
      "stripped_cut_spruce_wood",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_SPRUCE_WOOD),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_WARPED_HYPHAE = register(
      "stripped_cut_warped_hyphae",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_WARPED_HYPHAE),
      new Item.Settings()
  );
  public static final Block STRIPPED_CUT_WARPED_STEM = register(
      "stripped_cut_warped_stem",
      PillarSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.WARPED_STEM),
      new Item.Settings()
  );
  public static final Block TUFF_BRICK_WALL_SLAB = register(
      "tuff_brick_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.TUFF_BRICKS),
      new Item.Settings()
  );
  public static final Block TUFF_WALL_SLAB = register(
      "tuff_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.TUFF),
      new Item.Settings()
  );
  public static final Block WARPED_BEAM = register(
      "warped_beam",
      BeamBlock::new,
      AbstractBlock.Settings.copy(Blocks.STRIPPED_WARPED_STEM),
      new Item.Settings()
  );
  public static final Block WARPED_POLE = register(
      "warped_pole",
      PoleBlock::new,
      AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS),
      new Item.Settings()
  );
  public static final Block WARPED_WALL_SLAB = register(
      "warped_wall_slab",
      WallSlabBlock::new,
      AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS),
      new Item.Settings()
  );
  public static final Block WROUGHT_IRON_BAR_DOOR = register(
      "wrought_iron_bar_door",
      settings -> new MetalBarDoorBlock(DistantMoonsBlockSetTypes.WROUGHT_IRON, settings),
      AbstractBlock.Settings.copy(Blocks.IRON_DOOR)
          .mapColor(MapColor.TERRACOTTA_CYAN),
      new Item.Settings()
  );
  public static final Block WROUGHT_IRON_BARS = register(
      "wrought_iron_bars",
      PaneBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings()
  );
  public static final Block WROUGHT_IRON_FENCE = register(
      "wrought_iron_fence",
      SpikedFenceBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings()
  );
  public static final Block WROUGHT_IRON_LADDER = register(
      "wrought_iron_ladder",
      MetalLadderBlock::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BARS),
      new Item.Settings()
  );

  private static Block register(String id, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
    RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, UnderDistantMoons.identifierOf(id));
    return Registry.register(Registries.BLOCK, key, blockFactory.apply(settings.registryKey(key)));
  }

  private static Block register(String id, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings blockSettings, Item.Settings itemSettings) {
    Block block = register(id, blockFactory, blockSettings);
    RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, UnderDistantMoons.identifierOf(id));
    Registry.register(Registries.ITEM, key, new BlockItem(block, itemSettings.registryKey(key).useBlockPrefixedTranslationKey()));
    return block;
  }

  public static void initialize() {}
}
