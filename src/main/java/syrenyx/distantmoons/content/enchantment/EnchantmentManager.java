package syrenyx.distantmoons.content.enchantment;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;
import syrenyx.distantmoons.initializers.DistantMoonsEnchantmentEffectComponents;
import syrenyx.distantmoons.initializers.DistantMoonsLootContextTypes;

import java.util.Optional;

public abstract class EnchantmentManager {

  public static float getArmorEffectiveness(Entity entity, ItemStack item, DamageSource damageSource, float armorEffectiveness) {
    if (!(entity.getEntityWorld() instanceof ServerWorld serverWorld)) return armorEffectiveness;
    ItemEnchantmentsComponent activeEnchantments = item.get(DataComponentTypes.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return armorEffectiveness;
    MutableFloat mutableArmorEffectiveness = new MutableFloat(armorEffectiveness);
    for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantment : activeEnchantments.getEnchantmentEntries()) {
      enchantment.getKey().value().modifyArmorEffectiveness(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableArmorEffectiveness);
    }
    return mutableArmorEffectiveness.floatValue();
  }

  public static float getDamage(Entity entity, ItemStack item, DamageSource damageSource, float damage) {
    if (!(entity.getEntityWorld() instanceof ServerWorld serverWorld)) return damage;
    ItemEnchantmentsComponent activeEnchantments = item.get(DataComponentTypes.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return damage;
    MutableFloat mutableDamage = new MutableFloat(damage);
    for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantment : activeEnchantments.getEnchantmentEntries()) {
      enchantment.getKey().value().modifyDamage(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableDamage);
    }
    return mutableDamage.floatValue();
  }

  public static float getDamageProtection(LivingEntity entity, DamageSource damageSource, float damageProtection) {
    if (!(entity.getEntityWorld() instanceof ServerWorld serverWorld)) return damageProtection;
    MutableFloat mutableDamageProtection = new MutableFloat(damageProtection);
    for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
      ItemStack item = entity.getEquippedStack(equipmentSlot);
      ItemEnchantmentsComponent activeEnchantments = item.get(DataComponentTypes.ENCHANTMENTS);
      if (activeEnchantments == null || activeEnchantments.isEmpty()) continue;
      for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantment : activeEnchantments.getEnchantmentEntries()) {
        enchantment.getKey().value().modifyDamageProtection(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableDamageProtection);
      }
    }
    return mutableDamageProtection.floatValue();
  }

  public static float getFishingLuckBonus(Entity entity, ItemStack item, float fishingLuckBonus) {
    if (!(entity.getEntityWorld() instanceof ServerWorld serverWorld)) return fishingLuckBonus;
    ItemEnchantmentsComponent activeEnchantments = item.get(DataComponentTypes.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return fishingLuckBonus;
    MutableFloat mutableFishingLuckBonus = new MutableFloat(fishingLuckBonus);
    for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantment : activeEnchantments.getEnchantmentEntries()) {
      enchantment.getKey().value().modifyFishingLuckBonus(serverWorld, enchantment.getIntValue(), item, entity, mutableFishingLuckBonus);
    }
    return mutableFishingLuckBonus.floatValue();
  }

  public static float getFishingTimeReduction(Entity entity, ItemStack item, float fishingTimeReduction) {
    if (!(entity.getEntityWorld() instanceof ServerWorld serverWorld)) return fishingTimeReduction;
    ItemEnchantmentsComponent activeEnchantments = item.get(DataComponentTypes.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return fishingTimeReduction;
    MutableFloat mutableFishingTimeReduction = new MutableFloat(fishingTimeReduction);
    for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantment : activeEnchantments.getEnchantmentEntries()) {
      enchantment.getKey().value().modifyFishingTimeReduction(serverWorld, enchantment.getIntValue(), item, entity, mutableFishingTimeReduction);
    }
    return mutableFishingTimeReduction.floatValue();
  }

  public static float getKnockback(Entity entity, ItemStack item, DamageSource damageSource, float knockback) {
    if (!(entity.getEntityWorld() instanceof ServerWorld serverWorld)) return knockback;
    ItemEnchantmentsComponent activeEnchantments = item.get(DataComponentTypes.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return knockback;
    MutableFloat mutableKnockback = new MutableFloat(knockback);
    for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantment : activeEnchantments.getEnchantmentEntries()) {
      enchantment.getKey().value().modifyKnockback(serverWorld, enchantment.getIntValue(), item, entity, damageSource, mutableKnockback);
    }
    return mutableKnockback.floatValue();
  }

  public static void handleUsedItem(LivingEntity entity, ItemStack item) {
    if (item.isEmpty() || entity.getEntityWorld().isClient()) return;
    ItemEnchantmentsComponent activeEnchantments = item.get(DataComponentTypes.ENCHANTMENTS);
    if (activeEnchantments == null || activeEnchantments.isEmpty()) return;
    for (Object2IntMap.Entry<RegistryEntry<Enchantment>> enchantment : activeEnchantments.getEnchantmentEntries()) {
      enchantment.getKey().value().getEffect(DistantMoonsEnchantmentEffectComponents.USED_ITEM).forEach(effect -> {
        LootContext lootContext = getEnchantedItemLootContext(entity, item, enchantment.getIntValue());
        EnchantmentEffectContext context = new EnchantmentEffectContext(item, EquipmentSlot.MAINHAND, entity);
        if (effect.test(lootContext)) effect.effect().apply((ServerWorld) entity.getEntityWorld(), enchantment.getIntValue(), context, entity, entity.getEntityPos());
      });
    }
  }

  private static LootContext getEnchantedItemLootContext(Entity entity, ItemStack item, int level) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) entity.getEntityWorld())
            .add(LootContextParameters.ENCHANTMENT_LEVEL, level)
            .add(LootContextParameters.ORIGIN, entity.getEntityPos())
            .add(LootContextParameters.THIS_ENTITY, entity)
            .add(LootContextParameters.TOOL, item)
            .build(DistantMoonsLootContextTypes.ENCHANTED_ITEM)
    ).build(Optional.empty());
  }
}
