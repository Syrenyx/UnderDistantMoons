package syrenyx.distantmoons.content.affliction.effect.location_based;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.initializers.DistantMoonsRegistries;

import java.util.function.Function;

public interface AfflictionLocationBasedEffect {

  Codec<AfflictionLocationBasedEffect> CODEC = DistantMoonsRegistries.AFFLICTION_LOCATION_BASED_EFFECT_REGISTRY.getCodec().dispatch(AfflictionLocationBasedEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionLocationBasedEffect> getCodec();

  void apply(ServerWorld world, int stage, Entity target, Vec3d pos, AfflictionInstance instance);

  void remove(ServerWorld world, int stage, Entity target, Vec3d pos, AfflictionInstance instance);
}
