package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WallBlock;
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
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;
import com.mojang.math.OctahedralGroup;
import java.util.Map;

public class PoleBlock extends Block implements SimpleWaterloggedBlock {

  public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
  public static final BooleanProperty UP = PipeBlock.UP;
  public static final BooleanProperty DOWN = PipeBlock.DOWN;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  private static final VoxelShape EXTENSION_SHAPE = Block.box(6.0, 16.0, 6.0, 10.0, 22.0, 10.0);
  private static final Map<Direction.Axis, VoxelShape> CENTER_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0));
  private static final Map<Direction.Axis, VoxelShape> UP_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(EXTENSION_SHAPE);
  private static final Map<Direction.Axis, VoxelShape> DOWN_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Shapes.rotate(EXTENSION_SHAPE, OctahedralGroup.ROT_180_FACE_XY, VoxelShapeUtil.BLOCK_CENTER_ANCHOR));

  public PoleBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(AXIS, Direction.Axis.Y)
        .setValue(UP, false)
        .setValue(DOWN, false)
        .setValue(WATERLOGGED, false)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(AXIS, UP, DOWN, WATERLOGGED);
  }

  @Override
  protected @NonNull FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  public boolean canPlaceLiquid(@Nullable LivingEntity filler, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
    return SimpleWaterloggedBlock.super.canPlaceLiquid(filler, world, pos, state, fluid);
  }

  @Override
  public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
    return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidState);
  }

  @Override
  protected boolean isPathfindable(BlockState state, PathComputationType type) {
    if (type == PathComputationType.WATER) return state.getFluidState().is(FluidTags.WATER);
    return false;
  }

  @Override
  protected @NonNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    VoxelShape shape = CENTER_SHAPES_BY_AXIS.get(state.getValue(AXIS));
    if (state.getValue(UP)) shape = Shapes.or(shape, UP_SHAPES_BY_AXIS.get(state.getValue(AXIS)));
    if (state.getValue(DOWN)) shape = Shapes.or(shape, DOWN_SHAPES_BY_AXIS.get(state.getValue(AXIS)));
    return shape;
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockPos pos = context.getClickedPos();
    return this.updateState(context.getLevel(), pos, this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, context.getLevel().getFluidState(pos).getType() == Fluids.WATER));
  }

  @Override
  protected @NonNull BlockState updateShape(
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
    return super.updateShape(this.updateState(world, pos, state), world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  private BlockState updateState(BlockGetter world, BlockPos pos, BlockState state) {
    return switch (state.getValue(AXIS)) {
      case X -> state
          .setValue(UP, this.canConnectTo(world, pos, Direction.EAST))
          .setValue(DOWN, this.canConnectTo(world, pos, Direction.WEST));
      case Y -> state
          .setValue(UP, this.canConnectTo(world, pos, Direction.UP))
          .setValue(DOWN, this.canConnectTo(world, pos, Direction.DOWN));
      case Z -> state
          .setValue(UP, this.canConnectTo(world, pos, Direction.NORTH))
          .setValue(DOWN, this.canConnectTo(world, pos, Direction.SOUTH));
    };
  }

  protected boolean canConnectTo(BlockGetter world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.relative(direction));
    if (state.is(DistantMoonsBlockTags.POLE_NEVER_CONNECTS_TO)) return false;
    if (state.is(DistantMoonsBlockTags.POLE_ALWAYS_CONNECTS_TO)) return true;
    Direction.Axis axis = direction.getAxis();
    if (state.getBlock() instanceof PoleBlock && state.getValue(AXIS) != axis) return true;
    if (axis != Direction.Axis.Y && (state.getBlock() instanceof FenceBlock || state.getBlock() instanceof WallBlock)) return true;
    return false;
  }

  @Override
  protected @NonNull BlockState rotate(BlockState state, @NonNull Rotation rotation) {
    return switch (state.getValue(AXIS)) {
      case X -> switch (rotation) {
        case NONE -> state;
        case CLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.Z).setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
        case CLOCKWISE_180 -> state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
        case COUNTERCLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.Z);
      };
      case Y -> state;
      case Z -> switch (rotation) {
        case NONE -> state;
        case CLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.X);
        case CLOCKWISE_180 -> state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
        case COUNTERCLOCKWISE_90 -> state.setValue(AXIS, Direction.Axis.X).setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP));
      };
    };
  }

  @Override
  protected @NonNull BlockState mirror(@NonNull BlockState state, Mirror mirror) {
    return switch (mirror) {
      case NONE -> state;
      case LEFT_RIGHT -> state.getValue(AXIS) == Direction.Axis.Z ? state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP)) : state;
      case FRONT_BACK -> state.getValue(AXIS) == Direction.Axis.X ? state.setValue(UP, state.getValue(DOWN)).setValue(DOWN, state.getValue(UP)) : state;
    };
  }
}
