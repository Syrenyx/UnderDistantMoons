package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;

public abstract class DistantMoonsRendering {

  static {
    BlockRenderLayerMap.putBlocks(
        BlockRenderLayer.CUTOUT,
        DistantMoonsBlocks.IRON_FENCE,
        DistantMoonsBlocks.REFINED_DEEP_IRON_BARS,
        DistantMoonsBlocks.REFINED_DEEP_IRON_FENCE,
        DistantMoonsBlocks.WROUGHT_IRON_BARS,
        DistantMoonsBlocks.WROUGHT_IRON_FENCE
    );
  }

  public static void initialize() {}
}
