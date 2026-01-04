package syrenyx.distantmoons.content.affliction.effect.value;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record RemoveBinomialEffect(LevelBasedValue chance) implements AfflictionValueEffect {

  public static final MapCodec<RemoveBinomialEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LevelBasedValue.CODEC.fieldOf("chance").forGetter(RemoveBinomialEffect::chance)
      )
      .apply(instance, RemoveBinomialEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionValueEffect> getCodec() {
    return CODEC;
  }

  @Override
  public float apply(int stage, RandomSource random, float input) {
    float chanceValue = this.chance.calculate(stage);
    float result = input;
    for (int i = 0; i < input; i++) {
      if (random.nextFloat() < chanceValue) result--;
    }
    return result;
  }
}
