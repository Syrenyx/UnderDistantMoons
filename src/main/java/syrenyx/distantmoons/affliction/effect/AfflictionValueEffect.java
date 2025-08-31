package syrenyx.distantmoons.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.initializers.Registries;

import java.util.function.Function;

public interface AfflictionValueEffect {
  Codec<AfflictionValueEffect> CODEC = Registries.AFFLICTION_VALUE_EFFECT_TYPE.getCodec().dispatch(AfflictionValueEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionValueEffect> getCodec();

  void apply(ServerWorld world, Entity target, Vec3d pos);

  default void remove(Entity target, Vec3d pos) {}
}
