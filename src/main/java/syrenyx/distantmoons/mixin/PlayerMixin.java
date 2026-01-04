package syrenyx.distantmoons.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.content.affliction.AfflictionManager;
import syrenyx.distantmoons.content.enchantment.EnchantmentManager;
import syrenyx.distantmoons.initializers.DistantMoonsAdvancementCriteria;

@Mixin(Player.class)
public abstract class PlayerMixin {

  @Unique ItemStack savedItemStack;

  @Inject(at = @At("HEAD"), method = "tick")
  public void distantMoons$tick(CallbackInfo callbackInfo) {
    ItemStack activeItem = ((Player) (Object) this).getUseItem();
    if (!activeItem.isEmpty()) this.savedItemStack = activeItem.copy();
  }

  @Inject(at = @At("HEAD"), method = "awardStat(Lnet/minecraft/stats/Stat;)V")
  public void distantMoons$awardStat(Stat<?> stat, CallbackInfo callbackInfo) {
    Player thisPlayerEntity = (Player) (Object) this;
    if (thisPlayerEntity.level().isClientSide() || stat.getType() != Stats.ITEM_USED) return;
    ItemStack activeItem = thisPlayerEntity.getUseItem();
    ItemStack stack = activeItem.isEmpty() ? this.savedItemStack : activeItem;
    if (stack == null) return;
    if (thisPlayerEntity instanceof ServerPlayer serverPlayer) DistantMoonsAdvancementCriteria.USED_ITEM.trigger(serverPlayer, stack);
    AfflictionManager.handleUsedItem(thisPlayerEntity, stack);
    EnchantmentManager.handleUsedItem(thisPlayerEntity, stack);
  }
}
