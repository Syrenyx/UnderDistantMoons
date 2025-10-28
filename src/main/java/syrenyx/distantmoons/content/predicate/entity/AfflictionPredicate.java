package syrenyx.distantmoons.content.predicate.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.NumberRange;
import net.minecraft.registry.entry.RegistryEntry;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.content.affliction.AfflictionManager;

import java.util.Map;

public record AfflictionPredicate(
    Map<RegistryEntry<Affliction>, AfflictionData> afflictions
) {

  public static final Codec<AfflictionPredicate> CODEC = Codec.unboundedMap(Affliction.FIXED_ENTRY_CODEC, AfflictionData.CODEC).xmap(AfflictionPredicate::new, AfflictionPredicate::afflictions);

  public boolean test(Entity entity) {
    if (!(entity instanceof LivingEntity livingEntity)) return false;
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = AfflictionManager.getActiveAfflictions(livingEntity);
    for (Map.Entry<RegistryEntry<Affliction>, AfflictionData> affliction : this.afflictions.entrySet()) {
      if (activeAfflictions.get(affliction.getKey()) == null) return false;
      AfflictionInstance activeAffliction = activeAfflictions.get(affliction.getKey());
      if (!affliction.getValue().stage.test(activeAffliction.stage()) || !affliction.getValue().progression.test(activeAffliction.progression())) return false;
    }
    return true;
  }

  public record AfflictionData(NumberRange.IntRange stage, NumberRange.DoubleRange progression) {

    public static final Codec<AfflictionData> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            NumberRange.IntRange.CODEC.optionalFieldOf("stage", NumberRange.IntRange.ANY).forGetter(AfflictionData::stage),
            NumberRange.DoubleRange.CODEC.optionalFieldOf("progression", NumberRange.DoubleRange.ANY).forGetter(AfflictionData::progression)
        )
        .apply(instance, AfflictionData::new)
    );
  }
}
