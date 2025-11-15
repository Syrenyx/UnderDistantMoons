package syrenyx.distantmoons.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.content.affliction.AfflictionManager;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

  @Inject(at = @At("HEAD"), method = "onDeath")
  public void distantMoons$onDeath(DamageSource damageSource, CallbackInfo callbackInfo) {
    AfflictionManager.handlePlayerDeath((ServerPlayerEntity) (Object) this, damageSource);
  }
}
