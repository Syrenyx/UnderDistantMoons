package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;

public abstract class DistantMoonsRendering {

  static {
    BlockRenderLayerMap.putBlocks(
        BlockRenderLayer.CUTOUT,
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR,
        DistantMoonsBlocks.DEEP_IRON_BARS,
        DistantMoonsBlocks.DEEP_IRON_CHAIN,
        DistantMoonsBlocks.DEEP_IRON_DOOR,
        DistantMoonsBlocks.DEEP_IRON_FENCE,
        DistantMoonsBlocks.DEEP_IRON_TRAPDOOR,
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER,
        DistantMoonsBlocks.FIXED_IRON_LADDER,
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER,
        DistantMoonsBlocks.IRON_BAR_DOOR,
        DistantMoonsBlocks.IRON_FENCE,
        DistantMoonsBlocks.ROPE_LADDER,
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR,
        DistantMoonsBlocks.WROUGHT_IRON_BARS,
        DistantMoonsBlocks.WROUGHT_IRON_FENCE
    );
  }

  public static void initialize() {}
}
