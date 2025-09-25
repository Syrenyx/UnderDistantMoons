package syrenyx.distantmoons.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.block.block_state_enums.SpikedFenceShape;
import syrenyx.distantmoons.references.DistantMoonsTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class SpikedFenceBlock extends Block implements Waterloggable {

  public static final BooleanProperty TOP = BooleanProperty.of("top");
  public static final EnumProperty<SpikedFenceShape> NORTH = EnumProperty.of("north", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> EAST = EnumProperty.of("east", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> SOUTH = EnumProperty.of("south", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> WEST = EnumProperty.of("west", SpikedFenceShape.class);
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final VoxelShape CENTER_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
  private static final VoxelShape SIDE_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 7.0);
  private static final Map<Direction, VoxelShape> SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(SIDE_SHAPE);
  private static final VoxelShape TALL_CENTER_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 24.0, 9.0);
  private static final VoxelShape TALL_SIDE_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 24.0, 7.0);
  private static final Map<Direction, VoxelShape> TALL_SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(TALL_SIDE_SHAPE);

  public SpikedFenceBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(TOP, true)
        .with(NORTH, SpikedFenceShape.NONE)
        .with(EAST, SpikedFenceShape.NONE)
        .with(SOUTH, SpikedFenceShape.NONE)
        .with(WEST, SpikedFenceShape.NONE)
        .with(WATERLOGGED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(TOP, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
  }

  @Override
  public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
    if (
        state.get(NORTH) != SpikedFenceShape.NONE
            || state.get(EAST) != SpikedFenceShape.NONE
            || state.get(SOUTH) != SpikedFenceShape.NONE
            || state.get(WEST) != SpikedFenceShape.NONE
    ) {
      entity.handleFallDamage(fallDistance + 2.5, 2.0F, world.getDamageSources().fall());
    } else super.onLandedUpon(world, state, pos, entity, fallDistance);
  }

  @Override
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    return false;
  }

  @Override
  protected FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  protected boolean isTransparent(BlockState state) {
    return !state.get(WATERLOGGED);
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    World world = context.getWorld();
    BlockPos pos = context.getBlockPos();
    return this.updateState(
        world, pos,
        this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
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
    tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    return this.updateState(world, pos, state);
  }

  private BlockState updateState(BlockView world, BlockPos pos, BlockState state) {
    BlockState topState = world.getBlockState(pos.up());
    VoxelShape topFace = topState.getCollisionShape(world, pos.up()).getFace(Direction.DOWN);
    return state
        .with(TOP, !blockedTop(CENTER_SHAPE, topFace, topState))
        .with(NORTH, this.canConnectTo(world, pos, Direction.NORTH) ? (blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.NORTH), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE)
        .with(EAST, this.canConnectTo(world, pos, Direction.EAST) ? (blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.EAST), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE)
        .with(SOUTH, this.canConnectTo(world, pos, Direction.SOUTH) ? (blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.SOUTH), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE)
        .with(WEST, this.canConnectTo(world, pos, Direction.WEST) ? (blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.WEST), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE);
  }

  private static boolean blockedTop(VoxelShape shape, VoxelShape topFace, BlockState topState) {
    return !topState.isIn(DistantMoonsTags.SPIKED_FENCE_NOT_BLOCKED_BY)
        && !VoxelShapes.matchesAnywhere(shape, topFace, BooleanBiFunction.ONLY_FIRST);
  }

  private boolean canConnectTo(BlockView world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.offset(direction));
    if (state.isIn(DistantMoonsTags.SPIKED_FENCE_NEVER_CONNECTS_TO)) return false;
    if (state.isIn(DistantMoonsTags.SPIKED_FENCE_ALWAYS_CONNECTS_TO)) return true;
    if (state.getBlock() instanceof FenceGateBlock) return FenceGateBlock.canWallConnect(state, direction);
    if (state.getBlock() instanceof FixedLadderBlock) return FixedLadderBlock.canWallConnect(state, direction);
    if (state.isSideSolidFullSquare(world, pos.offset(direction), direction.getOpposite())) return true;
    return false;
  }

  @Override
  protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return VoxelShapes.empty();
  }

  @Override
  protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    VoxelShape shape = TALL_CENTER_SHAPE;
    if (state.get(NORTH) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.NORTH));
    if (state.get(EAST) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.EAST));
    if (state.get(SOUTH) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.SOUTH));
    if (state.get(WEST) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.WEST));
    return shape;
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    VoxelShape shape = CENTER_SHAPE;
    if (state.get(NORTH) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.NORTH));
    if (state.get(EAST) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.EAST));
    if (state.get(SOUTH) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.SOUTH));
    if (state.get(WEST) != SpikedFenceShape.NONE) shape = VoxelShapes.union(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.WEST));
    return shape;
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    return switch (rotation) {
      case NONE -> state;
      case CLOCKWISE_90 -> state
          .with(NORTH, state.get(WEST))
          .with(EAST, state.get(NORTH))
          .with(SOUTH, state.get(EAST))
          .with(WEST, state.get(SOUTH));
      case CLOCKWISE_180 -> state
          .with(NORTH, state.get(SOUTH))
          .with(EAST, state.get(WEST))
          .with(SOUTH, state.get(NORTH))
          .with(WEST, state.get(EAST));
      case COUNTERCLOCKWISE_90 -> state
          .with(NORTH, state.get(EAST))
          .with(EAST, state.get(SOUTH))
          .with(SOUTH, state.get(WEST))
          .with(WEST, state.get(NORTH));
    };
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    return switch (mirror) {
      case NONE -> state;
      case LEFT_RIGHT -> state
          .with(NORTH, state.get(SOUTH))
          .with(SOUTH, state.get(NORTH));
      case FRONT_BACK -> state
          .with(EAST, state.get(WEST))
          .with(WEST, state.get(EAST));
    };
  }
}
