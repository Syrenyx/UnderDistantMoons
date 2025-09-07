package syrenyx.distantmoons.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.affliction.AfflictionManager;
import syrenyx.distantmoons.initializers.AfflictionEffectComponents;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

  @Inject(at = @At("HEAD"), method = "baseTick")
  public void baseTick(CallbackInfo callbackInfo) {
    LivingEntity entity = (LivingEntity) (Object) this;
    if (entity.getWorld() instanceof ServerWorld) AfflictionManager.handleTick(entity);
  }

  @Inject(at = @At("HEAD"), method = "onDeath")
  public void onDeath(DamageSource damageSource, CallbackInfo callbackInfo) {
    LivingEntity entity = (LivingEntity) (Object) this;
    if (entity.getWorld() instanceof ServerWorld) AfflictionManager.handlePostDamage(entity, damageSource, AfflictionEffectComponents.POST_DEATH);
  }
}
