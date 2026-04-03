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
      ServerLevel serverLevel,
      LivingEntity entity,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(entity, false);
  }

  @Inject(at = @At("HEAD"), method = "runLocationChangedEffects(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
  private static void distantMoons$runLocationChangedEffects(
      ServerLevel serverLevel,
      ItemStack stack,
      LivingEntity entity,
      EquipmentSlot slot,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(entity, false);
  }

  @Inject(at = @At("RETURN"), cancellable = true, method = "processAmmoUse")
  private static void distantMoons$processAmmoUse(
      ServerLevel serverLevel, ItemStack weapon, ItemStack ammo, int amount, CallbackInfoReturnable<Integer> callbackInfo
  ) {
    MutableFloat mutableFloat = new MutableFloat(callbackInfo.getReturnValue());
    EnchantmentHelper.runIterationOnItem(ammo, (enchantment, level) -> enchantment.value().modifyAmmoCount(serverLevel, level, ammo, mutableFloat));
    callbackInfo.setReturnValue(mutableFloat.intValue());
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "modifyArmorEffectiveness")
  private static void distantMoons$modifyArmorEffectiveness(
      ServerLevel serverLevel,
      ItemStack itemStack,
      Entity victim,
      DamageSource damageSource,
      float armorFraction,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(victim instanceof LivingEntity livingEntity)) return;
    float armorEffectiveness = AfflictionManager.getArmorEffectiveness(livingEntity, damageSource, armorFraction);
    if (armorEffectiveness == armorFraction) return;
    callbackInfo.setReturnValue(EnchantmentManager.getArmorEffectiveness(victim, itemStack, damageSource, armorEffectiveness));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "modifyDamage")
  private static void distantMoons$modifyDamage(
      ServerLevel serverLevel,
      ItemStack itemStack,
      Entity victim,
      DamageSource damageSource,
      float damage,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(damageSource.getEntity() instanceof LivingEntity livingEntity)) return;
    float resultingDamage = AfflictionManager.getDamage(livingEntity, victim, damageSource, damage);
    if (resultingDamage == damage) return;
    callbackInfo.setReturnValue(EnchantmentManager.getDamage(victim, itemStack, damageSource, damage));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "getFishingLuckBonus")
  private static void distantMoons$getFishingLuckBonus(
      ServerLevel serverLevel,
      ItemStack rod,
      Entity fisher,
      CallbackInfoReturnable<Integer> callbackInfo
  ) {
    if (!(fisher instanceof LivingEntity livingEntity)) return;
    float fishingLuckBonus = AfflictionManager.getFishingLuckBonus(livingEntity, rod);
    if (fishingLuckBonus == 0.0F) return;
    callbackInfo.setReturnValue((int) EnchantmentManager.getFishingLuckBonus(fisher, rod, fishingLuckBonus));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "getFishingTimeReduction")
  private static void distantMoons$getFishingTimeReduction(
      ServerLevel serverLevel,
      ItemStack rod,
      Entity fisher,
      CallbackInfoReturnable<Integer> callbackInfo
  ) {
    if (!(fisher instanceof LivingEntity livingEntity)) return;
    float fishingTimeReduction = AfflictionManager.getFishingTimeReduction(livingEntity, rod);
    if (fishingTimeReduction == 0.0F) return;
    callbackInfo.setReturnValue((int) EnchantmentManager.getFishingTimeReduction(fisher, rod, fishingTimeReduction));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "getDamageProtection")
  private static void distantMoons$getDamageProtection(
      ServerLevel serverLevel,
      LivingEntity victim,
      DamageSource source,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    if (!(victim instanceof LivingEntity livingEntity)) return;
    float damageProtection = AfflictionManager.getDamageProtection(livingEntity, source);
    if (damageProtection == 0.0F) return;
    callbackInfo.setReturnValue(EnchantmentManager.getDamageProtection(victim, source, damageProtection));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "modifyKnockback")
  private static void distantMoons$modifyKnockback(
      ServerLevel serverLevel,
      ItemStack itemStack,
      Entity victim,
      DamageSource damageSource,
      float knockback,
      CallbackInfoReturnable<Float> callbackInfo
  ) {
    Entity user = damageSource.getEntity();
    if (!(damageSource.getEntity() instanceof LivingEntity livingEntity) || user == null) return;
    float resultingKnockback = AfflictionManager.getKnockback(livingEntity, user, damageSource, knockback);
    if (resultingKnockback == knockback) return;
    callbackInfo.setReturnValue(EnchantmentManager.getKnockback(user, itemStack, damageSource, resultingKnockback));
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "isImmuneToDamage")
  private static void distantMoons$isImmuneToDamage(
      ServerLevel serverLevel,
      LivingEntity victim,
      DamageSource source,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (AfflictionManager.handleDamageImmunity(victim, source)) {
      callbackInfo.setReturnValue(true);
    }
  }

  @Inject(at = @At("HEAD"), method = "onHitBlock")
  private static void distantMoons$onHitBlock(
      ServerLevel serverLevel,
      ItemStack weapon,
      @Nullable LivingEntity owner,
      Entity entity,
      @Nullable EquipmentSlot slot,
      Vec3 hitLocation,
      BlockState hitBlock,
      Consumer<Item> onBreak,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleHitBlock(owner, hitLocation);
  }

  @Inject(at = @At("HEAD"), method = "doPostAttackEffectsWithItemSourceOnBreak(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;)V")
  private static void distantMoons$doPostAttackEffectsWithItemSourceOnBreak(
      ServerLevel serverLevel,
      Entity victim,
      DamageSource damageSource,
      @Nullable ItemStack source,
      Consumer<Item> attackerlessOnBreak,
      CallbackInfo callbackInfo
  ) {
    if (victim instanceof LivingEntity livingEntity) AfflictionManager.handlePostDamage(livingEntity, damageSource, DistantMoonsAfflictionEffectComponents.POST_ATTACK);
  }

  @Inject(at = @At("HEAD"), method = "stopLocationBasedEffects(Lnet/minecraft/world/entity/LivingEntity;)V")
  private static void distantMoons$stopLocationBasedEffects(
      LivingEntity entity,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(entity, true);
  }

  @Inject(at = @At("HEAD"), method = "stopLocationBasedEffects(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V")
  private static void distantMoons$stopLocationBasedEffects(
      ItemStack stack,
      LivingEntity entity,
      EquipmentSlot slot,
      CallbackInfo callbackInfo
  ) {
    AfflictionManager.handleLocationChanged(entity, true);
  }
}
