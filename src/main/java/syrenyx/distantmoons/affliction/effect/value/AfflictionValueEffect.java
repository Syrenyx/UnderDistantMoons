package syrenyx.distantmoons.affliction.effect.value;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.math.random.Random;
import syrenyx.distantmoons.initializers.DistantMoonsRegistries;

import java.util.function.Function;

public interface AfflictionValueEffect {

  Codec<AfflictionValueEffect> CODEC = DistantMoonsRegistries.AFFLICTION_VALUE_EFFECT_REGISTRY.getCodec().dispatch(AfflictionValueEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionValueEffect> getCodec();

  float apply(int stage, Random random, float input);
}
