package syrenyx.distantmoons.content.affliction.effect.value;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record AddEffect(LevelBasedValue value) implements AfflictionValueEffect {

  public static final MapCodec<AddEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LevelBasedValue.CODEC.fieldOf("value").forGetter(AddEffect::value)
      )
      .apply(instance, AddEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionValueEffect> getCodec() {
    return CODEC;
  }

  @Override
  public float apply(int stage, RandomSource random, float input) {
    return this.value.calculate(stage) + input;
  }
}
