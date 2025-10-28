package syrenyx.distantmoons.content.affliction;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
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
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
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

  public static boolean clearAffliction(LivingEntity entity, @Nullable RegistryEntry<Affliction> affliction) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
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

  public static @Nullable AfflictionInstance getAffliction(LivingEntity entity, RegistryEntry<Affliction> affliction) {
    return getActiveAfflictions(entity).get(affliction);
  }

  public static boolean giveAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    if (isImmune(entity, afflictionInstance.affliction())) return false;
    afflictionInstance.limitToAllowedValues();
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
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
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    activeAfflictions.put(afflictionInstance.affliction(), afflictionInstance);
    onAfflictionAdded(entity, afflictionInstance);
    return true;
  }

  private static void onAfflictionAdded(LivingEntity entity, AfflictionInstance afflictionInstance) {
    Affliction.processAttributeEffects(entity, false, afflictionInstance);
    Affliction.processStageChangedEffects(entity, afflictionInstance, false, DistantMoonsAfflictionEffectComponents.STAGE_CHANGED);
  }

  private static void onAfflictionRemoved(LivingEntity entity, AfflictionInstance afflictionInstance) {
    Affliction.processAttributeEffects(entity, true, afflictionInstance);
    Affliction.processStageChangedEffects(entity, afflictionInstance, true, DistantMoonsAfflictionEffectComponents.STAGE_CHANGED);
  }

  private static void onAfflictionStageChanged(LivingEntity entity, AfflictionInstance afflictionInstance) {
    Affliction.processAttributeEffects(entity, false, afflictionInstance);
    Affliction.processStageChangedEffects(entity, afflictionInstance, false, DistantMoonsAfflictionEffectComponents.STAGE_CHANGED);
  }

  public static float getArmorEffectiveness(LivingEntity entity, DamageSource damageSource, float armorEffectiveness) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      armorEffectiveness = Affliction.processArmorEffectiveness(entity, damageSource, armorEffectiveness, afflictionInstance);
    }
    return armorEffectiveness;
  }

  public static float getDamage(LivingEntity entity, Entity target, DamageSource damageSource, float damage) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      damage = Affliction.processDamage(entity, target, damageSource, damage, afflictionInstance);
    }
    return damage;
  }

  public static float getDamageProtection(LivingEntity entity, DamageSource damageSource) {
    float damageProtection = 0.0F;
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      damageProtection = Affliction.processDamageProtection(entity, damageSource, damageProtection, afflictionInstance);
    }
    return damageProtection;
  }

  public static float getFishingLuckBonus(LivingEntity entity, ItemStack stack) {
    float fishingLuckBonus = 0.0F;
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      fishingLuckBonus = Affliction.processFishingLuckBonus(entity, stack, fishingLuckBonus, afflictionInstance);
    }
    return fishingLuckBonus;
  }

  public static float getFishingTimeReduction(LivingEntity entity, ItemStack stack) {
    float fishingTimeReduction = 0.0F;
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      fishingTimeReduction = Affliction.processFishingTimeReduction(entity, stack, fishingTimeReduction, afflictionInstance);
    }
    return fishingTimeReduction;
  }

  public static float getKnockback(LivingEntity entity, Entity target, DamageSource damageSource, float knockback) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      knockback = Affliction.processKnockback(entity, target, damageSource, knockback, afflictionInstance);
    }
    return knockback;
  }

  public static boolean handleDamageImmunity(LivingEntity entity, DamageSource damageSource) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      if (Affliction.processDamageImmunityEffects(entity, damageSource, afflictionInstance)) return true;
    }
    return false;
  }

  public static void handleLocationChanged(LivingEntity entity, boolean remove) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processLocationChangedEffects(entity, remove, afflictionInstance, DistantMoonsAfflictionEffectComponents.LOCATION_CHANGED);
    }
  }

  public static void handlePlayerDeath(ServerPlayerEntity player, DamageSource damageSource) {
    handlePostDamage(player, damageSource, DistantMoonsAfflictionEffectComponents.POST_DEATH);
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(player);
    for (RegistryEntry<Affliction> affliction : activeAfflictions.keySet()) {
      if (!affliction.value().persistent()) activeAfflictions.remove(affliction);
    }
  }

  public static void handleHitBlock(LivingEntity entity, Vec3d pos) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processHitBlockEffects(entity, pos, afflictionInstance, DistantMoonsAfflictionEffectComponents.HIT_BLOCK);
    }
  }

  public static void handlePostDamage(Entity victim, DamageSource damageSource, ComponentType<List<TargetedAfflictionEffectEntry<AfflictionEntityEffect>>> componentType) {
    for (EnchantmentEffectTarget targetType : EnchantmentEffectTarget.values()) {
      Entity entity = switch (targetType) {
        case ATTACKER -> damageSource.getAttacker();
        case DAMAGING_ENTITY -> damageSource.getSource();
        case VICTIM -> victim;
      };
      if (!(entity instanceof LivingEntity afflicted)) continue;
      Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(afflicted);
      for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
        Affliction.processPostDamageEffects(victim, damageSource, targetType, afflictionInstance, componentType);
      }
    }
  }

  public static void handleProjectileSpawned(LivingEntity entity, ProjectileEntity projectile) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processProjectileSpawnedEffects(entity, projectile, afflictionInstance, DistantMoonsAfflictionEffectComponents.PROJECTILE_SPAWNED);
    }
  }

  public static void handleTick(LivingEntity entity) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction affliction = afflictionInstance.affliction().value();
      Affliction.processTickEffects(entity, afflictionInstance, DistantMoonsAfflictionEffectComponents.TICK);
      if (affliction.tickProgression().isEmpty()) continue;
      float previousProgression = afflictionInstance.progression();
      afflictionInstance.addToProgression(affliction.tickProgression().get().getValue(afflictionInstance.stage()));
      afflictionInstance.limitToAllowedValues();
      Affliction.processProgressionThresholdEffects(entity, previousProgression, afflictionInstance, DistantMoonsAfflictionEffectComponents.PROGRESSION_THRESHOLD);
    }
    if (entity instanceof ServerPlayerEntity player) ServerPlayNetworking.send(player, new ActiveAfflictionsPayload(activeAfflictions.values().stream().map(AfflictionPacket::fromInstance).toList()));
  }

  public static void handleUsedItem(LivingEntity entity, ItemStack item) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processUsedItemEffects(entity, item, afflictionInstance, DistantMoonsAfflictionEffectComponents.USED_ITEM);
    }
  }

  public static Map<RegistryEntry<Affliction>, AfflictionInstance> getActiveAfflictions(LivingEntity entity) {
    return entity instanceof PlayerEntity player
        ? PersistentStateManager.getPlayerState(player).livingEntityAttachment().activeAfflictions()
        : LivingEntityAttachment.getOrCreate(entity).activeAfflictions();
  }

  private static boolean isImmune(LivingEntity entity, RegistryEntry<Affliction> affliction) {
    if (affliction.value().immuneEntities().isEmpty()) return false;
    return affliction.value().immuneEntities().get().contains(Registries.ENTITY_TYPE.getEntry(entity.getType()));
  }
}
