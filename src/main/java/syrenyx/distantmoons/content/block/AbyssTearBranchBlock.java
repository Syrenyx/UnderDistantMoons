package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

import java.util.Optional;

public class AbyssTearBranchBlock extends ThinBranchBlock {

  public AbyssTearBranchBlock(Properties properties) {
    super(properties);
  }

  @Override
  public @NonNull ItemStack pickupBlock(@Nullable LivingEntity user, @NonNull LevelAccessor level, @NonNull BlockPos pos, @NonNull BlockState state) {
    if (!state.getValue(WATERLOGGED)) return ItemStack.EMPTY;
    level.setBlock(pos, state.setValue(WATERLOGGED, false), Block.UPDATE_ALL);
    return DistantMoonsItems.ABYSS_TEAR_BUCKET.getDefaultInstance();
  }

  @Override
  public @NonNull Optional<SoundEvent> getPickupSound() {
    return Optional.of(SoundEvents.BUCKET_FILL);
  }

  @Override
  public boolean placeLiquid(@NonNull LevelAccessor level, @NonNull BlockPos blockPos, @NonNull BlockState blockState, @NonNull FluidState fluidState) {
    if (blockState.getValue(WATERLOGGED) || !fluidState.is(Fluids.WATER)) return false;
    if (level.isClientSide()) return true;
    level.setBlock(blockPos, blockState.setValue(BlockStateProperties.WATERLOGGED, true), Block.UPDATE_ALL);
    return true;
  }

  @Override
  protected @NonNull BlockState updateShape(
      @NonNull BlockState blockState,
      @NonNull LevelReader level,
      ScheduledTickAccess tickView,
      @NonNull BlockPos blockPos,
      @NonNull Direction direction,
      @NonNull BlockPos neighborPos,
      @NonNull BlockState neighborState,
      @NonNull RandomSource random
  ) {
    BlockState updatedState = this.updateState(level, blockPos, blockState);
    return updatedState != null ? updatedState : Blocks.AIR.defaultBlockState();
  }
}
