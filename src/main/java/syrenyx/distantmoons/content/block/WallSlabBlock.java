package syrenyx.distantmoons.content.block;

import net.minecraft.block.*;
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
import syrenyx.distantmoons.content.block.block_state_enums.WallSlabShape;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class WallSlabBlock extends Block implements Waterloggable {

  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
  public static final EnumProperty<WallSlabShape> SHAPE = EnumProperty.of("shape", WallSlabShape.class);
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final VoxelShape FLAT_SHAPE = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
  private static final VoxelShape OUTER_SHAPE = Block.createCuboidShape(8.0, 0.0, 8.0, 16.0, 16.0, 16.0);
  private static final VoxelShape INNER_SHAPE = VoxelShapes.union(FLAT_SHAPE, VoxelShapes.transform(OUTER_SHAPE, DirectionTransformation.ROT_90_Y_POS));
  private static final Map<Direction, VoxelShape> FLAT_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(FLAT_SHAPE);
  private static final Map<Direction, VoxelShape> OUTER_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(OUTER_SHAPE);
  private static final Map<Direction, VoxelShape> INNER_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(INNER_SHAPE);

  public WallSlabBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(FACING, Direction.NORTH)
        .with(SHAPE, WallSlabShape.FLAT)
        .with(WATERLOGGED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, SHAPE, WATERLOGGED);
  }

  @Override
  protected FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  public boolean canFillWithFluid(@Nullable LivingEntity filler, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
    return state.get(SHAPE) != WallSlabShape.DOUBLE && Waterloggable.super.canFillWithFluid(filler, world, pos, state, fluid);
  }

  @Override
  public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
    return state.get(SHAPE) != WallSlabShape.DOUBLE && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
  }

  @Override
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    if (type == NavigationType.WATER) return state.getFluidState().isIn(FluidTags.WATER);
    return false;
  }

  @Override
  protected boolean canReplace(BlockState state, ItemPlacementContext context) {
    ItemStack itemStack = context.getStack();
    WallSlabShape wallShape = state.get(SHAPE);
    if (wallShape != WallSlabShape.FLAT || !itemStack.isOf(this.asItem())) return false;
    if (context.canReplaceExisting()) return context.getSide() == state.get(FACING);
    return true;
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(SHAPE)) {
      case FLAT -> FLAT_SHAPES_BY_DIRECTION.get(state.get(FACING).getOpposite());
      case INNER_LEFT -> INNER_SHAPES_BY_DIRECTION.get(state.get(FACING).getOpposite());
      case INNER_RIGHT -> INNER_SHAPES_BY_DIRECTION.get(state.get(FACING).rotateYCounterclockwise());
      case OUTER_LEFT -> OUTER_SHAPES_BY_DIRECTION.get(state.get(FACING).getOpposite());
      case OUTER_RIGHT -> OUTER_SHAPES_BY_DIRECTION.get(state.get(FACING).rotateYCounterclockwise());
      case DOUBLE -> VoxelShapes.fullCube();
    };
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    BlockPos pos = context.getBlockPos();
    BlockState currentState = context.getWorld().getBlockState(pos);
    if (currentState.isOf(this)) return currentState.with(SHAPE, WallSlabShape.DOUBLE).with(WATERLOGGED, false);
    Direction placementDirection = context.getSide();
    World world = context.getWorld();
    return updateState(
        world, pos,
        this.getDefaultState()
            .with(FACING, placementDirection.getAxis() == Direction.Axis.Y ? context.getHorizontalPlayerFacing() : placementDirection.getOpposite())
            .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
    );
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
    return super.getStateForNeighborUpdate(updateState(world, pos, state), world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  private static BlockState updateState(BlockView world, BlockPos pos, BlockState state) {
    if (state.get(SHAPE) == WallSlabShape.DOUBLE) return state;
    Direction direction = state.get(FACING);
    BlockState leftState = world.getBlockState(pos.offset(direction.rotateYCounterclockwise()));
    boolean leftLocked = canConnectTo(leftState.getBlock()) && leftState.get(FACING) == direction;
    BlockState rightState = world.getBlockState(pos.offset(direction.rotateYClockwise()));
    boolean rightLocked = canConnectTo(rightState.getBlock()) && rightState.get(FACING) == direction;
    BlockState frontState = world.getBlockState(pos.offset(direction.getOpposite()));
    BlockState backState = world.getBlockState(pos.offset(direction));
    if (canConnectTo(frontState.getBlock())) {
      Direction facing = frontState.get(FACING);
      if (direction.rotateYCounterclockwise() == facing && !leftLocked) return state.with(SHAPE, WallSlabShape.INNER_LEFT);
      if (direction.rotateYClockwise() == facing && !rightLocked) return state.with(SHAPE, WallSlabShape.INNER_RIGHT);
    }
    if (canConnectTo(backState.getBlock())) {
      Direction facing = backState.get(FACING);
      if (direction.rotateYCounterclockwise() == facing && !rightLocked) return state.with(SHAPE, WallSlabShape.OUTER_LEFT);
      if (direction.rotateYClockwise() == facing && !leftLocked) return state.with(SHAPE, WallSlabShape.OUTER_RIGHT);
    }
    return state.with(SHAPE, WallSlabShape.FLAT);
  }

  private static boolean canConnectTo(Block block) {
    return block instanceof StairsBlock || block instanceof WallSlabBlock;
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    Direction facing = state.get(FACING);
    return state.with(FACING, switch (rotation) {
      case NONE -> facing;
      case CLOCKWISE_90 -> switch (facing) {
        case NORTH -> Direction.EAST;
        case EAST -> Direction.SOUTH;
        case SOUTH -> Direction.WEST;
        case WEST -> Direction.NORTH;
        default -> null;
      };
      case CLOCKWISE_180 -> switch (facing) {
        case NORTH -> Direction.SOUTH;
        case EAST -> Direction.WEST;
        case SOUTH -> Direction.NORTH;
        case WEST -> Direction.EAST;
        default -> null;
      };
      case COUNTERCLOCKWISE_90 -> switch (facing) {
        case NORTH -> Direction.WEST;
        case EAST -> Direction.NORTH;
        case SOUTH -> Direction.EAST;
        case WEST -> Direction.SOUTH;
        default -> null;
      };
    });
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    Direction facing = state.get(FACING);
    WallSlabShape shape = state.get(SHAPE);
    return switch (mirror) {
      case NONE -> state;
      case FRONT_BACK -> state.rotate(facing.getAxis() == Direction.Axis.X ? BlockRotation.CLOCKWISE_180 : BlockRotation.NONE).with(SHAPE, getMirrored(shape));
      case LEFT_RIGHT -> state.rotate(facing.getAxis() == Direction.Axis.Z ? BlockRotation.CLOCKWISE_180 : BlockRotation.NONE).with(SHAPE, getMirrored(shape));
    };
  }

  private static WallSlabShape getMirrored(WallSlabShape shape) {
    return switch (shape) {
      case INNER_LEFT -> WallSlabShape.INNER_RIGHT;
      case INNER_RIGHT -> WallSlabShape.INNER_LEFT;
      case OUTER_LEFT -> WallSlabShape.OUTER_RIGHT;
      case OUTER_RIGHT -> WallSlabShape.OUTER_LEFT;
      default -> shape;
    };
  }
}
