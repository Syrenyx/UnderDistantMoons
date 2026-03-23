package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.block_state_enums.UnderworldConfluxState;
import syrenyx.distantmoons.content.block.entity.UnderworldConfluxBlockEntity;
import syrenyx.distantmoons.content.particle.UnderworldConfluxEffectOptions;
import syrenyx.distantmoons.content.particle.UnderworldParticleOptions;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;
import syrenyx.distantmoons.references.DistantMoonsBlockStateProperties;
import syrenyx.distantmoons.utility.VectorUtil;

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

  @Override
  public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    if (!state.getValue(STATE).lit()) return;
    Vec3 anchor = blockPos.getCenter();
    int color = getKeystoneColor(level, blockPos);
    spawnUnderworldConfluxParticles(level, anchor, color, randomSource.nextInt(1, 3), false, randomSource);
    if (randomSource.nextInt(10) != 0) return;
    Vec3 particlePosition = anchor.add(VectorUtil.randomPointOnSphere(randomSource, ((float) randomSource.nextInt(2, 4) / 5) + 1));
    level.addParticle(
        new UnderworldParticleOptions(anchor, color),
        particlePosition.x(), particlePosition.y(), particlePosition.z(),
        0.0F, 0.0F, 0.0F
    );
  }

  private static void spawnUnderworldConfluxParticles(Level level, Vec3 center, int color, int amount, boolean inverted, RandomSource randomSource) {
    for (int i = 0; i < amount; i++) {
      Vec3 outerPosition = center.add(VectorUtil.randomPointOnSphere(randomSource, 5));
      level.addParticle(
          new UnderworldConfluxEffectOptions(
              Mth.HALF_PI * randomSource.nextInt(0, 4),
              !inverted,
              color,
              inverted ? center : outerPosition
          ),
          inverted ? outerPosition.x() : center.x(),
          inverted ? outerPosition.y() : center.y(),
          inverted ? outerPosition.z() : center.z(),
          0.0F, 0.0F, 0.0F
      );
    }
  }

  private static int getKeystoneColor(@NonNull Level level, @NonNull BlockPos blockPos) {
    if (!(level.getBlockEntity(blockPos) instanceof UnderworldConfluxBlockEntity blockEntity)) return UnderworldBlock.DEFAULT_COLOR;
    ItemStack itemStack = blockEntity.getItem();
    if (itemStack.isEmpty() || !itemStack.has(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE)) return UnderworldBlock.DEFAULT_COLOR;
    return itemStack.get(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE).color();
  }

  public static void onKeystoneChanged(Level level, BlockPos blockPos) {
    BlockState blockState = level.getBlockState(blockPos);
    level.sendBlockUpdated(blockPos, blockState, blockState, 0);
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

  public static int tintColor(BlockState blockState, BlockAndTintGetter level, BlockPos blockPos, int tintIndex) {
    if (tintIndex != 1 || level == null || blockPos == null) return -1;
    if (blockState.getValue(UnderworldConfluxBlock.STATE) == UnderworldConfluxState.UNLIT) return UnderworldBlock.UNLIT_COLOR;
    var test = level.getBlockEntityRenderData(blockPos);
    return level.getBlockEntityRenderData(blockPos) instanceof Integer color ? color : UnderworldBlock.DEFAULT_COLOR;
  }
}
