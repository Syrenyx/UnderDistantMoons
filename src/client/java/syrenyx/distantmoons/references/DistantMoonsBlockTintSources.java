package syrenyx.distantmoons.references;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.block.UnderworldBlock;
import syrenyx.distantmoons.content.block.UnderworldConfluxBlock;
import syrenyx.distantmoons.content.block.block_state_enums.UnderworldConfluxState;
import syrenyx.distantmoons.utility.ColorUtil;

import java.util.Set;

public abstract class DistantMoonsBlockTintSources {

  public static final BlockTintSource UNDERWORLD_CONFLUX_TINT_SOURCE = new BlockTintSource() {

    @Override
    public int color(@NonNull BlockState blockState) {
      return UnderworldBlock.DEFAULT_COLOR;
    }

    @Override
    public int colorInWorld(final @NonNull BlockState blockState, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos blockPos) {
      if (blockState.getValue(UnderworldConfluxBlock.STATE) == UnderworldConfluxState.UNLIT) return UnderworldBlock.UNLIT_COLOR;
      return level.getBlockEntityRenderData(blockPos) instanceof Integer color ? color : UnderworldBlock.DEFAULT_COLOR;
    }

    @Override
    public int colorAsTerrainParticle(final @NonNull BlockState blockState, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos blockPos) {
      return ColorUtil.UNTINTED;
    }

    @Override
    public @NonNull Set<Property<?>> relevantProperties() {
      return Set.of(UnderworldConfluxBlock.STATE);
    }
  };
}
