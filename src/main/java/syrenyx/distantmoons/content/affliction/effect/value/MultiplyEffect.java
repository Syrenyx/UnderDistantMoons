package syrenyx.distantmoons.content.affliction.effect.value;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record MultiplyEffect(LevelBasedValue factor) implements AfflictionValueEffect {

  public static final MapCodec<MultiplyEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LevelBasedValue.CODEC.fieldOf("factor").forGetter(MultiplyEffect::factor)
      )
      .apply(instance, MultiplyEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionValueEffect> getCodec() {
    return CODEC;
  }

  @Override
  public float apply(int stage, RandomSource random, float input) {
    return this.factor.calculate(stage) * input;
  }
}
