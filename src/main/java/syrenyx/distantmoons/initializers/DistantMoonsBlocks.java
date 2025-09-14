package syrenyx.distantmoons.initializers;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import syrenyx.distantmoons.UnderDistantMoons;

import java.util.function.Function;

public abstract class DistantMoonsBlocks {

  //SIMPLE BLOCKS
  public static final Block BLACKSTONE_DEEP_IRON_ORE = register(
      "blackstone_deep_iron_ore",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.BLACKSTONE),
      new Item.Settings()
          .fireproof()
  );
  public static final Block CRUDE_DEEP_IRON_BLOCK = register(
      "crude_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BLOCK),
      new Item.Settings()
          .fireproof()
  );
  public static final Block DEEPSLATE_DEEP_IRON_ORE = register(
      "deepslate_deep_iron_ore",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.DEEPSLATE),
      new Item.Settings()
          .fireproof()
  );
  public static final Block NETHERRACK_DEEP_IRON_ORE = register(
      "netherrack_deep_iron_ore",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.NETHERRACK),
      new Item.Settings()
          .fireproof()
  );
  public static final Block RAW_DEEP_IRON_BLOCK = register(
      "raw_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BLOCK),
      new Item.Settings()
          .fireproof()
  );
  public static final Block REFINED_DEEP_IRON_BLOCK = register(
      "refined_deep_iron_block",
      Block::new,
      AbstractBlock.Settings.copy(Blocks.IRON_BLOCK),
      new Item.Settings()
          .fireproof()
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
