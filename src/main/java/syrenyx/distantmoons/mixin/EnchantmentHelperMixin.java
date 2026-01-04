package syrenyx.distantmoons.mixin;

import org.apache.commons.lang3.mutable.MutableFloat;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

  @Inject(at = @At("HEAD"), method = "runLocationChangedEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V")
  private static void distantMoons$runLocationChangedEffects(
      ServerLevel world,
      LivingEntity user,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, false);
  }

  @Inject(at = @At("HEAD"), method = "runLocationChangedEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
  private static void distantMoons$runLocationChangedEffects(
      ServerLevel world,
      ItemStack stack,
      LivingEntity user,
      EquipmentSlot slot,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, false);
  }

  @Inject(at = @At("RETURN"), cancellable = true, method = "processAmmoUse")
  private static void distantMoons$processAmmoUse(
      ServerLevel world, ItemStack rangedWeaponStack, ItemStack projectileStack, int baseAmmoUse, CallbackInfoReturnable<Integer> callbackInfo
  ) {
    MutableFloat mutableFloat = new MutableFloat(callbackInfo.getReturnValue());
    EnchantmentHelper.runIterationOnItem(projectileStack, (enchantment, level) -> enchantment.value().modifyAmmoCount(world, level, projectileStack, mutableFloat));
    callbackInfo.setReturnValue(mutableFloat.intValue());
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "modifyArmorEffectiveness")
  private static void distantMoons$modifyArmorEffectiveness(
      ServerLevel world,
      ItemStack stack,
      Entity user,
      DamageSource damageSource,
      float baseArmorEffectiveness,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float armorEffectiveness = AfflictionManager.getArmorEffectiveness(livingEntity, damageSource, baseArmorEffectiveness);
    if (armorEffectiveness == baseArmorEffectiveness) return;
    callbackInfo.setReturnValue(EnchantmentManager.getArmorEffectiveness(user, stack, damageSource, armorEffectiveness));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "modifyDamage")
  private static void distantMoons$modifyDamage(
      ServerLevel world,
      ItemStack stack,
      Entity target,
      DamageSource damageSource,
      float baseDamage,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(damageSource.getEntity() instanceof LivingEntity livingEntity)) return;
    float damage = AfflictionManager.getDamage(livingEntity, target, damageSource, baseDamage);
    if (damage == baseDamage) return;
    callbackInfo.setReturnValue(EnchantmentManager.getDamage(target, stack, damageSource, damage));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "getFishingLuckBonus")
  private static void distantMoons$getFishingLuckBonus(
      ServerLevel world,
      ItemStack stack,
      Entity user,
      CallbackInfoReturnable<Integer> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float fishingLuckBonus = AfflictionManager.getFishingLuckBonus(livingEntity, stack);
    if (fishingLuckBonus == 0.0F) return;
    callbackInfo.setReturnValue((int) EnchantmentManager.getFishingLuckBonus(user, stack, fishingLuckBonus));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "getFishingTimeReduction")
  private static void distantMoons$getFishingTimeReduction(
      ServerLevel world,
      ItemStack stack,
      Entity user,
      CallbackInfoReturnable<Integer> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float fishingTimeReduction = AfflictionManager.getFishingTimeReduction(livingEntity, stack);
    if (fishingTimeReduction == 0.0F) return;
    callbackInfo.setReturnValue((int) EnchantmentManager.getFishingTimeReduction(user, stack, fishingTimeReduction));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "getDamageProtection")
  private static void distantMoons$getDamageProtection(
      ServerLevel world,
      LivingEntity user,
      DamageSource damageSource,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(user instanceof LivingEntity livingEntity)) return;
    float damageProtection = AfflictionManager.getDamageProtection(livingEntity, damageSource);
    if (damageProtection == 0.0F) return;
    callbackInfo.setReturnValue(EnchantmentManager.getDamageProtection(user, damageSource, damageProtection));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "modifyKnockback")
  private static void distantMoons$modifyKnockback(
      ServerLevel world,
      ItemStack stack,
      Entity target,
      DamageSource damageSource,
      float baseKnockback,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    Entity user = damageSource.getEntity();
    if (!(damageSource.getEntity() instanceof LivingEntity livingEntity) || user == null) return;
    float knockback = AfflictionManager.getKnockback(livingEntity, user, damageSource, baseKnockback);
    if (knockback == baseKnockback) return;
    callbackInfo.setReturnValue(EnchantmentManager.getKnockback(user, stack, damageSource, knockback));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "isImmuneToDamage")
  private static void distantMoons$isImmuneToDamage(
      ServerLevel world,
      LivingEntity user,
      DamageSource damageSource,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (AfflictionManager.handleDamageImmunity(user, damageSource)) {
      callbackInfo.setReturnValue(true);
    }
  }

  @Inject(at = @At("HEAD"), method = "onHitBlock")
  private static void distantMoons$onHitBlock(
      ServerLevel world,
      ItemStack stack,
      @Nullable LivingEntity user,
      Entity enchantedEntity,
      @Nullable EquipmentSlot slot,
      Vec3 pos,
      BlockState state,
      Consumer<Item> onBreak,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleHitBlock(user, pos);
  }

  @Inject(at = @At("HEAD"), method = "doPostAttackEffectsWithItemSourceOnBreak(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;)V")
  private static void distantMoons$doPostAttackEffectsWithItemSourceOnBreak(
      ServerLevel world,
      Entity target,
      DamageSource damageSource,
      @Nullable ItemStack weapon,
      Consumer<Item> breakCallback,
      CallbackInfo callbackInfo
  ) {
    if (target instanceof LivingEntity livingEntity) AfflictionManager.handlePostDamage(livingEntity, damageSource, DistantMoonsAfflictionEffectComponents.POST_ATTACK);
  }

  @Inject(at = @At("HEAD"), method = "stopLocationBasedEffects(Lnet/minecraft/world/entity/LivingEntity;)V")
  private static void distantMoons$stopLocationBasedEffects(
      LivingEntity user,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, true);
  }

  @Inject(at = @At("HEAD"), method = "stopLocationBasedEffects(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
  private static void distantMoons$stopLocationBasedEffects(
      ItemStack stack,
      LivingEntity user,
      EquipmentSlot slot,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(user, true);
  }
}
