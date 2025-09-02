package syrenyx.distantmoons.attached_data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.initializers.AttachedData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public record LivingEntityAttachment(Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions) {

  public static final Codec<LivingEntityAttachment> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.unboundedMap(Affliction.FIXED_ENTRY_CODEC, AfflictionInstance.CODEC).optionalFieldOf("active_afflictions", new HashMap<>()).forGetter(LivingEntityAttachment::activeAfflictions)
      )
      .apply(instance, LivingEntityAttachment::new)
  );

  public LivingEntityAttachment(Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions) {
    this.activeAfflictions = new HashMap<>(activeAfflictions);
  }

  public static LivingEntityAttachment getOrCreate(LivingEntity entity) {
    if (!entity.hasAttached(AttachedData.LIVING_ENTITY_ATTACHMENT)) {
      entity.setAttached(AttachedData.LIVING_ENTITY_ATTACHMENT, new LivingEntityAttachment(
          new HashMap<>()
      ));
    }
    return entity.getAttached(AttachedData.LIVING_ENTITY_ATTACHMENT);
  }
}
