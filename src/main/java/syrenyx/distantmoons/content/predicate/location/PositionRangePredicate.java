package syrenyx.distantmoons.content.predicate.location;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.predicate.NumberRange;

import java.util.Optional;

public record PositionRangePredicate(NumberRange.DoubleRange x, NumberRange.DoubleRange y, NumberRange.DoubleRange z) {
  public static final Codec<PositionRangePredicate> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
              NumberRange.DoubleRange.CODEC.optionalFieldOf("x", NumberRange.DoubleRange.ANY).forGetter(PositionRangePredicate::x),
              NumberRange.DoubleRange.CODEC.optionalFieldOf("y", NumberRange.DoubleRange.ANY).forGetter(PositionRangePredicate::y),
              NumberRange.DoubleRange.CODEC.optionalFieldOf("z", NumberRange.DoubleRange.ANY).forGetter(PositionRangePredicate::z)
          )
          .apply(instance, PositionRangePredicate::new)
  );

  static Optional<PositionRangePredicate> create(NumberRange.DoubleRange x, NumberRange.DoubleRange y, NumberRange.DoubleRange z) {
    return x.isDummy() && y.isDummy() && z.isDummy() ? Optional.empty() : Optional.of(new PositionRangePredicate(x, y, z));
  }

  public boolean test(double x, double y, double z) {
    return this.x.test(x) && this.y.test(y) && this.z.test(z);
  }
}
