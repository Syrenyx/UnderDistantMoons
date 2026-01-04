package syrenyx.distantmoons.content.predicate.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.content.affliction.AfflictionManager;

import java.util.Map;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public record AfflictionPredicate(
    Map<Holder<Affliction>, AfflictionData> afflictions
) {

  public static final Codec<AfflictionPredicate> CODEC = Codec.unboundedMap(Affliction.FIXED_ENTRY_CODEC, AfflictionData.CODEC).xmap(AfflictionPredicate::new, AfflictionPredicate::afflictions);

  public boolean test(Entity entity) {
    if (!(entity instanceof LivingEntity livingEntity)) return false;
    Map<Holder<Affliction>, AfflictionInstance> activeAfflictions = AfflictionManager.getActiveAfflictions(livingEntity);
    for (Map.Entry<Holder<Affliction>, AfflictionData> affliction : this.afflictions.entrySet()) {
      if (activeAfflictions.get(affliction.getKey()) == null) return false;
      AfflictionInstance activeAffliction = activeAfflictions.get(affliction.getKey());
      if (!affliction.getValue().stage.matches(activeAffliction.stage()) || !affliction.getValue().progression.matches(activeAffliction.progression())) return false;
    }
    return true;
  }

  public record AfflictionData(MinMaxBounds.Ints stage, MinMaxBounds.Doubles progression) {

    public static final Codec<AfflictionData> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            MinMaxBounds.Ints.CODEC.optionalFieldOf("stage", MinMaxBounds.Ints.ANY).forGetter(AfflictionData::stage),
            MinMaxBounds.Doubles.CODEC.optionalFieldOf("progression", MinMaxBounds.Doubles.ANY).forGetter(AfflictionData::progression)
        )
        .apply(instance, AfflictionData::new)
    );
  }
}
