package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsEnvironmentAttributes;

public interface UnderworldBlock {

  static boolean inUnderworld(Level level, BlockPos blockPos) {
    return level.environmentAttributes().getValue(DistantMoonsEnvironmentAttributes.UNDERWORLD, blockPos);
  }

  static BlockState setLitState(BlockState blockState, Level level, BlockPos blockPos) {
    return blockState.setValue(BlockStateProperties.LIT, inUnderworld(level, blockPos));
  }

  static BlockState getStateForPlacement(BlockState blockState, Level level, BlockPos blockPos) {
    if (blockState == null) return null;
    return setLitState(blockState, level, blockPos);
  }

  static void randomTick(@NonNull BlockState blockState, @NonNull ServerLevel serverLevel, @NonNull BlockPos blockPos) {
    boolean inUnderworld = inUnderworld(serverLevel, blockPos);
    if (inUnderworld == blockState.getValue(BlockStateProperties.LIT)) return;
    serverLevel.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, inUnderworld), Block.UPDATE_ALL);
  }
}
