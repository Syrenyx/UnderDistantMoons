package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class MetalBarDoorBlock extends DoorBlock {

  public static final BooleanProperty DOUBLE = BooleanProperty.create("double");

  public MetalBarDoorBlock(BlockSetType type, Properties settings) {
    super(type, settings);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(DOUBLE, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(DOUBLE);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    BlockState state = super.getStateForPlacement(context);
    if (state == null) return null;
    return updateState(context.getLevel(), context.getClickedPos(), state);
  }

  @Override
  protected @NonNull BlockState updateShape(
      @NonNull BlockState state,
      @NonNull LevelReader world,
      @NonNull ScheduledTickAccess tickView,
      @NonNull BlockPos pos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    return updateState(world, pos, super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random));
  }

  private BlockState updateState(BlockGetter world, BlockPos pos, BlockState state) {
    return state.getBlock() instanceof MetalBarDoorBlock ? state.setValue(DOUBLE, canConnectTo(world, pos, state)) : state;
  }

  private boolean canConnectTo(BlockGetter world, BlockPos pos, BlockState state) {
    Direction offset = state.getValue(DoorBlock.FACING).getClockWise(Direction.Axis.Y);
    BlockState neighborState = world.getBlockState(pos.relative(state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT ? offset.getOpposite() : offset));
    if (!(neighborState.getBlock() instanceof DoorBlock) || state.is(DistantMoonsBlockTags.METAL_BAR_DOOR_NEVER_CONNECTS_TO)) return false;
    return state.getValue(DoorBlock.FACING) == neighborState.getValue(DoorBlock.FACING)
        && state.getValue(DoorBlock.HALF) == neighborState.getValue(DoorBlock.HALF)
        && state.getValue(DoorBlock.HINGE) != neighborState.getValue(DoorBlock.HINGE);
  }
}
