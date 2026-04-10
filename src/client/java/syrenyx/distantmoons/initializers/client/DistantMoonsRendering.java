package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import syrenyx.distantmoons.content.entity.SittingSpotRenderer;
import syrenyx.distantmoons.content.rendering.block.entity.UnderworldConfluxRenderer;
import syrenyx.distantmoons.initializers.DistantMoonsBlockEntityTypes;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsEntityTypes;
import syrenyx.distantmoons.references.DistantMoonsBlockTintSources;

import java.util.List;

public abstract class DistantMoonsRendering {

  static {
    BlockColorRegistry.register(List.of(DistantMoonsBlockTintSources.UNDERWORLD_CONFLUX_TINT_SOURCE), DistantMoonsBlocks.UNDERWORLD_CONFLUX);

    BlockEntityRenderers.register(DistantMoonsBlockEntityTypes.UNDERWORLD_CONFLUX, UnderworldConfluxRenderer::new);

    EntityRenderers.register(DistantMoonsEntityTypes.SITTING_SPOT, SittingSpotRenderer::new);
  }

  public static void initialize() {}
}
