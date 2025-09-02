package syrenyx.distantmoons.affliction;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import syrenyx.distantmoons.attached_data.LivingEntityAttachment;

import java.util.Map;

public abstract class AfflictionManager {

  public static void clearAffliction(LivingEntity entity, RegistryEntry<Affliction> affliction) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    if (affliction == null) activeAfflictions.clear();
    else activeAfflictions.remove(affliction);
  }

  public static void setAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    activeAfflictions.put(afflictionInstance.affliction(), afflictionInstance);
  }

  public static void giveAffliction(LivingEntity entity, AfflictionInstance afflictionInstance) {
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = getActiveAfflictions(entity);
    activeAfflictions.put(afflictionInstance.affliction(), afflictionInstance);
  }

  private static Map<RegistryEntry<Affliction>, AfflictionInstance> getActiveAfflictions(LivingEntity entity) {
    return LivingEntityAttachment.getOrCreate(entity).activeAfflictions();
  }
}
