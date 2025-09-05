package syrenyx.distantmoons.affliction;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.data.attachment.LivingEntityAttachment;
import syrenyx.distantmoons.data.networking.AfflictionPacket;
import syrenyx.distantmoons.data.persistent.PersistentStateManager;
import syrenyx.distantmoons.initializers.component.AfflictionEffectComponents;
import syrenyx.distantmoons.payload.ActiveAfflictionsPayload;

import java.util.Map;

public abstract class AfflictionManager {

  public static boolean clearAffliction(LivingEntity entity, @Nullable RegistryEntry<Affliction> affliction) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    if (activeAfflictions.isEmpty()) return false;
    if (affliction != null) {
      if (activeAfflictions.get(affliction) != null) Affliction.processStageChangedEffects(entity, activeAfflictions.get(affliction), true, AfflictionEffectComponents.STAGE_CHANGED);
      return activeAfflictions.remove(affliction) != null;
    }
    for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
      Affliction.processStageChangedEffects(entity, afflictionInstance, true, AfflictionEffectComponents.STAGE_CHANGED);
    }
    activeAfflictions.clear();
    return true;
  }

  public static boolean setAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    if (isImmune(entity, afflictionInstance.affliction())) return false;
    afflictionInstance.limitToAllowedValues();
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    activeAfflictions.put(afflictionInstance.affliction(), afflictionInstance);
    Affliction.processStageChangedEffects(entity, afflictionInstance, false, AfflictionEffectComponents.STAGE_CHANGED);
    return true;
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
      result = true;
    }
    return result;
  }

  public static void handlePlayerDeath(ServerPlayerEntity player) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(player);
    for (RegistryEntry<Affliction> affliction : activeAfflictions.keySet()) {
      if (!affliction.value().persistent()) activeAfflictions.remove(affliction);
    }
  }

  public static void handlePostAttack(Entity victim, DamageSource damageSource) {
    for (EnchantmentEffectTarget targetType : EnchantmentEffectTarget.values()) {
      Entity entity = switch (targetType) {
        case ATTACKER -> damageSource.getAttacker();
        case DAMAGING_ENTITY -> damageSource.getSource();
        case VICTIM -> victim;
      };
      if (!(entity instanceof LivingEntity afflicted)) return;
      Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(afflicted);
      for (AfflictionInstance afflictionInstance : activeAfflictions.values()) {
        Affliction.processPostAttackEffects(victim, damageSource, targetType, afflictionInstance, AfflictionEffectComponents.POST_ATTACK);
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
      afflictionInstance.addToProgression(affliction.tickProgression().get().getValue(afflictionInstance.stage()));
      afflictionInstance.limitToAllowedValues();
    }
    if (entity instanceof ServerPlayerEntity player) ServerPlayNetworking.send(player, new ActiveAfflictionsPayload(activeAfflictions.values().stream().map(AfflictionPacket::fromInstance).toList()));
  }

  private static Map<RegistryEntry<Affliction>, AfflictionInstance> getActiveAfflictions(LivingEntity entity) {
    return entity instanceof PlayerEntity player
        ? PersistentStateManager.getPlayerState(player).livingEntityAttachment().activeAfflictions()
        : LivingEntityAttachment.getOrCreate(entity).activeAfflictions();
  }

  private static boolean isImmune(LivingEntity entity, RegistryEntry<Affliction> affliction) {
    if (affliction.value().immuneEntities().isEmpty()) return false;
    return affliction.value().immuneEntities().get().contains(Registries.ENTITY_TYPE.getEntry(entity.getType()));
  }
}
