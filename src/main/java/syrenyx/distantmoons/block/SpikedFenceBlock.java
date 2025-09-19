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
    return !(Boolean)state.get(WATERLOGGED);
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    return this.calculateState(context.getWorld(), context.getBlockPos());
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
    return this.calculateState(world, pos);
  }

  private BlockState calculateState(BlockView world, BlockPos pos) {
    BlockState state = this.getDefaultState();
    BlockState topState = world.getBlockState(pos.up());
    VoxelShape topFace = topState.getCollisionShape(world, pos.up()).getFace(Direction.DOWN);
    if (blockedTop(CENTER_SHAPE, topFace, topState)) state = state.with(TOP, false);
    if (canConnectTo(world, pos, Direction.NORTH)) state = state.with(NORTH, blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.NORTH), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP);
    if (canConnectTo(world, pos, Direction.EAST)) state = state.with(EAST, blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.EAST), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP);
    if (canConnectTo(world, pos, Direction.SOUTH)) state = state.with(SOUTH, blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.SOUTH), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP);
    if (canConnectTo(world, pos, Direction.WEST)) state = state.with(WEST, blockedTop(SIDE_SHAPES_BY_DIRECTION.get(Direction.WEST), topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP);
    return state;
  }

  private static boolean blockedTop(VoxelShape shape, VoxelShape topFace, BlockState topState) {
    return !topState.isIn(DistantMoonsTags.SPIKED_FENCE_NOT_BLOCKED_BY)
        && !VoxelShapes.matchesAnywhere(shape, topFace, BooleanBiFunction.ONLY_FIRST);
  }

  private boolean canConnectTo(BlockView world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.offset(direction));
    if (state.getBlock() instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, direction)) return true;
    if (state.isIn(DistantMoonsTags.SPIKED_FENCE_NEVER_CONNECTS_TO)) return false;
    if (state.isSideSolidFullSquare(world, pos.offset(direction), direction.getOpposite())) return true;
    return state.isIn(DistantMoonsTags.SPIKED_FENCE_ALWAYS_CONNECTS_TO);
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
}
