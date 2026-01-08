package syrenyx.distantmoons.content.block;

import com.mojang.math.OctahedralGroup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.block.block_state_enums.FixedLadderSideShape;
import syrenyx.distantmoons.content.block.block_state_enums.HorizontalAxis;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class FixedLadderBlock extends Block {

  public static final EnumProperty<HorizontalAxis> AXIS = EnumProperty.create("axis", HorizontalAxis.class);
  public static final EnumProperty<FixedLadderSideShape> LEFT_SHAPE = EnumProperty.create("left_shape", FixedLadderSideShape.class);
  public static final EnumProperty<FixedLadderSideShape> RIGHT_SHAPE = EnumProperty.create("right_shape", FixedLadderSideShape.class);
  public static final BooleanProperty LEFT_CAPPED = BooleanProperty.create("left_capped");
  public static final BooleanProperty RIGHT_CAPPED = BooleanProperty.create("right_capped");
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  private static final VoxelShape X_SHAPE = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 16.0);
  private static final VoxelShape Z_SHAPE = Shapes.rotate(X_SHAPE, OctahedralGroup.ROT_90_Y_POS, new Vec3(0.5, 0.5, 0.5));

  public FixedLadderBlock(Properties settings) {
    super(settings);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(AXIS, HorizontalAxis.X)
        .setValue(LEFT_SHAPE, FixedLadderSideShape.NONE)
        .setValue(RIGHT_SHAPE, FixedLadderSideShape.NONE)
        .setValue(LEFT_CAPPED, false)
        .setValue(RIGHT_CAPPED, false)
        .setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(AXIS, LEFT_SHAPE, RIGHT_SHAPE, LEFT_CAPPED, RIGHT_CAPPED, WATERLOGGED);
  }

  @Override
  protected boolean isPathfindable(BlockState state, PathComputationType type) {
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

  @Nullable
  @Override
  @SuppressWarnings("DataFlowIssue")
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    Direction.Axis horizontalFacingAxis = context.getHorizontalDirection().getAxis();
    Direction.Axis sideAxis = context.getClickedFace().getAxis();
    HorizontalAxis blockAxis = HorizontalAxis.fromDirectionAxis(horizontalFacingAxis);
    if (sideAxis != Direction.Axis.Y && getConnectionType(world, pos, context.getClickedFace().getOpposite()) != FixedLadderSideShape.NONE) {
      blockAxis = HorizontalAxis.fromDirectionAxis(sideAxis).opposite();
    }
    return this.updateState(
        world, pos,
        this.defaultBlockState().setValue(AXIS, blockAxis).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)
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
    boolean x = state.getValue(AXIS) == HorizontalAxis.X;
    Direction leftDirection = x ? Direction.SOUTH : Direction.EAST;
    Direction rightDirection = x ? Direction.NORTH : Direction.WEST;
    FixedLadderSideShape leftShape = this.getConnectionType(world, pos, leftDirection);
    FixedLadderSideShape rightShape = this.getConnectionType(world, pos, rightDirection);
    return state
        .setValue(LEFT_SHAPE, leftShape).setValue(RIGHT_SHAPE, rightShape)
        .setValue(LEFT_CAPPED, leftShape != FixedLadderSideShape.NONE && world.getBlockState(pos.relative(leftDirection)).is(DistantMoonsBlockTags.FIXED_LADDER_CAPPED_TO))
        .setValue(RIGHT_CAPPED, rightShape != FixedLadderSideShape.NONE && world.getBlockState(pos.relative(rightDirection)).is(DistantMoonsBlockTags.FIXED_LADDER_CAPPED_TO));
  }

  private FixedLadderSideShape getConnectionType(BlockGetter world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.relative(direction));
    if (state.is(DistantMoonsBlockTags.FIXED_LADDER_NEVER_CONNECTS_TO)) return FixedLadderSideShape.NONE;
    if (state.is(DistantMoonsBlockTags.FIXED_LADDER_ALWAYS_CONNECTS_TO)) return state.is(DistantMoonsBlockTags.FIXED_LADDER_ATTACHES_TO)
        ? FixedLadderSideShape.ATTACHED : FixedLadderSideShape.CONNECTED;
    if (state.getBlock() instanceof FixedLadderBlock) {
      return (state.getValue(AXIS) != HorizontalAxis.fromDirectionAxis(direction.getAxis()))
          ? FixedLadderSideShape.ATTACHED : FixedLadderSideShape.NONE;
    }
    return FixedLadderSideShape.NONE;
  }

  @Override
  protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    return state.getValue(AXIS) == HorizontalAxis.X ? X_SHAPE : Z_SHAPE;
  }

  public static boolean canWallConnect(BlockState state, Direction direction) {
    return state.getValue(AXIS) != HorizontalAxis.fromDirectionAxis(direction.getAxis());
  }

  public static boolean blocksTop(BlockState state, Direction direction) {
    return switch (direction) {
      case NORTH -> state.getValue(FixedLadderBlock.AXIS) == HorizontalAxis.X && state.getValue(FixedLadderBlock.RIGHT_SHAPE) != FixedLadderSideShape.NONE;
      case EAST -> state.getValue(FixedLadderBlock.AXIS) == HorizontalAxis.Z && state.getValue(FixedLadderBlock.LEFT_SHAPE) != FixedLadderSideShape.NONE;
      case SOUTH -> state.getValue(FixedLadderBlock.AXIS) == HorizontalAxis.X && state.getValue(FixedLadderBlock.LEFT_SHAPE) != FixedLadderSideShape.NONE;
      case WEST -> state.getValue(FixedLadderBlock.AXIS) == HorizontalAxis.Z && state.getValue(FixedLadderBlock.RIGHT_SHAPE) != FixedLadderSideShape.NONE;
      default -> true;
    };
  }

  @Override
  protected BlockState rotate(BlockState state, Rotation rotation) {
    boolean x = state.getValue(AXIS) == HorizontalAxis.X;
    return switch (rotation) {
      case NONE -> state;
      case CLOCKWISE_90 -> state
          .setValue(AXIS, state.getValue(AXIS).opposite())
          .setValue(LEFT_SHAPE, state.getValue(x ? LEFT_SHAPE : RIGHT_SHAPE))
          .setValue(RIGHT_SHAPE, state.getValue(x ? RIGHT_SHAPE : LEFT_SHAPE));
      case CLOCKWISE_180 -> state
          .setValue(LEFT_SHAPE, state.getValue(RIGHT_SHAPE))
          .setValue(RIGHT_SHAPE, state.getValue(LEFT_SHAPE));
      case COUNTERCLOCKWISE_90 -> state
          .setValue(AXIS, state.getValue(AXIS).opposite())
          .setValue(LEFT_SHAPE, state.getValue(x ? RIGHT_SHAPE : LEFT_SHAPE))
          .setValue(RIGHT_SHAPE, state.getValue(x ? LEFT_SHAPE : RIGHT_SHAPE));
    };
  }

  @Override
  protected BlockState mirror(BlockState state, Mirror mirror) {
    boolean x = state.getValue(AXIS) == HorizontalAxis.X;
    if (x && mirror == Mirror.LEFT_RIGHT || !x && mirror == Mirror.FRONT_BACK) {
      return state.setValue(LEFT_SHAPE, state.getValue(RIGHT_SHAPE)).setValue(RIGHT_SHAPE, state.getValue(LEFT_SHAPE));
    }
    return state;
  }
}
