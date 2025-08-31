package syrenyx.distantmoons.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.affliction.AfflictionManager;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

  @Inject(at = @At("HEAD"), method = "baseTick")
  public void baseTick(CallbackInfo info) {
    AfflictionManager.processTickEffects((LivingEntity) (Object) this);
  }

  @Inject(at = @At("HEAD"), method = "applyMovementEffects")
  public void applyMovementEffects(ServerWorld world, BlockPos pos, CallbackInfo info) {
    AfflictionManager.processMovementEffects((LivingEntity) (Object) this);
  }

  @Inject(at = @At("HEAD"), method = "onDeath")
  public void onDeath(DamageSource damageSource, CallbackInfo info) {
    AfflictionManager.processDeathEffects((LivingEntity) (Object) this);
  }
}
