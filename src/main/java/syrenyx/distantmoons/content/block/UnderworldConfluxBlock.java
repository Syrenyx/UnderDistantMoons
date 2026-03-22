package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.block_state_enums.UnderworldConfluxState;
import syrenyx.distantmoons.content.block.entity.UnderworldConfluxBlockEntity;
import syrenyx.distantmoons.references.DistantMoonsBlockStateProperties;

public class UnderworldConfluxBlock extends BaseEntityBlock implements UnderworldBlock {

  public static final MapCodec<UnderworldConfluxBlock> CODEC = simpleCodec(UnderworldConfluxBlock::new);

  public static final EnumProperty<UnderworldConfluxState> STATE = DistantMoonsBlockStateProperties.UNDERWORLD_CONFLUX_STATE;

  public UnderworldConfluxBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(STATE, UnderworldConfluxState.LIT)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(STATE);
  }

  @Override
  protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
    return new UnderworldConfluxBlockEntity(blockPos, blockState);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    BlockState blockState = super.getStateForPlacement(context);
    if (blockState == null) return null;
    return blockState.setValue(STATE, UnderworldConfluxState.getAtPosition(context.getLevel(), context.getClickedPos()));
  }

  @Override
  protected void randomTick(@NonNull BlockState blockState, @NonNull ServerLevel serverLevel, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    boolean inUnderworld = UnderworldBlock.inUnderworld(serverLevel, blockPos);
    if (inUnderworld == blockState.getValue(STATE).lit()) return;
    serverLevel.setBlock(blockPos, blockState.setValue(STATE, inUnderworld ? UnderworldConfluxState.LIT : UnderworldConfluxState.UNLIT), Block.UPDATE_ALL);
  }

  public static int lightLevel(BlockState blockState) {
    return switch (blockState.getValue(STATE)) {
      case ACTIVE -> 15;
      case LIT -> 10;
      case UNLIT -> 0;
    };
  }

  public static MapColor mapColor(BlockState blockState) {
    return blockState.getValue(STATE).lit() ? MapColor.COLOR_ORANGE : MapColor.COLOR_BLACK;
  }
}
