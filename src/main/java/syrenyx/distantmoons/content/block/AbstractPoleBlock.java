package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public abstract class AbstractPoleBlock extends Block implements SimpleWaterloggedBlock {

  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
  public static final BooleanProperty UP = BlockStateProperties.UP;
  public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  public AbstractPoleBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(AXIS, Direction.Axis.Y)
        .setValue(UP, false)
        .setValue(DOWN, false)
        .setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(AXIS, UP, DOWN, WATERLOGGED);
  }

  @Override
  protected @NonNull FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  public boolean canPlaceLiquid(@Nullable LivingEntity filler, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Fluid fluid) {
    return SimpleWaterloggedBlock.super.canPlaceLiquid(filler, world, pos, state, fluid);
  }

  @Override
  public boolean placeLiquid(@NonNull LevelAccessor world, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull FluidState fluidState) {
    return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidState);
  }

  @Override
  protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
    if (type == PathComputationType.WATER) return state.getFluidState().is(FluidTags.WATER);
    return false;
  }

  @Override
  protected abstract @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context);

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockPos pos = context.getClickedPos();
    return this.updateState(context.getLevel(), pos, this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, context.getLevel().getFluidState(pos).getType() == Fluids.WATER));
  }

  @Override
  protected @NonNull BlockState updateShape(
      BlockState state,
      @NonNull LevelReader world,
      @NonNull ScheduledTickAccess tickView,
      @NonNull BlockPos pos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    if (state.getValue(WATERLOGGED)) tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    return super.updateShape(this.updateState(world, pos, state), world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  private BlockState updateState(BlockGetter world, BlockPos pos, BlockState state) {
    return switch (state.getValue(AXIS)) {
      case X -> state
          .setValue(UP, this.canConnectTo(world, pos, Direction.EAST))
          .setValue(DOWN, this.canConnectTo(world, pos, Direction.WEST));
      case Y -> state
          .setValue(UP, this.canConnectTo(world, pos, Direction.UP))
          .setValue(DOWN, this.canConnectTo(world, pos, Direction.DOWN));
      case Z -> state
          .setValue(UP, this.canConnectTo(world, pos, Direction.NORTH))
          .setValue(DOWN, this.canConnectTo(world, pos, Direction.SOUTH));
    };
  }

  protected abstract boolean canConnectTo(BlockGetter world, BlockPos pos, Direction direction);

  @Override
  protected @NonNull BlockState rotate(BlockState state, @NonNull Rotation rotation) {
    return switch (state.getValue(AXIS)) {
      case X -> switch (rotation) {
        case NONE -> state;
        case CLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.Z).setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
        case CLOCKWISE_180 -> state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
        case COUNTERCLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.Z);
      };
      case Y -> state;
      case Z -> switch (rotation) {
        case NONE -> state;
        case CLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.X);
        case CLOCKWISE_180 -> state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
        case COUNTERCLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.X).setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
      };
    };
  }

  @Override
  protected @NonNull BlockState mirror(@NonNull BlockState state, Mirror mirror) {
    return switch (mirror) {
      case NONE -> state;
      case LEFT_RIGHT -> state.getValue(AXIS) == Direction.Axis.Z ? state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP)) : state;
      case FRONT_BACK -> state.getValue(AXIS) == Direction.Axis.X ? state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP)) : state;
    };
  }
}
