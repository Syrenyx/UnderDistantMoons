package syrenyx.distantmoons.content.predicate.location;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.criterion.MinMaxBounds;

public record PositionRangePredicate(MinMaxBounds.Doubles x, MinMaxBounds.Doubles y, MinMaxBounds.Doubles z) {
  public static final Codec<PositionRangePredicate> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
              MinMaxBounds.Doubles.CODEC.optionalFieldOf("x", MinMaxBounds.Doubles.ANY).forGetter(PositionRangePredicate::x),
              MinMaxBounds.Doubles.CODEC.optionalFieldOf("y", MinMaxBounds.Doubles.ANY).forGetter(PositionRangePredicate::y),
              MinMaxBounds.Doubles.CODEC.optionalFieldOf("z", MinMaxBounds.Doubles.ANY).forGetter(PositionRangePredicate::z)
          )
          .apply(instance, PositionRangePredicate::new)
  );

  static Optional<PositionRangePredicate> create(MinMaxBounds.Doubles x, MinMaxBounds.Doubles y, MinMaxBounds.Doubles z) {
    return x.isAny() && y.isAny() && z.isAny() ? Optional.empty() : Optional.of(new PositionRangePredicate(x, y, z));
  }

  public boolean test(double x, double y, double z) {
    return this.x.matches(x) && this.y.matches(y) && this.z.matches(z);
  }
}
