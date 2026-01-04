package syrenyx.distantmoons.content.affliction.effect.location_based;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.initializers.DistantMoonsRegistries;

import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface AfflictionLocationBasedEffect {

  Codec<AfflictionLocationBasedEffect> CODEC = DistantMoonsRegistries.AFFLICTION_LOCATION_BASED_EFFECT_REGISTRY.byNameCodec().dispatch(AfflictionLocationBasedEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionLocationBasedEffect> getCodec();

  void apply(ServerLevel world, int stage, Entity target, Vec3 pos, AfflictionInstance instance);

  void remove(ServerLevel world, int stage, Entity target, Vec3 pos, AfflictionInstance instance);
}
