package syrenyx.distantmoons.content.affliction.effect.value;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.util.math.random.Random;

public record SetEffect(EnchantmentLevelBasedValue value) implements AfflictionValueEffect {

  public static final MapCodec<SetEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          EnchantmentLevelBasedValue.CODEC.fieldOf("value").forGetter(SetEffect::value)
      )
      .apply(instance, SetEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionValueEffect> getCodec() {
    return CODEC;
  }

  @Override
  public float apply(int stage, Random random, float input) {
    return this.value.getValue(stage);
  }
}
