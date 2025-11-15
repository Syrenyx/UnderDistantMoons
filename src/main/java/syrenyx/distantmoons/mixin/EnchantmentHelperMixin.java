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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.affliction.AfflictionManager;
import syrenyx.distantmoons.content.enchantment.EnchantmentManager;
import syrenyx.distantmoons.initializers.DistantMoonsAfflictionEffectComponents;

import java.util.function.Consumer;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

  @Inject(at = @At("HEAD"), method = "applyLocationBasedEffects(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V")
  private static void distantMoons$applyLocationBasedEffects(
      ServerWorld world,
      LivingEntity user,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, false);
  }

  @Inject(at = @At("HEAD"), method = "applyLocationBasedEffects(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V")
  private static void distantMoons$applyLocationBasedEffects(
      ServerWorld world,
      ItemStack stack,
      LivingEntity user,
      EquipmentSlot slot,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, false);
  }

  @Inject(at = @At("HEAD"), cancellable = true, method =  "getArmorEffectiveness")
  private static void distantMoons$getArmorEffectiveness(
      ServerWorld world,
      ItemStack stack,
      Entity user,
      DamageSource damageSource,
      float baseArmorEffectiveness,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float armorEffectiveness = AfflictionManager.getArmorEffectiveness(livingEntity, damageSource, baseArmorEffectiveness);
    if (armorEffectiveness == baseArmorEffectiveness) return;
    callbackInfo.cancel();
    callbackInfo.setReturnValue(EnchantmentManager.getArmorEffectiveness(user, stack, damageSource, armorEffectiveness));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method =  "getDamage")
  private static void distantMoons$getDamage(
      ServerWorld world,
      ItemStack stack,
      Entity target,
      DamageSource damageSource,
      float baseDamage,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(damageSource.getAttacker() instanceof LivingEntity livingEntity)) return;
    float damage = AfflictionManager.getDamage(livingEntity, target, damageSource, baseDamage);
    if (damage == baseDamage) return;
    callbackInfo.cancel();
    callbackInfo.setReturnValue(EnchantmentManager.getDamage(target, stack, damageSource, damage));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method =  "getFishingLuckBonus")
  private static void distantMoons$getFishingLuckBonus(
      ServerWorld world,
      ItemStack stack,
      Entity user,
      CallbackInfoReturnable<Integer> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float fishingLuckBonus = AfflictionManager.getFishingLuckBonus(livingEntity, stack);
    if (fishingLuckBonus == 0.0F) return;
    callbackInfo.cancel();
    callbackInfo.setReturnValue((int) EnchantmentManager.getFishingLuckBonus(user, stack, fishingLuckBonus));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method =  "getFishingTimeReduction")
  private static void distantMoons$getFishingTimeReduction(
      ServerWorld world,
      ItemStack stack,
      Entity user,
      CallbackInfoReturnable<Integer> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float fishingTimeReduction = AfflictionManager.getFishingTimeReduction(livingEntity, stack);
    if (fishingTimeReduction == 0.0F) return;
    callbackInfo.cancel();
    callbackInfo.setReturnValue((int) EnchantmentManager.getFishingTimeReduction(user, stack, fishingTimeReduction));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method =  "getProtectionAmount")
  private static void distantMoons$getProtectionAmount(
      ServerWorld world,
      LivingEntity user,
      DamageSource damageSource,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float damageProtection = AfflictionManager.getDamageProtection(livingEntity, damageSource);
    if (damageProtection == 0.0F) return;
    callbackInfo.cancel();
    callbackInfo.setReturnValue(EnchantmentManager.getDamageProtection(user, damageSource, damageProtection));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method =  "modifyKnockback")
  private static void distantMoons$modifyKnockback(
      ServerWorld world,
      ItemStack stack,
      Entity target,
      DamageSource damageSource,
      float baseKnockback,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    Entity user = damageSource.getAttacker();
    if (!(damageSource.getAttacker() instanceof LivingEntity livingEntity) || user == null) return;
    float knockback = AfflictionManager.getKnockback(livingEntity, user, damageSource, baseKnockback);
    if (knockback == baseKnockback) return;
    callbackInfo.cancel();
    callbackInfo.setReturnValue(EnchantmentManager.getKnockback(user, stack, damageSource, knockback));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "isInvulnerableTo")
  private static void distantMoons$isInvulnerableTo(
      ServerWorld world,
      LivingEntity user,
      DamageSource damageSource,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (AfflictionManager.handleDamageImmunity(user, damageSource)) {
      callbackInfo.cancel();
      callbackInfo.setReturnValue(true);
    }
  }

  @Inject(at = @At("HEAD"), method = "onHitBlock")
  private static void distantMoons$onHitBlock(
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

  @Inject(at = @At("HEAD"), method = "onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/item/ItemStack;Ljava/util/function/Consumer;)V")
  private static void distantMoons$onTargetDamaged(
      ServerWorld world,
      Entity target,
      DamageSource damageSource,
      @Nullable ItemStack weapon,
      Consumer<Item> breakCallback,
      CallbackInfo callbackInfo
  ) {
    if (target instanceof LivingEntity livingEntity) AfflictionManager.handlePostDamage(livingEntity, damageSource, DistantMoonsAfflictionEffectComponents.POST_ATTACK);
  }

  @Inject(at = @At("HEAD"), method = "removeLocationBasedEffects(Lnet/minecraft/entity/LivingEntity;)V")
  private static void distantMoons$removeLocationBasedEffects(
      LivingEntity user,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, true);
  }

  @Inject(at = @At("HEAD"), method = "removeLocationBasedEffects(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V")
  private static void distantMoons$removeLocationBasedEffects(
      ItemStack stack,
      LivingEntity user,
      EquipmentSlot slot,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, true);
  }
}
