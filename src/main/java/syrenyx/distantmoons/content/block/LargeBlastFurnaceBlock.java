package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.block_state_enums.BlockCorner;
import syrenyx.distantmoons.content.block.entity.LargeBlastFurnaceBlockEntity;
import syrenyx.distantmoons.initializers.DistantMoonsBlockEntityTypes;
import syrenyx.distantmoons.initializers.DistantMoonsStats;
import syrenyx.distantmoons.utility.BlockUtil;

import java.util.List;
import java.util.Map;

public class LargeBlastFurnaceBlock extends BaseEntityBlock {

  public static final MapCodec<LargeBlastFurnaceBlock> CODEC = simpleCodec(LargeBlastFurnaceBlock::new);
  public static final EnumProperty<BlockCorner> CORNER = EnumProperty.create("corner", BlockCorner.class);
  public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
  public static final BooleanProperty MIRRORED = BooleanProperty.create("mirrored");
  public static final IntegerProperty HEAT = IntegerProperty.create("heat", 0, 3);
  private static boolean overrideIntegrityCheck = false;

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
    return new LargeBlastFurnaceBlockEntity(blockPos, blockState);
  }

  @Override
  public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> blockEntityType) {
    return level instanceof ServerLevel
        ? createTickerHelper(blockEntityType, DistantMoonsBlockEntityTypes.LARGE_BLAST_FURNACE, LargeBlastFurnaceBlockEntity::serverTick)
        : null;
  }

  protected boolean hasAnalogOutputSignal(@NonNull BlockState blockState) {
    return true;
  }

  protected int getAnalogOutputSignal(@NonNull BlockState blockState, Level level, @NonNull BlockPos blockPos, @NonNull Direction direction) {
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    return blockEntity instanceof LargeBlastFurnaceBlockEntity largeBlastFurnaceBlockEntity ? largeBlastFurnaceBlockEntity.getAnalogOutputSignal() : 0;
  }

  @Override
  protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull Player player, @NonNull BlockHitResult blockHitResult) {
    if (level.isClientSide()) return InteractionResult.SUCCESS;
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if (!(blockEntity instanceof LargeBlastFurnaceBlockEntity largeBlastFurnaceBlockEntity)) return InteractionResult.SUCCESS;
    player.openMenu(largeBlastFurnaceBlockEntity);
    player.awardStat(DistantMoonsStats.INTERACT_WITH_LARGE_BLAST_FURNACE);
    return InteractionResult.SUCCESS;
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
    overrideIntegrityCheck = true;
    corners.forEach((pos, corner) -> {
      if (blockPos.equals(pos)) return;
      level.destroyBlock(pos, true);
      level.setBlock(pos, blockState.setValue(CORNER, corner), Block.UPDATE_ALL);
    });
    overrideIntegrityCheck = false;
  }

  @Override
  protected @NonNull BlockState updateShape(
      @NonNull BlockState state,
      @NonNull LevelReader level,
      @NonNull ScheduledTickAccess tickView,
      @NonNull BlockPos pos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    return shapeIntegrityCheck(level, state, pos)
        ? super.updateShape(state, level, tickView, pos, direction, neighborPos, neighborState, random)
        : Blocks.AIR.defaultBlockState();
  }

  public static boolean shapeIntegrityCheck(LevelReader level, BlockState state, BlockPos pos) {
    if (overrideIntegrityCheck) return true;
    Direction facing = state.getValue(FACING);
    boolean mirrored = state.getValue(MIRRORED);
    Map<BlockPos, BlockCorner> corners = state.getValue(CORNER).getCornersForPositionsInBlock(pos);
    for (Map.Entry<BlockPos, BlockCorner> corner : corners.entrySet()) {
      BlockState blockState = level.getBlockState(corner.getKey());
      if (
          !(blockState.getBlock() instanceof LargeBlastFurnaceBlock)
              || blockState.getValue(CORNER) != corner.getValue()
              || blockState.getValue(FACING) != facing
              || blockState.getValue(MIRRORED) != mirrored
      ) return false;
    }
    return true;
  }

  public static boolean hasFuelAccess(BlockState blockState) {
    BlockCorner corner = blockState.getValue(CORNER);
    boolean mirrored = blockState.getValue(MIRRORED);
    return switch (blockState.getValue(FACING)) {
      case NORTH -> (corner.north && mirrored) != corner.east;
      case SOUTH -> (!corner.north && mirrored) == corner.east;
      case WEST -> (corner.east && mirrored) != corner.north;
      case EAST -> (!corner.east && mirrored) == corner.north;
      default -> false;
    };
  }
}
