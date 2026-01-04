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
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class SittingSpotEntity extends Entity {

  public SittingSpotEntity(EntityType<?> type, Level world) {
    super(type, world);
    this.noPhysics = true;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {}

  @Override
  public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
    return false;
  }

  @Override
  protected void readAdditionalSaveData(ValueInput view) {}

  @Override
  protected void addAdditionalSaveData(ValueOutput view) {}

  @Override
  protected void removePassenger(Entity passenger) {
    super.removePassenger(passenger);
    if (this.level() instanceof ServerLevel serverWorld) this.kill(serverWorld);
  }

  public PushReaction getPistonPushReaction() {
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
  protected void positionRider(Entity passenger, MoveFunction positionUpdater) {
    super.positionRider(passenger, positionUpdater);
    if (passenger instanceof LivingEntity entity) entity.yBodyRot = this.getYRot();
  }
}
