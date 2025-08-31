package syrenyx.distantmoons.affliction;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import syrenyx.distantmoons.attached_data.ActiveAfflictionsAttachment;
import syrenyx.distantmoons.persistent_state.StateManager;

import java.util.Map;

public abstract class AfflictionManager {

  public static void addAffliction(LivingEntity entity, AfflictionInstance affliction) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> afflictions = getAfflictions(entity);
    AfflictionInstance currentAffliction = afflictions.get(affliction.getAfflictionType());
    if (currentAffliction == null) {
      setAffliction(entity, affliction);
      return;
    }
    currentAffliction.addToStage(affliction.getStage());
    currentAffliction.addToProgression(affliction.getProgression());
  }

  public static void setAffliction(LivingEntity entity, AfflictionInstance affliction) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> afflictions = getAfflictions(entity);
    afflictions.put(affliction.getAfflictionType(), affliction);
  }

  public static boolean clearAfflictions(LivingEntity entity) {
    return clearAffliction(entity, null);
  }

  public static boolean clearAffliction(LivingEntity entity, RegistryEntry<Affliction> affliction) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> afflictions = getAfflictions(entity);
    if (afflictions == null) return false;
    if (affliction == null) {
      boolean result = !afflictions.isEmpty();
      afflictions.clear();
      return result;
    }
    else return afflictions.remove(affliction) != null;
  }

  public static void processPlayerDeath(ServerPlayerEntity player) {
    processDeathEffects(player);
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = StateManager.getPlayerState(player).getActiveAfflictions();
    for (RegistryEntry<Affliction> affliction : activeAfflictions.keySet()) {
      if (!affliction.value().persistent()) activeAfflictions.remove(affliction);
    }
  }

  public static void processDeathEffects(LivingEntity entity) {
    if (entity instanceof PlayerEntity) return;
  }

  public static void processMovementEffects(LivingEntity entity) {
    if (entity instanceof PlayerEntity) return;
  }

  public static void processTickEffects(LivingEntity entity) {
    if (entity instanceof PlayerEntity) return;
  }

  private static Map<RegistryEntry<Affliction>, AfflictionInstance> getAfflictions(LivingEntity entity) {
    return entity instanceof PlayerEntity player
        ? StateManager.getPlayerState(player).getActiveAfflictions()
        : ActiveAfflictionsAttachment.getOrCreate(entity).activeAfflictions();
  }
}
