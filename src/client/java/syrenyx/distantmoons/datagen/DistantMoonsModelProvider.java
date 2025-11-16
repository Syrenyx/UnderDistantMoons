package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.block.*;
import syrenyx.distantmoons.content.block.block_state_enums.*;
import syrenyx.distantmoons.datagen.utility.ModelProviderUtil;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;
import syrenyx.distantmoons.references.DistantMoonsTextureKeys;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistantMoonsModelProvider extends FabricModelProvider {

  private static final ModelVariantOperator NO_OP = BlockStateModelGenerator.NO_OP;
  private static final ModelVariantOperator UV_LOCK = BlockStateModelGenerator.UV_LOCK;
  private static final ModelVariantOperator ROTATE_X_90 = BlockStateModelGenerator.ROTATE_X_90;
  private static final ModelVariantOperator ROTATE_X_180 = BlockStateModelGenerator.ROTATE_X_180;
  private static final ModelVariantOperator ROTATE_X_270 = BlockStateModelGenerator.ROTATE_X_270;
  private static final ModelVariantOperator ROTATE_Y_90 = BlockStateModelGenerator.ROTATE_Y_90;
  private static final ModelVariantOperator ROTATE_Y_180 = BlockStateModelGenerator.ROTATE_Y_180;
  private static final ModelVariantOperator ROTATE_Y_270 = BlockStateModelGenerator.ROTATE_Y_270;

  private static final Map<TextureKey, String> SIMPLE_BLOCK_TEXTURE_MAP = Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%"));
  private static final Map<TextureKey, String> SIMPLE_ITEM_TEXTURE_MAP = Map.of(TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%"));
  private static final Map<TextureKey, String> CHAIN_TEXTURE_MAP = Map.of(
      TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%"),
      TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%")
  );
  private static final Map<TextureKey, String> FIXED_LADDER_TEXTURE_MAP = Map.of(
      TextureKey.BOTTOM, UnderDistantMoons.withPrefixedNamespace("block/%/bottom"),
      DistantMoonsTextureKeys.CENTER, UnderDistantMoons.withPrefixedNamespace("block/%/center"),
      TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      TextureKey.FRONT, UnderDistantMoons.withPrefixedNamespace("block/%/front"),
      TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      TextureKey.TOP, UnderDistantMoons.withPrefixedNamespace("block/%/top"),
      TextureKey.PARTICLE, UnderDistantMoons.withPrefixedNamespace("block/%/particle")
  );
  private static final Map<TextureKey, String> METAL_LADDER_TEXTURE_MAP = Map.of(
      TextureKey.BOTTOM, UnderDistantMoons.withPrefixedNamespace("block/%/bottom"),
      DistantMoonsTextureKeys.DETAIL, UnderDistantMoons.withPrefixedNamespace("block/%/detail"),
      TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      DistantMoonsTextureKeys.SUPPORT, UnderDistantMoons.withPrefixedNamespace("block/%/support"),
      TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%"),
      TextureKey.TOP, UnderDistantMoons.withPrefixedNamespace("block/%/top")
  );
  private static final Map<TextureKey, String> PILLAR_TEXTURE_MAP = Map.of(
      TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side")
  );
  private static final Map<TextureKey, String> POLE_TEXTURE_MAP = Map.of(
      TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      DistantMoonsTextureKeys.HORIZONTAL, UnderDistantMoons.withPrefixedNamespace("block/%/horizontal"),
      DistantMoonsTextureKeys.VERTICAL, UnderDistantMoons.withPrefixedNamespace("block/%/vertical")
  );
  private static final Map<TextureKey, String> ROPE_LADDER_TEXTURE_MAP = Map.of(
      DistantMoonsTextureKeys.CEILING, UnderDistantMoons.withPrefixedNamespace("block/%/ropes/ceiling"),
      TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      DistantMoonsTextureKeys.ENDS, UnderDistantMoons.withPrefixedNamespace("block/%/ropes/ends"),
      DistantMoonsTextureKeys.MIDDLE, UnderDistantMoons.withPrefixedNamespace("block/%/ropes/middle"),
      TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%"),
      TextureKey.PARTICLE, UnderDistantMoons.withPrefixedNamespace("block/%/particle")
  );
  private static final Map<TextureKey, String> SPIKED_FENCE_TEXTURE_MAP = Map.of(
      TextureKey.BOTTOM, UnderDistantMoons.withPrefixedNamespace("block/%/bottom"),
      TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      TextureKey.TOP, UnderDistantMoons.withPrefixedNamespace("block/%/top"),
      DistantMoonsTextureKeys.SIDE_TOP, UnderDistantMoons.withPrefixedNamespace("block/%/side_top"),
      TextureKey.PARTICLE, UnderDistantMoons.withPrefixedNamespace("block/%/particle")
  );

  private BlockStateModelGenerator blockGenerator;
  private ItemModelGenerator itemGenerator;

  public DistantMoonsModelProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator generator) {

    this.blockGenerator = generator;

    //SIMPLE BLOCKS
    registerSimpleBlock(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.CHARCOAL_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.COKE_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.GRAY_PRISMARINE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE, Map.of(TextureKey.SIDE, "minecraft:block/chiseled_deepslate"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE, Map.of(TextureKey.SIDE, "minecraft:block/cobbled_deepslate"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS, Map.of(TextureKey.SIDE, "minecraft:block/cracked_deepslate_bricks"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES, Map.of(TextureKey.SIDE, "minecraft:block/cracked_deepslate_tiles"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS, Map.of(TextureKey.SIDE, "minecraft:block/deepslate_bricks"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES, Map.of(TextureKey.SIDE, "minecraft:block/deepslate_tiles"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE, Map.of(TextureKey.SIDE, "minecraft:block/mossy_cobblestone"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE, Map.of(TextureKey.SIDE, "minecraft:block/polished_deepslate"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_SMOOTH_STONE, Map.of(TextureKey.SIDE, "minecraft:block/smooth_stone"));
    registerSimpleBlock(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.PALE_PRISMARINE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.PALE_PRISMARINE_TILES, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.PALE_SEA_LANTERN, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.PRISMARINE_TILES, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);

    registerSimpleBlock(DistantMoonsBlocks.EXPOSED_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.WEATHERED_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.RUSTED_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.WAXED_IRON_BLOCK, Map.of(TextureKey.SIDE, "minecraft:block/iron_block"));
    registerSimpleBlock(DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/exposed_iron_block")));
    registerSimpleBlock(DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/weathered_iron_block")));
    registerSimpleBlock(DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/rusted_iron_block")));

    //BARS
    registerBarsBlock(DistantMoonsBlocks.DEEP_IRON_BARS, false, PILLAR_TEXTURE_MAP);
    registerBarsBlock(DistantMoonsBlocks.WROUGHT_IRON_BARS, false, PILLAR_TEXTURE_MAP);

    //CHAINS
    registerChainBlock(DistantMoonsBlocks.DEEP_IRON_CHAIN, CHAIN_TEXTURE_MAP);

    //DOORS
    registerDoorBlock(DistantMoonsBlocks.DEEP_IRON_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureKeys.BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/bottom_left")),
        Map.entry(DistantMoonsTextureKeys.BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/bottom_right")),
        Map.entry(TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/end")),
        Map.entry(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/side")),
        Map.entry(TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/deep_iron_door")),
        Map.entry(DistantMoonsTextureKeys.TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/top_left")),
        Map.entry(DistantMoonsTextureKeys.TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/top_right"))
    ));

    //FIXED LADDERS
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER, FIXED_LADDER_TEXTURE_MAP);
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_IRON_LADDER, FIXED_LADDER_TEXTURE_MAP);
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER, FIXED_LADDER_TEXTURE_MAP);

    //METAL BAR DOORS
    registerMetalBarDoorBlock(DistantMoonsBlocks.DEEP_IRON_BAR_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/bottom_left")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/bottom_right")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/top_left")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/top_right")),
        Map.entry(TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/end")),
        Map.entry(TextureKey.INSIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/inside")),
        Map.entry(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/side")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/bottom_left")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/bottom_right")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/top_left")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/top_right")),
        Map.entry(TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/deep_iron_bar_door"))
    ));
    registerMetalBarDoorBlock(DistantMoonsBlocks.IRON_BAR_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/bottom_left")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/bottom_right")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/top_left")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/top_right")),
        Map.entry(TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/iron_door/end")),
        Map.entry(TextureKey.INSIDE, UnderDistantMoons.withPrefixedNamespace("block/iron_door/inside")),
        Map.entry(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/iron_door/side")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/bottom_left")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/bottom_right")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/top_left")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/top_right")),
        Map.entry(TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/iron_bar_door"))
    ));
    registerMetalBarDoorBlock(DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/bottom_left")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/bottom_right")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/top_left")),
        Map.entry(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/top_right")),
        Map.entry(TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/end")),
        Map.entry(TextureKey.INSIDE, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/inside")),
        Map.entry(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/side")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/bottom_left")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/bottom_right")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/top_left")),
        Map.entry(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/top_right")),
        Map.entry(TextureKey.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/wrought_iron_bar_door"))
    ));

    //METAL LADDER BLOCKS
    registerMetalLadderBlock(DistantMoonsBlocks.DEEP_IRON_LADDER, METAL_LADDER_TEXTURE_MAP);
    registerMetalLadderBlock(DistantMoonsBlocks.IRON_LADDER, METAL_LADDER_TEXTURE_MAP);
    registerMetalLadderBlock(DistantMoonsBlocks.WROUGHT_IRON_LADDER, METAL_LADDER_TEXTURE_MAP);

    //PILLAR SLABS - SIMPLE
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_WOOD, Map.of(TextureKey.END, "minecraft:block/acacia_log", TextureKey.SIDE, "minecraft:block/acacia_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_BASALT, Map.of(TextureKey.END, "minecraft:block/basalt_top", TextureKey.SIDE, "minecraft:block/basalt_side"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_WOOD, Map.of(TextureKey.END, "minecraft:block/birch_log", TextureKey.SIDE, "minecraft:block/birch_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_BONE_BLOCK, Map.of(TextureKey.END, "minecraft:block/bone_block_top", TextureKey.SIDE, "minecraft:block/bone_block_side"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_WOOD, Map.of(TextureKey.END, "minecraft:block/cherry_log", TextureKey.SIDE, "minecraft:block/cherry_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE, Map.of(TextureKey.END, "minecraft:block/crimson_stem", TextureKey.SIDE, "minecraft:block/crimson_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_STEM, Map.of(TextureKey.END, "minecraft:block/crimson_stem_top", TextureKey.SIDE, "minecraft:block/crimson_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/dark_oak_log", TextureKey.SIDE, "minecraft:block/dark_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_WOOD, Map.of(TextureKey.END, "minecraft:block/jungle_log", TextureKey.SIDE, "minecraft:block/jungle_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_WOOD, Map.of(TextureKey.END, "minecraft:block/mangrove_log", TextureKey.SIDE, "minecraft:block/mangrove_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/oak_log", TextureKey.SIDE, "minecraft:block/oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/pale_oak_log", TextureKey.SIDE, "minecraft:block/pale_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_WOOD, Map.of(TextureKey.END, "minecraft:block/spruce_log", TextureKey.SIDE, "minecraft:block/spruce_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_HYPHAE, Map.of(TextureKey.END, "minecraft:block/warped_stem", TextureKey.SIDE, "minecraft:block/warped_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.POLISHED_CUT_BASALT, Map.of(TextureKey.END, "minecraft:block/polished_basalt_top", TextureKey.SIDE, "minecraft:block/polished_basalt_side"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_acacia_log", TextureKey.SIDE, "minecraft:block/stripped_acacia_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_birch_log", TextureKey.SIDE, "minecraft:block/stripped_birch_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_cherry_log", TextureKey.SIDE, "minecraft:block/stripped_cherry_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE, Map.of(TextureKey.END, "minecraft:block/stripped_crimson_stem", TextureKey.SIDE, "minecraft:block/stripped_crimson_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_dark_oak_log", TextureKey.SIDE, "minecraft:block/stripped_dark_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_jungle_log", TextureKey.SIDE, "minecraft:block/stripped_jungle_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_mangrove_log", TextureKey.SIDE, "minecraft:block/stripped_mangrove_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_oak_log", TextureKey.SIDE, "minecraft:block/stripped_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_pale_oak_log", TextureKey.SIDE, "minecraft:block/stripped_pale_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_spruce_log", TextureKey.SIDE, "minecraft:block/stripped_spruce_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE, Map.of(TextureKey.END, "minecraft:block/stripped_warped_stem", TextureKey.SIDE, "minecraft:block/stripped_warped_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_STEM, Map.of(TextureKey.END, "minecraft:block/warped_stem_top", TextureKey.SIDE, "minecraft:block/warped_stem"));

    //PILLAR SLABS - AXIS
    registerAxisPillarSlabBlock(DistantMoonsBlocks.CUT_BAMBOO_BLOCK, Map.of(TextureKey.END, "minecraft:block/bamboo_block_top", TextureKey.SIDE, "minecraft:block/bamboo_block"));
    registerAxisPillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_LOG, Map.of(TextureKey.END, "minecraft:block/cherry_log_top", TextureKey.SIDE, "minecraft:block/cherry_log"));
    registerAxisPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK, Map.of(TextureKey.END, "minecraft:block/stripped_bamboo_block_top", TextureKey.SIDE, "minecraft:block/stripped_bamboo_block"));
    registerAxisPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_cherry_log_top", TextureKey.SIDE, "minecraft:block/stripped_cherry_log"));

    //PILLAR SLABS - HORIZONTAL
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_LOG, Map.of(TextureKey.END, "minecraft:block/acacia_log_top", TextureKey.SIDE, "minecraft:block/acacia_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_LOG, Map.of(TextureKey.END, "minecraft:block/birch_log_top", TextureKey.SIDE, "minecraft:block/birch_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/dark_oak_log_top", TextureKey.SIDE, "minecraft:block/dark_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_DEEPSLATE, Map.of(TextureKey.END, "minecraft:block/deepslate_top", TextureKey.SIDE, "minecraft:block/deepslate"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_LOG, Map.of(TextureKey.END, "minecraft:block/jungle_log_top", TextureKey.SIDE, "minecraft:block/jungle_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_LOG, Map.of(TextureKey.END, "minecraft:block/mangrove_log_top", TextureKey.SIDE, "minecraft:block/mangrove_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/oak_log_top", TextureKey.SIDE, "minecraft:block/oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/pale_oak_log_top", TextureKey.SIDE, "minecraft:block/pale_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_PURPUR_PILLAR, Map.of(TextureKey.END, "minecraft:block/purpur_pillar_top", TextureKey.SIDE, "minecraft:block/purpur_pillar"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_QUARTZ_PILLAR, Map.of(TextureKey.END, "minecraft:block/quartz_pillar_top", TextureKey.SIDE, "minecraft:block/quartz_pillar"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_LOG, Map.of(TextureKey.END, "minecraft:block/spruce_log_top", TextureKey.SIDE, "minecraft:block/spruce_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_acacia_log_top", TextureKey.SIDE, "minecraft:block/stripped_acacia_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_birch_log_top", TextureKey.SIDE, "minecraft:block/stripped_birch_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM, Map.of(TextureKey.END, "minecraft:block/stripped_crimson_stem_top", TextureKey.SIDE, "minecraft:block/stripped_crimson_stem"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_dark_oak_log_top", TextureKey.SIDE, "minecraft:block/stripped_dark_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_jungle_log_top", TextureKey.SIDE, "minecraft:block/stripped_jungle_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_mangrove_log_top", TextureKey.SIDE, "minecraft:block/stripped_mangrove_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_oak_log_top", TextureKey.SIDE, "minecraft:block/stripped_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_pale_oak_log_top", TextureKey.SIDE, "minecraft:block/stripped_pale_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_spruce_log_top", TextureKey.SIDE, "minecraft:block/stripped_spruce_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM, Map.of(TextureKey.END, "minecraft:block/stripped_warped_stem_top", TextureKey.SIDE, "minecraft:block/stripped_warped_stem"));

    //POLES - BEAM
    registerPoleBlock(DistantMoonsBlocks.ACACIA_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.BIRCH_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.CHERRY_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.CRIMSON_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.DARK_OAK_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.JUNGLE_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.MANGROVE_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.OAK_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.PALE_OAK_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.SPRUCE_BEAM, POLE_TEXTURE_MAP, "beam");
    registerPoleBlock(DistantMoonsBlocks.WARPED_BEAM, POLE_TEXTURE_MAP, "beam");

    //POLES - POLE
    registerPoleBlock(DistantMoonsBlocks.ACACIA_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.BAMBOO_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.BIRCH_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.CHERRY_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.CRIMSON_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.DARK_OAK_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.JUNGLE_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.MANGROVE_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.OAK_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.PALE_OAK_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.SPRUCE_POLE, POLE_TEXTURE_MAP, "pole");
    registerPoleBlock(DistantMoonsBlocks.WARPED_POLE, POLE_TEXTURE_MAP, "pole");

    //ROPE LADDERS
    registerRopeLadderBlock(DistantMoonsBlocks.ROPE_LADDER, ROPE_LADDER_TEXTURE_MAP);

    //SLABS
    registerSimpleSlabBlock(DistantMoonsBlocks.GRAY_PRISMARINE_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/gray_prismarine")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_bricks")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_tiles")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PRISMARINE_TILE_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/prismarine_tiles")));

    //SPIKED FENCES
    registerSpikedFenceBlock(DistantMoonsBlocks.DEEP_IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);
    registerSpikedFenceBlock(DistantMoonsBlocks.IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);
    registerSpikedFenceBlock(DistantMoonsBlocks.WROUGHT_IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);

    //STAIRS
    registerSimpleStairsBlock(DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/gray_prismarine")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_bricks")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PALE_PRISMARINE_STAIRS, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_tiles")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PRISMARINE_TILE_STAIRS, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/prismarine_tiles")));

    //TRAPDOORS
    registerTrapdoorBlock(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR, false, Map.of(
        TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/trapdoor"),
        TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/end")
    ));

    //WALLS
    registerSimpleWallBlock(DistantMoonsBlocks.PALE_PRISMARINE_WALL, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));

    //WALL SLABS - SIMPLE
    registerSimpleWallSlabBlock(DistantMoonsBlocks.ACACIA_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/acacia_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.ANDESITE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/andesite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/bamboo_mosaic"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BAMBOO_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/bamboo_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BIRCH_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/birch_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.CHERRY_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/cherry_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/cobbled_deepslate"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.COBBLESTONE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/cobblestone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.CRIMSON_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/crimson_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DARK_OAK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/dark_oak_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/dark_prismarine"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/deepslate_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/deepslate_tiles"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DIORITE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/diorite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/end_stone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/exposed_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.GRANITE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/granite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/gray_prismarine")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.JUNGLE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/jungle_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MANGROVE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/mangrove_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/mossy_cobblestone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/mossy_stone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/mud_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/nether_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.OAK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/oak_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/oxidized_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_OAK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/pale_oak_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_bricks")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_tiles")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/polished_andesite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/polished_blackstone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/polished_blackstone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/polished_deepslate"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/polished_diorite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/polished_granite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/polished_tuff"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/prismarine_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB, Map.of(TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/prismarine_tiles")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PRISMARINE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/prismarine"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PURPUR_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/purpur_block"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/red_nether_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/resin_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/quartz_block_bottom"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/red_sandstone_top"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/sandstone_top"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SPRUCE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/spruce_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.STONE_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/stone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.STONE_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/stone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/tuff_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.TUFF_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/tuff"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WARPED_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/warped_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/exposed_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/oxidized_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/weathered_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB, Map.of(TextureKey.SIDE, "minecraft:block/weathered_cut_copper"));

    //WALL SLABS - PILLAR
    registerPillarWallSlabBlock(DistantMoonsBlocks.BLACKSTONE_WALL_SLAB, Map.of(TextureKey.END, "minecraft:block/blackstone_top", TextureKey.SIDE, "minecraft:block/blackstone"));
    registerPillarWallSlabBlock(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB, Map.of(TextureKey.END, "minecraft:block/red_sandstone_top", TextureKey.SIDE, "minecraft:block/cut_red_sandstone"));
    registerPillarWallSlabBlock(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB, Map.of(TextureKey.END, "minecraft:block/sandstone_top", TextureKey.SIDE, "minecraft:block/cut_sandstone"));
    registerPillarWallSlabBlock(DistantMoonsBlocks.QUARTZ_WALL_SLAB, Map.of(TextureKey.END, "minecraft:block/quartz_block_top", TextureKey.SIDE, "minecraft:block/quartz_block_side"));

    //WALL SLABS - VERTICAL
    registerVerticalWallSlabBlock(DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB, Map.of(TextureKey.BOTTOM, "minecraft:block/red_sandstone_bottom", TextureKey.SIDE, "minecraft:block/red_sandstone", TextureKey.TOP, "minecraft:block/red_sandstone_top"));
    registerVerticalWallSlabBlock(DistantMoonsBlocks.SANDSTONE_WALL_SLAB, Map.of(TextureKey.BOTTOM, "minecraft:block/sandstone_bottom", TextureKey.SIDE, "minecraft:block/sandstone", TextureKey.TOP, "minecraft:block/sandstone_top"));

    //WALL SLABS - COMPLEX
    registerComplexWallSlabBlock(DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB, Map.of(
        DistantMoonsTextureKeys.BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/bottom_left"),
        DistantMoonsTextureKeys.BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/bottom_right"),
        DistantMoonsTextureKeys.HORIZONTAL_END, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/end/horizontal"),
        DistantMoonsTextureKeys.HORIZONTAL_SIDE, "minecraft:block/smooth_stone_slab_side",
        TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/side/flat"),
        DistantMoonsTextureKeys.TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/top_left"),
        DistantMoonsTextureKeys.TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/top_right"),
        DistantMoonsTextureKeys.VERTICAL_END, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/end/vertical"),
        DistantMoonsTextureKeys.VERTICAL_SIDE, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/side/vertical")
    ));
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {

    this.itemGenerator = generator;

    //SIMPLE ITEMS
    registerSimpleItem(DistantMoonsItems.COILED_ROPE_LADDER, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.COKE, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.COPPER_ROD, "stick", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_AXE, "axe", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_BOOTS, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_CHESTPLATE, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_HELMET, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_HOE, "hoe", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_HORSE_ARMOR, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_LEGGINGS, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_PICKAXE, "pickaxe", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_SHOVEL, "shovel", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_SWORD, "sword", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.IRON_ROD, "stick", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.PALE_PRISMARINE_SHARD, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.RAW_DEEP_IRON, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_INGOT, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_ROD, "stick", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.ROTTEN_FISH, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.UNDERWORLD_DUST, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.WROUGHT_IRON_ROD, "stick", SIMPLE_ITEM_TEXTURE_MAP);
  }

  private void registerSimpleBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "simple_block", null, textureMap));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, variant));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variant)));
  }

  private void registerBarsBlock(Block block, boolean mirrored, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapCap = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    Map<TextureKey, String> textureMapItem = Map.of(TextureKey.TEXTURE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    Map<TextureKey, String> textureMapSide = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    WeightedVariant variantCap = createWeightedVariant(createObjectModel(block, "metal_bars/cap/default", "/cap/default", textureMapCap));
    WeightedVariant variantCapMirrored = createWeightedVariant(createObjectModel(block, "metal_bars/cap/mirrored", "/cap/mirrored", textureMapCap));
    WeightedVariant variantCenterCaps = createWeightedVariant(createObjectModel(block, "metal_bars/center_caps", "/center_caps", textureMapCap));
    WeightedVariant variantCenterPost = createWeightedVariant(createObjectModel(block, "metal_bars/center_post", "/center_post", textureMapCap));
    WeightedVariant variantSide = createWeightedVariant(createObjectModel(block, "metal_bars/side/default", "/side/default", textureMapSide));
    WeightedVariant variantSideLeft = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_left", "/side/mirrored_left", textureMapSide));
    WeightedVariant variantSideRight = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_right", "/side/mirrored_right", textureMapSide));
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(variantCenterCaps)
        .with(ModelProviderUtil.directionalMultipartCondition(false, false, false, false), variantCenterPost)
        .with(ModelProviderUtil.directionalMultipartCondition(true, false, false, false), variantCap)
        .with(ModelProviderUtil.directionalMultipartCondition(false, true, false, false), variantCap.apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(false, false, true, false), variantCapMirrored.apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(false, false, false, true), variantCapMirrored.apply(ROTATE_Y_270).apply(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(true, null, null, null), mirrored ? variantSideLeft : variantSide)
        .with(ModelProviderUtil.directionalMultipartCondition(null, true, null, null), (mirrored ? variantSideLeft : variantSide).apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(null, null, true, null), (mirrored ? variantSideRight : variantSide).apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(null, null, null, true), (mirrored ? variantSideRight : variantSide).apply(ROTATE_Y_270).apply(UV_LOCK))
    );
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerChainBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapBlock = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    Map<TextureKey, String> textureMapItem = Map.of(TextureKey.TEXTURE, rawTextureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.TEXTURE));
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "chain", null, textureMapBlock));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(ChainBlock.AXIS)
        .register(Direction.Axis.X, variant.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.Y, variant)
        .register(Direction.Axis.Z, variant.apply(ROTATE_X_90))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerDoorBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapBottomLeft = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT));
    Map<TextureKey, String> textureMapBottomRight = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT));
    Map<TextureKey, String> textureMapTopLeft = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(DistantMoonsTextureKeys.TOP_LEFT), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.TOP_LEFT));
    Map<TextureKey, String> textureMapTopRight = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.TOP_LEFT), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.TOP_RIGHT));
    Map<TextureKey, String> textureMapItem = Map.of(TextureKey.TEXTURE, rawTextureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.TEXTURE));
    WeightedVariant variantBottomLeft = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_left", textureMapBottomLeft));
    WeightedVariant variantBottomRight = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_right", textureMapBottomRight));
    WeightedVariant variantTopLeft = createWeightedVariant(createObjectModel(block, "door/top", "/top_left", textureMapTopLeft));
    WeightedVariant variantTopRight = createWeightedVariant(createObjectModel(block, "door/top", "/top_right", textureMapTopRight));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(DoorBlock.HALF, DoorBlock.FACING, DoorBlock.HINGE, DoorBlock.OPEN)
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, false, variantBottomLeft)
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, false, variantBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, false, variantBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, false, variantBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, true, variantBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, true, variantBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, true, variantBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, true, variantBottomRight)
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, false, variantBottomRight)
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, false, variantBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, false, variantBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, false, variantBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, true, variantBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, true, variantBottomLeft)
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, true, variantBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, true, variantBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, false, variantTopLeft)
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, false, variantTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, false, variantTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, false, variantTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, true, variantTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, true, variantTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, true, variantTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, true, variantTopRight)
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, false, variantTopRight)
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, false, variantTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, false, variantTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, false, variantTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, true, variantTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, true, variantTopLeft)
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, true, variantTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, true, variantTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerFixedLadderBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapCenter = Map.of(TextureKey.BOTTOM, rawTextureMap.get(TextureKey.BOTTOM), TextureKey.FRONT, rawTextureMap.get(TextureKey.FRONT), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.CENTER), TextureKey.TOP, rawTextureMap.get(TextureKey.TOP), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapCenterCaps = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapExtension = Map.of(TextureKey.BOTTOM, rawTextureMap.get(TextureKey.BOTTOM), TextureKey.FRONT, rawTextureMap.get(TextureKey.FRONT), TextureKey.TOP, rawTextureMap.get(TextureKey.TOP), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapSide = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapSideCaps = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    WeightedVariant variantCenter = createWeightedVariant(createObjectModel(block, "fixed_ladder/center", "/center", textureMapCenter));
    WeightedVariant variantCenterCaps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/center", "/caps/center", textureMapCenterCaps));
    WeightedVariant variantExtension = createWeightedVariant(createObjectModel(block, "fixed_ladder/extension", "/extension", textureMapExtension));
    WeightedVariant variantSide = createWeightedVariant(createObjectModel(block, "fixed_ladder/side", "/side", textureMapSide));
    WeightedVariant variantSideCaps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/side", "/caps/side", textureMapSideCaps));
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X), variantCenter.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z), variantCenter)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide.apply(ROTATE_Y_270))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension.apply(ROTATE_Y_270))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.LEFT_CAPPED, true), variantSideCaps.apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.RIGHT_CAPPED, true), variantSideCaps.apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.LEFT_CAPPED, true), variantSideCaps.apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.RIGHT_CAPPED, true), variantSideCaps.apply(ROTATE_Y_270).apply(UV_LOCK))
        .with(new MultipartModelCombinedCondition(MultipartModelCombinedCondition.LogicalOperator.OR, List.of(
            new MultipartModelConditionBuilder().put(FixedLadderBlock.LEFT_CAPPED, true).build(),
            new MultipartModelConditionBuilder().put(FixedLadderBlock.RIGHT_CAPPED, true).build())
        ), variantCenterCaps)
    );
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variantCenter)));
  }

  private void registerMetalBarDoorBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapDoubleBottomLeft = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT));
    Map<TextureKey, String> textureMapDoubleBottomRight = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT));
    Map<TextureKey, String> textureMapDoubleTopLeft = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT));
    Map<TextureKey, String> textureMapDoubleTopRight = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT));
    Map<TextureKey, String> textureMapSingleBottomLeft = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT));
    Map<TextureKey, String> textureMapSingleBottomRight = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT));
    Map<TextureKey, String> textureMapSingleTopLeft = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_LEFT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_LEFT));
    Map<TextureKey, String> textureMapSingleTopRight = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.BACK, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_LEFT), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT), TextureKey.INSIDE, rawTextureMap.get(TextureKey.INSIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT));
    Map<TextureKey, String> textureMapItem = Map.of(TextureKey.TEXTURE, rawTextureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.TEXTURE));
    WeightedVariant variantDoubleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_left", "/double/bottom_left", textureMapDoubleBottomLeft));
    WeightedVariant variantDoubleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_right", "/double/bottom_right", textureMapDoubleBottomRight));
    WeightedVariant variantDoubleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_left", "/double/top_left", textureMapDoubleTopLeft));
    WeightedVariant variantDoubleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_right", "/double/top_right", textureMapDoubleTopRight));
    WeightedVariant variantSingleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_left", textureMapSingleBottomLeft));
    WeightedVariant variantSingleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_right", textureMapSingleBottomRight));
    WeightedVariant variantSingleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_left", textureMapSingleTopLeft));
    WeightedVariant variantSingleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_right", textureMapSingleTopRight));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(MetalBarDoorBlock.DOUBLE, DoorBlock.HALF, DoorBlock.FACING, DoorBlock.HINGE, DoorBlock.OPEN)
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, false, variantSingleBottomLeft)
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, false, variantSingleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, false, variantSingleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, false, variantSingleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, true, variantSingleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, true, variantSingleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, true, variantSingleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, true, variantSingleBottomRight)
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, false, variantSingleBottomRight)
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, false, variantSingleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, false, variantSingleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, false, variantSingleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, true, variantSingleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, true, variantSingleBottomLeft)
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, true, variantSingleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, true, variantSingleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, false, variantSingleTopLeft)
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, false, variantSingleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, false, variantSingleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, false, variantSingleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, true, variantSingleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, true, variantSingleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, true, variantSingleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, true, variantSingleTopRight)
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, false, variantSingleTopRight)
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, false, variantSingleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, false, variantSingleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, false, variantSingleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, true, variantSingleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, true, variantSingleTopLeft)
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, true, variantSingleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, true, variantSingleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, false, variantDoubleBottomLeft)
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, false, variantDoubleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, false, variantDoubleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, false, variantDoubleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, true, variantDoubleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, true, variantDoubleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, true, variantDoubleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, true, variantDoubleBottomRight)
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, false, variantDoubleBottomRight)
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, false, variantDoubleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, false, variantDoubleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, false, variantDoubleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, true, variantDoubleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, true, variantDoubleBottomLeft)
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, true, variantDoubleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, true, variantDoubleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, false, variantDoubleTopLeft)
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, false, variantDoubleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, false, variantDoubleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, false, variantDoubleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, true, variantDoubleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, true, variantDoubleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, true, variantDoubleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, true, variantDoubleTopRight)
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, false, variantDoubleTopRight)
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, false, variantDoubleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, false, variantDoubleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, false, variantDoubleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, true, variantDoubleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, true, variantDoubleTopLeft)
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, true, variantDoubleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, true, variantDoubleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerMetalLadderBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapBlock = Map.of(TextureKey.BOTTOM, rawTextureMap.get(TextureKey.BOTTOM), DistantMoonsTextureKeys.DETAIL, rawTextureMap.get(DistantMoonsTextureKeys.DETAIL), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), DistantMoonsTextureKeys.SUPPORT, rawTextureMap.get(DistantMoonsTextureKeys.SUPPORT), TextureKey.TOP, rawTextureMap.get(TextureKey.TOP), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    Map<TextureKey, String> textureMapItem = Map.of(TextureKey.TEXTURE, rawTextureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.TEXTURE));
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "metal_ladder", "/block", textureMapBlock));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(LadderBlock.FACING)
        .register(Direction.NORTH, variant)
        .register(Direction.EAST, variant.apply(ROTATE_Y_90))
        .register(Direction.SOUTH, variant.apply(ROTATE_Y_180))
        .register(Direction.WEST, variant.apply(ROTATE_Y_270))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerSimplePillarSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    WeightedVariant variantBottom = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/bottom", "/bottom_vertical", textureMap));
    WeightedVariant variantDouble = createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double_vertical", textureMap));
    WeightedVariant variantTop = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/top", "/top_vertical", textureMap));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(PillarSlabBlock.AXIS, PillarSlabBlock.TYPE)
        .register(Direction.Axis.X, SlabType.BOTTOM, variantBottom.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.X, SlabType.DOUBLE, variantDouble.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.X, SlabType.TOP, variantTop.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.Y, SlabType.BOTTOM, variantBottom)
        .register(Direction.Axis.Y, SlabType.DOUBLE, variantDouble)
        .register(Direction.Axis.Y, SlabType.TOP, variantTop)
        .register(Direction.Axis.Z, SlabType.BOTTOM, variantBottom.apply(ROTATE_X_90))
        .register(Direction.Axis.Z, SlabType.DOUBLE, variantDouble.apply(ROTATE_X_90))
        .register(Direction.Axis.Z, SlabType.TOP, variantTop.apply(ROTATE_X_90))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variantBottom)));
  }

  private void registerAxisPillarSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    WeightedVariant variantBottomX = createWeightedVariant(createObjectModel(block, "slab/pillar/west", "/bottom_x", textureMap));
    WeightedVariant variantBottomY = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/bottom", "/bottom_y", textureMap));
    WeightedVariant variantBottomZ = createWeightedVariant(createObjectModel(block, "slab/pillar/south", "/bottom_z", textureMap));
    WeightedVariant variantDoubleX = createWeightedVariant(createObjectModel(block, "pillar/axis_x", "/double_x", textureMap));
    WeightedVariant variantDoubleY = createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double_y", textureMap));
    WeightedVariant variantDoubleZ = createWeightedVariant(createObjectModel(block, "pillar/axis_z", "/double_z", textureMap));
    WeightedVariant variantTopX = createWeightedVariant(createObjectModel(block, "slab/pillar/east", "/top_x", textureMap));
    WeightedVariant variantTopY = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/top", "/top_y", textureMap));
    WeightedVariant variantTopZ = createWeightedVariant(createObjectModel(block, "slab/pillar/north", "/top_z", textureMap));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(PillarSlabBlock.AXIS, PillarSlabBlock.TYPE)
        .register(Direction.Axis.X, SlabType.BOTTOM, variantBottomX)
        .register(Direction.Axis.X, SlabType.DOUBLE, variantDoubleX)
        .register(Direction.Axis.X, SlabType.TOP, variantTopX)
        .register(Direction.Axis.Y, SlabType.BOTTOM, variantBottomY)
        .register(Direction.Axis.Y, SlabType.DOUBLE, variantDoubleY)
        .register(Direction.Axis.Y, SlabType.TOP, variantTopY)
        .register(Direction.Axis.Z, SlabType.BOTTOM, variantBottomZ)
        .register(Direction.Axis.Z, SlabType.DOUBLE, variantDoubleZ)
        .register(Direction.Axis.Z, SlabType.TOP, variantTopZ)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variantBottomY)));
  }

  private void registerHorizontalPillarSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    WeightedVariant variantBottomHorizontal = createWeightedVariant(createObjectModel(block, "slab/pillar/horizontal/bottom", "/bottom_horizontal", textureMap));
    WeightedVariant variantBottomVertical = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/bottom", "/bottom_vertical", textureMap));
    WeightedVariant variantDoubleHorizontal = createWeightedVariant(createObjectModel(block, "pillar/horizontal", "/double_horizontal", textureMap));
    WeightedVariant variantDoubleVertical = createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double_vertical", textureMap));
    WeightedVariant variantTopHorizontal = createWeightedVariant(createObjectModel(block, "slab/pillar/horizontal/top", "/top_horizontal", textureMap));
    WeightedVariant variantTopVertical = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/top", "/top_vertical", textureMap));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(PillarSlabBlock.AXIS, PillarSlabBlock.TYPE)
        .register(Direction.Axis.X, SlabType.BOTTOM, variantBottomHorizontal.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.X, SlabType.DOUBLE, variantDoubleHorizontal.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.X, SlabType.TOP, variantTopHorizontal.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.Y, SlabType.BOTTOM, variantBottomVertical)
        .register(Direction.Axis.Y, SlabType.DOUBLE, variantDoubleVertical)
        .register(Direction.Axis.Y, SlabType.TOP, variantTopVertical)
        .register(Direction.Axis.Z, SlabType.BOTTOM, variantBottomHorizontal.apply(ROTATE_X_90))
        .register(Direction.Axis.Z, SlabType.DOUBLE, variantDoubleHorizontal.apply(ROTATE_X_90))
        .register(Direction.Axis.Z, SlabType.TOP, variantTopHorizontal.apply(ROTATE_X_90))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variantBottomHorizontal)));
  }

  private void registerPoleBlock(Block block, Map<TextureKey, String> rawTextureMap, String parentType) {
    Map<TextureKey, String> textureMapX = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), DistantMoonsTextureKeys.HORIZONTAL, rawTextureMap.get(DistantMoonsTextureKeys.HORIZONTAL), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL));
    Map<TextureKey, String> textureMapY = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), DistantMoonsTextureKeys.VERTICAL, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL));
    Map<TextureKey, String> textureMapZ = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), DistantMoonsTextureKeys.HORIZONTAL, rawTextureMap.get(DistantMoonsTextureKeys.HORIZONTAL), DistantMoonsTextureKeys.VERTICAL, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL));
    WeightedVariant variantCenterX = createWeightedVariant(createObjectModel(block, parentType + "/center_x", "/center_x", textureMapX));
    WeightedVariant variantCenterY = createWeightedVariant(createObjectModel(block, parentType + "/center_y", "/center_y", textureMapY));
    WeightedVariant variantCenterZ = createWeightedVariant(createObjectModel(block, parentType + "/center_z", "/center_z", textureMapZ));
    WeightedVariant variantExtensionX = createWeightedVariant(createObjectModel(block, parentType + "/extension_x", "/extension_x", textureMapX));
    WeightedVariant variantExtensionY = createWeightedVariant(createObjectModel(block, parentType + "/extension_y", "/extension_y", textureMapY));
    WeightedVariant variantExtensionZ = createWeightedVariant(createObjectModel(block, parentType + "/extension_z", "/extension_z", textureMapZ));
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.X), variantCenterX.apply(ROTATE_X_90).apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.Y), variantCenterY)
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.Z), variantCenterZ.apply(ROTATE_X_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.X).put(PoleBlock.UP, true), variantExtensionX.apply(ROTATE_X_90).apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.Y).put(PoleBlock.UP, true), variantExtensionY)
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.Z).put(PoleBlock.UP, true), variantExtensionZ.apply(ROTATE_X_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.X).put(PoleBlock.DOWN, true), variantExtensionX.apply(ROTATE_X_270).apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.Y).put(PoleBlock.DOWN, true), variantExtensionY.apply(ROTATE_X_180).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(PoleBlock.AXIS, Direction.Axis.Z).put(PoleBlock.DOWN, true), variantExtensionZ.apply(ROTATE_X_270).apply(UV_LOCK))
    );
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variantCenterY)));
  }

  private void registerRopeLadderBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapBottom = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.ENDS), TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.MIDDLE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapInnerEnds = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.ENDS), TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.CEILING), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapInnerTop = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.MIDDLE), TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.CEILING), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapMiddle = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.MIDDLE), TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.MIDDLE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapOuterEnds = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.ENDS), TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.ENDS), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapOuterTop = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.MIDDLE), TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.ENDS), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.PARTICLE));
    Map<TextureKey, String> textureMapItem = Map.of(TextureKey.TEXTURE, rawTextureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.TEXTURE));
    WeightedVariant variantInnerBottom = createWeightedVariant(createObjectModel(block, "rope_ladder/inner", "/inner/bottom", textureMapBottom));
    WeightedVariant variantInnerEnds = createWeightedVariant(createObjectModel(block, "rope_ladder/ceiling", "/inner/ends", textureMapInnerEnds));
    WeightedVariant variantInnerMiddle = createWeightedVariant(createObjectModel(block, "rope_ladder/inner", "/inner/middle", textureMapMiddle));
    WeightedVariant variantInnerTop = createWeightedVariant(createObjectModel(block, "rope_ladder/ceiling", "/inner/top", textureMapInnerTop));
    WeightedVariant variantOuterBottom = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/bottom", textureMapBottom));
    WeightedVariant variantOuterEnds = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/ends", textureMapOuterEnds));
    WeightedVariant variantOuterMiddle = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/middle", textureMapMiddle));
    WeightedVariant variantOuterTop = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/top", textureMapOuterTop));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(RopeLadderBlock.DIRECTION, RopeLadderBlock.TOP, RopeLadderBlock.BOTTOM)
        .register(RopeLadderDirection.NORTH, false, false, variantOuterMiddle)
        .register(RopeLadderDirection.NORTH, false, true, variantOuterBottom)
        .register(RopeLadderDirection.NORTH, true, false, variantOuterTop)
        .register(RopeLadderDirection.NORTH, true, true, variantOuterEnds)
        .register(RopeLadderDirection.EAST, false, false, variantOuterMiddle.apply(ROTATE_Y_90))
        .register(RopeLadderDirection.EAST, false, true, variantOuterBottom.apply(ROTATE_Y_90))
        .register(RopeLadderDirection.EAST, true, false, variantOuterTop.apply(ROTATE_Y_90))
        .register(RopeLadderDirection.EAST, true, true, variantOuterEnds.apply(ROTATE_Y_90))
        .register(RopeLadderDirection.SOUTH, false, false, variantOuterMiddle.apply(ROTATE_Y_180))
        .register(RopeLadderDirection.SOUTH, false, true, variantOuterBottom.apply(ROTATE_Y_180))
        .register(RopeLadderDirection.SOUTH, true, false, variantOuterTop.apply(ROTATE_Y_180))
        .register(RopeLadderDirection.SOUTH, true, true, variantOuterEnds.apply(ROTATE_Y_180))
        .register(RopeLadderDirection.WEST, false, false, variantOuterMiddle.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.WEST, false, true, variantOuterBottom.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.WEST, true, false, variantOuterTop.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.WEST, true, true, variantOuterEnds.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.X, false, false, variantInnerMiddle.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.X, false, true, variantInnerBottom.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.X, true, false, variantInnerTop.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.X, true, true, variantInnerEnds.apply(ROTATE_Y_270))
        .register(RopeLadderDirection.Z, false, false, variantInnerMiddle)
        .register(RopeLadderDirection.Z, false, true, variantInnerBottom)
        .register(RopeLadderDirection.Z, true, false, variantInnerTop)
        .register(RopeLadderDirection.Z, true, true, variantInnerEnds)
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerSimpleSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    registerSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "slab/simple/bottom", "/bottom", textureMap)),
        createWeightedVariant(createObjectModel(block, "simple_block", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "slab/simple/top", "/top", textureMap))
    );
  }

  private void registerSlabBlock(
      Block block,
      WeightedVariant variantBottom,
      WeightedVariant variantDouble,
      WeightedVariant variantTop
  ) {
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(SlabBlock.TYPE)
        .register(SlabType.BOTTOM, variantBottom)
        .register(SlabType.DOUBLE, variantDouble)
        .register(SlabType.TOP, variantTop)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variantBottom)));
  }

  private void registerSpikedFenceBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapPole = Map.of(TextureKey.BOTTOM, rawTextureMap.get(TextureKey.BOTTOM), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(TextureKey.BOTTOM), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    Map<TextureKey, String> textureMapSide = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    Map<TextureKey, String> textureMapTopPole = Map.of(TextureKey.BOTTOM, rawTextureMap.get(TextureKey.BOTTOM), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.SIDE_TOP), TextureKey.TOP, rawTextureMap.get(TextureKey.TOP), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.SIDE_TOP));
    Map<TextureKey, String> textureMapTopSide = Map.of(TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.SIDE_TOP), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.SIDE_TOP));
    Map<TextureKey, String> textureMapItem = Map.of(TextureKey.BOTTOM, rawTextureMap.get(TextureKey.BOTTOM), DistantMoonsTextureKeys.LOWER_SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(TextureKey.TOP), DistantMoonsTextureKeys.UPPER_SIDE, rawTextureMap.get(DistantMoonsTextureKeys.SIDE_TOP), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.SIDE_TOP));
    WeightedVariant variantPole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/pole", textureMapPole));
    WeightedVariant variantSide = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/side", textureMapSide));
    WeightedVariant variantTopPole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/top_pole", textureMapTopPole));
    WeightedVariant variantTopSide = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/top_side", textureMapTopSide));
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.TOP, false), variantPole)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.TOP, true), variantTopPole)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.NORTH, SpikedFenceShape.SIDE), variantSide)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.EAST, SpikedFenceShape.SIDE), variantSide.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.SOUTH, SpikedFenceShape.SIDE), variantSide.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.WEST, SpikedFenceShape.SIDE), variantSide.apply(ROTATE_Y_270))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.NORTH, SpikedFenceShape.TOP), variantTopSide)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.EAST, SpikedFenceShape.TOP), variantTopSide.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.SOUTH, SpikedFenceShape.TOP), variantTopSide.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.WEST, SpikedFenceShape.TOP), variantTopSide.apply(ROTATE_Y_270))
    );
    Identifier inventoryModel = createObjectModel(block, "spiked_fence/item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerSimpleStairsBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    registerStairsBlock(
        block,
        createWeightedVariant(createObjectModel(block, "stairs/simple/straight", "/straight", textureMap)),
        createWeightedVariant(createObjectModel(block, "stairs/simple/inner", "/inner", textureMap)),
        createWeightedVariant(createObjectModel(block, "stairs/simple/outer", "/outer", textureMap)),
        createObjectModel(block, "stairs/simple/item", "/item", textureMap)
    );
  }

  private void registerStairsBlock(
      Block block,
      WeightedVariant variantStraight,
      WeightedVariant variantInner,
      WeightedVariant variantOuter,
      Identifier inventoryModel
  ) {
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(SimplifiedStairsBlock.HALF, SimplifiedStairsBlock.SHAPE, SimplifiedStairsBlock.FACING)
        .register(BlockHalf.BOTTOM, StairShape.STRAIGHT, Direction.NORTH, variantStraight)
        .register(BlockHalf.BOTTOM, StairShape.STRAIGHT, Direction.EAST, variantStraight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.STRAIGHT, Direction.SOUTH, variantStraight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.STRAIGHT, Direction.WEST, variantStraight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.INNER_LEFT, Direction.NORTH, variantInner)
        .register(BlockHalf.BOTTOM, StairShape.INNER_LEFT, Direction.EAST, variantInner.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.INNER_LEFT, Direction.SOUTH, variantInner.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.INNER_LEFT, Direction.WEST, variantInner.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.INNER_RIGHT, Direction.NORTH, variantInner.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.INNER_RIGHT, Direction.EAST, variantInner.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.INNER_RIGHT, Direction.SOUTH, variantInner.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.INNER_RIGHT, Direction.WEST, variantInner)
        .register(BlockHalf.BOTTOM, StairShape.OUTER_LEFT, Direction.NORTH, variantOuter)
        .register(BlockHalf.BOTTOM, StairShape.OUTER_LEFT, Direction.EAST, variantOuter.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.OUTER_LEFT, Direction.SOUTH, variantOuter.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.OUTER_LEFT, Direction.WEST, variantOuter.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.OUTER_RIGHT, Direction.NORTH, variantOuter.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.OUTER_RIGHT, Direction.EAST, variantOuter.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.OUTER_RIGHT, Direction.SOUTH, variantOuter.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, StairShape.OUTER_RIGHT, Direction.WEST, variantOuter)
        .register(BlockHalf.TOP, StairShape.STRAIGHT, Direction.NORTH, variantStraight.apply(ROTATE_X_180).apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.STRAIGHT, Direction.EAST, variantStraight.apply(ROTATE_X_180).apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.STRAIGHT, Direction.SOUTH, variantStraight.apply(ROTATE_X_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.STRAIGHT, Direction.WEST, variantStraight.apply(ROTATE_X_180).apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_LEFT, Direction.NORTH, variantInner.apply(ROTATE_X_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_LEFT, Direction.EAST, variantInner.apply(ROTATE_X_180).apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_LEFT, Direction.SOUTH, variantInner.apply(ROTATE_X_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_LEFT, Direction.WEST, variantInner.apply(ROTATE_X_180).apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_RIGHT, Direction.NORTH, variantInner.apply(ROTATE_X_180).apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_RIGHT, Direction.EAST, variantInner.apply(ROTATE_X_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_RIGHT, Direction.SOUTH, variantInner.apply(ROTATE_X_180).apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.INNER_RIGHT, Direction.WEST, variantInner.apply(ROTATE_X_180).apply(ROTATE_Y_180))
        .register(BlockHalf.TOP, StairShape.OUTER_LEFT, Direction.NORTH, variantOuter.apply(ROTATE_X_180).apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.OUTER_LEFT, Direction.EAST, variantOuter.apply(ROTATE_X_180).apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.OUTER_LEFT, Direction.SOUTH, variantOuter.apply(ROTATE_X_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.OUTER_LEFT, Direction.WEST, variantOuter.apply(ROTATE_X_180).apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.OUTER_RIGHT, Direction.NORTH, variantOuter.apply(ROTATE_X_180).apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.OUTER_RIGHT, Direction.EAST, variantOuter.apply(ROTATE_X_180).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.OUTER_RIGHT, Direction.SOUTH, variantOuter.apply(ROTATE_X_180).apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.TOP, StairShape.OUTER_RIGHT, Direction.WEST, variantOuter.apply(ROTATE_X_180).apply(ROTATE_Y_180).apply(UV_LOCK))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerTrapdoorBlock(Block block, boolean orientable, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.END));
    WeightedVariant variantBottom = createWeightedVariant(createObjectModel(block, "trapdoor/bottom", "/bottom", textureMap));
    WeightedVariant variantOpen = createWeightedVariant(createObjectModel(block, "trapdoor/open", "/open", textureMap));
    WeightedVariant variantTop = createWeightedVariant(createObjectModel(block, "trapdoor/top", "/top", textureMap));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(TrapdoorBlock.HALF, TrapdoorBlock.FACING, TrapdoorBlock.OPEN)
        .register(BlockHalf.BOTTOM, Direction.NORTH, false, variantBottom)
        .register(BlockHalf.BOTTOM, Direction.EAST, false, variantBottom.apply(orientable ? ROTATE_Y_90 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.BOTTOM, Direction.SOUTH, false, variantBottom.apply(orientable ? ROTATE_Y_180 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.BOTTOM, Direction.WEST, false, variantBottom.apply(orientable ? ROTATE_Y_270 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.BOTTOM, Direction.NORTH, true, variantOpen)
        .register(BlockHalf.BOTTOM, Direction.EAST, true, variantOpen.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, Direction.SOUTH, true, variantOpen.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, Direction.WEST, true, variantOpen.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.TOP, Direction.NORTH, false, variantTop)
        .register(BlockHalf.TOP, Direction.EAST, false, variantTop.apply(orientable ? ROTATE_Y_90 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.SOUTH, false, variantTop.apply(orientable ? ROTATE_Y_180 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.WEST, false, variantTop.apply(orientable ? ROTATE_Y_270 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.NORTH, true, variantOpen.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? ROTATE_Y_180 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.EAST, true, variantOpen.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? ROTATE_Y_270 : ROTATE_Y_90).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.SOUTH, true, variantOpen.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? NO_OP : ROTATE_Y_180).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.WEST, true, variantOpen.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? ROTATE_Y_90 : ROTATE_Y_270).apply(orientable ? UV_LOCK : NO_OP))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variantBottom)));
  }

  private void registerSimpleWallBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    registerWallBlock(
        block,
        createWeightedVariant(createObjectModel(block, "wall/simple/low", "/low", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall/simple/post", "/post", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall/simple/tall", "/tall", textureMap)),
        createObjectModel(block, "wall/simple/item", "/item", textureMap)
    );
  }

  private void registerWallBlock(
      Block block,
      WeightedVariant variantLow,
      WeightedVariant variantPost,
      WeightedVariant variantTall,
      Identifier inventoryModel
  ) {
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(new MultipartModelConditionBuilder().put(WallBlock.UP, true), variantPost)
        .with(new MultipartModelConditionBuilder().put(WallBlock.NORTH_WALL_SHAPE, WallShape.LOW), variantLow)
        .with(new MultipartModelConditionBuilder().put(WallBlock.NORTH_WALL_SHAPE, WallShape.TALL), variantTall)
        .with(new MultipartModelConditionBuilder().put(WallBlock.EAST_WALL_SHAPE, WallShape.LOW), variantLow.apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(WallBlock.EAST_WALL_SHAPE, WallShape.TALL), variantTall.apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(WallBlock.SOUTH_WALL_SHAPE, WallShape.LOW), variantLow.apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(WallBlock.SOUTH_WALL_SHAPE, WallShape.TALL), variantTall.apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(WallBlock.WEST_WALL_SHAPE, WallShape.LOW), variantLow.apply(ROTATE_Y_270).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(WallBlock.WEST_WALL_SHAPE, WallShape.TALL), variantTall.apply(ROTATE_Y_270).apply(UV_LOCK))
    );
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerSimpleWallSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    registerWallSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "simple_block", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/simple/flat", "/flat", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/simple/inner", "/inner", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/simple/outer", "/outer", textureMap)),
        createObjectModel(block, "wall_slab/simple/item", "/item", textureMap)
    );
  }

  private void registerPillarWallSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.END, rawTextureMap.get(TextureKey.END), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    registerWallSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/pillar/flat", "/flat", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/pillar/inner", "/inner", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/pillar/outer", "/outer", textureMap)),
        createObjectModel(block, "wall_slab/pillar/item", "/item", textureMap)
    );
  }

  private void registerVerticalWallSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.BOTTOM, rawTextureMap.get(TextureKey.BOTTOM), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(TextureKey.TOP), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.SIDE));
    registerWallSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "vertical_block", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/vertical/flat", "/flat", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/vertical/inner", "/inner", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/vertical/outer", "/outer", textureMap)),
        createObjectModel(block, "wall_slab/vertical/item", "/item", textureMap)
    );
  }

  private void registerWallSlabBlock(
      Block block,
      WeightedVariant variantDouble,
      WeightedVariant variantFlat,
      WeightedVariant variantInner,
      WeightedVariant variantOuter,
      Identifier inventoryModel
  ) {
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(WallSlabBlock.SHAPE, WallSlabBlock.FACING)
        .register(WallSlabShape.DOUBLE, Direction.NORTH, variantDouble)
        .register(WallSlabShape.DOUBLE, Direction.EAST, variantDouble)
        .register(WallSlabShape.DOUBLE, Direction.SOUTH, variantDouble)
        .register(WallSlabShape.DOUBLE, Direction.WEST, variantDouble)
        .register(WallSlabShape.FLAT, Direction.NORTH, variantFlat)
        .register(WallSlabShape.FLAT, Direction.EAST, variantFlat.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.FLAT, Direction.SOUTH, variantFlat.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.FLAT, Direction.WEST, variantFlat.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.INNER_LEFT, Direction.NORTH, variantInner)
        .register(WallSlabShape.INNER_LEFT, Direction.EAST, variantInner.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.INNER_LEFT, Direction.SOUTH, variantInner.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.INNER_LEFT, Direction.WEST, variantInner.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.NORTH, variantInner.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.EAST, variantInner.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.SOUTH, variantInner.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.WEST, variantInner)
        .register(WallSlabShape.OUTER_LEFT, Direction.NORTH, variantOuter)
        .register(WallSlabShape.OUTER_LEFT, Direction.EAST, variantOuter.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_LEFT, Direction.SOUTH, variantOuter.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_LEFT, Direction.WEST, variantOuter.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.NORTH, variantOuter.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.EAST, variantOuter.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.SOUTH, variantOuter.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.WEST, variantOuter)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerComplexWallSlabBlock(Block block, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMapDoubleX = Map.of(TextureKey.END, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_END), TextureKey.FRONT, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.SIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapDoubleZ = Map.of(TextureKey.END, rawTextureMap.get(DistantMoonsTextureKeys.HORIZONTAL_END), TextureKey.FRONT, rawTextureMap.get(TextureKey.SIDE), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapFlatX = Map.of(TextureKey.END, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_END), TextureKey.FRONT, rawTextureMap.get(TextureKey.SIDE), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapFlatZ = Map.of(TextureKey.END, rawTextureMap.get(DistantMoonsTextureKeys.HORIZONTAL_END), TextureKey.FRONT, rawTextureMap.get(TextureKey.SIDE), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapInnerBottomLeft = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapInnerBottomRight = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.TOP_LEFT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapInnerTopLeft = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapInnerTopRight = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.TOP_LEFT), TextureKey.INSIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), DistantMoonsTextureKeys.OUTSIDE, rawTextureMap.get(TextureKey.SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapOuterBottomLeft = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapOuterBottomRight = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.TOP_LEFT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapOuterTopLeft = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    Map<TextureKey, String> textureMapOuterTopRight = Map.of(TextureKey.BOTTOM, rawTextureMap.get(DistantMoonsTextureKeys.TOP_LEFT), TextureKey.SIDE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE), TextureKey.TOP, rawTextureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), TextureKey.PARTICLE, rawTextureMap.get(DistantMoonsTextureKeys.VERTICAL_SIDE));
    WeightedVariant variantDoubleX = createWeightedVariant(createObjectModel(block, "axis_block", "/double_x", textureMapDoubleX));
    WeightedVariant variantDoubleZ = createWeightedVariant(createObjectModel(block, "axis_block", "/double_z", textureMapDoubleZ));
    WeightedVariant variantFlatX = createWeightedVariant(createObjectModel(block, "wall_slab/complex/flat", "/flat_x", textureMapFlatX));
    WeightedVariant variantFlatZ = createWeightedVariant(createObjectModel(block, "wall_slab/complex/flat", "/flat_z", textureMapFlatZ));
    WeightedVariant variantInnerBottomLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/bottom_left", textureMapInnerBottomLeft));
    WeightedVariant variantInnerBottomRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/bottom_right", textureMapInnerBottomRight));
    WeightedVariant variantInnerTopLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/top_left", textureMapInnerTopLeft));
    WeightedVariant variantInnerTopRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/top_right", textureMapInnerTopRight));
    WeightedVariant variantOuterBottomLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/bottom_left", textureMapOuterBottomLeft));
    WeightedVariant variantOuterBottomRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/bottom_right", textureMapOuterBottomRight));
    WeightedVariant variantOuterTopLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/top_left", textureMapOuterTopLeft));
    WeightedVariant variantOuterTopRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/top_right", textureMapOuterTopRight));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(WallSlabBlock.SHAPE, WallSlabBlock.FACING)
        .register(WallSlabShape.DOUBLE, Direction.NORTH, variantDoubleZ)
        .register(WallSlabShape.DOUBLE, Direction.EAST, variantDoubleX)
        .register(WallSlabShape.DOUBLE, Direction.SOUTH, variantDoubleZ)
        .register(WallSlabShape.DOUBLE, Direction.WEST, variantDoubleX)
        .register(WallSlabShape.FLAT, Direction.NORTH, variantFlatZ)
        .register(WallSlabShape.FLAT, Direction.EAST, variantFlatX.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.FLAT, Direction.SOUTH, variantFlatZ.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.FLAT, Direction.WEST, variantFlatX.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.INNER_LEFT, Direction.NORTH, variantInnerTopLeft)
        .register(WallSlabShape.INNER_LEFT, Direction.EAST, variantInnerTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.INNER_LEFT, Direction.SOUTH, variantInnerBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.INNER_LEFT, Direction.WEST, variantInnerBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.NORTH, variantInnerTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.EAST, variantInnerBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.SOUTH, variantInnerBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.INNER_RIGHT, Direction.WEST, variantInnerTopLeft)
        .register(WallSlabShape.OUTER_LEFT, Direction.NORTH, variantOuterBottomRight)
        .register(WallSlabShape.OUTER_LEFT, Direction.EAST, variantOuterBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_LEFT, Direction.SOUTH, variantOuterTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_LEFT, Direction.WEST, variantOuterTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.NORTH, variantOuterBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.EAST, variantOuterTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.SOUTH, variantOuterTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(WallSlabShape.OUTER_RIGHT, Direction.WEST, variantOuterBottomRight)
    ));
    Identifier inventoryModel = createObjectModel(block, "wall_slab/complex/item", "/item", textureMapFlatZ);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerSimpleItem(Item item, String parent, Map<TextureKey, String> rawTextureMap) {
    Map<TextureKey, String> textureMap = Map.of(TextureKey.TEXTURE, rawTextureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, rawTextureMap.get(TextureKey.TEXTURE));
    this.itemGenerator.output.accept(item, ItemModels.basic(createObjectModel(item, parent, null, textureMap)));
  }

  private static Identifier getObjectModelPath(Block block, String suffix) {
    return UnderDistantMoons.identifierOf("object/" + DistantMoonsBlocks.getStringIdOf(block) + suffix);
  }

  private static Identifier getObjectModelPath(Item item, String suffix) {
    return UnderDistantMoons.identifierOf("object/" + DistantMoonsItems.getStringIdOf(item) + suffix);
  }

  private Identifier createObjectModel(Block block, String parent, @Nullable String variant, Map<TextureKey, String> rawTextureMap) {
    Model model = new Model(
        Optional.of(UnderDistantMoons.identifierOf("template/" + parent)),
        variant != null ? Optional.of(variant) : Optional.empty(),
        rawTextureMap.keySet().toArray(new TextureKey[0])
    );
    return model.upload(
        getObjectModelPath(block, variant == null ? "" : variant),
        createTextureMapWithKeys(block, rawTextureMap),
        this.blockGenerator.modelCollector
    );
  }

  private Identifier createObjectModel(Item item, String parent, @Nullable String variant, Map<TextureKey, String> rawTextureMap) {
    Model model = new Model(
        Optional.of(UnderDistantMoons.identifierOf("template/" + parent)),
        variant != null ? Optional.of(variant) : Optional.empty(),
        rawTextureMap.keySet().toArray(new TextureKey[0])
    );
    return model.upload(
        getObjectModelPath(item, variant == null ? "" : variant),
        createTextureMapWithKeys(item, rawTextureMap),
        this.itemGenerator.modelCollector
    );
  }

  private static Identifier getFirstEntryOf(WeightedVariant variant) {
    return variant.variants().getEntries().getFirst().value().modelId();
  }

  private static TextureMap createTextureMapWithKeys(Block block, Map<TextureKey, String> rawTextureMap) {
    final TextureMap textureMap = new TextureMap();
    for (TextureKey key : rawTextureMap.keySet()) {
      textureMap.put(key, Identifier.of(rawTextureMap.get(key).replace("%", DistantMoonsBlocks.getStringIdOf(block))));
    }
    return textureMap;
  }

  private static TextureMap createTextureMapWithKeys(Item item, Map<TextureKey, String> rawTextureMap) {
    final TextureMap textureMap = new TextureMap();
    for (TextureKey key : rawTextureMap.keySet()) {
      textureMap.put(key, Identifier.of(rawTextureMap.get(key).replace("%", DistantMoonsItems.getStringIdOf(item))));
    }
    return textureMap;
  }

  private static WeightedVariant createWeightedVariant(Identifier block) {
    return createWeightedVariant(block, "");
  }

  private static WeightedVariant createWeightedVariant(Identifier block, String sharedSuffix, String ... variantSuffixes) {
    Pool.Builder<ModelVariant> builder = new Pool.Builder<>();
    if (variantSuffixes.length == 0) builder.add(new ModelVariant(block.withSuffixedPath(sharedSuffix)));
    for (var variantSuffix : variantSuffixes) {
      builder.add(new ModelVariant(block.withSuffixedPath(sharedSuffix + variantSuffix)));
    }
    return new WeightedVariant(builder.build());
  }
}
