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

  private BlockStateModelGenerator blockGenerator;
  private ItemModelGenerator itemGenerator;

  public DistantMoonsModelProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator generator) {

    this.blockGenerator = generator;

    //SIMPLE BLOCKS
    registerSimpleBlock(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE);
    registerSimpleBlock(DistantMoonsBlocks.CHARCOAL_BLOCK);
    registerSimpleBlock(DistantMoonsBlocks.COKE_BLOCK);
    registerSimpleBlock(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
    registerSimpleBlock(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE);
    registerSimpleBlock(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE);
    registerSimpleBlock(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
    registerSimpleBlock(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);

    //SIMPLE DIRECTIONAL BLOCKS
    registerMetalLadderBlock(DistantMoonsBlocks.DEEP_IRON_LADDER);
    registerMetalLadderBlock(DistantMoonsBlocks.IRON_LADDER);
    registerMetalLadderBlock(DistantMoonsBlocks.WROUGHT_IRON_LADDER);

    //CHAINS
    registerChainBlock(DistantMoonsBlocks.DEEP_IRON_CHAIN);

    //CUT PILLAR SLABS
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_ACACIA_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BAMBOO_BLOCK);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BASALT);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BIRCH_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_BONE_BLOCK);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CHERRY_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_CRIMSON_STEM);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_DARK_OAK_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_DEEPSLATE);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_JUNGLE_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_MANGROVE_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_OAK_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_OAK_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_PALE_OAK_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_PURPUR_PILLAR);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_QUARTZ_PILLAR);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_SPRUCE_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_HYPHAE);
    registerPillarSlabBlock(DistantMoonsBlocks.CUT_WARPED_STEM);
    registerPillarSlabBlock(DistantMoonsBlocks.POLISHED_CUT_BASALT);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE);
    registerPillarSlabBlock(DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM);

    //DOORS
    registerDoorBlock(DistantMoonsBlocks.DEEP_IRON_DOOR);

    //FIXED LADDERS
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER);
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_IRON_LADDER);
    registerFixedLadderBlock(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER);

    //METAL BAR DOORS
    registerMetalBarDoorBlock(DistantMoonsBlocks.DEEP_IRON_BAR_DOOR);
    registerMetalBarDoorBlock(DistantMoonsBlocks.IRON_BAR_DOOR);
    registerMetalBarDoorBlock(DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR);

    //BARS
    registerMetalBarsBlock(DistantMoonsBlocks.DEEP_IRON_BARS, false);
    registerMetalBarsBlock(DistantMoonsBlocks.WROUGHT_IRON_BARS, false);

    //SPIKED FENCES
    registerSpikedFenceBlock(DistantMoonsBlocks.DEEP_IRON_FENCE);
    registerSpikedFenceBlock(DistantMoonsBlocks.IRON_FENCE);
    registerSpikedFenceBlock(DistantMoonsBlocks.WROUGHT_IRON_FENCE);

    //TRAPDOORS
    registerTrapdoorBlock(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR, false);
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {

    //SIMPLE ITEMS
    registerSimpleItem(DistantMoonsItems.COKE, "simple_item");
    registerSimpleItem(DistantMoonsItems.COPPER_ROD, "stick");
    registerSimpleItem(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, "simple_item");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_AXE, "axe");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_BOOTS, "simple_item");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_CHESTPLATE, "simple_item");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_HELMET, "simple_item");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_HOE, "hoe");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_HORSE_ARMOR, "simple_item");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_LEGGINGS, "simple_item");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_PICKAXE, "pickaxe");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_SHOVEL, "shovel");
    registerSimpleItem(DistantMoonsItems.DEEP_IRON_SWORD, "sword");
    registerSimpleItem(DistantMoonsItems.IRON_ROD, "stick");
    registerSimpleItem(DistantMoonsItems.RAW_DEEP_IRON, "simple_item");
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_INGOT, "simple_item");
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, "simple_item");
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_ROD, "stick");
    registerSimpleItem(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, "simple_item");
    registerSimpleItem(DistantMoonsItems.ROTTEN_FISH, "simple_item");
    registerSimpleItem(DistantMoonsItems.UNDERWORLD_DUST, "simple_item");
    registerSimpleItem(DistantMoonsItems.WROUGHT_IRON_ROD, "stick");
  }

  private void registerSimpleBlock(Block block) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "simple_block", null, blockGenerator, Map.of(
        TextureKey.SIDE, "", TextureKey.PARTICLE, ""
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, variant));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variant)));
  }

  private void registerChainBlock(Block block) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "chain", null, this.blockGenerator, Map.of(
        TextureKey.SIDE, "/block", TextureKey.PARTICLE, "/block"
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(ChainBlock.AXIS)
        .register(Direction.Axis.X, variant.apply(ROTATE_X_90).apply(ROTATE_Y_90))
        .register(Direction.Axis.Y, variant)
        .register(Direction.Axis.Z, variant.apply(ROTATE_X_90))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", this.blockGenerator, Map.of(
        TextureKey.TEXTURE, "/item", TextureKey.PARTICLE, "/item"
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerPillarSlabBlock(Block block) {
    WeightedVariant bottom = createWeightedVariant(createObjectModel(block, "slab/pillar/bottom", "/bottom", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant doubleSlab = createWeightedVariant(createObjectModel(block, "pillar", null, this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant top = createWeightedVariant(createObjectModel(block, "slab/pillar/top", "/top", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side"
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

  private void registerDoorBlock(Block block) {
    WeightedVariant bottomLeft = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_left", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.INSIDE, "/front/bottom_right", TextureKey.of("outside"), "/front/bottom_left", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/front/bottom_left"
    )));
    WeightedVariant bottomRight = createWeightedVariant(createObjectModel(block, "door/bottom", "/bottom_right", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.INSIDE, "/front/bottom_left", TextureKey.of("outside"), "/front/bottom_right", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/front/bottom_right"
    )));
    WeightedVariant topLeft = createWeightedVariant(createObjectModel(block, "door/top", "/top_left", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.INSIDE, "/front/top_right", TextureKey.of("outside"), "/front/top_left", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/front/top_left"
    )));
    WeightedVariant topRight = createWeightedVariant(createObjectModel(block, "door/top", "/top_right", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.INSIDE, "/front/top_left", TextureKey.of("outside"), "/front/top_right", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/front/top_right"
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
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", this.blockGenerator, Map.of(
        TextureKey.TEXTURE, "/item", TextureKey.PARTICLE, "/item"
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerFixedLadderBlock(Block block) {
    WeightedVariant center = createWeightedVariant(createObjectModel(block, "fixed_ladder/center", "/center", this.blockGenerator, Map.of(
        TextureKey.BOTTOM, "/bottom", TextureKey.FRONT, "/front", TextureKey.SIDE, "/center", TextureKey.TOP, "/top", TextureKey.PARTICLE, "/particle"
    )));
    WeightedVariant center_caps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/center", "/caps/center", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.PARTICLE, "/particle"
    )));
    WeightedVariant extension = createWeightedVariant(createObjectModel(block, "fixed_ladder/extension", "/extension", this.blockGenerator, Map.of(
        TextureKey.BOTTOM, "/bottom", TextureKey.FRONT, "/front", TextureKey.TOP, "/top", TextureKey.PARTICLE, "/particle"
    )));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "fixed_ladder/side", "/side", this.blockGenerator, Map.of(
        TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/particle"
    )));
    WeightedVariant side_caps = createWeightedVariant(createObjectModel(block, "fixed_ladder/caps/side", "/caps/side", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.PARTICLE, "/particle"
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

  private void registerMetalBarDoorBlock(Block block) {
    WeightedVariant doubleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_left", "/double/bottom_left", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/double/bottom_right", TextureKey.FRONT, "/double/bottom_left", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/double/bottom_left"
    )));
    WeightedVariant doubleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/bottom_right", "/double/bottom_right", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/double/bottom_left", TextureKey.FRONT, "/double/bottom_right", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/double/bottom_right"
    )));
    WeightedVariant doubleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_left", "/double/top_left", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/double/top_right", TextureKey.FRONT, "/double/top_left", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/double/top_left"
    )));
    WeightedVariant doubleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/double/top_right", "/double/top_right", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/double/top_left", TextureKey.FRONT, "/double/top_right", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/double/top_right"
    )));
    WeightedVariant singleBottomLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_left", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/single/bottom_right", TextureKey.FRONT, "/single/bottom_left", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/single/bottom_left"
    )));
    WeightedVariant singleBottomRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/bottom", "/single/bottom_right", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/single/bottom_left", TextureKey.FRONT, "/single/bottom_right", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/single/bottom_right"
    )));
    WeightedVariant singleTopLeft = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_left", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/single/top_right", TextureKey.FRONT, "/single/top_left", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/single/top_left"
    )));
    WeightedVariant singleTopRight = createWeightedVariant(createObjectModel(block, "metal_bar_door/single/top", "/single/top_right", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.BACK, "/single/top_left", TextureKey.FRONT, "/single/top_right", TextureKey.INSIDE, "/inside", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/single/top_right"
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
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", this.blockGenerator, Map.of(
        TextureKey.TEXTURE, "/item", TextureKey.PARTICLE, "/item"
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerMetalLadderBlock(Block block) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "metal_ladder", "/block", this.blockGenerator, Map.of(
        TextureKey.BOTTOM, "/bottom", TextureKey.of("detail"), "/detail", TextureKey.SIDE, "/side", TextureKey.of("support"), "/support", TextureKey.TOP, "/top", TextureKey.PARTICLE, "/side"
    )));
    this.blockGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(LadderBlock.FACING)
        .register(Direction.NORTH, variant)
        .register(Direction.EAST, variant.apply(ROTATE_Y_90))
        .register(Direction.SOUTH, variant.apply(ROTATE_Y_180))
        .register(Direction.WEST, variant.apply(ROTATE_Y_270))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", this.blockGenerator, Map.of(
        TextureKey.TEXTURE, "/item", TextureKey.PARTICLE, "/item"
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerMetalBarsBlock(Block block, boolean mirrored) {
    WeightedVariant centerCaps = createWeightedVariant(createObjectModel(block, "metal_bars/center_caps", "/center_caps", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant centerPost = createWeightedVariant(createObjectModel(block, "metal_bars/center_post", "/center_post", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant cap = createWeightedVariant(createObjectModel(block, "metal_bars/cap/default", "/cap/default", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant cap_mirrored = createWeightedVariant(createObjectModel(block, "metal_bars/cap/mirrored", "/cap/mirrored", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "metal_bars/side/default", "/side/default", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant side_left = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_left", "/side/mirrored_left", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant side_right = createWeightedVariant(createObjectModel(block, "metal_bars/side/mirrored_right", "/side/mirrored_right", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side"
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
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", this.blockGenerator, Map.of(
        TextureKey.TEXTURE, "/side", TextureKey.PARTICLE, "/side"
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerSpikedFenceBlock(Block block) {
    WeightedVariant pole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/pole", this.blockGenerator, Map.of(
        TextureKey.BOTTOM, "/bottom", TextureKey.SIDE, "/side", TextureKey.TOP, "/bottom", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant topPole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/top_pole", this.blockGenerator, Map.of(
        TextureKey.BOTTOM, "/bottom", TextureKey.SIDE, "/side_top", TextureKey.TOP, "/top", TextureKey.PARTICLE, "/side_top"
    )));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/side", this.blockGenerator, Map.of(
        TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side"
    )));
    WeightedVariant topSide = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/top_side", this.blockGenerator, Map.of(
        TextureKey.SIDE, "/side_top", TextureKey.PARTICLE, "/side_top"
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
    Identifier inventoryModel = createObjectModel(block, "spiked_fence/item", "/item", this.blockGenerator, Map.of(
        TextureKey.BOTTOM, "/bottom", TextureKey.of("lower_side"), "/side", TextureKey.TOP, "/top", TextureKey.of("upper_side"), "/side_top", TextureKey.PARTICLE, "/side_top"
    ));
    this.blockGenerator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private void registerTrapdoorBlock(Block block, boolean orientable) {
    WeightedVariant bottom = createWeightedVariant(createObjectModel(block, "trapdoor/bottom", "/bottom", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/end"
    )));
    WeightedVariant open = createWeightedVariant(createObjectModel(block, "trapdoor/open", "/open", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/end"
    )));
    WeightedVariant top = createWeightedVariant(createObjectModel(block, "trapdoor/top", "/top", this.blockGenerator, Map.of(
        TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/end"
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

  private void registerSimpleItem(Item item, String parent) {
    this.itemGenerator.output.accept(item, ItemModels.basic(createObjectModel(item, parent, null, itemGenerator, Map.of(TextureKey.TEXTURE, ""))));
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

  private static Identifier createObjectModel(Block block, String parent, @Nullable String variant, BlockStateModelGenerator generator, Map<TextureKey, String> textureKeys) {
    Model model;
    if (variant == null) model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.empty(), textureKeys.keySet().toArray(new TextureKey[0]));
    else model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.of(variant), textureKeys.keySet().toArray(new TextureKey[0]));
    return model.upload(getObjectModelPath(block, variant == null ? "" : variant), createTextureMapWithKeys(block, textureKeys), generator.modelCollector);
  }

  private static Identifier createObjectModel(Item item, String parent, @Nullable String variant, ItemModelGenerator generator, Map<TextureKey, String> textureKeys) {
    Model model;
    if (variant == null) model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.empty(), textureKeys.keySet().toArray(new TextureKey[0]));
    else model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.of(variant), textureKeys.keySet().toArray(new TextureKey[0]));
    return model.upload(getObjectModelPath(item, variant == null ? "" : variant), createTextureMapWithKeys(item, textureKeys), generator.modelCollector);
  }

  private static Identifier getFirstEntryOf(WeightedVariant variant) {
    return variant.variants().getEntries().getFirst().value().modelId();
  }

  private static TextureMap createTextureMapWithKeys(Block block, Map<TextureKey, String> textureKeys) {
    final TextureMap textureMap = new TextureMap();
    for (TextureKey key : textureKeys.keySet()) {
      textureMap.put(key, UnderDistantMoons.identifierOf("block/" + getStringIdOf(block) + textureKeys.get(key)));
    }
    return textureMap;
  }

  private static TextureMap createTextureMapWithKeys(Item item, Map<TextureKey, String> textureKeys) {
    final TextureMap textureMap = new TextureMap();
    for (TextureKey key : textureKeys.keySet()) {
      textureMap.put(key, UnderDistantMoons.identifierOf("item/" + getStringIdOf(item) + textureKeys.get(key)));
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
