package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;

public abstract class DistantMoonsBlockModifications {

  static {
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_ACACIA_LOG, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_ACACIA_WOOD, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_BAMBOO_BLOCK, DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_BIRCH_LOG, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_BIRCH_WOOD, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_CHERRY_LOG, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_CHERRY_WOOD, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_CRIMSON_STEM, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_DARK_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_DARK_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_JUNGLE_LOG, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_JUNGLE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_MANGROVE_LOG, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_MANGROVE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_PALE_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_PALE_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_SPRUCE_LOG, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_SPRUCE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_WARPED_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE);
    StrippableBlockRegistry.registerCopyState(DistantMoonsBlocks.CUT_WARPED_STEM, DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM);
  }

  public static void initialize() {}
}
