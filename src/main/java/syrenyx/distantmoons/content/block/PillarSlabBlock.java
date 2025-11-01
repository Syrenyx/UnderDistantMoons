package syrenyx.distantmoons.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class PillarSlabBlock extends Block implements Waterloggable {

  public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
  public static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final Map<Direction.Axis, VoxelShape> BOTTOM_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.createColumnShape(16.0, 0.0, 8.0));
  private static final Map<Direction.Axis, VoxelShape> TOP_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.createColumnShape(16.0, 8.0, 16.0));

  public PillarSlabBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState().
        with(AXIS, Direction.Axis.Y).
        with(TYPE, SlabType.BOTTOM).
        with(WATERLOGGED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AXIS, TYPE, WATERLOGGED);
  }

  @Override
  protected FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  public boolean canFillWithFluid(@Nullable LivingEntity filler, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
    return state.get(TYPE) != SlabType.DOUBLE && Waterloggable.super.canFillWithFluid(filler, world, pos, state, fluid);
  }

  @Override
  public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
    return state.get(TYPE) != SlabType.DOUBLE && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
  }

  @Override
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    if (type == NavigationType.WATER) return state.getFluidState().isIn(FluidTags.WATER);
    return false;
  }

  @Override
  protected boolean canReplace(BlockState state, ItemPlacementContext context) {
    ItemStack itemStack = context.getStack();
    SlabType slabType = state.get(TYPE);
    if (slabType == SlabType.DOUBLE || !itemStack.isOf(this.asItem())) return false;
    if (context.canReplaceExisting()) {
      Direction placementDirection = context.getSide();
      return switch (state.get(AXIS)) {
        case X -> slabType == SlabType.BOTTOM ? placementDirection == Direction.EAST : placementDirection == Direction.WEST;
        case Y -> slabType == SlabType.BOTTOM ? placementDirection == Direction.UP : placementDirection == Direction.DOWN;
        case Z -> slabType == SlabType.BOTTOM ? placementDirection == Direction.NORTH : placementDirection == Direction.SOUTH;
      };
    }
    return true;
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(TYPE)) {
      case TOP -> TOP_SHAPES_BY_AXIS.get(state.get(AXIS));
      case BOTTOM -> BOTTOM_SHAPES_BY_AXIS.get(state.get(AXIS));
      case DOUBLE -> VoxelShapes.fullCube();
    };
  }

  @Nullable @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    BlockPos pos = context.getBlockPos();
    BlockState currentState = context.getWorld().getBlockState(pos);
    if (currentState.isOf(this)) return currentState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
    BlockState state = this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(pos).getFluid() == Fluids.WATER);
    Direction placementDirection = context.getSide();
    return switch (placementDirection) {
      case NORTH -> state.with(AXIS, Direction.Axis.Z).with(TYPE, SlabType.BOTTOM);
      case EAST -> state.with(AXIS, Direction.Axis.X).with(TYPE, SlabType.BOTTOM);
      case SOUTH -> state.with(AXIS, Direction.Axis.Z).with(TYPE, SlabType.TOP);
      case WEST -> state.with(AXIS, Direction.Axis.X).with(TYPE, SlabType.TOP);
      case DOWN -> state.with(TYPE, SlabType.TOP);
      case UP -> state;
    };
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
    return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
  }
}
