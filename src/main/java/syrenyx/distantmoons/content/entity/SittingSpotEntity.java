package syrenyx.distantmoons.content.entity;

import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class SittingSpotEntity extends Entity {

  public SittingSpotEntity(EntityType<?> type, World world) {
    super(type, world);
    this.noClip = true;
  }

  @Override
  protected void initDataTracker(DataTracker.Builder builder) {}

  @Override
  public boolean damage(ServerWorld world, DamageSource source, float amount) {
    return false;
  }

  @Override
  protected void readCustomData(ReadView view) {}

  @Override
  protected void writeCustomData(WriteView view) {}

  @Override
  protected void removePassenger(Entity passenger) {
    super.removePassenger(passenger);
    if (this.getEntityWorld() instanceof ServerWorld serverWorld) this.kill(serverWorld);
  }

  public PistonBehavior getPistonBehavior() {
    return PistonBehavior.IGNORE;
  }

  @Override
  public void tick() {
    super.tick();
    if (this.getFirstPassenger() instanceof LivingEntity entity) this.setRotation(entity.getYaw(), 0.0F);
    if (!(this.getEntityWorld() instanceof ServerWorld serverWorld)) return;
    if (!serverWorld.getBlockState(BlockPos.ofFloored(this.getEntityPos())).isIn(DistantMoonsBlockTags.SUPPORT_BLOCK_FOR_SITTING_SPOT)) this.kill(serverWorld);
  }

  @Override
  protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
    super.updatePassengerPosition(passenger, positionUpdater);
    if (passenger instanceof LivingEntity entity) entity.bodyYaw = this.getYaw();
  }
}
