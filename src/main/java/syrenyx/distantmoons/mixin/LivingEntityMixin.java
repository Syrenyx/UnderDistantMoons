package syrenyx.distantmoons.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.content.affliction.AfflictionManager;
import syrenyx.distantmoons.initializers.DistantMoonsAfflictionEffectComponents;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

  @Inject(at = @At("HEAD"), method = "baseTick")
  public void distantMoons$baseTick(CallbackInfo callbackInfo) {
    LivingEntity entity = (LivingEntity) (Object) this;
    if (entity.level() instanceof ServerLevel) AfflictionManager.handleTick(entity);
  }

  @Inject(at = @At("HEAD"), method = "die")
  public void distantMoons$die(DamageSource damageSource, CallbackInfo callbackInfo) {
    LivingEntity entity = (LivingEntity) (Object) this;
    if (entity.level() instanceof ServerLevel) AfflictionManager.handlePostDamage(entity, damageSource, DistantMoonsAfflictionEffectComponents.POST_DEATH);
  }
}
