package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class UnderworldLanternBlock extends LanternBlock implements UnderworldBlock {

  public static BooleanProperty LIT = BlockStateProperties.LIT;

  public UnderworldLanternBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(LIT, true)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(LIT);
  }

  @Nullable @Override
  public BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    return UnderworldBlock.getStateForPlacement(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
  }

  @Override
  protected void randomTick(@NonNull BlockState blockState, @NonNull ServerLevel serverLevel, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    UnderworldBlock.randomTick(blockState, serverLevel, blockPos);
  }

  public static int lightLevel(BlockState blockState) {
    return blockState.getValue(UnderworldAnchorBlock.LIT) ? 15 : 0;
  }
}
