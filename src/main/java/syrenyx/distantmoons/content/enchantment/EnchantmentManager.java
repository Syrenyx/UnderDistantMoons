package syrenyx.distantmoons.content.enchantment;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.apache.commons.lang3.mutable.MutableFloat;
import syrenyx.distantmoons.initializers.DistantMoonsEnchantmentEffectComponents;
import syrenyx.distantmoons.initializers.DistantMoonsLootContextTypes;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public abstract class EnchantmentManager {

  public static float getArmorEffectiveness(Entity entity, ItemStack item, DamageSource damageSource, float armorEffectiveness) {
    if (!(entity.level() instanceof ServerLevel serverWorld)) return armorEffectiveness;
    ItemEnchantments activeEnchantments = item.get(DataComponents.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return armorEffectiveness;
    MutableFloat mutableArmorEffectiveness = new MutableFloat(armorEffectiveness);
    for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : activeEnchantments.entrySet()) {
      enchantment.getKey().value().modifyArmorEffectivness(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableArmorEffectiveness);
    }
    return mutableArmorEffectiveness.floatValue();
  }

  public static float getDamage(Entity entity, ItemStack item, DamageSource damageSource, float damage) {
    if (!(entity.level() instanceof ServerLevel serverWorld)) return damage;
    ItemEnchantments activeEnchantments = item.get(DataComponents.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return damage;
    MutableFloat mutableDamage = new MutableFloat(damage);
    for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : activeEnchantments.entrySet()) {
      enchantment.getKey().value().modifyDamage(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableDamage);
    }
    return mutableDamage.floatValue();
  }

  public static float getDamageProtection(LivingEntity entity, DamageSource damageSource, float damageProtection) {
    if (!(entity.level() instanceof ServerLevel serverWorld)) return damageProtection;
    MutableFloat mutableDamageProtection = new MutableFloat(damageProtection);
    for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
      ItemStack item = entity.getItemBySlot(equipmentSlot);
      ItemEnchantments activeEnchantments = item.get(DataComponents.ENCHANTMENTS);
      if (activeEnchantments == null || activeEnchantments.isEmpty()) continue;
      for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : activeEnchantments.entrySet()) {
        enchantment.getKey().value().modifyDamageProtection(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableDamageProtection);
      }
    }
    return mutableDamageProtection.floatValue();
  }

  public static float getFishingLuckBonus(Entity entity, ItemStack item, float fishingLuckBonus) {
    if (!(entity.level() instanceof ServerLevel serverWorld)) return fishingLuckBonus;
    ItemEnchantments activeEnchantments = item.get(DataComponents.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return fishingLuckBonus;
    MutableFloat mutableFishingLuckBonus = new MutableFloat(fishingLuckBonus);
    for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : activeEnchantments.entrySet()) {
      enchantment.getKey().value().modifyFishingLuckBonus(serverWorld, enchantment.getIntValue(), item, entity, mutableFishingLuckBonus);
    }
    return mutableFishingLuckBonus.floatValue();
  }

  public static float getFishingTimeReduction(Entity entity, ItemStack item, float fishingTimeReduction) {
    if (!(entity.level() instanceof ServerLevel serverWorld)) return fishingTimeReduction;
    ItemEnchantments activeEnchantments = item.get(DataComponents.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return fishingTimeReduction;
    MutableFloat mutableFishingTimeReduction = new MutableFloat(fishingTimeReduction);
    for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : activeEnchantments.entrySet()) {
      enchantment.getKey().value().modifyFishingTimeReduction(serverWorld, enchantment.getIntValue(), item, entity, mutableFishingTimeReduction);
    }
    return mutableFishingTimeReduction.floatValue();
  }

  public static float getKnockback(Entity entity, ItemStack item, DamageSource damageSource, float knockback) {
    if (!(entity.level() instanceof ServerLevel serverWorld)) return knockback;
    ItemEnchantments activeEnchantments = item.get(DataComponents.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return knockback;
    MutableFloat mutableKnockback = new MutableFloat(knockback);
    for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : activeEnchantments.entrySet()) {
      enchantment.getKey().value().modifyKnockback(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableKnockback);
    }
    return mutableKnockback.floatValue();
  }

  public static void handleUsedItem(LivingEntity entity, ItemStack item) {
    if (item.isEmpty() || entity.level().isClientSide()) return;
    ItemEnchantments activeEnchantments = item.get(DataComponents.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return;
    for (Object2IntMap.Entry<Holder<Enchantment>> enchantment : activeEnchantments.entrySet()) {
      enchantment.getKey().value().getEffects(DistantMoonsEnchantmentEffectComponents.USED_ITEM).forEach(effect -> {
        LootContext lootContext = getEnchantedItemLootContext(entity, item, enchantment.getIntValue());
        EnchantedItemInUse context = new EnchantedItemInUse(item, EquipmentSlot.MAINHAND, entity);
        if (effect.matches(lootContext)) effect.effect().apply((ServerLevel) entity.level(), enchantment.getIntValue(), context, entity, entity.position());
      });
    }
  }

  private static LootContext getEnchantedItemLootContext(Entity entity, ItemStack item, int level) {
    return new LootContext.Builder(
        new LootParams
            .Builder((ServerLevel) entity.level())
            .withParameter(LootContextParams.ENCHANTMENT_LEVEL, level)
            .withParameter(LootContextParams.ORIGIN, entity.position())
            .withParameter(LootContextParams.THIS_ENTITY, entity)
            .withParameter(LootContextParams.TOOL, item)
            .create(DistantMoonsLootContextTypes.ENCHANTED_ITEM)
    ).create(Optional.empty());
  }
}
