package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.block_state_enums.EndCappedState;
import syrenyx.distantmoons.content.block.block_state_enums.BalustradeSideState;
import syrenyx.distantmoons.references.DistantMoonsBlockStateProperties;

import java.util.Map;

public abstract class AbstractBalustradeBlock extends Block implements SimpleWaterloggedBlock {

  public static final EnumProperty<EndCappedState> CENTER_SHAPE = DistantMoonsBlockStateProperties.CENTER_CAPPED_STATE;
  public static final EnumProperty<BalustradeSideState> NORTH_SHAPE = DistantMoonsBlockStateProperties.BALUSTRADE_STATE_NORTH;
  public static final EnumProperty<BalustradeSideState> EAST_SHAPE = DistantMoonsBlockStateProperties.BALUSTRADE_STATE_EAST;
  public static final EnumProperty<BalustradeSideState> SOUTH_SHAPE = DistantMoonsBlockStateProperties.BALUSTRADE_STATE_SOUTH;
  public static final EnumProperty<BalustradeSideState> WEST_SHAPE = DistantMoonsBlockStateProperties.BALUSTRADE_STATE_WEST;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private static final Map<Direction, EnumProperty<BalustradeSideState>> SIDE_SHAPE_PROPERTIES_BY_DIRECTION = Map.of(
      Direction.NORTH, NORTH_SHAPE,
      Direction.EAST, EAST_SHAPE,
      Direction.SOUTH, SOUTH_SHAPE,
      Direction.WEST, WEST_SHAPE
  );

  public AbstractBalustradeBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(CENTER_SHAPE, EndCappedState.DOUBLE_CAPPED)
        .setValue(NORTH_SHAPE, BalustradeSideState.NONE)
        .setValue(EAST_SHAPE, BalustradeSideState.NONE)
        .setValue(SOUTH_SHAPE, BalustradeSideState.NONE)
        .setValue(WEST_SHAPE, BalustradeSideState.NONE)
        .setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(CENTER_SHAPE, NORTH_SHAPE, EAST_SHAPE, SOUTH_SHAPE, WEST_SHAPE, WATERLOGGED);
  }

  @Override
  protected @NonNull FluidState getFluidState(BlockState blockState) {
    return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
  }

  @Override
  public boolean canPlaceLiquid(@Nullable LivingEntity filler, @NonNull BlockGetter level, @NonNull BlockPos blockPos, @NonNull BlockState blockState, @NonNull Fluid fluid) {
    return SimpleWaterloggedBlock.super.canPlaceLiquid(filler, level, blockPos, blockState, fluid);
  }

  @Override
  public boolean placeLiquid(@NonNull LevelAccessor level, @NonNull BlockPos blockPos, @NonNull BlockState blockState, @NonNull FluidState fluidState) {
    return SimpleWaterloggedBlock.super.placeLiquid(level, blockPos, blockState, fluidState);
  }

  @Override
  protected boolean isPathfindable(@NonNull BlockState blockState, @NonNull PathComputationType type) {
    if (type == PathComputationType.WATER) return blockState.getFluidState().is(FluidTags.WATER);
    return false;
  }

  @Override
  protected boolean propagatesSkylightDown(BlockState blockState) {
    return !blockState.getValue(WATERLOGGED);
  }

  @Nullable @Override
  public BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    return this.updateState(
        level, blockPos,
        this.defaultBlockState().setValue(WATERLOGGED, level.getFluidState(blockPos).getType() == Fluids.WATER)
    );
  }

  @Override
  protected @NonNull BlockState updateShape(
      @NonNull BlockState blockState,
      @NonNull LevelReader level,
      ScheduledTickAccess tickView,
      @NonNull BlockPos blockPos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    tickView.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    return this.updateState(level, blockPos, blockState);
  }

  private BlockState updateState(BlockGetter level, BlockPos blockPos, BlockState blockState) {
    BalustradeSideState northShape = getSideState(level, blockPos, Direction.NORTH);
    BalustradeSideState eastShape = getSideState(level, blockPos, Direction.EAST);
    BalustradeSideState southShape = getSideState(level, blockPos, Direction.SOUTH);
    BalustradeSideState westShape = getSideState(level, blockPos, Direction.WEST);
    return blockState
        .setValue(CENTER_SHAPE, EndCappedState.getFrom(
            !canConnectTo(level, blockPos, Direction.DOWN) || northShape.bottomCapped() || eastShape.bottomCapped() || southShape.bottomCapped() || westShape.bottomCapped(),
            !canConnectTo(level, blockPos, Direction.UP) || northShape.topCapped() || eastShape.topCapped() || southShape.topCapped() || westShape.topCapped()
        ))
        .setValue(NORTH_SHAPE, northShape)
        .setValue(EAST_SHAPE, eastShape)
        .setValue(SOUTH_SHAPE, southShape)
        .setValue(WEST_SHAPE, westShape);
  }

  private boolean canConnectTo(BlockGetter level, BlockPos blockPos, Direction direction) {
    boolean vertical = direction.getAxis() == Direction.Axis.Y;
    BlockState blockState = level.getBlockState(blockPos.relative(direction));
    if (vertical) {
      if (blockState.is(this.neverConnectsToVertically())) return false;
      if (blockState.is(this.alwaysConnectsToVertically())) return true;
      if (blockState.is(this.balustradeType())) {
        if (this.canConnectToBalustradeCap()) return true;
        EndCappedState centerShape = blockState.getValue(CENTER_SHAPE);
        return direction == Direction.DOWN ? !centerShape.topCapped() : !centerShape.bottomCapped();
      }
    }
    else {
      if (blockState.is(this.neverConnectsToHorizontally())) return false;
      if (blockState.is(this.alwaysConnectsToHorizontally())) return true;
      return blockState.isFaceSturdy(level, blockPos.relative(direction), direction.getOpposite());
    }
    return false;
  }

  private BalustradeSideState getSideState(BlockGetter level, BlockPos blockPos, Direction direction) {
    if (direction.getAxis() == Direction.Axis.Y || !canConnectTo(level, blockPos, direction)) return BalustradeSideState.NONE;

    BlockState bottomState = level.getBlockState(blockPos.below());
    boolean bottomCap;
    if (bottomState.is(this.neverConnectsToVertically())) bottomCap = true;
    else if (bottomState.is(this.alwaysConnectsToVertically())) bottomCap = false;
    else if (bottomState.getBlock() instanceof AbstractBalustradeBlock && bottomState.getValue(SIDE_SHAPE_PROPERTIES_BY_DIRECTION.get(direction)) != BalustradeSideState.NONE) bottomCap = false;
    else bottomCap = true;

    BlockState topState = level.getBlockState(blockPos.above());
    boolean topCap;
    if (topState.is(this.neverConnectsToVertically())) topCap = true;
    else if (topState.is(this.alwaysConnectsToVertically())) topCap = false;
    else if (topState.getBlock() instanceof AbstractBalustradeBlock && topState.getValue(SIDE_SHAPE_PROPERTIES_BY_DIRECTION.get(direction)) != BalustradeSideState.NONE) topCap = false;
    else topCap = true;

    return bottomCap
        ? topCap ? BalustradeSideState.DOUBLE_CAPPED : BalustradeSideState.BOTTOM_CAPPED
        : topCap ? BalustradeSideState.TOP_CAPPED : BalustradeSideState.UNCAPPED;
  }

  protected abstract TagKey<Block> balustradeType();
  protected abstract TagKey<Block> alwaysConnectsToHorizontally();
  protected abstract TagKey<Block> alwaysConnectsToVertically();
  protected abstract boolean canConnectToBalustradeCap();
  protected abstract TagKey<Block> neverConnectsToHorizontally();
  protected abstract TagKey<Block> neverConnectsToVertically();

  @Override
  protected @NonNull VoxelShape getShape(@NonNull BlockState blockState, @NonNull BlockGetter level, @NonNull BlockPos blockPos, @NonNull CollisionContext context) {
    VoxelShape shape = this.getCenterShape(blockState.getValue(CENTER_SHAPE));
    if (blockState.getValue(NORTH_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideShape(blockState.getValue(NORTH_SHAPE), Direction.NORTH));
    if (blockState.getValue(EAST_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideShape(blockState.getValue(EAST_SHAPE), Direction.EAST));
    if (blockState.getValue(SOUTH_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideShape(blockState.getValue(SOUTH_SHAPE), Direction.SOUTH));
    if (blockState.getValue(WEST_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideShape(blockState.getValue(WEST_SHAPE), Direction.WEST));
    return shape;
  }

  protected abstract VoxelShape getCenterShape(EndCappedState state);
  protected abstract VoxelShape getSideShape(BalustradeSideState state, Direction direction);

  @Override
  protected @NonNull VoxelShape getCollisionShape(@NonNull BlockState blockState, @NonNull BlockGetter blockGetter, @NonNull BlockPos blockPos, @NonNull CollisionContext collisionContext) {
    VoxelShape shape = this.getCenterCollisionShape();
    if (blockState.getValue(NORTH_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideCollisionShape(Direction.NORTH));
    if (blockState.getValue(EAST_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideCollisionShape(Direction.EAST));
    if (blockState.getValue(SOUTH_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideCollisionShape(Direction.SOUTH));
    if (blockState.getValue(WEST_SHAPE) != BalustradeSideState.NONE) shape = Shapes.or(shape, getSideCollisionShape(Direction.WEST));
    return shape;
  }

  protected abstract VoxelShape getCenterCollisionShape();
  protected abstract VoxelShape getSideCollisionShape(Direction direction);

  @Override
  protected @NonNull BlockState rotate(@NonNull BlockState blockState, @NonNull Rotation rotation) {
    return switch (rotation) {
      case NONE -> blockState;
      case CLOCKWISE_90 -> blockState
          .setValue(NORTH_SHAPE, blockState.getValue(WEST_SHAPE))
          .setValue(EAST_SHAPE, blockState.getValue(NORTH_SHAPE))
          .setValue(SOUTH_SHAPE, blockState.getValue(EAST_SHAPE))
          .setValue(WEST_SHAPE, blockState.getValue(SOUTH_SHAPE));
      case CLOCKWISE_180 -> blockState
          .setValue(NORTH_SHAPE, blockState.getValue(SOUTH_SHAPE))
          .setValue(EAST_SHAPE, blockState.getValue(WEST_SHAPE))
          .setValue(SOUTH_SHAPE, blockState.getValue(NORTH_SHAPE))
          .setValue(WEST_SHAPE, blockState.getValue(EAST_SHAPE));
      case COUNTERCLOCKWISE_90 -> blockState
          .setValue(NORTH_SHAPE, blockState.getValue(EAST_SHAPE))
          .setValue(EAST_SHAPE, blockState.getValue(SOUTH_SHAPE))
          .setValue(SOUTH_SHAPE, blockState.getValue(WEST_SHAPE))
          .setValue(WEST_SHAPE, blockState.getValue(NORTH_SHAPE));
    };
  }

  @Override
  protected @NonNull BlockState mirror(@NonNull BlockState blockState, @NonNull Mirror mirror) {
    return switch (mirror) {
      case NONE -> blockState;
      case LEFT_RIGHT -> blockState
          .setValue(EAST_SHAPE, blockState.getValue(WEST_SHAPE))
          .setValue(WEST_SHAPE, blockState.getValue(EAST_SHAPE));
      case FRONT_BACK -> blockState
          .setValue(NORTH_SHAPE, blockState.getValue(SOUTH_SHAPE))
          .setValue(SOUTH_SHAPE, blockState.getValue(NORTH_SHAPE));
    };
  }
}
