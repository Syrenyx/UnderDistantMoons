package syrenyx.distantmoons.content.rendering.block;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class ExtendedBlockTintSource implements BlockTintSource {

  private final BlockTintSource blockTintSource;

  public ExtendedBlockTintSource(final BlockTintSource blockTintSource) {
    this.blockTintSource = blockTintSource;
  }

  @Override
  public int color(@NonNull BlockState state) {
    return this.blockTintSource.color(state);
  }

  @Override
  public int colorInWorld(final @NonNull BlockState state, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos) {
    return this.blockTintSource.colorInWorld(state, level, pos);
  }

  @Override
  public int colorAsTerrainParticle(final @NonNull BlockState state, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos) {
    return this.blockTintSource.colorAsTerrainParticle(state, level, pos);
  }

  @Override
  public @NonNull Set<Property<?>> relevantProperties() {
    return this.blockTintSource.relevantProperties();
  }
}
