package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.particle.UnderworldParticleOptions;
import syrenyx.distantmoons.utility.VectorUtil;

public class UnderworldAnchorBlock extends Block implements UnderworldBlock {

  public static final BooleanProperty LIT = BlockStateProperties.LIT;

  public UnderworldAnchorBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(LIT, true)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(LIT);
  }

  @Nullable @Override
  public BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    return UnderworldBlock.getStateForPlacement(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
  }

  @Override
  protected void randomTick(@NonNull BlockState blockState, @NonNull ServerLevel serverLevel, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    UnderworldBlock.randomTick(blockState, serverLevel, blockPos);
  }

  @Override
  public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    if (!state.getValue(LIT) || randomSource.nextInt(2) != 0) return;
    Vec3 anchor = blockPos.getCenter();
    Vec3 particlePosition = anchor.add(VectorUtil.randomPointOnSphere(randomSource, ((float) randomSource.nextInt(3, 10) / 5) + 1));
    level.addParticle(
        new UnderworldParticleOptions(anchor, UnderworldBlock.DEFAULT_COLOR),
        particlePosition.x(), particlePosition.y(), particlePosition.z(),
        0.0F, 0.0F, 0.0F
    );
  }

  public static int lightLevel(BlockState blockState) {
    return blockState.getValue(UnderworldAnchorBlock.LIT) ? 15 : 0;
  }

  public static MapColor mapColor(BlockState blockState) {
    return blockState.getValue(UnderworldAnchorBlock.LIT) ? MapColor.COLOR_ORANGE : MapColor.COLOR_BLACK;
  }
}
