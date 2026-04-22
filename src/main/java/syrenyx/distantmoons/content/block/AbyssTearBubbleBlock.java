package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

import java.util.Optional;

public class AbyssTearBubbleBlock extends Block implements BucketPickup {

  private static final float BOUNCE_FALL_DISTANCE = 6.0F;
  private static final float LIVING_ENTITY_BOUNCE_FACTOR = 0.8F;
  private static final float NON_LIVING_ENTITY_BOUNCE_FACTOR = 0.5F;

  public AbyssTearBubbleBlock(Properties properties) {
    super(properties);
  }

  @Override
  public @NonNull ItemStack pickupBlock(@Nullable LivingEntity user, @NonNull LevelAccessor level, @NonNull BlockPos pos, @NonNull BlockState state) {
    level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
    if (!level.isClientSide()) level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
    return DistantMoonsItems.ABYSS_TEAR_BUCKET.getDefaultInstance();
  }

  @Override
  public @NonNull Optional<SoundEvent> getPickupSound() {
    return Optional.of(SoundEvents.BUCKET_FILL);
  }

  @Override
  protected @NonNull FluidState getFluidState(@NonNull BlockState blockState) {
    return Fluids.WATER.getSource(false);
  }

  @Override
  protected boolean canBeReplaced(@NonNull BlockState state, @NonNull BlockPlaceContext context) {
    return !context.getItemInHand().is(DistantMoonsItems.ABYSS_TEAR_BUCKET);
  }

  @Override
  public void updateEntityMovementAfterFallOn(final @NonNull BlockGetter level, final @NonNull Entity entity) {
    if ((entity.fallDistance < BOUNCE_FALL_DISTANCE) || entity.isSuppressingBounce()) {
      super.updateEntityMovementAfterFallOn(level, entity);
      return;
    }
    Vec3 movement = entity.getDeltaMovement();
    entity.setDeltaMovement(
        movement.x,
        -movement.y * (entity instanceof LivingEntity ? LIVING_ENTITY_BOUNCE_FACTOR : NON_LIVING_ENTITY_BOUNCE_FACTOR),
        movement.z
    );
  }
}
