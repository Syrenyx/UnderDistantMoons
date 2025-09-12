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
import syrenyx.distantmoons.enchantment.effect.entity.EnchantmentManager;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

  @Unique ItemStack savedItemStack;

  @Inject(at = @At("HEAD"), method = "tick")
  public void tick(CallbackInfo callbackInfo) {
    ItemStack activeItem = ((PlayerEntity) (Object) this).getActiveItem();
    if (!activeItem.isEmpty()) this.savedItemStack = activeItem.copy();
  }

  @Inject(at = @At("HEAD"), method = "incrementStat(Lnet/minecraft/stat/Stat;)V")
  public void incrementStat(Stat<?> stat, CallbackInfo callbackInfo) {
    PlayerEntity thisPlayerEntity = (PlayerEntity) (Object) this;
    if (thisPlayerEntity.getWorld().isClient() || stat.getType() != Stats.USED) return;
    ItemStack activeItem = thisPlayerEntity.getActiveItem();
    ItemStack stack = activeItem.isEmpty() ? this.savedItemStack : activeItem;
    AfflictionManager.handleUsedItem(thisPlayerEntity, stack);
    EnchantmentManager.handleUsedItem(thisPlayerEntity, stack);
  }
}
