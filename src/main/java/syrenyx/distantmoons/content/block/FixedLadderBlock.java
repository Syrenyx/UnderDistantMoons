package syrenyx.distantmoons.content.block;

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
import syrenyx.distantmoons.content.block.block_state_enums.FixedLadderSideShape;
import syrenyx.distantmoons.content.block.block_state_enums.HorizontalAxis;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class FixedLadderBlock extends Block {

  public static final EnumProperty<HorizontalAxis> AXIS = EnumProperty.of("axis", HorizontalAxis.class);
  public static final EnumProperty<FixedLadderSideShape> LEFT_SHAPE = EnumProperty.of("left_shape", FixedLadderSideShape.class);
  public static final EnumProperty<FixedLadderSideShape> RIGHT_SHAPE = EnumProperty.of("right_shape", FixedLadderSideShape.class);
  public static final BooleanProperty LEFT_CAPPED = BooleanProperty.of("left_capped");
  public static final BooleanProperty RIGHT_CAPPED = BooleanProperty.of("right_capped");
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  private static final VoxelShape X_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 16.0);
  private static final VoxelShape Z_SHAPE = VoxelShapes.transform(X_SHAPE, DirectionTransformation.ROT_90_Y_POS, new Vec3d(0.5, 0.5, 0.5));

  public FixedLadderBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(AXIS, HorizontalAxis.X)
        .with(LEFT_SHAPE, FixedLadderSideShape.NONE)
        .with(RIGHT_SHAPE, FixedLadderSideShape.NONE)
        .with(LEFT_CAPPED, false)
        .with(RIGHT_CAPPED, false)
        .with(WATERLOGGED, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(AXIS, LEFT_SHAPE, RIGHT_SHAPE, LEFT_CAPPED, RIGHT_CAPPED, WATERLOGGED);
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
    Direction.Axis horizontalFacingAxis = context.getHorizontalPlayerFacing().getAxis();
    Direction.Axis sideAxis = context.getSide().getAxis();
    HorizontalAxis blockAxis = HorizontalAxis.fromDirectionAxis(horizontalFacingAxis);
    if (sideAxis != Direction.Axis.Y && getConnectionType(world, pos, context.getSide().getOpposite()) != FixedLadderSideShape.NONE) {
      blockAxis = HorizontalAxis.fromDirectionAxis(sideAxis).opposite();
    }
    return this.updateState(
        world, pos,
        this.getDefaultState().with(AXIS, blockAxis).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
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
    Direction leftDirection = x ? Direction.SOUTH : Direction.EAST;
    Direction rightDirection = x ? Direction.NORTH : Direction.WEST;
    FixedLadderSideShape leftShape = this.getConnectionType(world, pos, leftDirection);
    FixedLadderSideShape rightShape = this.getConnectionType(world, pos, rightDirection);
    return state
        .with(LEFT_SHAPE, leftShape).with(RIGHT_SHAPE, rightShape)
        .with(LEFT_CAPPED, leftShape != FixedLadderSideShape.NONE && world.getBlockState(pos.offset(leftDirection)).isIn(DistantMoonsBlockTags.FIXED_LADDER_CAPPED_TO))
        .with(RIGHT_CAPPED, rightShape != FixedLadderSideShape.NONE && world.getBlockState(pos.offset(rightDirection)).isIn(DistantMoonsBlockTags.FIXED_LADDER_CAPPED_TO));
  }

  private FixedLadderSideShape getConnectionType(BlockView world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.offset(direction));
    if (state.isIn(DistantMoonsBlockTags.FIXED_LADDER_NEVER_CONNECTS_TO)) return FixedLadderSideShape.NONE;
    if (state.isIn(DistantMoonsBlockTags.FIXED_LADDER_ALWAYS_CONNECTS_TO)) return state.isIn(DistantMoonsBlockTags.FIXED_LADDER_ATTACHES_TO)
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

  public static boolean blocksTop(BlockState state, Direction direction) {
    return switch (direction) {
      case NORTH -> state.get(FixedLadderBlock.AXIS) == HorizontalAxis.X && state.get(FixedLadderBlock.RIGHT_SHAPE) != FixedLadderSideShape.NONE;
      case EAST -> state.get(FixedLadderBlock.AXIS) == HorizontalAxis.Z && state.get(FixedLadderBlock.LEFT_SHAPE) != FixedLadderSideShape.NONE;
      case SOUTH -> state.get(FixedLadderBlock.AXIS) == HorizontalAxis.X && state.get(FixedLadderBlock.LEFT_SHAPE) != FixedLadderSideShape.NONE;
      case WEST -> state.get(FixedLadderBlock.AXIS) == HorizontalAxis.Z && state.get(FixedLadderBlock.RIGHT_SHAPE) != FixedLadderSideShape.NONE;
      default -> true;
    };
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    boolean x = state.get(AXIS) == HorizontalAxis.X;
    return switch (rotation) {
      case NONE -> state;
      case CLOCKWISE_90 -> state
          .with(AXIS, state.get(AXIS).opposite())
          .with(LEFT_SHAPE, state.get(x ? LEFT_SHAPE : RIGHT_SHAPE))
          .with(RIGHT_SHAPE, state.get(x ? RIGHT_SHAPE : LEFT_SHAPE));
      case CLOCKWISE_180 -> state
          .with(LEFT_SHAPE, state.get(RIGHT_SHAPE))
          .with(RIGHT_SHAPE, state.get(LEFT_SHAPE));
      case COUNTERCLOCKWISE_90 -> state
          .with(AXIS, state.get(AXIS).opposite())
          .with(LEFT_SHAPE, state.get(x ? RIGHT_SHAPE : LEFT_SHAPE))
          .with(RIGHT_SHAPE, state.get(x ? LEFT_SHAPE : RIGHT_SHAPE));
    };
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    boolean x = state.get(AXIS) == HorizontalAxis.X;
    if (x && mirror == BlockMirror.LEFT_RIGHT || !x && mirror == BlockMirror.FRONT_BACK) {
      return state.with(LEFT_SHAPE, state.get(RIGHT_SHAPE)).with(RIGHT_SHAPE, state.get(LEFT_SHAPE));
    }
    return state;
  }
}
