package syrenyx.distantmoons.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.initializers.Registries;

import java.util.function.Function;

public interface AfflictionEntityEffect {

  Codec<AfflictionEntityEffect> CODEC = Registries.AFFLICTION_ENTITY_EFFECT_REGISTRY.getCodec().dispatch(AfflictionEntityEffect::getCodec, Function.identity());

  MapCodec<? extends AfflictionEntityEffect> getCodec();

  void apply(ServerWorld world, int stage, Entity target, Vec3d pos);

  default void remove(Entity target, Vec3d pos) {}
}
