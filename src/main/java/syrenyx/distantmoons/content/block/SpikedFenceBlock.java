package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.block.block_state_enums.SpikedFenceShape;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.BlockConnectionUtil;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class SpikedFenceBlock extends Block implements SimpleWaterloggedBlock {

  public static final BooleanProperty TOP = BooleanProperty.create("top");
  public static final EnumProperty<SpikedFenceShape> NORTH = EnumProperty.create("north", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> EAST = EnumProperty.create("east", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> SOUTH = EnumProperty.create("south", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> WEST = EnumProperty.create("west", SpikedFenceShape.class);
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  private static final VoxelShape CENTER_SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
  private static final VoxelShape SIDE_SHAPE = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 7.0);
  private static final Map<Direction, VoxelShape> SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(SIDE_SHAPE);
  private static final VoxelShape TALL_CENTER_SHAPE = Block.box(7.0, 0.0, 7.0, 9.0, 24.0, 9.0);
  private static final VoxelShape TALL_SIDE_SHAPE = Block.box(7.0, 0.0, 0.0, 9.0, 24.0, 7.0);
  private static final Map<Direction, VoxelShape> TALL_SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(TALL_SIDE_SHAPE);

  public SpikedFenceBlock(Properties settings) {
    super(settings);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(TOP, true)
        .setValue(NORTH, SpikedFenceShape.NONE)
        .setValue(EAST, SpikedFenceShape.NONE)
        .setValue(SOUTH, SpikedFenceShape.NONE)
        .setValue(WEST, SpikedFenceShape.NONE)
        .setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(TOP, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
  }

  @Override
  public void fallOn(@NonNull Level world, BlockState state, @NonNull BlockPos pos, @NonNull Entity entity, double fallDistance) {
    if (
        state.getValue(NORTH) != SpikedFenceShape.NONE
            || state.getValue(EAST) != SpikedFenceShape.NONE
            || state.getValue(SOUTH) != SpikedFenceShape.NONE
            || state.getValue(WEST) != SpikedFenceShape.NONE
    ) {
      entity.causeFallDamage(fallDistance + 2.5, 2.0F, world.damageSources().fall());
    } else super.fallOn(world, state, pos, entity, fallDistance);
  }

  @Override
  protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
    return false;
  }

  @Override
  protected @NonNull FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  protected boolean propagatesSkylightDown(BlockState state) {
    return !state.getValue(WATERLOGGED);
  }

  @Nullable @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    return this.updateState(
        world, pos,
        this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)
    );
  }

  @Override
  protected @NonNull BlockState updateShape(
      @NonNull BlockState state,
      @NonNull LevelReader world,
      ScheduledTickAccess tickView,
      @NonNull BlockPos pos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    return this.updateState(world, pos, state);
  }

  private BlockState updateState(BlockGetter world, BlockPos pos, BlockState state) {
    BlockState topState = world.getBlockState(pos.above());
    VoxelShape topFace = topState.getCollisionShape(world, pos.above()).getFaceShape(Direction.DOWN);
    return state
        .setValue(TOP, !blockedTop(Direction.UP, topFace, topState))
        .setValue(NORTH, this.canConnectTo(world, pos, Direction.NORTH) ? (blockedTop(Direction.NORTH, topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE)
        .setValue(EAST, this.canConnectTo(world, pos, Direction.EAST) ? (blockedTop(Direction.EAST, topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE)
        .setValue(SOUTH, this.canConnectTo(world, pos, Direction.SOUTH) ? (blockedTop(Direction.SOUTH, topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE)
        .setValue(WEST, this.canConnectTo(world, pos, Direction.WEST) ? (blockedTop(Direction.WEST, topFace, topState) ? SpikedFenceShape.SIDE : SpikedFenceShape.TOP) : SpikedFenceShape.NONE);
  }

  private static boolean blockedTop(Direction direction, VoxelShape topFace, BlockState topState) {
    if (topState.is(DistantMoonsBlockTags.SPIKED_FENCE_NOT_BLOCKED_BY)) return false;
    if (topState.getBlock() instanceof FixedLadderBlock) return FixedLadderBlock.blocksTop(topState, direction);
    return !Shapes.joinIsNotEmpty(SIDE_SHAPES_BY_DIRECTION.getOrDefault(direction, CENTER_SHAPE), topFace, BooleanOp.ONLY_FIRST);
  }

  private boolean canConnectTo(BlockGetter world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.relative(direction));
    if (state.is(DistantMoonsBlockTags.SPIKED_FENCE_NEVER_CONNECTS_TO)) return false;
    if (state.is(DistantMoonsBlockTags.SPIKED_FENCE_ALWAYS_CONNECTS_TO)) return true;
    if (state.getBlock() instanceof FenceGateBlock) return FenceGateBlock.connectsToDirection(state, direction);
    if (state.getBlock() instanceof FixedLadderBlock) return FixedLadderBlock.canWallConnect(state, direction);
    if (state.isFaceSturdy(world, pos.relative(direction), direction.getOpposite())) return true;
    if (state.getBlock() instanceof StairBlock) return BlockConnectionUtil.isStairBlockFacePartiallySturdy(state, direction.getOpposite());
    if (state.getBlock() instanceof WallSlabBlock) return WallSlabBlock.isFacePartiallySturdy(state, direction.getOpposite());
    return false;
  }

  @Override
  protected @NonNull VoxelShape getVisualShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    return Shapes.empty();
  }

  @Override
  protected @NonNull VoxelShape getCollisionShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    VoxelShape shape = TALL_CENTER_SHAPE;
    if (state.getValue(NORTH) != SpikedFenceShape.NONE) shape = Shapes.or(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.NORTH));
    if (state.getValue(EAST) != SpikedFenceShape.NONE) shape = Shapes.or(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.EAST));
    if (state.getValue(SOUTH) != SpikedFenceShape.NONE) shape = Shapes.or(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.SOUTH));
    if (state.getValue(WEST) != SpikedFenceShape.NONE) shape = Shapes.or(shape, TALL_SIDE_SHAPES_BY_DIRECTION.get(Direction.WEST));
    return shape;
  }

  @Override
  protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    VoxelShape shape = CENTER_SHAPE;
    if (state.getValue(NORTH) != SpikedFenceShape.NONE) shape = Shapes.or(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.NORTH));
    if (state.getValue(EAST) != SpikedFenceShape.NONE) shape = Shapes.or(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.EAST));
    if (state.getValue(SOUTH) != SpikedFenceShape.NONE) shape = Shapes.or(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.SOUTH));
    if (state.getValue(WEST) != SpikedFenceShape.NONE) shape = Shapes.or(shape, SIDE_SHAPES_BY_DIRECTION.get(Direction.WEST));
    return shape;
  }

  @Override
  protected @NonNull BlockState rotate(@NonNull BlockState state, Rotation rotation) {
    return switch (rotation) {
      case NONE -> state;
      case CLOCKWISE_90 -> state
          .setValue(NORTH, state.getValue(WEST))
          .setValue(EAST, state.getValue(NORTH))
          .setValue(SOUTH, state.getValue(EAST))
          .setValue(WEST, state.getValue(SOUTH));
      case CLOCKWISE_180 -> state
          .setValue(NORTH, state.getValue(SOUTH))
          .setValue(EAST, state.getValue(WEST))
          .setValue(SOUTH, state.getValue(NORTH))
          .setValue(WEST, state.getValue(EAST));
      case COUNTERCLOCKWISE_90 -> state
          .setValue(NORTH, state.getValue(EAST))
          .setValue(EAST, state.getValue(SOUTH))
          .setValue(SOUTH, state.getValue(WEST))
          .setValue(WEST, state.getValue(NORTH));
    };
  }

  @Override
  protected @NonNull BlockState mirror(@NonNull BlockState state, Mirror mirror) {
    return switch (mirror) {
      case NONE -> state;
      case LEFT_RIGHT -> state
          .setValue(NORTH, state.getValue(SOUTH))
          .setValue(SOUTH, state.getValue(NORTH));
      case FRONT_BACK -> state
          .setValue(EAST, state.getValue(WEST))
          .setValue(WEST, state.getValue(EAST));
    };
  }
}
