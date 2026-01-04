package syrenyx.distantmoons.content.affliction;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.content.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.content.affliction.effect.TargetedAfflictionEffectEntry;
import syrenyx.distantmoons.data.attachment.LivingEntityAttachment;
import syrenyx.distantmoons.data.networking.AfflictionPacket;
import syrenyx.distantmoons.data.persistent.PersistentStateManager;
import syrenyx.distantmoons.initializers.DistantMoonsAfflictionEffectComponents;
import syrenyx.distantmoons.data.networking.ActiveAfflictionsPayload;

import java.util.List;
import java.util.Map;

public abstract class AfflictionManager {

  public static boolean addToAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    if (isImmune(entity, afflictionInstance.affliction())) return false;
    if (afflictionInstance.stage() == 0 && afflictionInstance.progression() == 0.0F) return false;
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    AfflictionInstance activeAffliction = getActiveAfflictions(entity).get(afflictionInstance.affliction());
    if (activeAffliction == null) {
      if (afflictionInstance.stage() < 1 || afflictionInstance.progression() < 0.0F) return false;
      afflictionInstance.limitToAllowedValues();
      activeAfflictions.put(afflictionInstance.affliction(), afflictionInstance);
      onAfflictionAdded(entity, afflictionInstance);
      return true;
    }
    if (activeAffliction.stage() + afflictionInstance.stage() < 1) {
      onAfflictionRemoved(entity, activeAffliction);
      activeAfflictions.remove(afflictionInstance.affliction());
      return true;
    }
    int currentStage = activeAffliction.stage();
    activeAffliction.addToStage(afflictionInstance.stage());
    activeAffliction.addToProgression(afflictionInstance.progression());
    activeAffliction.limitToAllowedValues();
    if (currentStage != activeAffliction.stage()) onAfflictionStageChanged(entity, activeAffliction);
    return true;
  }

  public static boolean clearAffliction(LivingEntity entity, @Nullable Holder<Affliction> affliction) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    if (activeAfflictions.isEmpty()) return false;
    if (affliction != null) {
      if (activeAfflictions.get(affliction) == null) return false;
      onAfflictionRemoved(entity, activeAfflictions.get(affliction));
      activeAfflictions.remove(affliction);
      return true;
    }
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      onAfflictionRemoved(entity, afflictionInstance);
    }
    activeAfflictions.clear();
    return true;
  }

  public static @Nullable AfflictionInstance getAffliction(LivingEntity entity, Holder<Affliction> affliction) {
    return getActiveAfflictions(entity).get(affliction);
  }

  public static boolean giveAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    if (isImmune(entity, afflictionInstance.affliction())) return false;
    afflictionInstance.limitToAllowedValues();
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    AfflictionInstance activeAffliction = activeAfflictions.putIfAbsent(afflictionInstance.affliction(), afflictionInstance);
    if (activeAffliction == null) return true;
    boolean result = false;
    if (afflictionInstance.stage() > activeAffliction.stage()) onAfflictionStageChanged(entity, afflictionInstance);
    if (afflictionInstance.stage() > activeAffliction.stage() || afflictionInstance.stage() == activeAffliction.stage() && afflictionInstance.progression() > activeAffliction.progression()) {
      activeAffliction.setStage(afflictionInstance.stage());
      if (afflictionInstance.progression() > activeAffliction.progression()) activeAffliction.setProgression(afflictionInstance.progression());
      result = true;
    }
    return result;
  }

  public static boolean setAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    if (isImmune(entity, afflictionInstance.affliction())) return false;
    afflictionInstance.limitToAllowedValues();
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    activeAfflictions.put(afflictionInstance.affliction(), afflictionInstance);
    onAfflictionAdded(entity, afflictionInstance);
    return true;
  }

  private static void onAfflictionAdded(LivingEntity entity, AfflictionInstance afflictionInstance) {
    afflictionInstance.processAttributeEffects(entity, false);
    afflictionInstance.processStageChangedEffects(entity, false, DistantMoonsAfflictionEffectComponents.STAGE_CHANGED);
  }

  private static void onAfflictionRemoved(LivingEntity entity, AfflictionInstance afflictionInstance) {
    afflictionInstance.processAttributeEffects(entity, true);
    afflictionInstance.processStageChangedEffects(entity, true, DistantMoonsAfflictionEffectComponents.STAGE_CHANGED);
  }

  private static void onAfflictionStageChanged(LivingEntity entity, AfflictionInstance afflictionInstance) {
    afflictionInstance.processAttributeEffects(entity, false);
    afflictionInstance.processStageChangedEffects(entity, false, DistantMoonsAfflictionEffectComponents.STAGE_CHANGED);
  }

  public static float getArmorEffectiveness(LivingEntity entity, DamageSource damageSource, float armorEffectiveness) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      armorEffectiveness = afflictionInstance.processArmorEffectiveness(entity, damageSource, armorEffectiveness);
    }
    return armorEffectiveness;
  }

  public static float getDamage(LivingEntity entity, Entity target, DamageSource damageSource, float damage) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      damage = afflictionInstance.processDamage(entity, target, damageSource, damage);
    }
    return damage;
  }

  public static float getDamageProtection(LivingEntity entity, DamageSource damageSource) {
    float damageProtection = 0.0F;
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      damageProtection = afflictionInstance.processDamageProtection(entity, damageSource, damageProtection);
    }
    return damageProtection;
  }

  public static float getFishingLuckBonus(LivingEntity entity, ItemStack stack) {
    float fishingLuckBonus = 0.0F;
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      fishingLuckBonus = afflictionInstance.processFishingLuckBonus(entity, stack, fishingLuckBonus);
    }
    return fishingLuckBonus;
  }

  public static float getFishingTimeReduction(LivingEntity entity, ItemStack stack) {
    float fishingTimeReduction = 0.0F;
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      fishingTimeReduction = afflictionInstance.processFishingTimeReduction(entity, stack, fishingTimeReduction);
    }
    return fishingTimeReduction;
  }

  public static float getKnockback(LivingEntity entity, Entity target, DamageSource damageSource, float knockback) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      knockback = afflictionInstance.processKnockback(entity, target, damageSource, knockback);
    }
    return knockback;
  }

  public static boolean handleDamageImmunity(LivingEntity entity, DamageSource damageSource) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      if (afflictionInstance.processDamageImmunityEffects(entity, damageSource)) return true;
    }
    return false;
  }

  public static void handleLocationChanged(LivingEntity entity, boolean remove) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      afflictionInstance.processLocationChangedEffects(entity, remove, DistantMoonsAfflictionEffectComponents.LOCATION_CHANGED);
    }
  }

  public static void handlePlayerDeath(ServerPlayer player, DamageSource damageSource) {
    handlePostDamage(player, damageSource, DistantMoonsAfflictionEffectComponents.POST_DEATH);
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(player);
    for (Holder<Affliction> affliction : activeAfflictions.keySet()) {
      if (!affliction.value().persistent()) activeAfflictions.remove(affliction);
    }
  }

  public static void handleHitBlock(LivingEntity entity, Vec3 pos) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      afflictionInstance.processHitBlockEffects(entity, pos, DistantMoonsAfflictionEffectComponents.HIT_BLOCK);
    }
  }

  public static void handlePostDamage(Entity victim, DamageSource damageSource, DataComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> componentType) {
    for (EnchantmentTarget targetType : EnchantmentTarget.values()) {
      Entity entity = switch (targetType) {
        case ATTACKER -> damageSource.getEntity();
        case DAMAGING_ENTITY -> damageSource.getDirectEntity();
        case VICTIM -> victim;
      };
      if (!(entity instanceof LivingEntity afflicted)) continue;
      Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(afflicted);
      for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
        afflictionInstance.processPostDamageEffects(victim, damageSource, targetType, componentType);
      }
    }
  }

  public static void handleProjectileSpawned(LivingEntity entity, Projectile projectile) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      afflictionInstance.processProjectileSpawnedEffects(entity, projectile, DistantMoonsAfflictionEffectComponents.PROJECTILE_SPAWNED);
    }
  }

  public static void handleTick(LivingEntity entity) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction affliction = afflictionInstance.affliction().value();
      afflictionInstance.processTickEffects(entity, DistantMoonsAfflictionEffectComponents.TICK);
      if (affliction.tickProgression().isEmpty()) continue;
      float previousProgression = afflictionInstance.progression();
      afflictionInstance.addToProgression(affliction.tickProgression().get().calculate(afflictionInstance.stage()));
      afflictionInstance.limitToAllowedValues();
      afflictionInstance.processProgressionThresholdEffects(entity, previousProgression, DistantMoonsAfflictionEffectComponents.PROGRESSION_THRESHOLD);
    }
    if (entity instanceof ServerPlayer player) ServerPlayNetworking.send(player, new ActiveAfflictionsPayload(activeAfflictions.values().stream().map(AfflictionPacket::fromInstance).toList()));
  }

  public static void handleUsedItem(LivingEntity entity, ItemStack item) {
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      afflictionInstance.processUsedItemEffects(entity, item, DistantMoonsAfflictionEffectComponents.USED_ITEM);
    }
  }

  public static Map<Holder<Affliction>, AfflictionInstance> getActiveAfflictions(LivingEntity entity) {
    return entity instanceof Player player
        ? PersistentStateManager.getPlayerState(player).livingEntityAttachment().activeAfflictions()
        : LivingEntityAttachment.getOrCreate(entity).activeAfflictions();
  }

  private static boolean isImmune(LivingEntity entity, Holder<Affliction> affliction) {
    if (affliction.value().immuneEntities().isEmpty()) return false;
    return affliction.value().immuneEntities().get().contains(BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(entity.getType()));
  }
}
