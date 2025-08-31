package syrenyx.distantmoons.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record AfflictionsPredicate(List<AfflictionPredicate> afflictionPredicates) {

  public static final Codec<AfflictionsPredicate> CODEC = AfflictionPredicate.CODEC.listOf().xmap(AfflictionsPredicate::new, AfflictionsPredicate::getAfflictionPredicates);

  private List<AfflictionPredicate> getAfflictionPredicates() {
    return this.afflictionPredicates;
  }

  public boolean test(@Nullable Entity entity) {
    if (!(entity instanceof LivingEntity)) return false;
    for (AfflictionPredicate afflictionPredicate : this.afflictionPredicates) {
      if (!afflictionPredicate.test(entity)) return false;
    }
    return true;
  }
}
