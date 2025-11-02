package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.block.PillarSlabBlock;
import syrenyx.distantmoons.content.block.FixedLadderBlock;
import syrenyx.distantmoons.content.block.MetalBarDoorBlock;
import syrenyx.distantmoons.content.block.SpikedFenceBlock;
import syrenyx.distantmoons.content.block.block_state_enums.FixedLadderSideShape;
import syrenyx.distantmoons.content.block.block_state_enums.HorizontalAxis;
import syrenyx.distantmoons.content.block.block_state_enums.SpikedFenceShape;
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
    registerSimpleBlock(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);

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

    //PILLAR SLABS
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_LOG, Map.of(TextureKey.END, "minecraft:block/acacia_log_top", TextureKey.SIDE, "minecraft:block/acacia_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_WOOD, Map.of(TextureKey.END, "minecraft:block/acacia_log", TextureKey.SIDE, "minecraft:block/acacia_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BAMBOO_BLOCK, Map.of(TextureKey.END, "minecraft:block/bamboo_block_top", TextureKey.SIDE, "minecraft:block/bamboo_block"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BASALT, Map.of(TextureKey.END, "minecraft:block/basalt_top", TextureKey.SIDE, "minecraft:block/basalt_side"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_LOG, Map.of(TextureKey.END, "minecraft:block/birch_log_top", TextureKey.SIDE, "minecraft:block/birch_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_WOOD, Map.of(TextureKey.END, "minecraft:block/birch_log", TextureKey.SIDE, "minecraft:block/birch_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BONE_BLOCK, Map.of(TextureKey.END, "minecraft:block/bone_block_top", TextureKey.SIDE, "minecraft:block/bone_block_side"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_LOG, Map.of(TextureKey.END, "minecraft:block/cherry_log_top", TextureKey.SIDE, "minecraft:block/cherry_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_WOOD, Map.of(TextureKey.END, "minecraft:block/cherry_log", TextureKey.SIDE, "minecraft:block/cherry_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE, Map.of(TextureKey.END, "minecraft:block/crimson_stem", TextureKey.SIDE, "minecraft:block/crimson_stem"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_STEM, Map.of(TextureKey.END, "minecraft:block/crimson_stem_top", TextureKey.SIDE, "minecraft:block/crimson_stem"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/dark_oak_log_top", TextureKey.SIDE, "minecraft:block/dark_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/dark_oak_log", TextureKey.SIDE, "minecraft:block/dark_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_DEEPSLATE, Map.of(TextureKey.END, "minecraft:block/deepslate_top", TextureKey.SIDE, "minecraft:block/deepslate"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_LOG, Map.of(TextureKey.END, "minecraft:block/jungle_log_top", TextureKey.SIDE, "minecraft:block/jungle_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_WOOD, Map.of(TextureKey.END, "minecraft:block/jungle_log", TextureKey.SIDE, "minecraft:block/jungle_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_LOG, Map.of(TextureKey.END, "minecraft:block/mangrove_log_top", TextureKey.SIDE, "minecraft:block/mangrove_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_WOOD, Map.of(TextureKey.END, "minecraft:block/mangrove_log", TextureKey.SIDE, "minecraft:block/mangrove_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/oak_log_top", TextureKey.SIDE, "minecraft:block/oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/oak_log", TextureKey.SIDE, "minecraft:block/oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/pale_oak_log_top", TextureKey.SIDE, "minecraft:block/pale_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/pale_oak_log", TextureKey.SIDE, "minecraft:block/pale_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_PURPUR_PILLAR, Map.of(TextureKey.END, "minecraft:block/purpur_pillar_top", TextureKey.SIDE, "minecraft:block/purpur_pillar"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_QUARTZ_PILLAR, Map.of(TextureKey.END, "minecraft:block/quartz_pillar_top", TextureKey.SIDE, "minecraft:block/quartz_pillar"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_LOG, Map.of(TextureKey.END, "minecraft:block/spruce_log_top", TextureKey.SIDE, "minecraft:block/spruce_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_WOOD, Map.of(TextureKey.END, "minecraft:block/spruce_log", TextureKey.SIDE, "minecraft:block/spruce_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_HYPHAE, Map.of(TextureKey.END, "minecraft:block/warped_stem", TextureKey.SIDE, "minecraft:block/warped_stem"));
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_STEM, Map.of(TextureKey.END, "minecraft:block/warped_stem_top", TextureKey.SIDE, "minecraft:block/warped_stem"));
    registerPillarSlabBlock(DistantMoonsBlocks.POLISHED_CUT_BASALT, Map.of(TextureKey.END, "minecraft:block/polished_basalt_top", TextureKey.SIDE, "minecraft:block/polished_basalt_side"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_acacia_log_top", TextureKey.SIDE, "minecraft:block/stripped_acacia_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_acacia_log", TextureKey.SIDE, "minecraft:block/stripped_acacia_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK, Map.of(TextureKey.END, "minecraft:block/stripped_bamboo_block_top", TextureKey.SIDE, "minecraft:block/stripped_bamboo_block"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_birch_log_top", TextureKey.SIDE, "minecraft:block/stripped_birch_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_birch_log", TextureKey.SIDE, "minecraft:block/stripped_birch_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_cherry_log_top", TextureKey.SIDE, "minecraft:block/stripped_cherry_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_cherry_log", TextureKey.SIDE, "minecraft:block/stripped_cherry_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE, Map.of(TextureKey.END, "minecraft:block/stripped_crimson_stem", TextureKey.SIDE, "minecraft:block/stripped_crimson_stem"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM, Map.of(TextureKey.END, "minecraft:block/stripped_crimson_stem_top", TextureKey.SIDE, "minecraft:block/stripped_crimson_stem"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_dark_oak_log_top", TextureKey.SIDE, "minecraft:block/stripped_dark_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_dark_oak_log", TextureKey.SIDE, "minecraft:block/stripped_dark_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_jungle_log_top", TextureKey.SIDE, "minecraft:block/stripped_jungle_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_jungle_log", TextureKey.SIDE, "minecraft:block/stripped_jungle_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_mangrove_log_top", TextureKey.SIDE, "minecraft:block/stripped_mangrove_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_mangrove_log", TextureKey.SIDE, "minecraft:block/stripped_mangrove_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_oak_log_top", TextureKey.SIDE, "minecraft:block/stripped_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_oak_log", TextureKey.SIDE, "minecraft:block/stripped_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_pale_oak_log_top", TextureKey.SIDE, "minecraft:block/stripped_pale_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_pale_oak_log", TextureKey.SIDE, "minecraft:block/stripped_pale_oak_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG, Map.of(TextureKey.END, "minecraft:block/stripped_spruce_log_top", TextureKey.SIDE, "minecraft:block/stripped_spruce_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD, Map.of(TextureKey.END, "minecraft:block/stripped_spruce_log", TextureKey.SIDE, "minecraft:block/stripped_spruce_log"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE, Map.of(TextureKey.END, "minecraft:block/stripped_warped_stem", TextureKey.SIDE, "minecraft:block/stripped_warped_stem"));
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM, Map.of(TextureKey.END, "minecraft:block/stripped_warped_stem_top", TextureKey.SIDE, "minecraft:block/stripped_warped_stem"));

    //SPIKED FENCES
    registerSpikedFenceBlock(DistantMoonsBlocks.DEEP_IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);
    registerSpikedFenceBlock(DistantMoonsBlocks.IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);
    registerSpikedFenceBlock(DistantMoonsBlocks.WROUGHT_IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);

    //TRAPDOORS
    registerTrapdoorBlock(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR, false, Map.of(
        TextureKey.END, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/trapdoor"),
        TextureKey.SIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/end")
    ));
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {

    this.itemGenerator = generator;

    //SIMPLE ITEMS
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
    registerSimpleItem(DistantMoonsItems.RAW_DEEP_IRON, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_INGOT, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_ROD, "stick", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.ROTTEN_FISH, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.UNDERWORLD_DUST, "simple_item", SIMPLE_ITEM_TEXTURE_MAP);
    registerSimpleItem(DistantMoonsItems.WROUGHT_IRON_ROD, "stick", SIMPLE_ITEM_TEXTURE_MAP);
  }

  private void registerSimpleBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "simple_block", null, Map.of(
        TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, variant));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variant)));
  }

  private void registerBarsBlock(Block block, boolean mirrored, Map<TextureKey, String> textureMap) {
    WeightedVariant cap = createWeightedVariant(createObjectModel(block, "metal_bars/cap/default", "/cap/default", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant cap_mirrored = createWeightedVariant(createObjectModel(block, "metal_bars/cap/mirrored", "/cap/mirrored", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant centerCaps = createWeightedVariant(createObjectModel(block, "metal_bars/center_caps", "/center_caps", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant centerPost = createWeightedVariant(createObjectModel(block, "metal_bars/center_post", "/center_post", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "metal_bars/side/default", "/side/default", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant side_left = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_left", "/side/mirrored_left", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant side_right = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_right", "/side/mirrored_right", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(centerCaps)
        .with(directionalMultipartCondition(false, false, false, false), centerPost)
        .with(directionalMultipartCondition(true, false, false, false), cap)
        .with(directionalMultipartCondition(false, true, false, false), cap.apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(directionalMultipartCondition(false, false, true, false), cap_mirrored.apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(directionalMultipartCondition(false, false, false, true), cap_mirrored.apply(ROTATE_Y_270).apply(UV_LOCK))
        .with(directionalMultipartCondition(true, null, null, null), mirrored ? side_left : side)
        .with(directionalMultipartCondition(null, true, null, null), (mirrored ? side_left : side).apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(directionalMultipartCondition(null, null, true, null), (mirrored ? side_right : side).apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(directionalMultipartCondition(null, null, null, true), (mirrored ? side_right : side).apply(ROTATE_Y_270).apply(UV_LOCK))
    );
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", Map.of(
        TextureKey.TEXTURE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerChainBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "chain", null, Map.of(
        TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(ChainBlock.AXIS)
        .register(Direction.Axis.X, variant.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.Y, variant)
        .register(Direction.Axis.Z, variant.apply(ROTATE_X_90))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", Map.of(
        TextureKey.TEXTURE, textureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, textureMap.get(TextureKey.TEXTURE)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerPillarSlabBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant bottom = createWeightedVariant(createObjectModel(block, "slab/pillar/bottom", "/bottom", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant doubleSlab = createWeightedVariant(createObjectModel(block, "pillar", null, Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant top = createWeightedVariant(createObjectModel(block, "slab/pillar/top", "/top", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(PillarSlabBlock.AXIS, PillarSlabBlock.TYPE)
        .register(Direction.Axis.X, SlabType.BOTTOM, bottom.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.X, SlabType.DOUBLE, doubleSlab.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.X, SlabType.TOP, top.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.Y, SlabType.BOTTOM, bottom)
        .register(Direction.Axis.Y, SlabType.DOUBLE, doubleSlab)
        .register(Direction.Axis.Y, SlabType.TOP, top)
        .register(Direction.Axis.Z, SlabType.BOTTOM, bottom.apply(ROTATE_X_90))
        .register(Direction.Axis.Z, SlabType.DOUBLE, doubleSlab.apply(ROTATE_X_90))
        .register(Direction.Axis.Z, SlabType.TOP, top.apply(ROTATE_X_90))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(bottom.variants().getEntries().getFirst().value().modelId()));
  }

  private void registerDoorBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant bottomLeft = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_left", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.INSIDE, textureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), DistantMoonsTextureKeys.OUTSIDE, textureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT)
    )));
    WeightedVariant bottomRight = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_right", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.INSIDE, textureMap.get(DistantMoonsTextureKeys.BOTTOM_LEFT), DistantMoonsTextureKeys.OUTSIDE, textureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.BOTTOM_RIGHT)
    )));
    WeightedVariant topLeft = createWeightedVariant(createObjectModel(block, "door/top", "/top_left", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.INSIDE, textureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), DistantMoonsTextureKeys.OUTSIDE, textureMap.get(DistantMoonsTextureKeys.TOP_LEFT), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.TOP_LEFT)
    )));
    WeightedVariant topRight = createWeightedVariant(createObjectModel(block, "door/top", "/top_right", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.INSIDE, textureMap.get(DistantMoonsTextureKeys.TOP_LEFT), DistantMoonsTextureKeys.OUTSIDE, textureMap.get(DistantMoonsTextureKeys.TOP_RIGHT), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.TOP_RIGHT)
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(DoorBlock.HALF, DoorBlock.FACING, DoorBlock.HINGE, DoorBlock.OPEN)
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, false, bottomLeft)
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, false, bottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, false, bottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, false, bottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, true, bottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, true, bottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, true, bottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, true, bottomRight)
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, false, bottomRight)
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, false, bottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, false, bottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, false, bottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, true, bottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, true, bottomLeft)
        .register(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, true, bottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, true, bottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, false, topLeft)
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, false, topLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, false, topLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, false, topLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, true, topRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, true, topRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, true, topRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, true, topRight)
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, false, topRight)
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, false, topRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, false, topRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, false, topRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, true, topLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, true, topLeft)
        .register(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, true, topLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, true, topLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", Map.of(
        TextureKey.TEXTURE, textureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, textureMap.get(TextureKey.TEXTURE)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerFixedLadderBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant center = createWeightedVariant(createObjectModel(block, "fixed_ladder/center", "/center", Map.of(
        TextureKey.BOTTOM, textureMap.get(TextureKey.BOTTOM), TextureKey.FRONT, textureMap.get(TextureKey.FRONT), TextureKey.SIDE, textureMap.get(DistantMoonsTextureKeys.CENTER), TextureKey.TOP, textureMap.get(TextureKey.TOP), TextureKey.PARTICLE, textureMap.get(TextureKey.PARTICLE)
    )));
    WeightedVariant center_caps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/center", "/caps/center", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.PARTICLE, textureMap.get(TextureKey.PARTICLE)
    )));
    WeightedVariant extension = createWeightedVariant(createObjectModel(block, "fixed_ladder/extension", "/extension", Map.of(
        TextureKey.BOTTOM, textureMap.get(TextureKey.BOTTOM), TextureKey.FRONT, textureMap.get(TextureKey.FRONT), TextureKey.TOP, textureMap.get(TextureKey.TOP), TextureKey.PARTICLE, textureMap.get(TextureKey.PARTICLE)
    )));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "fixed_ladder/side", "/side", Map.of(
        TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.PARTICLE)
    )));
    WeightedVariant side_caps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/side", "/caps/side", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.PARTICLE, textureMap.get(TextureKey.PARTICLE)
    )));
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X), center.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z), center)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), side.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), side)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), side.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), side.apply(ROTATE_Y_270))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED), extension.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED), extension)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED), extension.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED), extension.apply(ROTATE_Y_270))
        .with(new MultipartModelCombinedCondition(MultipartModelCombinedCondition.LogicalOperator.OR, List.of(
            new MultipartModelConditionBuilder().put(FixedLadderBlock.LEFT_CAPPED, true).build(),
            new MultipartModelConditionBuilder().put(FixedLadderBlock.RIGHT_CAPPED, true).build())
        ), center_caps)
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.LEFT_CAPPED, true), side_caps.apply(ROTATE_Y_180).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.X).put(FixedLadderBlock.RIGHT_CAPPED, true), side_caps.apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.LEFT_CAPPED, true), side_caps.apply(ROTATE_Y_90).apply(UV_LOCK))
        .with(new MultipartModelConditionBuilder().put(FixedLadderBlock.AXIS, HorizontalAxis.Z).put(FixedLadderBlock.RIGHT_CAPPED, true), side_caps.apply(ROTATE_Y_270).apply(UV_LOCK))
    );
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(center.variants().getEntries().getFirst().value().modelId()));
  }

  private void registerMetalBarDoorBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant doubleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_left", "/double/bottom_left", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT)
    )));
    WeightedVariant doubleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_right", "/double/bottom_right", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_LEFT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.DOUBLE_BOTTOM_RIGHT)
    )));
    WeightedVariant doubleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_left", "/double/top_left", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT)
    )));
    WeightedVariant doubleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_right", "/double/top_right", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_LEFT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.DOUBLE_TOP_RIGHT)
    )));
    WeightedVariant singleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_left", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT)
    )));
    WeightedVariant singleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_right", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_LEFT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.SINGLE_BOTTOM_RIGHT)
    )));
    WeightedVariant singleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_left", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_LEFT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_LEFT)
    )));
    WeightedVariant singleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_right", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.BACK, textureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_LEFT), TextureKey.FRONT, textureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT), TextureKey.INSIDE, textureMap.get(TextureKey.INSIDE), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.SINGLE_TOP_RIGHT)
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(MetalBarDoorBlock.DOUBLE, DoorBlock.HALF, DoorBlock.FACING, DoorBlock.HINGE, DoorBlock.OPEN)
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, false, singleBottomLeft)
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, false, singleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, false, singleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, false, singleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, true, singleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, true, singleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, true, singleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, true, singleBottomRight)
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, false, singleBottomRight)
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, false, singleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, false, singleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, false, singleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, true, singleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, true, singleBottomLeft)
        .register(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, true, singleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, true, singleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, false, singleTopLeft)
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, false, singleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, false, singleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, false, singleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, true, singleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, true, singleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, true, singleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, true, singleTopRight)
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, false, singleTopRight)
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, false, singleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, false, singleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, false, singleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, true, singleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, true, singleTopLeft)
        .register(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, true, singleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, true, singleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, false, doubleBottomLeft)
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, false, doubleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, false, doubleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, false, doubleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.LEFT, true, doubleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.LEFT, true, doubleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.LEFT, true, doubleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.LEFT, true, doubleBottomRight)
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, false, doubleBottomRight)
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, false, doubleBottomRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, false, doubleBottomRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, false, doubleBottomRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHinge.RIGHT, true, doubleBottomLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHinge.RIGHT, true, doubleBottomLeft)
        .register(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHinge.RIGHT, true, doubleBottomLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHinge.RIGHT, true, doubleBottomLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, false, doubleTopLeft)
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, false, doubleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, false, doubleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, false, doubleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.LEFT, true, doubleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.LEFT, true, doubleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.LEFT, true, doubleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.LEFT, true, doubleTopRight)
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, false, doubleTopRight)
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, false, doubleTopRight.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, false, doubleTopRight.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, false, doubleTopRight.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHinge.RIGHT, true, doubleTopLeft.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHinge.RIGHT, true, doubleTopLeft)
        .register(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHinge.RIGHT, true, doubleTopLeft.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHinge.RIGHT, true, doubleTopLeft.apply(ROTATE_Y_180).apply(UV_LOCK))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", Map.of(
        TextureKey.TEXTURE, textureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, textureMap.get(TextureKey.TEXTURE)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerMetalLadderBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "metal_ladder", "/block", Map.of(
        TextureKey.BOTTOM, textureMap.get(TextureKey.BOTTOM), DistantMoonsTextureKeys.DETAIL, textureMap.get(DistantMoonsTextureKeys.DETAIL), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), DistantMoonsTextureKeys.SUPPORT, textureMap.get(DistantMoonsTextureKeys.SUPPORT), TextureKey.TOP, textureMap.get(TextureKey.TOP), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(LadderBlock.FACING)
        .register(Direction.NORTH, variant)
        .register(Direction.EAST, variant.apply(ROTATE_Y_90))
        .register(Direction.SOUTH, variant.apply(ROTATE_Y_180))
        .register(Direction.WEST, variant.apply(ROTATE_Y_270))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", Map.of(
        TextureKey.TEXTURE, textureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, textureMap.get(TextureKey.TEXTURE)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerSpikedFenceBlock(Block block, Map<TextureKey, String> textureMap) {
    WeightedVariant pole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/pole", Map.of(
        TextureKey.BOTTOM, textureMap.get(TextureKey.BOTTOM), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.TOP, textureMap.get(TextureKey.BOTTOM), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant topPole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/top_pole", Map.of(
        TextureKey.BOTTOM, textureMap.get(TextureKey.BOTTOM), TextureKey.SIDE, textureMap.get(DistantMoonsTextureKeys.SIDE_TOP), TextureKey.TOP, textureMap.get(TextureKey.TOP), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.SIDE_TOP)
    )));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/side", Map.of(
        TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.SIDE)
    )));
    WeightedVariant topSide = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/top_side", Map.of(
        TextureKey.SIDE, textureMap.get(DistantMoonsTextureKeys.SIDE_TOP), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.SIDE_TOP)
    )));
    this.blockGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.TOP, false), pole)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.TOP, true), topPole)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.NORTH, SpikedFenceShape.SIDE), side)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.EAST, SpikedFenceShape.SIDE), side.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.SOUTH, SpikedFenceShape.SIDE), side.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.WEST, SpikedFenceShape.SIDE), side.apply(ROTATE_Y_270))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.NORTH, SpikedFenceShape.TOP), topSide)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.EAST, SpikedFenceShape.TOP), topSide.apply(ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.SOUTH, SpikedFenceShape.TOP), topSide.apply(ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.WEST, SpikedFenceShape.TOP), topSide.apply(ROTATE_Y_270))
    );
    Identifier inventoryModel = createObjectModel(block, "spiked_fence/item", "/item", Map.of(
        TextureKey.BOTTOM, textureMap.get(TextureKey.BOTTOM), TextureKey.of("lower_side"), textureMap.get(TextureKey.SIDE), TextureKey.TOP, textureMap.get(TextureKey.TOP), TextureKey.of("upper_side"), textureMap.get(DistantMoonsTextureKeys.SIDE_TOP), TextureKey.PARTICLE, textureMap.get(DistantMoonsTextureKeys.SIDE_TOP)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerTrapdoorBlock(Block block, boolean orientable, Map<TextureKey, String> textureMap) {
    WeightedVariant bottom = createWeightedVariant(createObjectModel(block, "trapdoor/bottom", "/bottom", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.END)
    )));
    WeightedVariant open = createWeightedVariant(createObjectModel(block, "trapdoor/open", "/open", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.END)
    )));
    WeightedVariant top = createWeightedVariant(createObjectModel(block, "trapdoor/top", "/top", Map.of(
        TextureKey.END, textureMap.get(TextureKey.END), TextureKey.SIDE, textureMap.get(TextureKey.SIDE), TextureKey.PARTICLE, textureMap.get(TextureKey.END)
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(TrapdoorBlock.HALF, TrapdoorBlock.FACING, TrapdoorBlock.OPEN)
        .register(BlockHalf.BOTTOM, Direction.NORTH, false, bottom)
        .register(BlockHalf.BOTTOM, Direction.EAST, false, bottom.apply(orientable ? ROTATE_Y_90 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.BOTTOM, Direction.SOUTH, false, bottom.apply(orientable ? ROTATE_Y_180 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.BOTTOM, Direction.WEST, false, bottom.apply(orientable ? ROTATE_Y_270 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.BOTTOM, Direction.NORTH, true, open)
        .register(BlockHalf.BOTTOM, Direction.EAST, true, open.apply(ROTATE_Y_90).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, Direction.SOUTH, true, open.apply(ROTATE_Y_180).apply(UV_LOCK))
        .register(BlockHalf.BOTTOM, Direction.WEST, true, open.apply(ROTATE_Y_270).apply(UV_LOCK))
        .register(BlockHalf.TOP, Direction.NORTH, false, top)
        .register(BlockHalf.TOP, Direction.EAST, false, top.apply(orientable ? ROTATE_Y_90 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.SOUTH, false, top.apply(orientable ? ROTATE_Y_180 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.WEST, false, top.apply(orientable ? ROTATE_Y_270 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.NORTH, true, open.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? ROTATE_Y_180 : NO_OP).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.EAST, true, open.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? ROTATE_Y_270 : ROTATE_Y_90).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.SOUTH, true, open.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? NO_OP : ROTATE_Y_180).apply(orientable ? UV_LOCK : NO_OP))
        .register(BlockHalf.TOP, Direction.WEST, true, open.apply(orientable ? ROTATE_X_180 : NO_OP).apply(orientable ? ROTATE_Y_90 : ROTATE_Y_270).apply(orientable ? UV_LOCK : NO_OP))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(bottom.variants().getEntries().getFirst().value().modelId()));
  }

  private void registerSimpleItem(Item item, String parent, Map<TextureKey, String> textureMap) {
    this.itemGenerator.output.accept(item, ItemModels.basic(createObjectModel(item, parent, null, Map.of(TextureKey.TEXTURE, textureMap.get(TextureKey.TEXTURE), TextureKey.PARTICLE, textureMap.get(TextureKey.TEXTURE)))));
  }

  private static String getStringIdOf(Block block) {
    return Registries.BLOCK.getEntry(block).getIdAsString().split(":")[1];
  }

  private static String getStringIdOf(Item item) {
    return Registries.ITEM.getEntry(item).getIdAsString().split(":")[1];
  }

  private static Identifier getObjectModelPath(Block block, String suffix) {
    return UnderDistantMoons.identifierOf("object/" + getStringIdOf(block) + suffix);
  }

  private static Identifier getObjectModelPath(Item item, String suffix) {
    return UnderDistantMoons.identifierOf("object/" + getStringIdOf(item) + suffix);
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
      textureMap.put(key, Identifier.of(rawTextureMap.get(key).replace("%", getStringIdOf(block))));
    }
    return textureMap;
  }

  private static TextureMap createTextureMapWithKeys(Item item, Map<TextureKey, String> rawTextureMap) {
    final TextureMap textureMap = new TextureMap();
    for (TextureKey key : rawTextureMap.keySet()) {
      textureMap.put(key, Identifier.of(rawTextureMap.get(key).replace("%", getStringIdOf(item))));
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

  private static MultipartModelConditionBuilder directionalMultipartCondition(@Nullable Boolean north, @Nullable Boolean east, @Nullable Boolean south, @Nullable Boolean west) {
    MultipartModelConditionBuilder builder = new MultipartModelConditionBuilder();
    if (north != null) builder.put(Properties.NORTH, north);
    if (east != null) builder.put(Properties.EAST, east);
    if (south != null) builder.put(Properties.SOUTH, south);
    if (west != null) builder.put(Properties.WEST, west);
    return builder;
  }
}
