package syrenyx.distantmoons.content.affliction.effect.value;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.util.math.random.Random;

public record MultiplyEffect(EnchantmentLevelBasedValue factor) implements AfflictionValueEffect {

  public static final MapCodec<MultiplyEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          EnchantmentLevelBasedValue.CODEC.fieldOf("factor").forGetter(MultiplyEffect::factor)
      )
      .apply(instance, MultiplyEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionValueEffect> getCodec() {
    return CODEC;
  }

  @Override
  public float apply(int stage, Random random, float input) {
    return this.factor.getValue(stage) * input;
  }
}
