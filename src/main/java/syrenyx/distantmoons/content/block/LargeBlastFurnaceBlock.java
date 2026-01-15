package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.block_state_enums.BlockCorner;
import syrenyx.distantmoons.utility.BlockUtil;

import java.util.List;
import java.util.Map;

public class LargeBlastFurnaceBlock extends BaseEntityBlock {

  public static final MapCodec<LargeBlastFurnaceBlock> CODEC = simpleCodec(LargeBlastFurnaceBlock::new);
  public static final EnumProperty<BlockCorner> CORNER = EnumProperty.create("corner", BlockCorner.class);
  public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
  public static final BooleanProperty MIRRORED = BooleanProperty.create("mirrored");
  public static final IntegerProperty HEAT = IntegerProperty.create("heat", 0, 3);

  public LargeBlastFurnaceBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(CORNER, BlockCorner.TOP_NORTH_EAST)
        .setValue(FACING, Direction.NORTH)
        .setValue(MIRRORED, false)
        .setValue(HEAT, 0)
    );
  }

  @Override
  protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(CORNER, FACING, MIRRORED, HEAT);
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
    return null; //new BlastFurnaceBlockEntity(blockPos, blockState);
  }

  @Override
  public BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    Level level = context.getLevel();
    Direction clickedFace = context.getClickedFace();
    List<Direction> closestEdges = BlockUtil.getBlockEdgesByCloseness(context.getClickLocation(), context.getClickedPos(), clickedFace.getAxis());
    BlockCorner corner = BlockCorner.getFrom(clickedFace, closestEdges);
    for (BlockPos pos : corner.getCornersForPositionsInBlock(context.getClickedPos()).keySet()) {
      if (level.isOutsideBuildHeight(pos) || !level.getBlockState(pos).canBeReplaced()) return null;
    }
    Direction facing = context.getHorizontalDirection().getOpposite();
    return this.defaultBlockState()
        .setValue(CORNER, corner)
        .setValue(FACING, facing)
        .setValue(MIRRORED, closestEdges.contains(facing.getClockWise()));
  }

  @Override
  public void setPlacedBy(@NonNull Level level, @NonNull BlockPos blockPos, @NonNull BlockState blockState, @Nullable LivingEntity livingEntity, @NonNull ItemStack itemStack) {
    super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
    if (level.isClientSide()) return;
    Map<BlockPos, BlockCorner> corners = blockState.getValue(CORNER).getCornersForPositionsInBlock(blockPos);
    corners.forEach((pos, corner) -> level.setBlock(pos, blockState.setValue(CORNER, corner), Block.UPDATE_ALL));
    //shape.keySet().forEach(pos -> level.updateNeighborsAt(pos, blockState.getBlock()));
    //shape.keySet().forEach(pos -> blockState.updateNeighbourShapes(level, pos, Block.UPDATE_ALL));
  }

  @Override
  protected @NonNull BlockState updateShape(
      @NonNull BlockState state,
      @NonNull LevelReader world,
      @NonNull ScheduledTickAccess tickView,
      @NonNull BlockPos pos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    return state;
  }
}
