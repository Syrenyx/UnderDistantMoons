package syrenyx.distantmoons.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.initializers.DistantMoonsAttachedData;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("UnstableApiUsage")
public record LivingEntityAttachment(Map<Holder<Affliction>, AfflictionInstance> activeAfflictions) {

  public static final Codec<LivingEntityAttachment> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.unboundedMap(Affliction.FIXED_ENTRY_CODEC, AfflictionInstance.CODEC).optionalFieldOf("active_afflictions", new HashMap<>()).forGetter(LivingEntityAttachment::activeAfflictions)
      )
      .apply(instance, LivingEntityAttachment::new)
  );

  public LivingEntityAttachment(Map<Holder<Affliction>, AfflictionInstance> activeAfflictions) {
    this.activeAfflictions = new HashMap<>(activeAfflictions);
  }

  public static LivingEntityAttachment newDefault() {
    return new LivingEntityAttachment(new HashMap<>());
  }

  public static LivingEntityAttachment getOrCreate(LivingEntity entity) {
    if (!entity.hasAttached(DistantMoonsAttachedData.LIVING_ENTITY_ATTACHMENT)) {
      entity.setAttached(DistantMoonsAttachedData.LIVING_ENTITY_ATTACHMENT, new LivingEntityAttachment(
          new HashMap<>()
      ));
    }
    return entity.getAttached(DistantMoonsAttachedData.LIVING_ENTITY_ATTACHMENT);
  }
}
