package syrenyx.distantmoons.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
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
import syrenyx.distantmoons.content.block.block_state_enums.RopeLadderDirection;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Arrays;
import java.util.Map;

public class RopeLadderBlock extends Block {

  public static final EnumProperty<RopeLadderDirection> DIRECTION = EnumProperty.of("direction", RopeLadderDirection.class);
  public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");
  public static final BooleanProperty TOP = BooleanProperty.of("top");
  private static final VoxelShape OUTER_SHAPE = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
  private static final VoxelShape INNER_SHAPE_X = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 16.0);
  private static final VoxelShape INNER_SHAPE_Z = VoxelShapes.transform(INNER_SHAPE_X, DirectionTransformation.ROT_90_Y_POS, VoxelShapeUtil.BLOCK_CENTER_ANCHOR);
  private static final VoxelShape CEILING_SHAPE_X = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 5.0, 16.0);
  private static final VoxelShape CEILING_SHAPE_Z = VoxelShapes.transform(CEILING_SHAPE_X, DirectionTransformation.ROT_90_Y_POS, VoxelShapeUtil.BLOCK_CENTER_ANCHOR);
  private static final Map<Direction, VoxelShape> OUTER_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(OUTER_SHAPE);

  public RopeLadderBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(DIRECTION, RopeLadderDirection.NORTH)
        .with(BOTTOM, true)
        .with(TOP, true)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(DIRECTION, BOTTOM, TOP);
  }

  @Override
  protected boolean isTransparent(BlockState state) {
    return true;
  }

  @Override
  protected boolean canPathfindThrough(BlockState state, NavigationType type) {
    return true;
  }

  @Override
  protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    if (state.get(DIRECTION).isCenter() && state.get(TOP)) return state.get(DIRECTION) == RopeLadderDirection.X ? CEILING_SHAPE_X : CEILING_SHAPE_Z;
    return this.getOutlineShape(state, world, pos, context);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(DIRECTION)) {
      case NORTH -> OUTER_SHAPES_BY_DIRECTION.get(Direction.NORTH);
      case EAST -> OUTER_SHAPES_BY_DIRECTION.get(Direction.EAST);
      case SOUTH -> OUTER_SHAPES_BY_DIRECTION.get(Direction.SOUTH);
      case WEST -> OUTER_SHAPES_BY_DIRECTION.get(Direction.WEST);
      case X -> INNER_SHAPE_X;
      case Z -> INNER_SHAPE_Z;
    };
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    World world = context.getWorld();
    BlockPos pos = context.getBlockPos();
    PlayerEntity player = context.getPlayer();
    boolean sneaking = player != null && player.isSneaking();
    BlockState top = world.getBlockState(pos.up());
    RopeLadderDirection ropeLadderDirection = !sneaking && top.isOf(this) ? top.get(DIRECTION) : Arrays
        .stream(context.getPlacementDirections())
        .filter(direction -> {
          if (direction.getAxis() != Direction.Axis.Y && world.getBlockState(pos.offset(direction)).isSideSolidFullSquare(world, pos.offset(direction), direction.getOpposite())) return true;
          return direction == Direction.UP && (Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) || world.getBlockState(pos.up()).isOf(this));
        })
        .findFirst()
        .map(direction -> direction != Direction.UP
            ? RopeLadderDirection.fromDirection(direction.getOpposite())
            : top.isOf(this) ? top.get(DIRECTION) : RopeLadderDirection.fromAxis(context.getHorizontalPlayerFacing().getAxis())
        )
        .orElse(null);
    if (ropeLadderDirection == null) return Blocks.AIR.getDefaultState();
    BlockState bottom = world.getBlockState(pos.down());
    return this.getDefaultState()
        .with(DIRECTION, ropeLadderDirection)
        .with(BOTTOM, !bottom.isOf(this) || bottom.get(DIRECTION) != ropeLadderDirection)
        .with(TOP, !top.isOf(this) || top.get(DIRECTION) != ropeLadderDirection);
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
    BlockState top = world.getBlockState(pos.up());
    BlockState bottom = world.getBlockState(pos.down());
    BlockState air = Blocks.AIR.getDefaultState();
    Direction ladderDirection = state.get(DIRECTION).getDirection();
    boolean ceilingSupport = Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) || (top.isOf(this) && top.get(DIRECTION) == state.get(DIRECTION));
    boolean sideSupport = ladderDirection != null && world.getBlockState(pos.offset(ladderDirection.getOpposite())).isSideSolidFullSquare(world, pos.south(), ladderDirection);
    if (!ceilingSupport && !sideSupport) return air;
    return state
        .with(BOTTOM, !bottom.isOf(this) || bottom.get(DIRECTION) != state.get(DIRECTION))
        .with(TOP, !top.isOf(this) || top.get(DIRECTION) != state.get(DIRECTION));
  }

  @Override
  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(DIRECTION, state.get(DIRECTION).rotate(rotation));
  }

  @Override
  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.with(DIRECTION, state.get(DIRECTION).mirror(mirror));
  }
}
