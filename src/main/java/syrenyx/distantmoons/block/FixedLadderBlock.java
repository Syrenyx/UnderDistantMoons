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
import syrenyx.distantmoons.block.block_state_enums.HorizontalAxis;
import syrenyx.distantmoons.references.DistantMoonsTags;

import java.util.Objects;

public class FixedLadderBlock extends Block {

  public static final EnumProperty<HorizontalAxis> AXIS = EnumProperty.of("axis", HorizontalAxis.class);
  public static final EnumProperty<FixedLadderSideShape> LEFT = EnumProperty.of("left", FixedLadderSideShape.class);
  public static final EnumProperty<FixedLadderSideShape> RIGHT = EnumProperty.of("right", FixedLadderSideShape.class);
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final VoxelShape X_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 16.0);
  private static final VoxelShape Z_SHAPE = VoxelShapes.transform(X_SHAPE, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R90), new Vec3d(0.5, 0.5, 0.5));

  public FixedLadderBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(AXIS, HorizontalAxis.X)
        .with(LEFT, FixedLadderSideShape.NONE)
        .with(RIGHT, FixedLadderSideShape.NONE)
        .with(WATERLOGGED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AXIS, LEFT, RIGHT, WATERLOGGED);
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
  @SuppressWarnings("DataFlowIssue")
  public BlockState getPlacementState(ItemPlacementContext context) {
    World world = context.getWorld();
    BlockPos pos = context.getBlockPos();
    Direction.Axis sideAxis = context.getSide().getAxis();
    return this.updateState(
        world, pos,
        this.getDefaultState()
            .with(AXIS, sideAxis == Direction.Axis.Y
                ? HorizontalAxis.fromDirectionAxis(context.getHorizontalPlayerFacing().getAxis())
                : HorizontalAxis.fromDirectionAxis(sideAxis).opposite()
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
    boolean x = state.get(AXIS) == HorizontalAxis.X;
    return state
        .with(LEFT, this.getConnectionType(world, pos, x ? Direction.SOUTH : Direction.EAST))
        .with(RIGHT, this.getConnectionType(world, pos, x ? Direction.NORTH : Direction.WEST));
  }

  private FixedLadderSideShape getConnectionType(BlockView world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.offset(direction));
    if (state.isIn(DistantMoonsTags.FIXED_LADDER_NEVER_CONNECTS_TO)) return FixedLadderSideShape.NONE;
    if (state.isIn(DistantMoonsTags.FIXED_LADDER_ALWAYS_CONNECTS_TO)) return state.isIn(DistantMoonsTags.FIXED_LADDER_ATTACHES_TO)
        ? FixedLadderSideShape.ATTACHED : FixedLadderSideShape.CONNECTED;
    if (state.getBlock() instanceof FixedLadderBlock) {
      return (state.get(AXIS) != HorizontalAxis.fromDirectionAxis(direction.getAxis()))
          ? FixedLadderSideShape.ATTACHED : FixedLadderSideShape.NONE;
    }
    return FixedLadderSideShape.NONE;
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return state.get(AXIS) == HorizontalAxis.X ? X_SHAPE : Z_SHAPE;
  }

  public static boolean canWallConnect(BlockState state, Direction direction) {
    return state.get(AXIS) != HorizontalAxis.fromDirectionAxis(direction.getAxis());
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    boolean x = state.get(AXIS) == HorizontalAxis.X;
    return switch (rotation) {
      case NONE -> state;
      case CLOCKWISE_90 -> state
          .with(AXIS, state.get(AXIS).opposite())
          .with(LEFT, state.get(x ? LEFT : RIGHT))
          .with(RIGHT, state.get(x ? RIGHT : LEFT));
      case CLOCKWISE_180 -> state
          .with(LEFT, state.get(RIGHT))
          .with(RIGHT, state.get(LEFT));
      case COUNTERCLOCKWISE_90 -> state
          .with(AXIS, state.get(AXIS).opposite())
          .with(LEFT, state.get(x ? RIGHT : LEFT))
          .with(RIGHT, state.get(x ? LEFT : RIGHT));
    };
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    boolean x = state.get(AXIS) == HorizontalAxis.X;
    if (x && mirror == BlockMirror.LEFT_RIGHT || !x && mirror == BlockMirror.FRONT_BACK) {
      return state.with(LEFT, state.get(RIGHT)).with(RIGHT, state.get(LEFT));
    }
    return state;
  }
}
