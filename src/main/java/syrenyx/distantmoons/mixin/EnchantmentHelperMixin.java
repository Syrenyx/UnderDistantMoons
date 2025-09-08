package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
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

  @Inject(at = @At("HEAD"), method = "applyLocationBasedEffects(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V")
  private static void applyLocationBasedEffects(ServerWorld world, LivingEntity user, CallbackInfo callbackInfo) {
    AfflictionManager.handleLocationChanged(user, false);
  }

  @Inject(at = @At("HEAD"), method = "applyLocationBasedEffects(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V")
  private static void applyLocationBasedEffects(ServerWorld world, ItemStack stack, LivingEntity user, EquipmentSlot slot, CallbackInfo callbackInfo) {
    AfflictionManager.handleLocationChanged(user, false);
  }

  @Inject(at = @At("HEAD"), method = "onHitBlock")
  private static void onHitBlock(
      ServerWorld world,
      ItemStack stack,
      @Nullable LivingEntity user,
      Entity enchantedEntity,
      @Nullable EquipmentSlot slot,
      Vec3d pos,
      BlockState state,
      Consumer<Item> onBreak,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleHitBlock(user, pos);
  }

  @Inject(at = @At("HEAD"), method = "removeLocationBasedEffects(Lnet/minecraft/entity/LivingEntity;)V")
  private static void removeLocationBasedEffects(LivingEntity user, CallbackInfo callbackInfo) {
    AfflictionManager.handleLocationChanged(user, true);
  }

  @Inject(at = @At("HEAD"), method = "removeLocationBasedEffects(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V")
  private static void removeLocationBasedEffects(ItemStack stack, LivingEntity user, EquipmentSlot slot, CallbackInfo callbackInfo) {
    AfflictionManager.handleLocationChanged(user, true);
  }

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
