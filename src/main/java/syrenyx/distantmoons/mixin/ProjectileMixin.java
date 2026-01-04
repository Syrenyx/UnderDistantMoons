package syrenyx.distantmoons.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.content.affliction.AfflictionManager;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {

  @Nullable @Shadow protected EntityReference<Entity> owner;

  @Inject(at = @At("HEAD"), method = "applyOnProjectileSpawned")
  public void distantMoons$applyOnProjectileSpawned(ServerLevel world, ItemStack projectileStack, CallbackInfo callbackInfo) {
    Projectile thisProjectile = (Projectile) (Object) this;
    Entity entity = thisProjectile.getOwner();
    if (!(entity instanceof LivingEntity ownerEntity)) return;
    AfflictionManager.handleProjectileSpawned(ownerEntity, thisProjectile);
  }
}
