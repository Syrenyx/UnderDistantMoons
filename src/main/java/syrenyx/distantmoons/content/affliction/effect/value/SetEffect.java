package syrenyx.distantmoons.content.affliction.effect.value;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record SetEffect(LevelBasedValue value) implements AfflictionValueEffect {

  public static final MapCodec<SetEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LevelBasedValue.CODEC.fieldOf("value").forGetter(SetEffect::value)
      )
      .apply(instance, SetEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionValueEffect> getCodec() {
    return CODEC;
  }

  @Override
  public float apply(int stage, RandomSource random, float input) {
    return this.value.calculate(stage);
  }
}
