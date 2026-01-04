package syrenyx.distantmoons.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.content.affliction.AfflictionManager;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

  @Inject(at = @At("HEAD"), method = "die")
  public void distantMoons$die(DamageSource damageSource, CallbackInfo callbackInfo) {
    AfflictionManager.handlePlayerDeath((ServerPlayer) (Object) this, damageSource);
  }
}
