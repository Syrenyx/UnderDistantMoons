package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.block.block_state_enums.RopeLadderDirection;
import syrenyx.distantmoons.utility.VoxelShapeUtil;
import com.mojang.math.OctahedralGroup;
import java.util.Arrays;
import java.util.Map;

public class RopeLadderBlock extends Block {

  public static final EnumProperty<RopeLadderDirection> DIRECTION = EnumProperty.create("direction", RopeLadderDirection.class);
  public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
  public static final BooleanProperty TOP = BooleanProperty.create("top");
  private static final VoxelShape OUTER_SHAPE = Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
  private static final VoxelShape INNER_SHAPE_X = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 16.0);
  private static final VoxelShape INNER_SHAPE_Z = Shapes.rotate(INNER_SHAPE_X, OctahedralGroup.ROT_90_Y_POS, VoxelShapeUtil.BLOCK_CENTER_ANCHOR);
  private static final VoxelShape CEILING_SHAPE_X = Block.box(7.0, 0.0, 0.0, 9.0, 5.0, 16.0);
  private static final VoxelShape CEILING_SHAPE_Z = Shapes.rotate(CEILING_SHAPE_X, OctahedralGroup.ROT_90_Y_POS, VoxelShapeUtil.BLOCK_CENTER_ANCHOR);
  private static final Map<Direction, VoxelShape> OUTER_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(OUTER_SHAPE);

  public RopeLadderBlock(Properties settings) {
    super(settings);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(DIRECTION, RopeLadderDirection.NORTH)
        .setValue(BOTTOM, true)
        .setValue(TOP, true)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(DIRECTION, BOTTOM, TOP);
  }

  @Override
  protected boolean propagatesSkylightDown(@NonNull BlockState state) {
    return true;
  }

  @Override
  protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
    return true;
  }

  @Override
  protected @NonNull VoxelShape getCollisionShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    if (state.getValue(DIRECTION).isCenter() && state.getValue(TOP)) return state.getValue(DIRECTION) == RopeLadderDirection.X ? CEILING_SHAPE_X : CEILING_SHAPE_Z;
    return this.getShape(state, world, pos, context);
  }

  @Override
  protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    return switch (state.getValue(DIRECTION)) {
      case NORTH -> OUTER_SHAPES_BY_DIRECTION.get(Direction.NORTH);
      case EAST -> OUTER_SHAPES_BY_DIRECTION.get(Direction.EAST);
      case SOUTH -> OUTER_SHAPES_BY_DIRECTION.get(Direction.SOUTH);
      case WEST -> OUTER_SHAPES_BY_DIRECTION.get(Direction.WEST);
      case X -> INNER_SHAPE_X;
      case Z -> INNER_SHAPE_Z;
    };
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Level world = context.getLevel();
    BlockPos pos = context.getClickedPos();
    Player player = context.getPlayer();
    boolean sneaking = player != null && player.isShiftKeyDown();
    BlockState top = world.getBlockState(pos.above());
    RopeLadderDirection ropeLadderDirection = !sneaking && top.is(this) ? top.getValue(DIRECTION) : Arrays
        .stream(context.getNearestLookingDirections())
        .filter(direction -> {
          if (direction.getAxis() != Direction.Axis.Y && world.getBlockState(pos.relative(direction)).isFaceSturdy(world, pos.relative(direction), direction.getOpposite())) return true;
          return direction == Direction.UP && (Block.canSupportCenter(world, pos.above(), Direction.DOWN) || world.getBlockState(pos.above()).is(this));
        })
        .findFirst()
        .map(direction -> direction != Direction.UP
            ? RopeLadderDirection.fromDirection(direction.getOpposite())
            : top.is(this) ? top.getValue(DIRECTION) : RopeLadderDirection.fromAxis(context.getHorizontalDirection().getAxis())
        )
        .orElse(null);
    if (ropeLadderDirection == null) return Blocks.AIR.defaultBlockState();
    BlockState bottom = world.getBlockState(pos.below());
    return this.defaultBlockState()
        .setValue(DIRECTION, ropeLadderDirection)
        .setValue(BOTTOM, !bottom.is(this) || bottom.getValue(DIRECTION) != ropeLadderDirection)
        .setValue(TOP, !top.is(this) || top.getValue(DIRECTION) != ropeLadderDirection);
  }

  @Override
  protected @NonNull BlockState updateShape(
      BlockState state,
      LevelReader world,
      @NonNull ScheduledTickAccess tickView,
      BlockPos pos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    BlockState top = world.getBlockState(pos.above());
    BlockState bottom = world.getBlockState(pos.below());
    BlockState air = Blocks.AIR.defaultBlockState();
    Direction ladderDirection = state.getValue(DIRECTION).getDirection();
    boolean ceilingSupport = Block.canSupportCenter(world, pos.above(), Direction.DOWN) || (top.is(this) && top.getValue(DIRECTION) == state.getValue(DIRECTION));
    boolean sideSupport = ladderDirection != null && world.getBlockState(pos.relative(ladderDirection.getOpposite())).isFaceSturdy(world, pos.south(), ladderDirection);
    if (!ceilingSupport && !sideSupport) return air;
    return state
        .setValue(BOTTOM, !bottom.is(this) || bottom.getValue(DIRECTION) != state.getValue(DIRECTION))
        .setValue(TOP, !top.is(this) || top.getValue(DIRECTION) != state.getValue(DIRECTION));
  }

  @Override
  protected @NonNull BlockState rotate(BlockState state, @NonNull Rotation rotation) {
    return state.setValue(DIRECTION, state.getValue(DIRECTION).rotate(rotation));
  }

  @Override
  protected @NonNull BlockState mirror(BlockState state, @NonNull Mirror mirror) {
    return state.setValue(DIRECTION, state.getValue(DIRECTION).mirror(mirror));
  }
}
