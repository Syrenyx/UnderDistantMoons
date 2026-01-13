package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.block.model.multipart.CombinedCondition;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.*;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.block.*;
import syrenyx.distantmoons.content.block.block_state_enums.*;
import syrenyx.distantmoons.content.rendering.item.properties.conditional.UnderworldDimension;
import syrenyx.distantmoons.datagen.utility.ModelProviderUtil;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;
import syrenyx.distantmoons.references.DistantMoonsTextureSlot;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistantMoonsModelProvider extends FabricModelProvider {

  private static final VariantMutator NO_OP = BlockModelGenerators.NOP;
  private static final VariantMutator UV_LOCK = BlockModelGenerators.UV_LOCK;
  private static final VariantMutator ROTATE_X_90 = BlockModelGenerators.X_ROT_90;
  private static final VariantMutator ROTATE_X_180 = BlockModelGenerators.X_ROT_180;
  private static final VariantMutator ROTATE_X_270 = BlockModelGenerators.X_ROT_270;
  private static final VariantMutator ROTATE_Y_90 = BlockModelGenerators.Y_ROT_90;
  private static final VariantMutator ROTATE_Y_180 = BlockModelGenerators.Y_ROT_180;
  private static final VariantMutator ROTATE_Y_270 = BlockModelGenerators.Y_ROT_270;

  private static final Map<TextureSlot, String> SIMPLE_BLOCK_TEXTURE_MAP = Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%"));
  private static final Map<TextureSlot, String> SIMPLE_ITEM_TEXTURE_MAP = Map.of(TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%"));
  private static final Map<TextureSlot, String> CHAIN_TEXTURE_MAP = Map.of(
      TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%"),
      TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%")
  );
  private static final Map<TextureSlot, String> FIXED_LADDER_TEXTURE_MAP = Map.of(
      TextureSlot.BOTTOM, UnderDistantMoons.withPrefixedNamespace("block/%/bottom"),
      DistantMoonsTextureSlot.CENTER, UnderDistantMoons.withPrefixedNamespace("block/%/center"),
      TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      TextureSlot.FRONT, UnderDistantMoons.withPrefixedNamespace("block/%/front"),
      TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      TextureSlot.TOP, UnderDistantMoons.withPrefixedNamespace("block/%/top"),
      TextureSlot.PARTICLE, UnderDistantMoons.withPrefixedNamespace("block/%/particle")
  );
  private static final Map<TextureSlot, String> LIGHTABLE_BLOCK_TEXTURE_MAP = Map.of(
      DistantMoonsTextureSlot.LIT, UnderDistantMoons.withPrefixedNamespace("block/%/lit"),
      DistantMoonsTextureSlot.LIT_ITEM, UnderDistantMoons.withPrefixedNamespace("item/%/lit"),
      DistantMoonsTextureSlot.UNLIT, UnderDistantMoons.withPrefixedNamespace("block/%/unlit"),
      DistantMoonsTextureSlot.UNLIT_ITEM, UnderDistantMoons.withPrefixedNamespace("item/%/unlit")
  );
  private static final Map<TextureSlot, String> METAL_LADDER_TEXTURE_MAP = Map.of(
      TextureSlot.BOTTOM, UnderDistantMoons.withPrefixedNamespace("block/%/bottom"),
      DistantMoonsTextureSlot.DETAIL, UnderDistantMoons.withPrefixedNamespace("block/%/detail"),
      TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      DistantMoonsTextureSlot.SUPPORT, UnderDistantMoons.withPrefixedNamespace("block/%/support"),
      TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%"),
      TextureSlot.TOP, UnderDistantMoons.withPrefixedNamespace("block/%/top")
  );
  private static final Map<TextureSlot, String> PILLAR_TEXTURE_MAP = Map.of(
      TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side")
  );
  private static final Map<TextureSlot, String> POLE_TEXTURE_MAP = Map.of(
      TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      DistantMoonsTextureSlot.HORIZONTAL, UnderDistantMoons.withPrefixedNamespace("block/%/horizontal"),
      DistantMoonsTextureSlot.VERTICAL, UnderDistantMoons.withPrefixedNamespace("block/%/vertical")
  );
  private static final Map<TextureSlot, String> ROPE_LADDER_TEXTURE_MAP = Map.of(
      DistantMoonsTextureSlot.CEILING, UnderDistantMoons.withPrefixedNamespace("block/%/ropes/ceiling"),
      TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/%/end"),
      DistantMoonsTextureSlot.ENDS, UnderDistantMoons.withPrefixedNamespace("block/%/ropes/ends"),
      DistantMoonsTextureSlot.MIDDLE, UnderDistantMoons.withPrefixedNamespace("block/%/ropes/middle"),
      TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/%"),
      TextureSlot.PARTICLE, UnderDistantMoons.withPrefixedNamespace("block/%/particle")
  );
  private static final Map<TextureSlot, String> SPIKED_FENCE_TEXTURE_MAP = Map.of(
      TextureSlot.BOTTOM, UnderDistantMoons.withPrefixedNamespace("block/%/bottom"),
      TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/%/side"),
      TextureSlot.TOP, UnderDistantMoons.withPrefixedNamespace("block/%/top"),
      DistantMoonsTextureSlot.SIDE_TOP, UnderDistantMoons.withPrefixedNamespace("block/%/side_top"),
      TextureSlot.PARTICLE, UnderDistantMoons.withPrefixedNamespace("block/%/particle")
  );

  private BlockModelGenerators blockGenerator;
  private ItemModelGenerators itemGenerator;

  public DistantMoonsModelProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(@NonNull BlockModelGenerators generator) {

    this.blockGenerator = generator;

    //SIMPLE BLOCKS
    registerSimpleBlock(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.CHARCOAL_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.COKE_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.GRAY_PRISMARINE, SIMPLE_BLOCK_TEXTURE_MAP);
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE, Map.of(TextureSlot.SIDE, "minecraft:block/chiseled_deepslate"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE, Map.of(TextureSlot.SIDE, "minecraft:block/cobbled_deepslate"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS, Map.of(TextureSlot.SIDE, "minecraft:block/cracked_deepslate_bricks"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES, Map.of(TextureSlot.SIDE, "minecraft:block/cracked_deepslate_tiles"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS, Map.of(TextureSlot.SIDE, "minecraft:block/deepslate_bricks"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES, Map.of(TextureSlot.SIDE, "minecraft:block/deepslate_tiles"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE, Map.of(TextureSlot.SIDE, "minecraft:block/mossy_cobblestone"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE, Map.of(TextureSlot.SIDE, "minecraft:block/polished_deepslate"));
    registerSimpleBlock(DistantMoonsBlocks.INFESTED_SMOOTH_STONE, Map.of(TextureSlot.SIDE, "minecraft:block/smooth_stone"));
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
    registerSimpleBlock(DistantMoonsBlocks.WAXED_IRON_BLOCK, Map.of(TextureSlot.SIDE, "minecraft:block/iron_block"));
    registerSimpleBlock(DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/exposed_iron_block")));
    registerSimpleBlock(DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/weathered_iron_block")));
    registerSimpleBlock(DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/rusted_iron_block")));

    //BARS
    registerBarsBlock(DistantMoonsBlocks.DEEP_IRON_BARS, false, PILLAR_TEXTURE_MAP);
    registerBarsBlock(DistantMoonsBlocks.WROUGHT_IRON_BARS, false, PILLAR_TEXTURE_MAP);

    //CHAINS
    registerChainBlock(DistantMoonsBlocks.DEEP_IRON_CHAIN, CHAIN_TEXTURE_MAP);

    //DOORS
    registerDoorBlock(DistantMoonsBlocks.DEEP_IRON_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureSlot.BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/bottom_left")),
        Map.entry(DistantMoonsTextureSlot.BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/bottom_right")),
        Map.entry(TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/end")),
        Map.entry(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/side")),
        Map.entry(TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/deep_iron_door")),
        Map.entry(DistantMoonsTextureSlot.TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/top_left")),
        Map.entry(DistantMoonsTextureSlot.TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/door/top_right"))
    ));

    //FIXED LADDERS
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER, FIXED_LADDER_TEXTURE_MAP);
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_IRON_LADDER, FIXED_LADDER_TEXTURE_MAP);
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER, FIXED_LADDER_TEXTURE_MAP);

    //LANTERNS
    registerLightableLanternBlock(DistantMoonsBlocks.UNDERWORLD_LANTERN, LIGHTABLE_BLOCK_TEXTURE_MAP);

    //METAL BAR DOORS
    registerMetalBarDoorBlock(DistantMoonsBlocks.DEEP_IRON_BAR_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureSlot.DOUBLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/bottom_left")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/bottom_right")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/top_left")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/double/top_right")),
        Map.entry(TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/end")),
        Map.entry(TextureSlot.INSIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/inside")),
        Map.entry(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/side")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/bottom_left")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/bottom_right")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/top_left")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/bar_door/single/top_right")),
        Map.entry(TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/deep_iron_bar_door"))
    ));
    registerMetalBarDoorBlock(DistantMoonsBlocks.IRON_BAR_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureSlot.DOUBLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/bottom_left")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/bottom_right")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/top_left")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/double/top_right")),
        Map.entry(TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/iron_door/end")),
        Map.entry(TextureSlot.INSIDE, UnderDistantMoons.withPrefixedNamespace("block/iron_door/inside")),
        Map.entry(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/iron_door/side")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/bottom_left")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/bottom_right")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/top_left")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/iron_door/bar_door/single/top_right")),
        Map.entry(TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/iron_bar_door"))
    ));
    registerMetalBarDoorBlock(DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR, Map.ofEntries(
        Map.entry(DistantMoonsTextureSlot.DOUBLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/bottom_left")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/bottom_right")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/top_left")),
        Map.entry(DistantMoonsTextureSlot.DOUBLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/double/top_right")),
        Map.entry(TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/end")),
        Map.entry(TextureSlot.INSIDE, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/inside")),
        Map.entry(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/side")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/bottom_left")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/bottom_right")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/top_left")),
        Map.entry(DistantMoonsTextureSlot.SINGLE_TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/wrought_iron_door/bar_door/single/top_right")),
        Map.entry(TextureSlot.TEXTURE, UnderDistantMoons.withPrefixedNamespace("item/wrought_iron_bar_door"))
    ));

    //METAL LADDER BLOCKS
    registerMetalLadderBlock(DistantMoonsBlocks.DEEP_IRON_LADDER, METAL_LADDER_TEXTURE_MAP);
    registerMetalLadderBlock(DistantMoonsBlocks.IRON_LADDER, METAL_LADDER_TEXTURE_MAP);
    registerMetalLadderBlock(DistantMoonsBlocks.WROUGHT_IRON_LADDER, METAL_LADDER_TEXTURE_MAP);

    //PILLAR SLABS - SIMPLE
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_WOOD, Map.of(TextureSlot.END, "minecraft:block/acacia_log", TextureSlot.SIDE, "minecraft:block/acacia_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_BASALT, Map.of(TextureSlot.END, "minecraft:block/basalt_top", TextureSlot.SIDE, "minecraft:block/basalt_side"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_WOOD, Map.of(TextureSlot.END, "minecraft:block/birch_log", TextureSlot.SIDE, "minecraft:block/birch_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_BONE_BLOCK, Map.of(TextureSlot.END, "minecraft:block/bone_block_top", TextureSlot.SIDE, "minecraft:block/bone_block_side"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_WOOD, Map.of(TextureSlot.END, "minecraft:block/cherry_log", TextureSlot.SIDE, "minecraft:block/cherry_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE, Map.of(TextureSlot.END, "minecraft:block/crimson_stem", TextureSlot.SIDE, "minecraft:block/crimson_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_STEM, Map.of(TextureSlot.END, "minecraft:block/crimson_stem_top", TextureSlot.SIDE, "minecraft:block/crimson_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_WOOD, Map.of(TextureSlot.END, "minecraft:block/dark_oak_log", TextureSlot.SIDE, "minecraft:block/dark_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_WOOD, Map.of(TextureSlot.END, "minecraft:block/jungle_log", TextureSlot.SIDE, "minecraft:block/jungle_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_WOOD, Map.of(TextureSlot.END, "minecraft:block/mangrove_log", TextureSlot.SIDE, "minecraft:block/mangrove_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_OAK_WOOD, Map.of(TextureSlot.END, "minecraft:block/oak_log", TextureSlot.SIDE, "minecraft:block/oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_WOOD, Map.of(TextureSlot.END, "minecraft:block/pale_oak_log", TextureSlot.SIDE, "minecraft:block/pale_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_WOOD, Map.of(TextureSlot.END, "minecraft:block/spruce_log", TextureSlot.SIDE, "minecraft:block/spruce_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_HYPHAE, Map.of(TextureSlot.END, "minecraft:block/warped_stem", TextureSlot.SIDE, "minecraft:block/warped_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.POLISHED_CUT_BASALT, Map.of(TextureSlot.END, "minecraft:block/polished_basalt_top", TextureSlot.SIDE, "minecraft:block/polished_basalt_side"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_acacia_log", TextureSlot.SIDE, "minecraft:block/stripped_acacia_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_birch_log", TextureSlot.SIDE, "minecraft:block/stripped_birch_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_cherry_log", TextureSlot.SIDE, "minecraft:block/stripped_cherry_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE, Map.of(TextureSlot.END, "minecraft:block/stripped_crimson_stem", TextureSlot.SIDE, "minecraft:block/stripped_crimson_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_dark_oak_log", TextureSlot.SIDE, "minecraft:block/stripped_dark_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_jungle_log", TextureSlot.SIDE, "minecraft:block/stripped_jungle_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_mangrove_log", TextureSlot.SIDE, "minecraft:block/stripped_mangrove_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_oak_log", TextureSlot.SIDE, "minecraft:block/stripped_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_pale_oak_log", TextureSlot.SIDE, "minecraft:block/stripped_pale_oak_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD, Map.of(TextureSlot.END, "minecraft:block/stripped_spruce_log", TextureSlot.SIDE, "minecraft:block/stripped_spruce_log"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE, Map.of(TextureSlot.END, "minecraft:block/stripped_warped_stem", TextureSlot.SIDE, "minecraft:block/stripped_warped_stem"));
    registerSimplePillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_STEM, Map.of(TextureSlot.END, "minecraft:block/warped_stem_top", TextureSlot.SIDE, "minecraft:block/warped_stem"));

    //PILLAR SLABS - AXIS
    registerAxisPillarSlabBlock(DistantMoonsBlocks.CUT_BAMBOO_BLOCK, Map.of(TextureSlot.END, "minecraft:block/bamboo_block_top", TextureSlot.SIDE, "minecraft:block/bamboo_block"));
    registerAxisPillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_LOG, Map.of(TextureSlot.END, "minecraft:block/cherry_log_top", TextureSlot.SIDE, "minecraft:block/cherry_log"));
    registerAxisPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK, Map.of(TextureSlot.END, "minecraft:block/stripped_bamboo_block_top", TextureSlot.SIDE, "minecraft:block/stripped_bamboo_block"));
    registerAxisPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_cherry_log_top", TextureSlot.SIDE, "minecraft:block/stripped_cherry_log"));

    //PILLAR SLABS - HORIZONTAL
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_LOG, Map.of(TextureSlot.END, "minecraft:block/acacia_log_top", TextureSlot.SIDE, "minecraft:block/acacia_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_LOG, Map.of(TextureSlot.END, "minecraft:block/birch_log_top", TextureSlot.SIDE, "minecraft:block/birch_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_LOG, Map.of(TextureSlot.END, "minecraft:block/dark_oak_log_top", TextureSlot.SIDE, "minecraft:block/dark_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_DEEPSLATE, Map.of(TextureSlot.END, "minecraft:block/deepslate_top", TextureSlot.SIDE, "minecraft:block/deepslate"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_LOG, Map.of(TextureSlot.END, "minecraft:block/jungle_log_top", TextureSlot.SIDE, "minecraft:block/jungle_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_LOG, Map.of(TextureSlot.END, "minecraft:block/mangrove_log_top", TextureSlot.SIDE, "minecraft:block/mangrove_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_OAK_LOG, Map.of(TextureSlot.END, "minecraft:block/oak_log_top", TextureSlot.SIDE, "minecraft:block/oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_LOG, Map.of(TextureSlot.END, "minecraft:block/pale_oak_log_top", TextureSlot.SIDE, "minecraft:block/pale_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_PURPUR_PILLAR, Map.of(TextureSlot.END, "minecraft:block/purpur_pillar_top", TextureSlot.SIDE, "minecraft:block/purpur_pillar"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_QUARTZ_PILLAR, Map.of(TextureSlot.END, "minecraft:block/quartz_pillar_top", TextureSlot.SIDE, "minecraft:block/quartz_pillar"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_LOG, Map.of(TextureSlot.END, "minecraft:block/spruce_log_top", TextureSlot.SIDE, "minecraft:block/spruce_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_acacia_log_top", TextureSlot.SIDE, "minecraft:block/stripped_acacia_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_birch_log_top", TextureSlot.SIDE, "minecraft:block/stripped_birch_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM, Map.of(TextureSlot.END, "minecraft:block/stripped_crimson_stem_top", TextureSlot.SIDE, "minecraft:block/stripped_crimson_stem"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_dark_oak_log_top", TextureSlot.SIDE, "minecraft:block/stripped_dark_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_jungle_log_top", TextureSlot.SIDE, "minecraft:block/stripped_jungle_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_mangrove_log_top", TextureSlot.SIDE, "minecraft:block/stripped_mangrove_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_oak_log_top", TextureSlot.SIDE, "minecraft:block/stripped_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_pale_oak_log_top", TextureSlot.SIDE, "minecraft:block/stripped_pale_oak_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG, Map.of(TextureSlot.END, "minecraft:block/stripped_spruce_log_top", TextureSlot.SIDE, "minecraft:block/stripped_spruce_log"));
    registerHorizontalPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM, Map.of(TextureSlot.END, "minecraft:block/stripped_warped_stem_top", TextureSlot.SIDE, "minecraft:block/stripped_warped_stem"));

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
    registerSimpleSlabBlock(DistantMoonsBlocks.GRAY_PRISMARINE_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/gray_prismarine")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_bricks")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_tiles")));
    registerSimpleSlabBlock(DistantMoonsBlocks.PRISMARINE_TILE_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/prismarine_tiles")));
    DistantMoonsBlocks.DYED_PILLOWS.values().forEach(block -> registerPillarSlabBlock(block, PILLAR_TEXTURE_MAP));

    //SPIKED FENCES
    registerSpikedFenceBlock(DistantMoonsBlocks.DEEP_IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);
    registerSpikedFenceBlock(DistantMoonsBlocks.IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);
    registerSpikedFenceBlock(DistantMoonsBlocks.WROUGHT_IRON_FENCE, SPIKED_FENCE_TEXTURE_MAP);

    //STAIRS
    registerSimpleStairsBlock(DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/gray_prismarine")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_bricks")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PALE_PRISMARINE_STAIRS, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_tiles")));
    registerSimpleStairsBlock(DistantMoonsBlocks.PRISMARINE_TILE_STAIRS, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/prismarine_tiles")));

    //TRAPDOORS
    registerTrapdoorBlock(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR, false, Map.of(
        TextureSlot.END, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/trapdoor"),
        TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/deep_iron_door/end")
    ));

    //WALLS
    registerSimpleWallBlock(DistantMoonsBlocks.PALE_PRISMARINE_WALL, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));

    //WALL SLABS - SIMPLE
    registerSimpleWallSlabBlock(DistantMoonsBlocks.ACACIA_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/acacia_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.ANDESITE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/andesite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/bamboo_mosaic"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BAMBOO_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/bamboo_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BIRCH_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/birch_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.CHERRY_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/cherry_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/cobbled_deepslate"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.COBBLESTONE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/cobblestone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.CRIMSON_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/crimson_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DARK_OAK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/dark_oak_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/dark_prismarine"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/deepslate_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/deepslate_tiles"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.DIORITE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/diorite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/end_stone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/exposed_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.GRANITE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/granite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/gray_prismarine")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.JUNGLE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/jungle_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MANGROVE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/mangrove_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/mossy_cobblestone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/mossy_stone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/mud_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/nether_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.OAK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/oak_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/oxidized_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_OAK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/pale_oak_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_bricks")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine_tiles")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/pale_prismarine")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/polished_andesite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/polished_blackstone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/polished_blackstone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/polished_deepslate"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/polished_diorite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/polished_granite"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/polished_tuff"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/prismarine_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB, Map.of(TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/prismarine_tiles")));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PRISMARINE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/prismarine"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.PURPUR_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/purpur_block"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/red_nether_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/resin_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/quartz_block_bottom"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/red_sandstone_top"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/sandstone_top"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.SPRUCE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/spruce_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.STONE_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/stone_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.STONE_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/stone"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/tuff_bricks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.TUFF_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/tuff"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WARPED_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/warped_planks"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/exposed_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/oxidized_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/weathered_cut_copper"));
    registerSimpleWallSlabBlock(DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB, Map.of(TextureSlot.SIDE, "minecraft:block/weathered_cut_copper"));

    //WALL SLABS - PILLAR
    registerPillarWallSlabBlock(DistantMoonsBlocks.BLACKSTONE_WALL_SLAB, Map.of(TextureSlot.END, "minecraft:block/blackstone_top", TextureSlot.SIDE, "minecraft:block/blackstone"));
    registerPillarWallSlabBlock(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB, Map.of(TextureSlot.END, "minecraft:block/red_sandstone_top", TextureSlot.SIDE, "minecraft:block/cut_red_sandstone"));
    registerPillarWallSlabBlock(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB, Map.of(TextureSlot.END, "minecraft:block/sandstone_top", TextureSlot.SIDE, "minecraft:block/cut_sandstone"));
    registerPillarWallSlabBlock(DistantMoonsBlocks.QUARTZ_WALL_SLAB, Map.of(TextureSlot.END, "minecraft:block/quartz_block_top", TextureSlot.SIDE, "minecraft:block/quartz_block_side"));

    //WALL SLABS - VERTICAL
    registerVerticalWallSlabBlock(DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB, Map.of(TextureSlot.BOTTOM, "minecraft:block/red_sandstone_bottom", TextureSlot.SIDE, "minecraft:block/red_sandstone", TextureSlot.TOP, "minecraft:block/red_sandstone_top"));
    registerVerticalWallSlabBlock(DistantMoonsBlocks.SANDSTONE_WALL_SLAB, Map.of(TextureSlot.BOTTOM, "minecraft:block/sandstone_bottom", TextureSlot.SIDE, "minecraft:block/sandstone", TextureSlot.TOP, "minecraft:block/sandstone_top"));

    //WALL SLABS - COMPLEX
    registerComplexWallSlabBlock(DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB, Map.of(
        DistantMoonsTextureSlot.BOTTOM_LEFT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/bottom_left"),
        DistantMoonsTextureSlot.BOTTOM_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/bottom_right"),
        DistantMoonsTextureSlot.HORIZONTAL_END, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/end/horizontal"),
        DistantMoonsTextureSlot.HORIZONTAL_SIDE, "minecraft:block/smooth_stone_slab_side",
        TextureSlot.SIDE, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/side/flat"),
        DistantMoonsTextureSlot.TOP_LEFT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/top_left"),
        DistantMoonsTextureSlot.TOP_RIGHT, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_stairs/end/top_right"),
        DistantMoonsTextureSlot.VERTICAL_END, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/end/vertical"),
        DistantMoonsTextureSlot.VERTICAL_SIDE, UnderDistantMoons.withPrefixedNamespace("block/smooth_stone_slab/side/vertical")
    ));
  }

  @Override
  public void generateItemModels(@NonNull ItemModelGenerators generator) {

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

  private void registerSimpleBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    MultiVariant variant = createWeightedVariant(createObjectModel(block, "simple_block", null, textureMap));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, variant));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variant)));
  }

  private void registerBarsBlock(Block block, boolean mirrored, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapCap = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    Map<TextureSlot, String> textureMapItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    Map<TextureSlot, String> textureMapSide = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    MultiVariant variantCap = createWeightedVariant(createObjectModel(block, "metal_bars/cap/default", "/cap/default", textureMapCap));
    MultiVariant variantCapMirrored = createWeightedVariant(createObjectModel(block, "metal_bars/cap/mirrored", "/cap/mirrored", textureMapCap));
    MultiVariant variantCenterCaps = createWeightedVariant(createObjectModel(block, "metal_bars/center_caps", "/center_caps", textureMapCap));
    MultiVariant variantCenterPost = createWeightedVariant(createObjectModel(block, "metal_bars/center_post", "/center_post", textureMapCap));
    MultiVariant variantSide = createWeightedVariant(createObjectModel(block, "metal_bars/side/default", "/side/default", textureMapSide));
    MultiVariant variantSideLeft = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_left", "/side/mirrored_left", textureMapSide));
    MultiVariant variantSideRight = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_right", "/side/mirrored_right", textureMapSide));
    this.blockGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
        .with(variantCenterCaps)
        .with(ModelProviderUtil.directionalMultipartCondition(false, false, false, false), variantCenterPost)
        .with(ModelProviderUtil.directionalMultipartCondition(true, false, false, false), variantCap)
        .with(ModelProviderUtil.directionalMultipartCondition(false, true, false, false), variantCap.with(ROTATE_Y_90).with(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(false, false, true, false), variantCapMirrored.with(ROTATE_Y_180).with(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(false, false, false, true), variantCapMirrored.with(ROTATE_Y_270).with(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(true, null, null, null), mirrored ? variantSideLeft : variantSide)
        .with(ModelProviderUtil.directionalMultipartCondition(null, true, null, null), (mirrored ? variantSideLeft : variantSide).with(ROTATE_Y_90).with(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(null, null, true, null), (mirrored ? variantSideRight : variantSide).with(ROTATE_Y_180).with(UV_LOCK))
        .with(ModelProviderUtil.directionalMultipartCondition(null, null, null, true), (mirrored ? variantSideRight : variantSide).with(ROTATE_Y_270).with(UV_LOCK))
    );
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerChainBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapBlock = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    Map<TextureSlot, String> textureMapItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(TextureSlot.TEXTURE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.TEXTURE));
    MultiVariant variant = createWeightedVariant(createObjectModel(block, "chain", null, textureMapBlock));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(ChainBlock.AXIS)
        .select(Direction.Axis.X, variant.with(ROTATE_X_90).with(ROTATE_Y_90))
        .select(Direction.Axis.Y, variant)
        .select(Direction.Axis.Z, variant.with(ROTATE_X_90))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerDoorBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapBottomLeft = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_RIGHT), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_LEFT), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_LEFT));
    Map<TextureSlot, String> textureMapBottomRight = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_LEFT), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_RIGHT), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_RIGHT));
    Map<TextureSlot, String> textureMapTopLeft = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.TOP_RIGHT), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(DistantMoonsTextureSlot.TOP_LEFT), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.TOP_LEFT));
    Map<TextureSlot, String> textureMapTopRight = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.TOP_LEFT), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(DistantMoonsTextureSlot.TOP_RIGHT), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.TOP_RIGHT));
    Map<TextureSlot, String> textureMapItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(TextureSlot.TEXTURE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.TEXTURE));
    MultiVariant variantBottomLeft = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_left", textureMapBottomLeft));
    MultiVariant variantBottomRight = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_right", textureMapBottomRight));
    MultiVariant variantTopLeft = createWeightedVariant(createObjectModel(block, "door/top", "/top_left", textureMapTopLeft));
    MultiVariant variantTopRight = createWeightedVariant(createObjectModel(block, "door/top", "/top_right", textureMapTopRight));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(DoorBlock.HALF, DoorBlock.FACING, DoorBlock.HINGE, DoorBlock.OPEN)
        .select(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.LEFT, false, variantBottomLeft)
        .select(DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.LEFT, false, variantBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.LEFT, false, variantBottomLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.LEFT, false, variantBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.LEFT, true, variantBottomRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.LEFT, true, variantBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.LEFT, true, variantBottomRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.LEFT, true, variantBottomRight)
        .select(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.RIGHT, false, variantBottomRight)
        .select(DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.RIGHT, false, variantBottomRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.RIGHT, false, variantBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.RIGHT, false, variantBottomRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.RIGHT, true, variantBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.RIGHT, true, variantBottomLeft)
        .select(DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.RIGHT, true, variantBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.RIGHT, true, variantBottomLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.LEFT, false, variantTopLeft)
        .select(DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.LEFT, false, variantTopLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.LEFT, false, variantTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.LEFT, false, variantTopLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.LEFT, true, variantTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.LEFT, true, variantTopRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.LEFT, true, variantTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.LEFT, true, variantTopRight)
        .select(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.RIGHT, false, variantTopRight)
        .select(DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.RIGHT, false, variantTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.RIGHT, false, variantTopRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.RIGHT, false, variantTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.RIGHT, true, variantTopLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.RIGHT, true, variantTopLeft)
        .select(DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.RIGHT, true, variantTopLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.RIGHT, true, variantTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerFixedLadderBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapCenter = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(TextureSlot.BOTTOM), TextureSlot.FRONT, rawTextureMap.get(TextureSlot.FRONT), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.CENTER), TextureSlot.TOP, rawTextureMap.get(TextureSlot.TOP), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapCenterCaps = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapExtension = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(TextureSlot.BOTTOM), TextureSlot.FRONT, rawTextureMap.get(TextureSlot.FRONT), TextureSlot.TOP, rawTextureMap.get(TextureSlot.TOP), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapSide = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapSideCaps = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    MultiVariant variantCenter = createWeightedVariant(createObjectModel(block, "fixed_ladder/center", "/center", textureMapCenter));
    MultiVariant variantCenterCaps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/center", "/caps/center", textureMapCenterCaps));
    MultiVariant variantExtension = createWeightedVariant(createObjectModel(block, "fixed_ladder/extension", "/extension", textureMapExtension));
    MultiVariant variantSide = createWeightedVariant(createObjectModel(block, "fixed_ladder/side", "/side", textureMapSide));
    MultiVariant variantSideCaps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/side", "/caps/side", textureMapSideCaps));
    this.blockGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.X), variantCenter.with(ROTATE_Y_90))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.Z), variantCenter)
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.X).term(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide.with(ROTATE_Y_180))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.X).term(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide)
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.Z).term(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide.with(ROTATE_Y_90))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.Z).term(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED, FixedLadderSideShape.CONNECTED), variantSide.with(ROTATE_Y_270))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.X).term(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension.with(ROTATE_Y_180))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.X).term(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension)
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.Z).term(FixedLadderBlock.LEFT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension.with(ROTATE_Y_90))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.Z).term(FixedLadderBlock.RIGHT_SHAPE, FixedLadderSideShape.ATTACHED), variantExtension.with(ROTATE_Y_270))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.X).term(FixedLadderBlock.LEFT_CAPPED, true), variantSideCaps.with(ROTATE_Y_180).with(UV_LOCK))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.X).term(FixedLadderBlock.RIGHT_CAPPED, true), variantSideCaps.with(UV_LOCK))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.Z).term(FixedLadderBlock.LEFT_CAPPED, true), variantSideCaps.with(ROTATE_Y_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(FixedLadderBlock.AXIS, HorizontalAxis.Z).term(FixedLadderBlock.RIGHT_CAPPED, true), variantSideCaps.with(ROTATE_Y_270).with(UV_LOCK))
        .with(new CombinedCondition(CombinedCondition.Operation.OR, List.of(
            new ConditionBuilder().term(FixedLadderBlock.LEFT_CAPPED, true).build(),
            new ConditionBuilder().term(FixedLadderBlock.RIGHT_CAPPED, true).build())
        ), variantCenterCaps)
    );
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variantCenter)));
  }

  private void registerLightableLanternBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapLit = Map.of(TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.LIT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.LIT));
    Map<TextureSlot, String> textureMapUnlit = Map.of(TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.UNLIT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.UNLIT));
    Map<TextureSlot, String> textureMapLitItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(DistantMoonsTextureSlot.LIT_ITEM), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.LIT_ITEM));
    Map<TextureSlot, String> textureMapUnlitItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(DistantMoonsTextureSlot.UNLIT_ITEM), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.UNLIT_ITEM));
    MultiVariant variantGroundLit = createWeightedVariant(createObjectModel(block, "lantern/ground", "/ground/lit", textureMapLit));
    MultiVariant variantGroundUnlit = createWeightedVariant(createObjectModel(block, "lantern/ground", "/ground/unlit", textureMapUnlit));
    MultiVariant variantHangingLit = createWeightedVariant(createObjectModel(block, "lantern/hanging", "/hanging/lit", textureMapLit));
    MultiVariant variantHangingUnlit = createWeightedVariant(createObjectModel(block, "lantern/hanging", "/hanging/unlit", textureMapUnlit));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(BlockStateProperties.HANGING, BlockStateProperties.LIT)
        .select(false, false, variantGroundUnlit)
        .select(false, true, variantGroundLit)
        .select(true, false, variantHangingUnlit)
        .select(true, true, variantHangingLit)
    ));
    Identifier inventoryModelLit = createObjectModel(block, "simple_item", "/item/lit", textureMapLitItem);
    Identifier inventoryModelUnlit = createObjectModel(block, "simple_item", "/item/unlit", textureMapUnlitItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.conditional(new UnderworldDimension(), ItemModelUtils.plainModel(inventoryModelLit), ItemModelUtils.plainModel(inventoryModelUnlit)));
  }

  private void registerMetalBarDoorBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapDoubleBottomLeft = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_BOTTOM_RIGHT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_BOTTOM_LEFT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_BOTTOM_LEFT));
    Map<TextureSlot, String> textureMapDoubleBottomRight = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_BOTTOM_LEFT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_BOTTOM_RIGHT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_BOTTOM_RIGHT));
    Map<TextureSlot, String> textureMapDoubleTopLeft = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_TOP_RIGHT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_TOP_LEFT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_TOP_LEFT));
    Map<TextureSlot, String> textureMapDoubleTopRight = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_TOP_LEFT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_TOP_RIGHT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.DOUBLE_TOP_RIGHT));
    Map<TextureSlot, String> textureMapSingleBottomLeft = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_BOTTOM_RIGHT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_BOTTOM_LEFT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_BOTTOM_LEFT));
    Map<TextureSlot, String> textureMapSingleBottomRight = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_BOTTOM_LEFT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_BOTTOM_RIGHT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_BOTTOM_RIGHT));
    Map<TextureSlot, String> textureMapSingleTopLeft = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_TOP_RIGHT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_TOP_LEFT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_TOP_LEFT));
    Map<TextureSlot, String> textureMapSingleTopRight = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.BACK, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_TOP_LEFT), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_TOP_RIGHT), TextureSlot.INSIDE, rawTextureMap.get(TextureSlot.INSIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.SINGLE_TOP_RIGHT));
    Map<TextureSlot, String> textureMapItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(TextureSlot.TEXTURE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.TEXTURE));
    MultiVariant variantDoubleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_left", "/double/bottom_left", textureMapDoubleBottomLeft));
    MultiVariant variantDoubleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_right", "/double/bottom_right", textureMapDoubleBottomRight));
    MultiVariant variantDoubleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_left", "/double/top_left", textureMapDoubleTopLeft));
    MultiVariant variantDoubleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_right", "/double/top_right", textureMapDoubleTopRight));
    MultiVariant variantSingleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_left", textureMapSingleBottomLeft));
    MultiVariant variantSingleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_right", textureMapSingleBottomRight));
    MultiVariant variantSingleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_left", textureMapSingleTopLeft));
    MultiVariant variantSingleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_right", textureMapSingleTopRight));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(MetalBarDoorBlock.DOUBLE, DoorBlock.HALF, DoorBlock.FACING, DoorBlock.HINGE, DoorBlock.OPEN)
        .select(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.LEFT, false, variantSingleBottomLeft)
        .select(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.LEFT, false, variantSingleBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.LEFT, false, variantSingleBottomLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.LEFT, false, variantSingleBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.LEFT, true, variantSingleBottomRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.LEFT, true, variantSingleBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.LEFT, true, variantSingleBottomRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.LEFT, true, variantSingleBottomRight)
        .select(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.RIGHT, false, variantSingleBottomRight)
        .select(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.RIGHT, false, variantSingleBottomRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.RIGHT, false, variantSingleBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.RIGHT, false, variantSingleBottomRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.RIGHT, true, variantSingleBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.RIGHT, true, variantSingleBottomLeft)
        .select(false, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.RIGHT, true, variantSingleBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.RIGHT, true, variantSingleBottomLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.LEFT, false, variantSingleTopLeft)
        .select(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.LEFT, false, variantSingleTopLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.LEFT, false, variantSingleTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.LEFT, false, variantSingleTopLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.LEFT, true, variantSingleTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.LEFT, true, variantSingleTopRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.LEFT, true, variantSingleTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.LEFT, true, variantSingleTopRight)
        .select(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.RIGHT, false, variantSingleTopRight)
        .select(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.RIGHT, false, variantSingleTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.RIGHT, false, variantSingleTopRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.RIGHT, false, variantSingleTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.RIGHT, true, variantSingleTopLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.RIGHT, true, variantSingleTopLeft)
        .select(false, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.RIGHT, true, variantSingleTopLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(false, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.RIGHT, true, variantSingleTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.LEFT, false, variantDoubleBottomLeft)
        .select(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.LEFT, false, variantDoubleBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.LEFT, false, variantDoubleBottomLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.LEFT, false, variantDoubleBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.LEFT, true, variantDoubleBottomRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.LEFT, true, variantDoubleBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.LEFT, true, variantDoubleBottomRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.LEFT, true, variantDoubleBottomRight)
        .select(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.RIGHT, false, variantDoubleBottomRight)
        .select(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.RIGHT, false, variantDoubleBottomRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.RIGHT, false, variantDoubleBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.RIGHT, false, variantDoubleBottomRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.NORTH, DoorHingeSide.RIGHT, true, variantDoubleBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.EAST, DoorHingeSide.RIGHT, true, variantDoubleBottomLeft)
        .select(true, DoubleBlockHalf.LOWER, Direction.SOUTH, DoorHingeSide.RIGHT, true, variantDoubleBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.LOWER, Direction.WEST, DoorHingeSide.RIGHT, true, variantDoubleBottomLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.LEFT, false, variantDoubleTopLeft)
        .select(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.LEFT, false, variantDoubleTopLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.LEFT, false, variantDoubleTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.LEFT, false, variantDoubleTopLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.LEFT, true, variantDoubleTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.LEFT, true, variantDoubleTopRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.LEFT, true, variantDoubleTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.LEFT, true, variantDoubleTopRight)
        .select(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.RIGHT, false, variantDoubleTopRight)
        .select(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.RIGHT, false, variantDoubleTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.RIGHT, false, variantDoubleTopRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.RIGHT, false, variantDoubleTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.NORTH, DoorHingeSide.RIGHT, true, variantDoubleTopLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.EAST, DoorHingeSide.RIGHT, true, variantDoubleTopLeft)
        .select(true, DoubleBlockHalf.UPPER, Direction.SOUTH, DoorHingeSide.RIGHT, true, variantDoubleTopLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(true, DoubleBlockHalf.UPPER, Direction.WEST, DoorHingeSide.RIGHT, true, variantDoubleTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerMetalLadderBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapBlock = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(TextureSlot.BOTTOM), DistantMoonsTextureSlot.DETAIL, rawTextureMap.get(DistantMoonsTextureSlot.DETAIL), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), DistantMoonsTextureSlot.SUPPORT, rawTextureMap.get(DistantMoonsTextureSlot.SUPPORT), TextureSlot.TOP, rawTextureMap.get(TextureSlot.TOP), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    Map<TextureSlot, String> textureMapItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(TextureSlot.TEXTURE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.TEXTURE));
    MultiVariant variant = createWeightedVariant(createObjectModel(block, "metal_ladder", "/block", textureMapBlock));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(LadderBlock.FACING)
        .select(Direction.NORTH, variant)
        .select(Direction.EAST, variant.with(ROTATE_Y_90))
        .select(Direction.SOUTH, variant.with(ROTATE_Y_180))
        .select(Direction.WEST, variant.with(ROTATE_Y_270))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerSimplePillarSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    MultiVariant variantBottom = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/bottom", "/bottom_vertical", textureMap));
    MultiVariant variantDouble = createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double_vertical", textureMap));
    MultiVariant variantTop = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/top", "/top_vertical", textureMap));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(PillarSlabBlock.AXIS, PillarSlabBlock.TYPE)
        .select(Direction.Axis.X, SlabType.BOTTOM, variantBottom.with(ROTATE_X_90).with(ROTATE_Y_90))
        .select(Direction.Axis.X, SlabType.DOUBLE, variantDouble.with(ROTATE_X_90).with(ROTATE_Y_90))
        .select(Direction.Axis.X, SlabType.TOP, variantTop.with(ROTATE_X_90).with(ROTATE_Y_90))
        .select(Direction.Axis.Y, SlabType.BOTTOM, variantBottom)
        .select(Direction.Axis.Y, SlabType.DOUBLE, variantDouble)
        .select(Direction.Axis.Y, SlabType.TOP, variantTop)
        .select(Direction.Axis.Z, SlabType.BOTTOM, variantBottom.with(ROTATE_X_90))
        .select(Direction.Axis.Z, SlabType.DOUBLE, variantDouble.with(ROTATE_X_90))
        .select(Direction.Axis.Z, SlabType.TOP, variantTop.with(ROTATE_X_90))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variantBottom)));
  }

  private void registerAxisPillarSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    MultiVariant variantBottomX = createWeightedVariant(createObjectModel(block, "slab/pillar/west", "/bottom_x", textureMap));
    MultiVariant variantBottomY = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/bottom", "/bottom_y", textureMap));
    MultiVariant variantBottomZ = createWeightedVariant(createObjectModel(block, "slab/pillar/south", "/bottom_z", textureMap));
    MultiVariant variantDoubleX = createWeightedVariant(createObjectModel(block, "pillar/axis_x", "/double_x", textureMap));
    MultiVariant variantDoubleY = createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double_y", textureMap));
    MultiVariant variantDoubleZ = createWeightedVariant(createObjectModel(block, "pillar/axis_z", "/double_z", textureMap));
    MultiVariant variantTopX = createWeightedVariant(createObjectModel(block, "slab/pillar/east", "/top_x", textureMap));
    MultiVariant variantTopY = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/top", "/top_y", textureMap));
    MultiVariant variantTopZ = createWeightedVariant(createObjectModel(block, "slab/pillar/north", "/top_z", textureMap));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(PillarSlabBlock.AXIS, PillarSlabBlock.TYPE)
        .select(Direction.Axis.X, SlabType.BOTTOM, variantBottomX)
        .select(Direction.Axis.X, SlabType.DOUBLE, variantDoubleX)
        .select(Direction.Axis.X, SlabType.TOP, variantTopX)
        .select(Direction.Axis.Y, SlabType.BOTTOM, variantBottomY)
        .select(Direction.Axis.Y, SlabType.DOUBLE, variantDoubleY)
        .select(Direction.Axis.Y, SlabType.TOP, variantTopY)
        .select(Direction.Axis.Z, SlabType.BOTTOM, variantBottomZ)
        .select(Direction.Axis.Z, SlabType.DOUBLE, variantDoubleZ)
        .select(Direction.Axis.Z, SlabType.TOP, variantTopZ)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variantBottomY)));
  }

  private void registerHorizontalPillarSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    MultiVariant variantBottomHorizontal = createWeightedVariant(createObjectModel(block, "slab/pillar/horizontal/bottom", "/bottom_horizontal", textureMap));
    MultiVariant variantBottomVertical = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/bottom", "/bottom_vertical", textureMap));
    MultiVariant variantDoubleHorizontal = createWeightedVariant(createObjectModel(block, "pillar/horizontal", "/double_horizontal", textureMap));
    MultiVariant variantDoubleVertical = createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double_vertical", textureMap));
    MultiVariant variantTopHorizontal = createWeightedVariant(createObjectModel(block, "slab/pillar/horizontal/top", "/top_horizontal", textureMap));
    MultiVariant variantTopVertical = createWeightedVariant(createObjectModel(block, "slab/pillar/vertical/top", "/top_vertical", textureMap));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(PillarSlabBlock.AXIS, PillarSlabBlock.TYPE)
        .select(Direction.Axis.X, SlabType.BOTTOM, variantBottomHorizontal.with(ROTATE_X_90).with(ROTATE_Y_90))
        .select(Direction.Axis.X, SlabType.DOUBLE, variantDoubleHorizontal.with(ROTATE_X_90).with(ROTATE_Y_90))
        .select(Direction.Axis.X, SlabType.TOP, variantTopHorizontal.with(ROTATE_X_90).with(ROTATE_Y_90))
        .select(Direction.Axis.Y, SlabType.BOTTOM, variantBottomVertical)
        .select(Direction.Axis.Y, SlabType.DOUBLE, variantDoubleVertical)
        .select(Direction.Axis.Y, SlabType.TOP, variantTopVertical)
        .select(Direction.Axis.Z, SlabType.BOTTOM, variantBottomHorizontal.with(ROTATE_X_90))
        .select(Direction.Axis.Z, SlabType.DOUBLE, variantDoubleHorizontal.with(ROTATE_X_90))
        .select(Direction.Axis.Z, SlabType.TOP, variantTopHorizontal.with(ROTATE_X_90))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variantBottomHorizontal)));
  }

  private void registerPoleBlock(Block block, Map<TextureSlot, String> rawTextureMap, String parentType) {
    Map<TextureSlot, String> textureMapX = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), DistantMoonsTextureSlot.HORIZONTAL, rawTextureMap.get(DistantMoonsTextureSlot.HORIZONTAL), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL));
    Map<TextureSlot, String> textureMapY = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), DistantMoonsTextureSlot.VERTICAL, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL));
    Map<TextureSlot, String> textureMapZ = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), DistantMoonsTextureSlot.HORIZONTAL, rawTextureMap.get(DistantMoonsTextureSlot.HORIZONTAL), DistantMoonsTextureSlot.VERTICAL, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL));
    MultiVariant variantCenterX = createWeightedVariant(createObjectModel(block, parentType + "/center_x", "/center_x", textureMapX));
    MultiVariant variantCenterY = createWeightedVariant(createObjectModel(block, parentType + "/center_y", "/center_y", textureMapY));
    MultiVariant variantCenterZ = createWeightedVariant(createObjectModel(block, parentType + "/center_z", "/center_z", textureMapZ));
    MultiVariant variantExtensionX = createWeightedVariant(createObjectModel(block, parentType + "/extension_x", "/extension_x", textureMapX));
    MultiVariant variantExtensionY = createWeightedVariant(createObjectModel(block, parentType + "/extension_y", "/extension_y", textureMapY));
    MultiVariant variantExtensionZ = createWeightedVariant(createObjectModel(block, parentType + "/extension_z", "/extension_z", textureMapZ));
    this.blockGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.X), variantCenterX.with(ROTATE_X_90).with(ROTATE_Y_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.Y), variantCenterY)
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.Z), variantCenterZ.with(ROTATE_X_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.X).term(PoleBlock.UP, true), variantExtensionX.with(ROTATE_X_90).with(ROTATE_Y_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.Y).term(PoleBlock.UP, true), variantExtensionY)
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.Z).term(PoleBlock.UP, true), variantExtensionZ.with(ROTATE_X_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.X).term(PoleBlock.DOWN, true), variantExtensionX.with(ROTATE_X_270).with(ROTATE_Y_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.Y).term(PoleBlock.DOWN, true), variantExtensionY.with(ROTATE_X_180).with(UV_LOCK))
        .with(new ConditionBuilder().term(PoleBlock.AXIS, Direction.Axis.Z).term(PoleBlock.DOWN, true), variantExtensionZ.with(ROTATE_X_270).with(UV_LOCK))
    );
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variantCenterY)));
  }

  private void registerRopeLadderBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapBottom = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.ENDS), TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.MIDDLE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapInnerEnds = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.ENDS), TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.CEILING), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapInnerTop = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.MIDDLE), TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.CEILING), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapMiddle = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.MIDDLE), TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.MIDDLE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapOuterEnds = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.ENDS), TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.ENDS), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapOuterTop = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.MIDDLE), TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.ENDS), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.PARTICLE));
    Map<TextureSlot, String> textureMapItem = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(TextureSlot.TEXTURE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.TEXTURE));
    MultiVariant variantInnerBottom = createWeightedVariant(createObjectModel(block, "rope_ladder/inner", "/inner/bottom", textureMapBottom));
    MultiVariant variantInnerEnds = createWeightedVariant(createObjectModel(block, "rope_ladder/ceiling", "/inner/ends", textureMapInnerEnds));
    MultiVariant variantInnerMiddle = createWeightedVariant(createObjectModel(block, "rope_ladder/inner", "/inner/middle", textureMapMiddle));
    MultiVariant variantInnerTop = createWeightedVariant(createObjectModel(block, "rope_ladder/ceiling", "/inner/top", textureMapInnerTop));
    MultiVariant variantOuterBottom = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/bottom", textureMapBottom));
    MultiVariant variantOuterEnds = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/ends", textureMapOuterEnds));
    MultiVariant variantOuterMiddle = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/middle", textureMapMiddle));
    MultiVariant variantOuterTop = createWeightedVariant(createObjectModel(block, "rope_ladder/outer", "/outer/top", textureMapOuterTop));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(RopeLadderBlock.DIRECTION, RopeLadderBlock.TOP, RopeLadderBlock.BOTTOM)
        .select(RopeLadderDirection.NORTH, false, false, variantOuterMiddle)
        .select(RopeLadderDirection.NORTH, false, true, variantOuterBottom)
        .select(RopeLadderDirection.NORTH, true, false, variantOuterTop)
        .select(RopeLadderDirection.NORTH, true, true, variantOuterEnds)
        .select(RopeLadderDirection.EAST, false, false, variantOuterMiddle.with(ROTATE_Y_90))
        .select(RopeLadderDirection.EAST, false, true, variantOuterBottom.with(ROTATE_Y_90))
        .select(RopeLadderDirection.EAST, true, false, variantOuterTop.with(ROTATE_Y_90))
        .select(RopeLadderDirection.EAST, true, true, variantOuterEnds.with(ROTATE_Y_90))
        .select(RopeLadderDirection.SOUTH, false, false, variantOuterMiddle.with(ROTATE_Y_180))
        .select(RopeLadderDirection.SOUTH, false, true, variantOuterBottom.with(ROTATE_Y_180))
        .select(RopeLadderDirection.SOUTH, true, false, variantOuterTop.with(ROTATE_Y_180))
        .select(RopeLadderDirection.SOUTH, true, true, variantOuterEnds.with(ROTATE_Y_180))
        .select(RopeLadderDirection.WEST, false, false, variantOuterMiddle.with(ROTATE_Y_270))
        .select(RopeLadderDirection.WEST, false, true, variantOuterBottom.with(ROTATE_Y_270))
        .select(RopeLadderDirection.WEST, true, false, variantOuterTop.with(ROTATE_Y_270))
        .select(RopeLadderDirection.WEST, true, true, variantOuterEnds.with(ROTATE_Y_270))
        .select(RopeLadderDirection.X, false, false, variantInnerMiddle.with(ROTATE_Y_270))
        .select(RopeLadderDirection.X, false, true, variantInnerBottom.with(ROTATE_Y_270))
        .select(RopeLadderDirection.X, true, false, variantInnerTop.with(ROTATE_Y_270))
        .select(RopeLadderDirection.X, true, true, variantInnerEnds.with(ROTATE_Y_270))
        .select(RopeLadderDirection.Z, false, false, variantInnerMiddle)
        .select(RopeLadderDirection.Z, false, true, variantInnerBottom)
        .select(RopeLadderDirection.Z, true, false, variantInnerTop)
        .select(RopeLadderDirection.Z, true, true, variantInnerEnds)
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerSimpleSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    registerSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "slab/simple/bottom", "/bottom", textureMap)),
        createWeightedVariant(createObjectModel(block, "simple_block", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "slab/simple/top", "/top", textureMap))
    );
  }

  private void registerPillarSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    registerSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "slab/pillar/horizontal/bottom", "/bottom", textureMap)),
        createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "slab/pillar/horizontal/top", "/top", textureMap))
    );
  }

  private void registerSlabBlock(
      Block block,
      MultiVariant variantBottom,
      MultiVariant variantDouble,
      MultiVariant variantTop
  ) {
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(SlabBlock.TYPE)
        .select(SlabType.BOTTOM, variantBottom)
        .select(SlabType.DOUBLE, variantDouble)
        .select(SlabType.TOP, variantTop)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variantBottom)));
  }

  private void registerSpikedFenceBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapPole = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(TextureSlot.BOTTOM), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(TextureSlot.BOTTOM), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    Map<TextureSlot, String> textureMapSide = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    Map<TextureSlot, String> textureMapTopPole = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(TextureSlot.BOTTOM), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.SIDE_TOP), TextureSlot.TOP, rawTextureMap.get(TextureSlot.TOP), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.SIDE_TOP));
    Map<TextureSlot, String> textureMapTopSide = Map.of(TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.SIDE_TOP), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.SIDE_TOP));
    Map<TextureSlot, String> textureMapItem = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(TextureSlot.BOTTOM), DistantMoonsTextureSlot.LOWER_SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(TextureSlot.TOP), DistantMoonsTextureSlot.UPPER_SIDE, rawTextureMap.get(DistantMoonsTextureSlot.SIDE_TOP), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.SIDE_TOP));
    MultiVariant variantPole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/pole", textureMapPole));
    MultiVariant variantSide = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/side", textureMapSide));
    MultiVariant variantTopPole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/top_pole", textureMapTopPole));
    MultiVariant variantTopSide = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/top_side", textureMapTopSide));
    this.blockGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
        .with(new ConditionBuilder().term(SpikedFenceBlock.TOP, false), variantPole)
        .with(new ConditionBuilder().term(SpikedFenceBlock.TOP, true), variantTopPole)
        .with(new ConditionBuilder().term(SpikedFenceBlock.NORTH, SpikedFenceShape.SIDE), variantSide)
        .with(new ConditionBuilder().term(SpikedFenceBlock.EAST, SpikedFenceShape.SIDE), variantSide.with(ROTATE_Y_90))
        .with(new ConditionBuilder().term(SpikedFenceBlock.SOUTH, SpikedFenceShape.SIDE), variantSide.with(ROTATE_Y_180))
        .with(new ConditionBuilder().term(SpikedFenceBlock.WEST, SpikedFenceShape.SIDE), variantSide.with(ROTATE_Y_270))
        .with(new ConditionBuilder().term(SpikedFenceBlock.NORTH, SpikedFenceShape.TOP), variantTopSide)
        .with(new ConditionBuilder().term(SpikedFenceBlock.EAST, SpikedFenceShape.TOP), variantTopSide.with(ROTATE_Y_90))
        .with(new ConditionBuilder().term(SpikedFenceBlock.SOUTH, SpikedFenceShape.TOP), variantTopSide.with(ROTATE_Y_180))
        .with(new ConditionBuilder().term(SpikedFenceBlock.WEST, SpikedFenceShape.TOP), variantTopSide.with(ROTATE_Y_270))
    );
    Identifier inventoryModel = createObjectModel(block, "spiked_fence/item", "/item", textureMapItem);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerSimpleStairsBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
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
      MultiVariant variantStraight,
      MultiVariant variantInner,
      MultiVariant variantOuter,
      Identifier inventoryModel
  ) {
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(SimplifiedStairsBlock.HALF, SimplifiedStairsBlock.SHAPE, SimplifiedStairsBlock.FACING)
        .select(Half.BOTTOM, StairsShape.STRAIGHT, Direction.NORTH, variantStraight)
        .select(Half.BOTTOM, StairsShape.STRAIGHT, Direction.EAST, variantStraight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.STRAIGHT, Direction.SOUTH, variantStraight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.STRAIGHT, Direction.WEST, variantStraight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.INNER_LEFT, Direction.NORTH, variantInner)
        .select(Half.BOTTOM, StairsShape.INNER_LEFT, Direction.EAST, variantInner.with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.INNER_LEFT, Direction.SOUTH, variantInner.with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.INNER_LEFT, Direction.WEST, variantInner.with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.INNER_RIGHT, Direction.NORTH, variantInner.with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.INNER_RIGHT, Direction.EAST, variantInner.with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.INNER_RIGHT, Direction.SOUTH, variantInner.with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.INNER_RIGHT, Direction.WEST, variantInner)
        .select(Half.BOTTOM, StairsShape.OUTER_LEFT, Direction.NORTH, variantOuter)
        .select(Half.BOTTOM, StairsShape.OUTER_LEFT, Direction.EAST, variantOuter.with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.OUTER_LEFT, Direction.SOUTH, variantOuter.with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.OUTER_LEFT, Direction.WEST, variantOuter.with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.OUTER_RIGHT, Direction.NORTH, variantOuter.with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.OUTER_RIGHT, Direction.EAST, variantOuter.with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.OUTER_RIGHT, Direction.SOUTH, variantOuter.with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.BOTTOM, StairsShape.OUTER_RIGHT, Direction.WEST, variantOuter)
        .select(Half.TOP, StairsShape.STRAIGHT, Direction.NORTH, variantStraight.with(ROTATE_X_180).with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.STRAIGHT, Direction.EAST, variantStraight.with(ROTATE_X_180).with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.TOP, StairsShape.STRAIGHT, Direction.SOUTH, variantStraight.with(ROTATE_X_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.STRAIGHT, Direction.WEST, variantStraight.with(ROTATE_X_180).with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_LEFT, Direction.NORTH, variantInner.with(ROTATE_X_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_LEFT, Direction.EAST, variantInner.with(ROTATE_X_180).with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_LEFT, Direction.SOUTH, variantInner.with(ROTATE_X_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_LEFT, Direction.WEST, variantInner.with(ROTATE_X_180).with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_RIGHT, Direction.NORTH, variantInner.with(ROTATE_X_180).with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_RIGHT, Direction.EAST, variantInner.with(ROTATE_X_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_RIGHT, Direction.SOUTH, variantInner.with(ROTATE_X_180).with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.TOP, StairsShape.INNER_RIGHT, Direction.WEST, variantInner.with(ROTATE_X_180).with(ROTATE_Y_180))
        .select(Half.TOP, StairsShape.OUTER_LEFT, Direction.NORTH, variantOuter.with(ROTATE_X_180).with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.OUTER_LEFT, Direction.EAST, variantOuter.with(ROTATE_X_180).with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.TOP, StairsShape.OUTER_LEFT, Direction.SOUTH, variantOuter.with(ROTATE_X_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.OUTER_LEFT, Direction.WEST, variantOuter.with(ROTATE_X_180).with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.TOP, StairsShape.OUTER_RIGHT, Direction.NORTH, variantOuter.with(ROTATE_X_180).with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.TOP, StairsShape.OUTER_RIGHT, Direction.EAST, variantOuter.with(ROTATE_X_180).with(UV_LOCK))
        .select(Half.TOP, StairsShape.OUTER_RIGHT, Direction.SOUTH, variantOuter.with(ROTATE_X_180).with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.TOP, StairsShape.OUTER_RIGHT, Direction.WEST, variantOuter.with(ROTATE_X_180).with(ROTATE_Y_180).with(UV_LOCK))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerTrapdoorBlock(Block block, boolean orientable, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.END));
    MultiVariant variantBottom = createWeightedVariant(createObjectModel(block, "trapdoor/bottom", "/bottom", textureMap));
    MultiVariant variantOpen = createWeightedVariant(createObjectModel(block, "trapdoor/open", "/open", textureMap));
    MultiVariant variantTop = createWeightedVariant(createObjectModel(block, "trapdoor/top", "/top", textureMap));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(TrapDoorBlock.HALF, TrapDoorBlock.FACING, TrapDoorBlock.OPEN)
        .select(Half.BOTTOM, Direction.NORTH, false, variantBottom)
        .select(Half.BOTTOM, Direction.EAST, false, variantBottom.with(orientable ? ROTATE_Y_90 : NO_OP).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.BOTTOM, Direction.SOUTH, false, variantBottom.with(orientable ? ROTATE_Y_180 : NO_OP).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.BOTTOM, Direction.WEST, false, variantBottom.with(orientable ? ROTATE_Y_270 : NO_OP).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.BOTTOM, Direction.NORTH, true, variantOpen)
        .select(Half.BOTTOM, Direction.EAST, true, variantOpen.with(ROTATE_Y_90).with(UV_LOCK))
        .select(Half.BOTTOM, Direction.SOUTH, true, variantOpen.with(ROTATE_Y_180).with(UV_LOCK))
        .select(Half.BOTTOM, Direction.WEST, true, variantOpen.with(ROTATE_Y_270).with(UV_LOCK))
        .select(Half.TOP, Direction.NORTH, false, variantTop)
        .select(Half.TOP, Direction.EAST, false, variantTop.with(orientable ? ROTATE_Y_90 : NO_OP).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.TOP, Direction.SOUTH, false, variantTop.with(orientable ? ROTATE_Y_180 : NO_OP).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.TOP, Direction.WEST, false, variantTop.with(orientable ? ROTATE_Y_270 : NO_OP).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.TOP, Direction.NORTH, true, variantOpen.with(orientable ? ROTATE_X_180 : NO_OP).with(orientable ? ROTATE_Y_180 : NO_OP).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.TOP, Direction.EAST, true, variantOpen.with(orientable ? ROTATE_X_180 : NO_OP).with(orientable ? ROTATE_Y_270 : ROTATE_Y_90).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.TOP, Direction.SOUTH, true, variantOpen.with(orientable ? ROTATE_X_180 : NO_OP).with(orientable ? NO_OP : ROTATE_Y_180).with(orientable ? UV_LOCK : NO_OP))
        .select(Half.TOP, Direction.WEST, true, variantOpen.with(orientable ? ROTATE_X_180 : NO_OP).with(orientable ? ROTATE_Y_90 : ROTATE_Y_270).with(orientable ? UV_LOCK : NO_OP))
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(getFirstEntryOf(variantBottom)));
  }

  private void registerSimpleWallBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
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
      MultiVariant variantLow,
      MultiVariant variantPost,
      MultiVariant variantTall,
      Identifier inventoryModel
  ) {
    this.blockGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
        .with(new ConditionBuilder().term(WallBlock.UP, true), variantPost)
        .with(new ConditionBuilder().term(WallBlock.NORTH, WallSide.LOW), variantLow)
        .with(new ConditionBuilder().term(WallBlock.NORTH, WallSide.TALL), variantTall)
        .with(new ConditionBuilder().term(WallBlock.EAST, WallSide.LOW), variantLow.with(ROTATE_Y_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(WallBlock.EAST, WallSide.TALL), variantTall.with(ROTATE_Y_90).with(UV_LOCK))
        .with(new ConditionBuilder().term(WallBlock.SOUTH, WallSide.LOW), variantLow.with(ROTATE_Y_180).with(UV_LOCK))
        .with(new ConditionBuilder().term(WallBlock.SOUTH, WallSide.TALL), variantTall.with(ROTATE_Y_180).with(UV_LOCK))
        .with(new ConditionBuilder().term(WallBlock.WEST, WallSide.LOW), variantLow.with(ROTATE_Y_270).with(UV_LOCK))
        .with(new ConditionBuilder().term(WallBlock.WEST, WallSide.TALL), variantTall.with(ROTATE_Y_270).with(UV_LOCK))
    );
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerSimpleWallSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    registerWallSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "simple_block", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/simple/flat", "/flat", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/simple/inner", "/inner", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/simple/outer", "/outer", textureMap)),
        createObjectModel(block, "wall_slab/simple/item", "/item", textureMap)
    );
  }

  private void registerPillarWallSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.END, rawTextureMap.get(TextureSlot.END), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
    registerWallSlabBlock(
        block,
        createWeightedVariant(createObjectModel(block, "pillar/vertical", "/double", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/pillar/flat", "/flat", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/pillar/inner", "/inner", textureMap)),
        createWeightedVariant(createObjectModel(block, "wall_slab/pillar/outer", "/outer", textureMap)),
        createObjectModel(block, "wall_slab/pillar/item", "/item", textureMap)
    );
  }

  private void registerVerticalWallSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(TextureSlot.BOTTOM), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(TextureSlot.TOP), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.SIDE));
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
      MultiVariant variantDouble,
      MultiVariant variantFlat,
      MultiVariant variantInner,
      MultiVariant variantOuter,
      Identifier inventoryModel
  ) {
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(WallSlabBlock.SHAPE, WallSlabBlock.FACING)
        .select(WallSlabShape.DOUBLE, Direction.NORTH, variantDouble)
        .select(WallSlabShape.DOUBLE, Direction.EAST, variantDouble)
        .select(WallSlabShape.DOUBLE, Direction.SOUTH, variantDouble)
        .select(WallSlabShape.DOUBLE, Direction.WEST, variantDouble)
        .select(WallSlabShape.FLAT, Direction.NORTH, variantFlat)
        .select(WallSlabShape.FLAT, Direction.EAST, variantFlat.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.FLAT, Direction.SOUTH, variantFlat.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.FLAT, Direction.WEST, variantFlat.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.INNER_LEFT, Direction.NORTH, variantInner)
        .select(WallSlabShape.INNER_LEFT, Direction.EAST, variantInner.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.INNER_LEFT, Direction.SOUTH, variantInner.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.INNER_LEFT, Direction.WEST, variantInner.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.NORTH, variantInner.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.EAST, variantInner.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.SOUTH, variantInner.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.WEST, variantInner)
        .select(WallSlabShape.OUTER_LEFT, Direction.NORTH, variantOuter)
        .select(WallSlabShape.OUTER_LEFT, Direction.EAST, variantOuter.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.OUTER_LEFT, Direction.SOUTH, variantOuter.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.OUTER_LEFT, Direction.WEST, variantOuter.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.NORTH, variantOuter.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.EAST, variantOuter.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.SOUTH, variantOuter.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.WEST, variantOuter)
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerComplexWallSlabBlock(Block block, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMapDoubleX = Map.of(TextureSlot.END, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_END), TextureSlot.FRONT, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.SIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapDoubleZ = Map.of(TextureSlot.END, rawTextureMap.get(DistantMoonsTextureSlot.HORIZONTAL_END), TextureSlot.FRONT, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapFlatX = Map.of(TextureSlot.END, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_END), TextureSlot.FRONT, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapFlatZ = Map.of(TextureSlot.END, rawTextureMap.get(DistantMoonsTextureSlot.HORIZONTAL_END), TextureSlot.FRONT, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapInnerBottomLeft = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_RIGHT), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.TOP_RIGHT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapInnerBottomRight = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_LEFT), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.TOP_LEFT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapInnerTopLeft = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.TOP_RIGHT), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_RIGHT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapInnerTopRight = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.TOP_LEFT), TextureSlot.INSIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), DistantMoonsTextureSlot.OUTSIDE, rawTextureMap.get(TextureSlot.SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_LEFT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapOuterBottomLeft = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_RIGHT), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.TOP_RIGHT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapOuterBottomRight = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_LEFT), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.TOP_LEFT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapOuterTopLeft = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.TOP_RIGHT), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_RIGHT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    Map<TextureSlot, String> textureMapOuterTopRight = Map.of(TextureSlot.BOTTOM, rawTextureMap.get(DistantMoonsTextureSlot.TOP_LEFT), TextureSlot.SIDE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE), TextureSlot.TOP, rawTextureMap.get(DistantMoonsTextureSlot.BOTTOM_LEFT), TextureSlot.PARTICLE, rawTextureMap.get(DistantMoonsTextureSlot.VERTICAL_SIDE));
    MultiVariant variantDoubleX = createWeightedVariant(createObjectModel(block, "axis_block", "/double_x", textureMapDoubleX));
    MultiVariant variantDoubleZ = createWeightedVariant(createObjectModel(block, "axis_block", "/double_z", textureMapDoubleZ));
    MultiVariant variantFlatX = createWeightedVariant(createObjectModel(block, "wall_slab/complex/flat", "/flat_x", textureMapFlatX));
    MultiVariant variantFlatZ = createWeightedVariant(createObjectModel(block, "wall_slab/complex/flat", "/flat_z", textureMapFlatZ));
    MultiVariant variantInnerBottomLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/bottom_left", textureMapInnerBottomLeft));
    MultiVariant variantInnerBottomRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/bottom_right", textureMapInnerBottomRight));
    MultiVariant variantInnerTopLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/top_left", textureMapInnerTopLeft));
    MultiVariant variantInnerTopRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/inner", "/inner/top_right", textureMapInnerTopRight));
    MultiVariant variantOuterBottomLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/bottom_left", textureMapOuterBottomLeft));
    MultiVariant variantOuterBottomRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/bottom_right", textureMapOuterBottomRight));
    MultiVariant variantOuterTopLeft = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/top_left", textureMapOuterTopLeft));
    MultiVariant variantOuterTopRight = createWeightedVariant(createObjectModel(block, "wall_slab/complex/outer", "/outer/top_right", textureMapOuterTopRight));
    this.blockGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
        .initial(WallSlabBlock.SHAPE, WallSlabBlock.FACING)
        .select(WallSlabShape.DOUBLE, Direction.NORTH, variantDoubleZ)
        .select(WallSlabShape.DOUBLE, Direction.EAST, variantDoubleX)
        .select(WallSlabShape.DOUBLE, Direction.SOUTH, variantDoubleZ)
        .select(WallSlabShape.DOUBLE, Direction.WEST, variantDoubleX)
        .select(WallSlabShape.FLAT, Direction.NORTH, variantFlatZ)
        .select(WallSlabShape.FLAT, Direction.EAST, variantFlatX.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.FLAT, Direction.SOUTH, variantFlatZ.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.FLAT, Direction.WEST, variantFlatX.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.INNER_LEFT, Direction.NORTH, variantInnerTopLeft)
        .select(WallSlabShape.INNER_LEFT, Direction.EAST, variantInnerTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.INNER_LEFT, Direction.SOUTH, variantInnerBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.INNER_LEFT, Direction.WEST, variantInnerBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.NORTH, variantInnerTopRight.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.EAST, variantInnerBottomRight.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.SOUTH, variantInnerBottomLeft.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.INNER_RIGHT, Direction.WEST, variantInnerTopLeft)
        .select(WallSlabShape.OUTER_LEFT, Direction.NORTH, variantOuterBottomRight)
        .select(WallSlabShape.OUTER_LEFT, Direction.EAST, variantOuterBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.OUTER_LEFT, Direction.SOUTH, variantOuterTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.OUTER_LEFT, Direction.WEST, variantOuterTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.NORTH, variantOuterBottomLeft.with(ROTATE_Y_90).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.EAST, variantOuterTopLeft.with(ROTATE_Y_180).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.SOUTH, variantOuterTopRight.with(ROTATE_Y_270).with(UV_LOCK))
        .select(WallSlabShape.OUTER_RIGHT, Direction.WEST, variantOuterBottomRight)
    ));
    Identifier inventoryModel = createObjectModel(block, "wall_slab/complex/item", "/item", textureMapFlatZ);
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inventoryModel));
  }

  private void registerSimpleItem(Item item, String parent, Map<TextureSlot, String> rawTextureMap) {
    Map<TextureSlot, String> textureMap = Map.of(TextureSlot.TEXTURE, rawTextureMap.get(TextureSlot.TEXTURE), TextureSlot.PARTICLE, rawTextureMap.get(TextureSlot.TEXTURE));
    this.itemGenerator.itemModelOutput.accept(item, ItemModelUtils.plainModel(createObjectModel(item, parent, null, textureMap)));
  }

  private static Identifier getObjectModelPath(Block block, String suffix) {
    return UnderDistantMoons.identifierOf("object/" + DistantMoonsBlocks.getStringIdOf(block) + suffix);
  }

  private static Identifier getObjectModelPath(Item item, String suffix) {
    return UnderDistantMoons.identifierOf("object/" + DistantMoonsItems.getStringIdOf(item) + suffix);
  }

  private Identifier createObjectModel(Block block, String parent, @Nullable String variant, Map<TextureSlot, String> rawTextureMap) {
    ModelTemplate model = new ModelTemplate(
        Optional.of(UnderDistantMoons.identifierOf("template/" + parent)),
        variant != null ? Optional.of(variant) : Optional.empty(),
        rawTextureMap.keySet().toArray(new TextureSlot[0])
    );
    return model.create(
        getObjectModelPath(block, variant == null ? "" : variant),
        createTextureMapWithKeys(block, rawTextureMap),
        this.blockGenerator.modelOutput
    );
  }

  private Identifier createObjectModel(Item item, String parent, @Nullable String variant, Map<TextureSlot, String> rawTextureMap) {
    ModelTemplate model = new ModelTemplate(
        Optional.of(UnderDistantMoons.identifierOf("template/" + parent)),
        variant != null ? Optional.of(variant) : Optional.empty(),
        rawTextureMap.keySet().toArray(new TextureSlot[0])
    );
    return model.create(
        getObjectModelPath(item, variant == null ? "" : variant),
        createTextureMapWithKeys(item, rawTextureMap),
        this.itemGenerator.modelOutput
    );
  }

  private static Identifier getFirstEntryOf(MultiVariant variant) {
    return variant.variants().unwrap().getFirst().value().modelLocation();
  }

  private static TextureMapping createTextureMapWithKeys(Block block, Map<TextureSlot, String> rawTextureMap) {
    final TextureMapping textureMap = new TextureMapping();
    for (TextureSlot key : rawTextureMap.keySet()) {
      textureMap.put(key, Identifier.parse(rawTextureMap.get(key).replace("%", DistantMoonsBlocks.getStringIdOf(block))));
    }
    return textureMap;
  }

  private static TextureMapping createTextureMapWithKeys(Item item, Map<TextureSlot, String> rawTextureMap) {
    final TextureMapping textureMap = new TextureMapping();
    for (TextureSlot key : rawTextureMap.keySet()) {
      textureMap.put(key, Identifier.parse(rawTextureMap.get(key).replace("%", DistantMoonsItems.getStringIdOf(item))));
    }
    return textureMap;
  }

  private static MultiVariant createWeightedVariant(Identifier block) {
    return createWeightedVariant(block, "");
  }

  private static MultiVariant createWeightedVariant(Identifier block, String sharedSuffix, String ... variantSuffixes) {
    WeightedList.Builder<Variant> builder = new WeightedList.Builder<>();
    if (variantSuffixes.length == 0) builder.add(new Variant(block.withSuffix(sharedSuffix)));
    for (var variantSuffix : variantSuffixes) {
      builder.add(new Variant(block.withSuffix(sharedSuffix + variantSuffix)));
    }
    return new MultiVariant(builder.build());
  }
}
