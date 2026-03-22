package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import syrenyx.distantmoons.content.block.UnderworldBlock;
import syrenyx.distantmoons.content.block.UnderworldConfluxBlock;
import syrenyx.distantmoons.content.block.block_state_enums.UnderworldConfluxState;
import syrenyx.distantmoons.content.data_component.DimensionKeystoneComponent;
import syrenyx.distantmoons.content.entity.SittingSpotRenderer;
import syrenyx.distantmoons.content.rendering.block.entity.UnderworldConfluxRenderer;
import syrenyx.distantmoons.initializers.DistantMoonsBlockEntityTypes;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsEntityTypes;

public abstract class DistantMoonsRendering {

  static {
    BlockEntityRenderers.register(DistantMoonsBlockEntityTypes.UNDERWORLD_CONFLUX, UnderworldConfluxRenderer::new);

    EntityRenderers.register(DistantMoonsEntityTypes.SITTING_SPOT, SittingSpotRenderer::new);

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
        DistantMoonsBlocks.UNDERWORLD_CONFLUX,
        DistantMoonsBlocks.UNDERWORLD_LANTERN,
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR,
        DistantMoonsBlocks.WROUGHT_IRON_BARS,
        DistantMoonsBlocks.WROUGHT_IRON_FENCE
    );

    ColorProviderRegistry.BLOCK.register(
        ((blockState, level, blockPos, tintIndex) -> {
          if (tintIndex != 0 || level == null || blockPos == null) return -1;
          if (blockState.getValue(UnderworldConfluxBlock.STATE) == UnderworldConfluxState.UNLIT) return UnderworldBlock.UNLIT_COLOR;
          return level.getBlockEntityRenderData(blockPos) instanceof DimensionKeystoneComponent component ? component.color() : UnderworldBlock.DEFAULT_COLOR;
        }),
        DistantMoonsBlocks.UNDERWORLD_CONFLUX
    );
  }

  public static void initialize() {}
}
