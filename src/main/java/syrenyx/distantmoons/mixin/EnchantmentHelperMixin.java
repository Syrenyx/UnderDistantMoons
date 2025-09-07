package syrenyx.distantmoons.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.affliction.AfflictionManager;
import syrenyx.distantmoons.initializers.AfflictionEffectComponents;

import java.util.function.Consumer;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

  @Inject(at = @At("HEAD"), method = "onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/item/ItemStack;Ljava/util/function/Consumer;)V")
  private static void onTargetDamaged(
      ServerWorld world,
      Entity target,
      DamageSource damageSource,
      @Nullable ItemStack weapon,
      Consumer<Item> breakCallback,
      CallbackInfo callbackInfo
  ) {
    if (target instanceof LivingEntity livingEntity) AfflictionManager.handlePostDamage(livingEntity, damageSource, AfflictionEffectComponents.POST_ATTACK);
  }
}
