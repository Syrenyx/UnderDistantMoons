package syrenyx.distantmoons.content.block;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.DirectionTransformation;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class PoleBlock extends Block implements Waterloggable {

  public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
  public static final BooleanProperty UP = ConnectingBlock.UP;
  public static final BooleanProperty DOWN = ConnectingBlock.DOWN;
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final VoxelShape EXTENSION_SHAPE = Block.createCuboidShape(6.0, 16.0, 6.0, 10.0, 22.0, 10.0);
  private static final Map<Direction.Axis, VoxelShape> CENTER_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0));
  private static final Map<Direction.Axis, VoxelShape> UP_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(EXTENSION_SHAPE);
  private static final Map<Direction.Axis, VoxelShape> DOWN_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(VoxelShapes.transform(EXTENSION_SHAPE, DirectionTransformation.fromRotations(AxisRotation.R180, AxisRotation.R0), VoxelShapeUtil.BLOCK_CENTER_ANCHOR));

  public PoleBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(AXIS, Direction.Axis.Y)
        .with(UP, false)
        .with(DOWN, false)
        .with(WATERLOGGED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AXIS, UP, DOWN, WATERLOGGED);
  }

  @Override
  protected FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  public boolean canFillWithFluid(@Nullable LivingEntity filler, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
    return Waterloggable.super.canFillWithFluid(filler, world, pos, state, fluid);
  }

  @Override
  public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
    return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
  }

  @Override
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    if (type == NavigationType.WATER) return state.getFluidState().isIn(FluidTags.WATER);
    return false;
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    VoxelShape shape = CENTER_SHAPES_BY_AXIS.get(state.get(AXIS));
    if (state.get(UP)) shape = VoxelShapes.union(shape, UP_SHAPES_BY_AXIS.get(state.get(AXIS)));
    if (state.get(DOWN)) shape = VoxelShapes.union(shape, DOWN_SHAPES_BY_AXIS.get(state.get(AXIS)));
    return shape;
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    return this.updateState(context.getWorld(), context.getBlockPos(), this.getDefaultState().with(AXIS, context.getSide().getAxis()));
  }

  @Override
  protected BlockState getStateForNeighborUpdate(
      BlockState state,
      WorldView world,
      ScheduledTickView tickView,
      BlockPos pos,
      Direction direction,
      BlockPos neighborPos,
      BlockState neighborState,
      Random random
  ) {
    if (state.get(WATERLOGGED)) tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    return super.getStateForNeighborUpdate(this.updateState(world, pos, state), world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  private BlockState updateState(BlockView world, BlockPos pos, BlockState state) {
    return switch (state.get(AXIS)) {
      case X -> state
          .with(UP, this.canConnectTo(world, pos, Direction.EAST))
          .with(DOWN, this.canConnectTo(world, pos, Direction.WEST));
      case Y -> state
          .with(UP, this.canConnectTo(world, pos, Direction.UP))
          .with(DOWN, this.canConnectTo(world, pos, Direction.DOWN));
      case Z -> state
          .with(UP, this.canConnectTo(world, pos, Direction.NORTH))
          .with(DOWN, this.canConnectTo(world, pos, Direction.SOUTH));
    };
  }

  protected boolean canConnectTo(BlockView world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.offset(direction));
    if (state.isIn(DistantMoonsBlockTags.POLE_NEVER_CONNECTS_TO)) return false;
    if (state.isIn(DistantMoonsBlockTags.POLE_ALWAYS_CONNECTS_TO)) return true;
    Direction.Axis axis = direction.getAxis();
    if (state.getBlock() instanceof PoleBlock && state.get(AXIS) != axis) return true;
    if (axis != Direction.Axis.Y && (state.getBlock() instanceof FenceBlock || state.getBlock() instanceof WallBlock)) return true;
    return false;
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    return switch (state.get(AXIS)) {
      case X -> switch (rotation) {
        case NONE -> state;
        case CLOCKWISE_90 -> state.with(AXIS, Direction.Axis.Z).with(UP, state.get(DOWN)).with(DOWN, state.get(UP));
        case CLOCKWISE_180 -> state.with(UP, state.get(DOWN)).with(DOWN, state.get(UP));
        case COUNTERCLOCKWISE_90 -> state.with(AXIS, Direction.Axis.Z);
      };
      case Y -> state;
      case Z -> switch (rotation) {
        case NONE -> state;
        case CLOCKWISE_90 -> state.with(AXIS, Direction.Axis.X);
        case CLOCKWISE_180 -> state.with(UP, state.get(DOWN)).with(DOWN, state.get(UP));
        case COUNTERCLOCKWISE_90 -> state.with(AXIS, Direction.Axis.X).with(UP, state.get(DOWN)).with(DOWN, state.get(UP));
      };
    };
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    return switch (mirror) {
      case NONE -> state;
      case LEFT_RIGHT -> state.get(AXIS) == Direction.Axis.Z ? state.with(UP, state.get(DOWN)).with(DOWN, state.get(UP)) : state;
      case FRONT_BACK -> state.get(AXIS) == Direction.Axis.X ? state.with(UP, state.get(DOWN)).with(DOWN, state.get(UP)) : state;
    };
  }
}
