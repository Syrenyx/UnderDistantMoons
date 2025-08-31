package syrenyx.distantmoons.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.initializers.Registries;

import java.util.function.Function;

public interface AfflictionLocationBasedEffect {
  Codec<AfflictionLocationBasedEffect> CODEC = Registries.AFFLICTION_LOCATION_BASED_EFFECT_TYPE.getCodec().dispatch(AfflictionLocationBasedEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionLocationBasedEffect> getCodec();

  void apply(ServerWorld world, Entity target, Vec3d pos, boolean newlyApplied);

  default void remove(Entity target, Vec3d pos) {}
}
