package syrenyx.distantmoons.content.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class SittingSpotEntity extends Entity {

  public SittingSpotEntity(EntityType<?> type, Level world) {
    super(type, world);
    this.noPhysics = true;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {}

  @Override
  public boolean hurtServer(@NonNull ServerLevel world, @NonNull DamageSource source, float amount) {
    return false;
  }

  @Override
  protected void readAdditionalSaveData(@NonNull ValueInput view) {}

  @Override
  protected void addAdditionalSaveData(@NonNull ValueOutput view) {}

  @Override
  protected void removePassenger(@NonNull Entity passenger) {
    super.removePassenger(passenger);
    if (this.level() instanceof ServerLevel serverWorld) this.kill(serverWorld);
  }

  public @NonNull PushReaction getPistonPushReaction() {
    return PushReaction.IGNORE;
  }

  @Override
  public void tick() {
    super.tick();
    if (this.getFirstPassenger() instanceof LivingEntity entity) this.setRot(entity.getYRot(), 0.0F);
    if (!(this.level() instanceof ServerLevel serverWorld)) return;
    if (!serverWorld.getBlockState(BlockPos.containing(this.position())).is(DistantMoonsBlockTags.SUPPORT_BLOCK_FOR_SITTING_SPOT)) this.kill(serverWorld);
  }

  @Override
  protected void positionRider(@NonNull Entity passenger, @NonNull MoveFunction positionUpdater) {
    super.positionRider(passenger, positionUpdater);
    if (passenger instanceof LivingEntity entity) entity.yBodyRot = this.getYRot();
  }
}
