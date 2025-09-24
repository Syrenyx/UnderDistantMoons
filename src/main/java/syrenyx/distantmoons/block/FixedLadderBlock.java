package syrenyx.distantmoons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
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
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.block.block_state_enums.FixedLadderSideShape;
import syrenyx.distantmoons.references.DistantMoonsTags;

public class FixedLadderBlock extends Block {

  public static final BooleanProperty ROTATED = BooleanProperty.of("rotated");
  public static final EnumProperty<FixedLadderSideShape> LEFT = EnumProperty.of("left", FixedLadderSideShape.class);
  public static final EnumProperty<FixedLadderSideShape> RIGHT = EnumProperty.of("right", FixedLadderSideShape.class);
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 7.0, 16.0, 16.0, 9.0);
  private static final VoxelShape ROTATED_SHAPE = VoxelShapes.transform(SHAPE, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R90), new Vec3d(0.5, 0.5, 0.5));

  public FixedLadderBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(ROTATED, false)
        .with(LEFT, FixedLadderSideShape.NONE)
        .with(RIGHT, FixedLadderSideShape.NONE)
        .with(WATERLOGGED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(ROTATED, LEFT, RIGHT, WATERLOGGED);
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
    Direction.Axis sideAxis = context.getSide().getAxis();
    return this.updateState(
        world, pos,
        this.getDefaultState()
            .with(ROTATED, sideAxis == Direction.Axis.Y
                ? context.getHorizontalPlayerFacing().getAxis() == Direction.Axis.X
                : sideAxis == Direction.Axis.Z
            )
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
    tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    return this.updateState(world, pos, state);
  }

  private BlockState updateState(BlockView world, BlockPos pos, BlockState state) {
    boolean rotated = state.get(ROTATED);
    return state
        .with(LEFT, this.getConnectionType(world, pos, rotated ? Direction.NORTH : Direction.WEST))
        .with(RIGHT, this.getConnectionType(world, pos, rotated ? Direction.SOUTH : Direction.EAST));
  }

  private FixedLadderSideShape getConnectionType(BlockView world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.offset(direction));
    if (state.isIn(DistantMoonsTags.FIXED_LADDER_NEVER_CONNECTS_TO)) return FixedLadderSideShape.NONE;
    if (state.isIn(DistantMoonsTags.FIXED_LADDER_ALWAYS_CONNECTS_TO)) return state.isIn(DistantMoonsTags.FIXED_LADDER_ATTACHES_TO)
        ? FixedLadderSideShape.ATTACHED : FixedLadderSideShape.CONNECTED;
    if (state.getBlock() instanceof FixedLadderBlock) {
      return (state.get(ROTATED) && direction.getAxis() == Direction.Axis.Z || !state.get(ROTATED) && direction.getAxis() == Direction.Axis.X)
          ? FixedLadderSideShape.ATTACHED : FixedLadderSideShape.NONE;
    }
    return FixedLadderSideShape.NONE;
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return state.get(ROTATED) ? ROTATED_SHAPE : SHAPE;
  }

  public static boolean canWallConnect(BlockState state, Direction direction) {
    return switch (direction.getAxis()) {
      case X -> !state.get(ROTATED);
      case Y -> false;
      case Z -> state.get(ROTATED);
    };
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    boolean rotated = state.get(ROTATED);
    return switch (rotation) {
      case NONE -> state;
      case CLOCKWISE_90 -> state
          .with(ROTATED, !rotated)
          .with(LEFT, state.get(rotated ? RIGHT : LEFT))
          .with(RIGHT, state.get(rotated ? LEFT : RIGHT));
      case CLOCKWISE_180 -> state
          .with(LEFT, state.get(RIGHT))
          .with(RIGHT, state.get(LEFT));
      case COUNTERCLOCKWISE_90 -> state
          .with(ROTATED, !state.get(ROTATED))
          .with(LEFT, state.get(rotated ? LEFT : RIGHT))
          .with(RIGHT, state.get(rotated ? RIGHT : LEFT));
    };
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    boolean rotated = state.get(ROTATED);
    if (!rotated && mirror == BlockMirror.LEFT_RIGHT || rotated && mirror == BlockMirror.FRONT_BACK) {
      return state.with(LEFT, state.get(RIGHT)).with(RIGHT, state.get(LEFT));
    }
    return state;
  }
}
