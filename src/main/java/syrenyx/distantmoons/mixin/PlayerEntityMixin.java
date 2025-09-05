package syrenyx.distantmoons.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.affliction.AfflictionManager;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

  @Unique ItemStack savedItemStack;

  @Inject(at = @At("HEAD"), method = "tick")
  public void tick(CallbackInfo callbackInfo) {
    PlayerEntity thisPlayerEntity = (PlayerEntity) (Object) this;
    if (!thisPlayerEntity.getActiveItem().isEmpty()) this.savedItemStack = thisPlayerEntity.getActiveItem().copy();
  }

  @Inject(at = @At("HEAD"), method = "incrementStat(Lnet/minecraft/stat/Stat;)V")
  public void incrementStat(Stat<?> stat, CallbackInfo callbackInfo) {
    PlayerEntity thisPlayerEntity = (PlayerEntity) (Object) this;
    if (thisPlayerEntity.getWorld().isClient()) return;
    if (stat.getType() == Stats.USED) {
      ItemStack stack = thisPlayerEntity.getActiveItem().isEmpty() ? savedItemStack : thisPlayerEntity.getActiveItem();
      AfflictionManager.handleUsedItem(thisPlayerEntity, stack);
    }
  }
}
