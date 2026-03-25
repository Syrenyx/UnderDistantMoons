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
import syrenyx.distantmoons.references.DistantMoonsBlockStateProperties;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class FixedLadderBlock extends Block {

  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
  public static final EnumProperty<FixedLadderSideShape> LEFT_SHAPE = DistantMoonsBlockStateProperties.FIXED_LADDER_LEFT_SHAPE;
  public static final EnumProperty<FixedLadderSideShape> RIGHT_SHAPE = DistantMoonsBlockStateProperties.FIXED_LADDER_RIGHT_SHAPE;
  public static final BooleanProperty LEFT_CAPPED = DistantMoonsBlockStateProperties.LEFT_CAPPED;
  public static final BooleanProperty RIGHT_CAPPED = DistantMoonsBlockStateProperties.RIGHT_CAPPED;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private static final VoxelShape X_SHAPE = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 16.0);
  private static final VoxelShape Z_SHAPE = Shapes.rotate(X_SHAPE, OctahedralGroup.ROT_90_Y_POS, new Vec3(0.5, 0.5, 0.5));

  public FixedLadderBlock(Properties settings) {
    super(settings);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(AXIS, Direction.Axis.X)
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
  protected boolean isPathfindable(@NonNull BlockState state, PathComputationType type) {
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
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    Direction.Axis horizontalFacingAxis = context.getHorizontalDirection().getAxis();
    Direction.Axis sideAxis = context.getClickedFace().getAxis();
    return this.updateState(
        world, pos,
        this.defaultBlockState()
            .setValue(
                AXIS,
                sideAxis != Direction.Axis.Y && getConnectionType(world, pos, context.getClickedFace().getOpposite()) != FixedLadderSideShape.NONE
                    ? rotateY(sideAxis) : horizontalFacingAxis
            )
            .setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)
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
    boolean x = state.getValue(AXIS) == Direction.Axis.X;
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
      return (state.getValue(AXIS) != direction.getAxis())
          ? FixedLadderSideShape.ATTACHED : FixedLadderSideShape.NONE;
    }
    return FixedLadderSideShape.NONE;
  }

  @Override
  protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    return state.getValue(AXIS) == Direction.Axis.X ? X_SHAPE : Z_SHAPE;
  }

  public static boolean canWallConnect(BlockState state, Direction direction) {
    return state.getValue(AXIS) != direction.getAxis();
  }

  public static boolean blocksTop(BlockState state, Direction direction) {
    return switch (direction) {
      case NORTH -> state.getValue(FixedLadderBlock.AXIS) == Direction.Axis.X && state.getValue(FixedLadderBlock.RIGHT_SHAPE) != FixedLadderSideShape.NONE;
      case EAST -> state.getValue(FixedLadderBlock.AXIS) == Direction.Axis.Z && state.getValue(FixedLadderBlock.LEFT_SHAPE) != FixedLadderSideShape.NONE;
      case SOUTH -> state.getValue(FixedLadderBlock.AXIS) == Direction.Axis.X && state.getValue(FixedLadderBlock.LEFT_SHAPE) != FixedLadderSideShape.NONE;
      case WEST -> state.getValue(FixedLadderBlock.AXIS) == Direction.Axis.Z && state.getValue(FixedLadderBlock.RIGHT_SHAPE) != FixedLadderSideShape.NONE;
      default -> true;
    };
  }

  @Override
  protected @NonNull BlockState rotate(BlockState blockState, Rotation rotation) {
    boolean x = blockState.getValue(AXIS) == Direction.Axis.X;
    return switch (rotation) {
      case NONE -> blockState;
      case CLOCKWISE_90 -> blockState
          .setValue(AXIS, rotateY(blockState.getValue(AXIS)))
          .setValue(LEFT_SHAPE, blockState.getValue(x ? LEFT_SHAPE : RIGHT_SHAPE))
          .setValue(RIGHT_SHAPE, blockState.getValue(x ? RIGHT_SHAPE : LEFT_SHAPE));
      case CLOCKWISE_180 -> blockState
          .setValue(LEFT_SHAPE, blockState.getValue(RIGHT_SHAPE))
          .setValue(RIGHT_SHAPE, blockState.getValue(LEFT_SHAPE));
      case COUNTERCLOCKWISE_90 -> blockState
          .setValue(AXIS, rotateY(blockState.getValue(AXIS)))
          .setValue(LEFT_SHAPE, blockState.getValue(x ? RIGHT_SHAPE : LEFT_SHAPE))
          .setValue(RIGHT_SHAPE, blockState.getValue(x ? LEFT_SHAPE : RIGHT_SHAPE));
    };
  }

  @Override
  protected @NonNull BlockState mirror(BlockState blockState, @NonNull Mirror mirror) {
    boolean x = blockState.getValue(AXIS) == Direction.Axis.X;
    if (x && mirror == Mirror.LEFT_RIGHT || !x && mirror == Mirror.FRONT_BACK) {
      return blockState.setValue(LEFT_SHAPE, blockState.getValue(RIGHT_SHAPE)).setValue(RIGHT_SHAPE, blockState.getValue(LEFT_SHAPE));
    }
    return blockState;
  }

  private static Direction.Axis rotateY(Direction.Axis axis) {
    return switch (axis) {
      case X -> Direction.Axis.Z;
      case Y -> Direction.Axis.Y;
      case Z -> Direction.Axis.X;
    };
  }
}
