package syrenyx.distantmoons.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class MetalBarDoorBlock extends DoorBlock {

  public static final BooleanProperty DOUBLE = BooleanProperty.of("double");

  public MetalBarDoorBlock(BlockSetType type, Settings settings) {
    super(type, settings);
    this.setDefaultState(this.getDefaultState()
        .with(DOUBLE, false)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(DOUBLE);
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    BlockState state = super.getPlacementState(context);
    if (state == null) return null;
    return updateState(context.getWorld(), context.getBlockPos(), state);
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
    return updateState(world, pos, super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random));
  }

  private BlockState updateState(BlockView world, BlockPos pos, BlockState state) {
    return state.getBlock() instanceof MetalBarDoorBlock ? state.with(DOUBLE, canConnectTo(world, pos, state)) : state;
  }

  private boolean canConnectTo(BlockView world, BlockPos pos, BlockState state) {
    Direction offset = state.get(DoorBlock.FACING).rotateClockwise(Direction.Axis.Y);
    BlockState neighborState = world.getBlockState(pos.offset(state.get(DoorBlock.HINGE) == DoorHinge.RIGHT ? offset.getOpposite() : offset));
    if (!(neighborState.getBlock() instanceof DoorBlock) || state.isIn(DistantMoonsBlockTags.METAL_BAR_DOOR_NEVER_CONNECTS_TO)) return false;
    return state.get(DoorBlock.FACING) == neighborState.get(DoorBlock.FACING)
        && state.get(DoorBlock.HALF) == neighborState.get(DoorBlock.HALF)
        && state.get(DoorBlock.HINGE) != neighborState.get(DoorBlock.HINGE);
  }
}
