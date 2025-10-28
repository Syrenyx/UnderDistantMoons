package syrenyx.distantmoons.initializers;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.block.FixedLadderBlock;
import syrenyx.distantmoons.content.block.MetalBarDoorBlock;
import syrenyx.distantmoons.content.block.MetalLadderBlock;
import syrenyx.distantmoons.content.block.SpikedFenceBlock;
import syrenyx.distantmoons.references.DistantMoonsBlockSetTypes;

import java.util.function.Function;

public abstract class DistantMoonsBlocks {

  //SIMPLE BLOCKS
  public static final Block BLACKSTONE_DEEP_IRON_ORE = register(
      "blackstone_deep_iron_ore",
      settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
      AbstractBlock.Settings.copy(Blocks.BLACKSTONE)
          .strength(3.0F, 3.0F),
      new Item.Settings().fireproof()
  );
  public static final Block CHARCOAL_BLOCK = register(
      "charcoal_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.COAL_BLOCK),
      new Item.Settings()
  );
  public static final Block COKE_BLOCK = register(
      "coke_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.COAL_BLOCK),
      new Item.Settings()
  );
  public static final Block CRUDE_DEEP_IRON_BLOCK = register(
      "crude_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings().fireproof()
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
  public static final Block DEEPSLATE_DEEP_IRON_ORE = register(
      "deepslate_deep_iron_ore",
      settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
      AbstractBlock.Settings.copy(Blocks.DEEPSLATE)
          .strength(4.5F, 3.0F),
      new Item.Settings().fireproof()
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
  public static final Block NETHERRACK_DEEP_IRON_ORE = register(
      "netherrack_deep_iron_ore",
      settings -> new ExperienceDroppingBlock(ConstantIntProvider.create(0), settings),
      AbstractBlock.Settings.copy(Blocks.NETHERRACK)
          .strength(3.0F, 3.0F),
      new Item.Settings().fireproof()
  );
  public static final Block RAW_DEEP_IRON_BLOCK = register(
      "raw_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)
          .mapColor(MapColor.TERRACOTTA_MAGENTA),
      new Item.Settings().fireproof()
  );
  public static final Block REFINED_DEEP_IRON_BLOCK = register(
      "refined_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
          .mapColor(MapColor.TERRACOTTA_WHITE),
      new Item.Settings().fireproof()
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
