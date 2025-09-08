package syrenyx.distantmoons.affliction;

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
import syrenyx.distantmoons.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.affliction.effect.TargetedAfflictionEffectEntry;
import syrenyx.distantmoons.data.attachment.LivingEntityAttachment;
import syrenyx.distantmoons.data.networking.AfflictionPacket;
import syrenyx.distantmoons.data.persistent.PersistentStateManager;
import syrenyx.distantmoons.initializers.AfflictionEffectComponents;
import syrenyx.distantmoons.payload.ActiveAfflictionsPayload;

import java.util.List;
import java.util.Map;

public abstract class AfflictionManager {

  public static boolean addToAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    if (afflictionInstance.stage() == 0 && afflictionInstance.progression() == 0.0F) return false;
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    AfflictionInstance activeAffliction = getActiveAfflictions(entity).get(afflictionInstance.affliction());
    if (activeAffliction == null) {
      if (afflictionInstance.stage() < 1 || afflictionInstance.progression() < 0.0F) return false;
      afflictionInstance.limitToAllowedValues();
      activeAfflictions.put(afflictionInstance.affliction(), afflictionInstance);
      Affliction.processStageChangedEffects(entity, afflictionInstance, false, AfflictionEffectComponents.STAGE_CHANGED);
      return true;
    }
    if (activeAffliction.stage() + afflictionInstance.stage() < 1) {
      Affliction.processStageChangedEffects(entity, activeAffliction, true, AfflictionEffectComponents.STAGE_CHANGED);
      activeAfflictions.remove(afflictionInstance.affliction());
      return true;
    }
    int currentStage = activeAffliction.stage();
    activeAffliction.addToStage(afflictionInstance.stage());
    activeAffliction.addToProgression(afflictionInstance.progression());
    activeAffliction.limitToAllowedValues();
    if (currentStage != activeAffliction.stage()) Affliction.processStageChangedEffects(entity, activeAffliction, false, AfflictionEffectComponents.STAGE_CHANGED);
    return true;
  }

  public static boolean clearAffliction(LivingEntity entity, @Nullable RegistryEntry<Affliction> affliction) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    if (activeAfflictions.isEmpty()) return false;
    if (affliction != null) {
      if (activeAfflictions.get(affliction) == null) return false;
      Affliction.processStageChangedEffects(entity, activeAfflictions.get(affliction), true, AfflictionEffectComponents.STAGE_CHANGED);
      activeAfflictions.remove(affliction);
      return true;
    }
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processStageChangedEffects(entity, afflictionInstance, true, AfflictionEffectComponents.STAGE_CHANGED);
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
    if (afflictionInstance.stage() > activeAffliction.stage()) Affliction.processStageChangedEffects(entity, afflictionInstance, false, AfflictionEffectComponents.STAGE_CHANGED);
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
    Affliction.processStageChangedEffects(entity, afflictionInstance, false, AfflictionEffectComponents.STAGE_CHANGED);
    return true;
  }

  public static void handleLocationChanged(LivingEntity entity, boolean remove) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processLocationChangedEffects(entity, remove, afflictionInstance, AfflictionEffectComponents.LOCATION_CHANGED);
    }
  }

  public static void handlePlayerDeath(ServerPlayerEntity player, DamageSource damageSource) {
    handlePostDamage(player, damageSource, AfflictionEffectComponents.POST_DEATH);
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(player);
    for (RegistryEntry<Affliction> affliction : activeAfflictions.keySet()) {
      if (!affliction.value().persistent()) activeAfflictions.remove(affliction);
    }
  }

  public static void handleHitBlock(LivingEntity entity, Vec3d pos) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processHitBlockEffects(entity, pos, afflictionInstance, AfflictionEffectComponents.HIT_BLOCK);
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
      Affliction.processProjectileSpawnedEffects(entity, projectile, afflictionInstance, AfflictionEffectComponents.PROJECTILE_SPAWNED);
    }
  }

  public static void handleTick(LivingEntity entity) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction affliction = afflictionInstance.affliction().value();
      Affliction.processTickEffects(entity, afflictionInstance, AfflictionEffectComponents.TICK);
      if (affliction.tickProgression().isEmpty()) continue;
      float previousProgression = afflictionInstance.progression();
      afflictionInstance.addToProgression(affliction.tickProgression().get().getValue(afflictionInstance.stage()));
      afflictionInstance.limitToAllowedValues();
      Affliction.processProgressionThresholdEffects(entity, previousProgression, afflictionInstance, AfflictionEffectComponents.PROGRESSION_THRESHOLD);
    }
    if (entity instanceof ServerPlayerEntity player) ServerPlayNetworking.send(player, new ActiveAfflictionsPayload(activeAfflictions.values().stream().map(AfflictionPacket::fromInstance).toList()));
  }

  public static void handleUsedItem(LivingEntity entity, ItemStack item) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processUsedItemEffects(entity, item, afflictionInstance, AfflictionEffectComponents.USED_ITEM);
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
