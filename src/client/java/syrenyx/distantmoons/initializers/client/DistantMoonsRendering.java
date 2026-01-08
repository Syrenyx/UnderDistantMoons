package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import syrenyx.distantmoons.content.entity.SittingSpotRenderer;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsEntityTypes;

public abstract class DistantMoonsRendering {

  static {
    BlockRenderLayerMap.putBlocks(
        ChunkSectionLayer.CUTOUT,
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
        DistantMoonsBlocks.UNDERWORLD_LANTERN,
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR,
        DistantMoonsBlocks.WROUGHT_IRON_BARS,
        DistantMoonsBlocks.WROUGHT_IRON_FENCE
    );

    EntityRenderers.register(DistantMoonsEntityTypes.SITTING_SPOT, SittingSpotRenderer::new);
  }

  public static void initialize() {}
}
