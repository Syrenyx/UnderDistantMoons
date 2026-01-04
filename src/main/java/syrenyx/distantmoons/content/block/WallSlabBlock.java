package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.content.block.block_state_enums.WallSlabShape;
import syrenyx.distantmoons.utility.VoxelShapeUtil;
import com.mojang.math.OctahedralGroup;
import java.util.Map;

public class WallSlabBlock extends Block implements SimpleWaterloggedBlock {

  public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
  public static final EnumProperty<WallSlabShape> SHAPE = EnumProperty.create("shape", WallSlabShape.class);
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  private static final VoxelShape FLAT_SHAPE = Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
  private static final VoxelShape OUTER_SHAPE = Block.box(8.0, 0.0, 8.0, 16.0, 16.0, 16.0);
  private static final VoxelShape INNER_SHAPE = Shapes.or(FLAT_SHAPE, Shapes.rotate(OUTER_SHAPE, OctahedralGroup.ROT_90_Y_POS));
  private static final Map<Direction, VoxelShape> FLAT_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(FLAT_SHAPE);
  private static final Map<Direction, VoxelShape> OUTER_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(OUTER_SHAPE);
  private static final Map<Direction, VoxelShape> INNER_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(INNER_SHAPE);

  public WallSlabBlock(Properties settings) {
    super(settings);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(FACING, Direction.NORTH)
        .setValue(SHAPE, WallSlabShape.FLAT)
        .setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING, SHAPE, WATERLOGGED);
  }

  @Override
  protected FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  public boolean canPlaceLiquid(@Nullable LivingEntity filler, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
    return state.getValue(SHAPE) != WallSlabShape.DOUBLE && SimpleWaterloggedBlock.super.canPlaceLiquid(filler, world, pos, state, fluid);
  }

  @Override
  public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
    return state.getValue(SHAPE) != WallSlabShape.DOUBLE && SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidState);
  }

  @Override
  protected boolean isPathfindable(BlockState state, PathComputationType type) {
    if (type == PathComputationType.WATER) return state.getFluidState().is(FluidTags.WATER);
    return false;
  }

  @Override
  protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
    ItemStack itemStack = context.getItemInHand();
    WallSlabShape wallShape = state.getValue(SHAPE);
    if (wallShape != WallSlabShape.FLAT || !itemStack.is(this.asItem())) return false;
    if (context.replacingClickedOnBlock()) return context.getClickedFace() == state.getValue(FACING);
    return true;
  }

  @Override
  protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    return switch (state.getValue(SHAPE)) {
      case FLAT -> FLAT_SHAPES_BY_DIRECTION.get(state.getValue(FACING).getOpposite());
      case INNER_LEFT -> INNER_SHAPES_BY_DIRECTION.get(state.getValue(FACING).getOpposite());
      case INNER_RIGHT -> INNER_SHAPES_BY_DIRECTION.get(state.getValue(FACING).getCounterClockWise());
      case OUTER_LEFT -> OUTER_SHAPES_BY_DIRECTION.get(state.getValue(FACING).getOpposite());
      case OUTER_RIGHT -> OUTER_SHAPES_BY_DIRECTION.get(state.getValue(FACING).getCounterClockWise());
      case DOUBLE -> Shapes.block();
    };
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockPos pos = context.getClickedPos();
    BlockState currentState = context.getLevel().getBlockState(pos);
    if (currentState.is(this)) return currentState.setValue(SHAPE, WallSlabShape.DOUBLE).setValue(WATERLOGGED, false);
    Direction placementDirection = context.getClickedFace();
    Level world = context.getLevel();
    return updateState(
        world, pos,
        this.defaultBlockState()
            .setValue(FACING, placementDirection.getAxis() == Direction.Axis.Y ? context.getHorizontalDirection() : placementDirection.getOpposite())
            .setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)
    );
  }

  @Override
  protected BlockState updateShape(
      BlockState state,
      LevelReader world,
      ScheduledTickAccess tickView,
      BlockPos pos,
      Direction direction,
      BlockPos neighborPos,
      BlockState neighborState,
      RandomSource random
  ) {
    if (state.getValue(WATERLOGGED)) tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    return super.updateShape(updateState(world, pos, state), world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  private static BlockState updateState(BlockGetter world, BlockPos pos, BlockState state) {
    if (state.getValue(SHAPE) == WallSlabShape.DOUBLE) return state;
    Direction direction = state.getValue(FACING);
    BlockState leftState = world.getBlockState(pos.relative(direction.getCounterClockWise()));
    boolean leftLocked = canConnectTo(leftState.getBlock()) && leftState.getValue(FACING) == direction;
    BlockState rightState = world.getBlockState(pos.relative(direction.getClockWise()));
    boolean rightLocked = canConnectTo(rightState.getBlock()) && rightState.getValue(FACING) == direction;
    BlockState frontState = world.getBlockState(pos.relative(direction.getOpposite()));
    BlockState backState = world.getBlockState(pos.relative(direction));
    if (canConnectTo(frontState.getBlock())) {
      Direction facing = frontState.getValue(FACING);
      if (direction.getCounterClockWise() == facing && !leftLocked) return state.setValue(SHAPE, WallSlabShape.INNER_LEFT);
      if (direction.getClockWise() == facing && !rightLocked) return state.setValue(SHAPE, WallSlabShape.INNER_RIGHT);
    }
    if (canConnectTo(backState.getBlock())) {
      Direction facing = backState.getValue(FACING);
      if (direction.getCounterClockWise() == facing && !rightLocked) return state.setValue(SHAPE, WallSlabShape.OUTER_LEFT);
      if (direction.getClockWise() == facing && !leftLocked) return state.setValue(SHAPE, WallSlabShape.OUTER_RIGHT);
    }
    return state.setValue(SHAPE, WallSlabShape.FLAT);
  }

  private static boolean canConnectTo(Block block) {
    return block instanceof StairBlock || block instanceof WallSlabBlock;
  }

  @Override
  protected BlockState rotate(BlockState state, Rotation rotation) {
    Direction facing = state.getValue(FACING);
    return state.setValue(FACING, switch (rotation) {
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
  protected BlockState mirror(BlockState state, Mirror mirror) {
    Direction facing = state.getValue(FACING);
    WallSlabShape shape = state.getValue(SHAPE);
    return switch (mirror) {
      case NONE -> state;
      case FRONT_BACK -> state.rotate(facing.getAxis() == Direction.Axis.X ? Rotation.CLOCKWISE_180 : Rotation.NONE).setValue(SHAPE, getMirrored(shape));
      case LEFT_RIGHT -> state.rotate(facing.getAxis() == Direction.Axis.Z ? Rotation.CLOCKWISE_180 : Rotation.NONE).setValue(SHAPE, getMirrored(shape));
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
