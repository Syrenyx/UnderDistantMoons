package syrenyx.distantmoons.datagen;

import com.google.common.collect.BiMap;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.LadderBlock;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.MultipartModelConditionBuilder;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.block.SpikedFenceBlock;
import syrenyx.distantmoons.block.block_state_enums.SpikedFenceShape;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

import java.util.Map;
import java.util.Optional;

public class DistantMoonsModelProvider extends FabricModelProvider {

  public DistantMoonsModelProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator generator) {

    //SIMPLE BLOCKS
    registerSimpleBlock(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE, generator);
    registerSimpleBlock(DistantMoonsBlocks.CHARCOAL_BLOCK, generator);
    registerSimpleBlock(DistantMoonsBlocks.COKE_BLOCK, generator);
    registerSimpleBlock(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK, generator);
    registerSimpleBlock(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, generator);
    registerSimpleBlock(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, generator);
    registerSimpleBlock(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK, generator);
    registerSimpleBlock(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK, generator);

    //SIMPLE DIRECTIONAL BLOCKS
    registerMetalLadderBlock(DistantMoonsBlocks.IRON_LADDER, generator);
    registerMetalLadderBlock(DistantMoonsBlocks.REFINED_DEEP_IRON_LADDER, generator);
    registerMetalLadderBlock(DistantMoonsBlocks.WROUGHT_IRON_LADDER, generator);

    //METAL BARS
    registerMetalBars(DistantMoonsBlocks.REFINED_DEEP_IRON_BARS, generator);
    registerMetalBars(DistantMoonsBlocks.WROUGHT_IRON_BARS, generator);

    //SPIKED FENCES
    registerSpikedFence(DistantMoonsBlocks.IRON_FENCE, generator);
    registerSpikedFence(DistantMoonsBlocks.REFINED_DEEP_IRON_FENCE, generator);
    registerSpikedFence(DistantMoonsBlocks.WROUGHT_IRON_FENCE, generator);
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {
    registerSimpleItem(DistantMoonsItems.COKE, "simple_item", generator);
    registerSimpleItem(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, "simple_item", generator);
    registerSimpleItem(DistantMoonsItems.IRON_ROD, "stick", generator);
    registerSimpleItem(DistantMoonsItems.RAW_DEEP_IRON, "simple_item", generator);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_INGOT, "simple_item", generator);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, "simple_item", generator);
    registerSimpleItem(DistantMoonsItems.REFINED_DEEP_IRON_ROD, "stick", generator);
    registerSimpleItem(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, "simple_item", generator);
    registerSimpleItem(DistantMoonsItems.UNDERWORLD_DUST, "simple_item", generator);
    registerSimpleItem(DistantMoonsItems.WROUGHT_IRON_ROD, "stick", generator);
  }

  private static void registerSimpleBlock(Block block, BlockStateModelGenerator generator) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "simple_block", null, false, generator, Map.of(TextureKey.SIDE, "", TextureKey.PARTICLE, "")));
    generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, variant));
    generator.itemModelOutput.accept(block.asItem(), ItemModels.basic(getFirstEntryOf(variant)));
  }

  private static void registerMetalLadderBlock(Block block, BlockStateModelGenerator generator) {
    WeightedVariant variant = createWeightedVariant(createObjectModel(block, "metal_ladder", "/block", true, generator, Map.of(TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side")));
    generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(BlockStateVariantMap
        .models(LadderBlock.FACING)
        .register(Direction.NORTH, variant)
        .register(Direction.EAST, variant.apply(BlockStateModelGenerator.ROTATE_Y_90))
        .register(Direction.SOUTH, variant.apply(BlockStateModelGenerator.ROTATE_Y_180))
        .register(Direction.WEST, variant.apply(BlockStateModelGenerator.ROTATE_Y_270))
    ));
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", false, generator, Map.of(TextureKey.TEXTURE, "/item", TextureKey.PARTICLE, "/item"));
    generator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private static void registerMetalBars(Block block, BlockStateModelGenerator generator) {
    WeightedVariant centerCaps = createWeightedVariant(createObjectModel(block, "metal_bars/center_caps", "/center_caps", true, generator, Map.of(TextureKey.END, "/end", TextureKey.PARTICLE, "/side")));
    WeightedVariant centerPost = createWeightedVariant(createObjectModel(block, "metal_bars/center_post", "/center_post", true, generator, Map.of(TextureKey.END, "/end", TextureKey.PARTICLE, "/side")));
    WeightedVariant cap = createWeightedVariant(createObjectModel(block, "metal_bars/cap", "/cap", true, generator, Map.of(TextureKey.END, "/end", TextureKey.PARTICLE, "/side")));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "metal_bars/side", "/side", true, generator, Map.of(TextureKey.END, "/end", TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side")));
    generator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(centerCaps)
        .with(directionalMultipartCondition(false, false, false, false), centerPost)
        .with(directionalMultipartCondition(true, false, false, false), cap)
        .with(directionalMultipartCondition(false, true, false, false), cap.apply(BlockStateModelGenerator.ROTATE_Y_90))
        .with(directionalMultipartCondition(false, false, true, false), cap.apply(BlockStateModelGenerator.ROTATE_Y_180))
        .with(directionalMultipartCondition(false, false, false, true), cap.apply(BlockStateModelGenerator.ROTATE_Y_270))
        .with(directionalMultipartCondition(true, null, null, null), side)
        .with(directionalMultipartCondition(null, true, null, null), side.apply(BlockStateModelGenerator.ROTATE_Y_90))
        .with(directionalMultipartCondition(null, null, true, null), side.apply(BlockStateModelGenerator.ROTATE_Y_180))
        .with(directionalMultipartCondition(null, null, null, true), side.apply(BlockStateModelGenerator.ROTATE_Y_270))
    );
    Identifier inventoryModel = createObjectModel(block, "simple_item", "/item", true, generator, Map.of(TextureKey.TEXTURE, "/side", TextureKey.PARTICLE, "/side"));
    generator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private static void registerSpikedFence(Block block, BlockStateModelGenerator generator) {
    WeightedVariant pole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/pole", true, generator, Map.of(TextureKey.BOTTOM, "/bottom", TextureKey.SIDE, "/side", TextureKey.TOP, "/bottom", TextureKey.PARTICLE, "/side")));
    WeightedVariant topPole = createWeightedVariant(createObjectModel(block, "spiked_fence/pole", "/top_pole", true, generator, Map.of(TextureKey.BOTTOM, "/bottom", TextureKey.SIDE, "/side_top", TextureKey.TOP, "/top", TextureKey.PARTICLE, "/side_top")));
    WeightedVariant side = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/side", true, generator, Map.of(TextureKey.SIDE, "/side", TextureKey.PARTICLE, "/side")));
    WeightedVariant topSide = createWeightedVariant(createObjectModel(block, "spiked_fence/side", "/top_side", true, generator, Map.of(TextureKey.SIDE, "/side_top", TextureKey.PARTICLE, "/side_top")));
    generator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(block)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.TOP, false), pole)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.TOP, true), topPole)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.NORTH, SpikedFenceShape.SIDE), side)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.EAST, SpikedFenceShape.SIDE), side.apply(BlockStateModelGenerator.ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.SOUTH, SpikedFenceShape.SIDE), side.apply(BlockStateModelGenerator.ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.WEST, SpikedFenceShape.SIDE), side.apply(BlockStateModelGenerator.ROTATE_Y_270))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.NORTH, SpikedFenceShape.TOP), topSide)
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.EAST, SpikedFenceShape.TOP), topSide.apply(BlockStateModelGenerator.ROTATE_Y_90))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.SOUTH, SpikedFenceShape.TOP), topSide.apply(BlockStateModelGenerator.ROTATE_Y_180))
        .with(new MultipartModelConditionBuilder().put(SpikedFenceBlock.WEST, SpikedFenceShape.TOP), topSide.apply(BlockStateModelGenerator.ROTATE_Y_270))
    );
    Identifier inventoryModel = createObjectModel(block, "spiked_fence/item", "/item", true, generator, Map.of(TextureKey.BOTTOM, "/bottom", TextureKey.of("lower_side"), "/side", TextureKey.TOP, "/top", TextureKey.of("upper_side"), "/side_top", TextureKey.PARTICLE, "/side_top"));
    generator.itemModelOutput.accept(block.asItem(), ItemModels.basic(inventoryModel));
  }

  private static void registerSimpleItem(Item item, String parent, ItemModelGenerator generator) {
    generator.output.accept(item, ItemModels.basic(createObjectModel(item, parent, null, false, generator, Map.of(TextureKey.TEXTURE, ""))));
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

  private static Identifier createObjectModel(Block block, String parent, @Nullable String variant, boolean textureMapKeySuffix, BlockStateModelGenerator generator, Map<TextureKey, String> textureKeys) {
    Model model;
    if (variant == null) model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.empty(), textureKeys.keySet().toArray(new TextureKey[0]));
    else model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.of(variant), textureKeys.keySet().toArray(new TextureKey[0]));
    return model.upload(getObjectModelPath(block, variant == null ? "" : variant), createTextureMapWithKeys(block, textureMapKeySuffix, textureKeys), generator.modelCollector);
  }

  private static Identifier createObjectModel(Item item, String parent, @Nullable String variant, boolean textureMapKeySuffix, ItemModelGenerator generator, Map<TextureKey, String> textureKeys) {
    Model model;
    if (variant == null) model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.empty(), textureKeys.keySet().toArray(new TextureKey[0]));
    else model = new Model(Optional.of(UnderDistantMoons.identifierOf("template/" + parent)), Optional.of(variant), textureKeys.keySet().toArray(new TextureKey[0]));
    return model.upload(getObjectModelPath(item, variant == null ? "" : variant), createTextureMapWithKeys(item, textureMapKeySuffix, textureKeys), generator.modelCollector);
  }

  private static Identifier getFirstEntryOf(WeightedVariant variant) {
    return variant.variants().getEntries().getFirst().value().modelId();
  }

  private static TextureMap createTextureMapWithKeys(Block block, boolean keySuffix, Map<TextureKey, String> textureKeys) {
    final TextureMap textureMap = new TextureMap();
    for (TextureKey key : textureKeys.keySet()) {
      textureMap.put(key, UnderDistantMoons.identifierOf("block/" + getStringIdOf(block) + (keySuffix ? textureKeys.get(key) : "")));
    }
    return textureMap;
  }

  private static TextureMap createTextureMapWithKeys(Item item, boolean keySuffix, Map<TextureKey, String> textureKeys) {
    final TextureMap textureMap = new TextureMap();
    for (TextureKey key : textureKeys.keySet()) {
      textureMap.put(key, UnderDistantMoons.identifierOf("item/" + getStringIdOf(item) + (keySuffix ? textureKeys.get(key) : "")));
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
