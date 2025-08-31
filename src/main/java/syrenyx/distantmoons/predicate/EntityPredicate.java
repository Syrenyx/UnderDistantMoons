package syrenyx.distantmoons.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record EntityPredicate(
    Optional<AfflictionsPredicate> afflictionsPredicate
) {

  public static final Codec<EntityPredicate> CODEC = Codec.recursive(
      "EntityPredicate",
      codec -> RecordCodecBuilder.create(instance -> instance
          .group(
              AfflictionsPredicate.CODEC.optionalFieldOf("activeAfflictions").forGetter(EntityPredicate::afflictionsPredicate)
          )
          .apply(instance, EntityPredicate::new)
      )
  );

  public boolean test(@Nullable Entity entity) {
    if (entity == null) return false;
    if (this.afflictionsPredicate.isPresent() && !this.afflictionsPredicate.get().test(entity)) return false;
    return true;
  }
}
