package syrenyx.distantmoons.content.affliction.effect.value;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import syrenyx.distantmoons.initializers.DistantMoonsRegistries;

import java.util.function.Function;
import net.minecraft.util.RandomSource;

public interface AfflictionValueEffect {

  Codec<AfflictionValueEffect> CODEC = DistantMoonsRegistries.AFFLICTION_VALUE_EFFECT_REGISTRY.byNameCodec().dispatch(AfflictionValueEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionValueEffect> getCodec();

  float apply(int stage, RandomSource random, float input);
}
