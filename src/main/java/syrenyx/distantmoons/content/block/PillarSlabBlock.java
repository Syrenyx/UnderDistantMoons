package syrenyx.distantmoons.content.block;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PillarSlabBlock extends Block implements SimpleWaterloggedBlock {

  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
  public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  private static final Map<Direction.Axis, VoxelShape> BOTTOM_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.column(16.0, 0.0, 8.0));
  private static final Map<Direction.Axis, VoxelShape> TOP_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.column(16.0, 8.0, 16.0));

  public PillarSlabBlock(Properties settings) {
    super(settings);
    this.registerDefaultState(this.defaultBlockState().
        setValue(AXIS, Direction.Axis.Y).
        setValue(TYPE, SlabType.BOTTOM).
        setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(AXIS, TYPE, WATERLOGGED);
  }

  @Override
  protected @NonNull FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  public boolean canPlaceLiquid(@Nullable LivingEntity filler, @NonNull BlockGetter world, @NonNull BlockPos pos, BlockState state, @NonNull Fluid fluid) {
    return state.getValue(TYPE) != SlabType.DOUBLE && SimpleWaterloggedBlock.super.canPlaceLiquid(filler, world, pos, state, fluid);
  }

  @Override
  public boolean placeLiquid(@NonNull LevelAccessor world, @NonNull BlockPos pos, BlockState state, @NonNull FluidState fluidState) {
    return state.getValue(TYPE) != SlabType.DOUBLE && SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidState);
  }

  @Override
  protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
    if (type == PathComputationType.WATER) return state.getFluidState().is(FluidTags.WATER);
    return false;
  }

  @Override
  protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
    ItemStack itemStack = context.getItemInHand();
    SlabType slabType = state.getValue(TYPE);
    if (slabType == SlabType.DOUBLE || !itemStack.is(this.asItem())) return false;
    if (context.replacingClickedOnBlock()) {
      Direction placementDirection = context.getClickedFace();
      return switch (state.getValue(AXIS)) {
        case X -> slabType == SlabType.BOTTOM ? placementDirection == Direction.EAST : placementDirection == Direction.WEST;
        case Y -> slabType == SlabType.BOTTOM ? placementDirection == Direction.UP : placementDirection == Direction.DOWN;
        case Z -> slabType == SlabType.BOTTOM ? placementDirection == Direction.NORTH : placementDirection == Direction.SOUTH;
      };
    }
    return true;
  }

  @Override
  protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    return switch (state.getValue(TYPE)) {
      case TOP -> TOP_SHAPES_BY_AXIS.get(state.getValue(AXIS));
      case BOTTOM -> BOTTOM_SHAPES_BY_AXIS.get(state.getValue(AXIS));
      case DOUBLE -> Shapes.block();
    };
  }

  @Nullable @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockPos pos = context.getClickedPos();
    BlockState currentState = context.getLevel().getBlockState(pos);
    if (currentState.is(this)) return currentState.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, false);
    BlockState state = this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(pos).getType() == Fluids.WATER);
    Direction placementDirection = context.getClickedFace();
    return switch (placementDirection) {
      case NORTH -> state.setValue(AXIS, Direction.Axis.Z).setValue(TYPE, SlabType.BOTTOM);
      case EAST -> state.setValue(AXIS, Direction.Axis.X).setValue(TYPE, SlabType.BOTTOM);
      case SOUTH -> state.setValue(AXIS, Direction.Axis.Z).setValue(TYPE, SlabType.TOP);
      case WEST -> state.setValue(AXIS, Direction.Axis.X).setValue(TYPE, SlabType.TOP);
      case DOWN -> state.setValue(TYPE, SlabType.TOP);
      case UP -> state;
    };
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
    return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  protected @NonNull BlockState rotate(BlockState state, @NonNull Rotation rotation) {
    Direction.Axis originalAxis = state.getValue(AXIS);
    if (originalAxis == Direction.Axis.Y) return state;
    BlockState rotatedAxisState = state.setValue(AXIS, getRotated(state.getValue(AXIS)));
    return switch (rotation) {
      case NONE -> state;
      case CLOCKWISE_90 -> originalAxis == Direction.Axis.X ? rotatedAxisState.setValue(TYPE, getOpposite(state.getValue(TYPE))) : rotatedAxisState;
      case CLOCKWISE_180 -> state.setValue(TYPE, getOpposite(state.getValue(TYPE)));
      case COUNTERCLOCKWISE_90 -> originalAxis == Direction.Axis.Z ? rotatedAxisState.setValue(TYPE, getOpposite(state.getValue(TYPE))) : rotatedAxisState;
    };
  }

  @Override
  protected @NonNull BlockState mirror(BlockState state, @NonNull Mirror mirror) {
    Direction.Axis axis = state.getValue(AXIS);
    if (
        mirror == Mirror.FRONT_BACK && axis == Direction.Axis.X || mirror == Mirror.LEFT_RIGHT && axis == Direction.Axis.Z
    ) return state.setValue(TYPE, getOpposite(state.getValue(TYPE)));
    return state;
  }

  private static SlabType getOpposite(SlabType type) {
    if (type == SlabType.BOTTOM) return SlabType.TOP;
    if (type == SlabType.TOP) return SlabType.BOTTOM;
    return type;
  }

  private static Direction.Axis getRotated(Direction.Axis axis) {
    if (axis == Direction.Axis.X) return Direction.Axis.Z;
    if (axis == Direction.Axis.Z) return Direction.Axis.X;
    return axis;
  }
}
