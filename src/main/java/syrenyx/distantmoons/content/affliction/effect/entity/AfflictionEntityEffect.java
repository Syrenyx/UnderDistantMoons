package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.content.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.initializers.DistantMoonsRegistries;

import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface AfflictionEntityEffect extends AfflictionLocationBasedEffect {

  Codec<AfflictionEntityEffect> CODEC = DistantMoonsRegistries.AFFLICTION_ENTITY_EFFECT_REGISTRY.byNameCodec().dispatch(AfflictionEntityEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionEntityEffect> getCodec();

  void apply(ServerLevel world, int stage, Entity target, Vec3 pos);

  @Override
  default void apply(ServerLevel world, int stage, Entity user, Vec3 pos, AfflictionInstance instance) {
    this.apply(world, stage, user, pos);
  }

  default void remove(ServerLevel world, int stage, Entity target, Vec3 pos, AfflictionInstance instance) {}
}
