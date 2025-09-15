package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.initializers.DistantMoonsRegistries;

import java.util.function.Function;

public interface AfflictionEntityEffect extends AfflictionLocationBasedEffect {

  Codec<AfflictionEntityEffect> CODEC = DistantMoonsRegistries.AFFLICTION_ENTITY_EFFECT_REGISTRY.getCodec().dispatch(AfflictionEntityEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionEntityEffect> getCodec();

  void apply(ServerWorld world, int stage, Entity target, Vec3d pos);

  @Override
  default void apply(ServerWorld world, int stage, Entity user, Vec3d pos, AfflictionInstance instance) {
    this.apply(world, stage, user, pos);
  }

  default void remove(ServerWorld world, int stage, Entity target, Vec3d pos, AfflictionInstance instance) {}
}
