package syrenyx.distantmoons.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.NumberRange;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.references.RegistryKeys;

import java.util.Optional;

public record AfflictionPredicate(
    Optional<RegistryEntryList<Affliction>> afflictions,
    NumberRange.IntRange stages,
    NumberRange.DoubleRange progression
) {

  public static final Codec<AfflictionPredicate> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          RegistryCodecs.entryList(RegistryKeys.AFFLICTION).optionalFieldOf("activeAfflictions").forGetter(AfflictionPredicate::afflictions),
          NumberRange.IntRange.CODEC.optionalFieldOf("stages", NumberRange.IntRange.between(1, Affliction.MAX_STAGE)).forGetter(AfflictionPredicate::stages),
          NumberRange.DoubleRange.CODEC.optionalFieldOf("progression", NumberRange.DoubleRange.between(1D, Affliction.MAX_PROGRESSION_LIMIT)).forGetter(AfflictionPredicate::progression)
      )
      .apply(instance, AfflictionPredicate::new)
  );

  public AfflictionPredicate(RegistryEntry<Affliction> affliction, NumberRange.IntRange stages, NumberRange.DoubleRange progression) {
    this(Optional.of(RegistryEntryList.of(affliction)), stages, progression);
  }

  public AfflictionPredicate(RegistryEntryList<Affliction> afflictions, NumberRange.IntRange stages, NumberRange.DoubleRange progression) {
    this(Optional.of(afflictions), stages, progression);
  }

  public boolean test(@Nullable Entity entity) {
    if (!(entity instanceof LivingEntity)) return false;
    //TODO: entity.getAfflictions()
    return true;
  }
}
