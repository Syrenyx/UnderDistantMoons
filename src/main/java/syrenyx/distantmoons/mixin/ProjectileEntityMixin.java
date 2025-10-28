package syrenyx.distantmoons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LazyEntityReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.content.affliction.AfflictionManager;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {

  @Nullable @Shadow protected LazyEntityReference<Entity> owner;

  @Inject(at = @At("HEAD"), method = "triggerProjectileSpawned")
  public void triggerProjectileSpawned(ServerWorld world, ItemStack projectileStack, CallbackInfo callbackInfo) {
    ProjectileEntity thisProjectile = (ProjectileEntity) (Object) this;
    Entity entity = thisProjectile.getOwner();
    if (!(entity instanceof LivingEntity ownerEntity)) return;
    AfflictionManager.handleProjectileSpawned(ownerEntity, thisProjectile);
  }
}
