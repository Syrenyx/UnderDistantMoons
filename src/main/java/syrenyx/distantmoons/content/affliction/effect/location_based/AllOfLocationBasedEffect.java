package syrenyx.distantmoons.content.affliction.effect.location_based;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;

import java.util.List;

public record AllOfLocationBasedEffect(List<AfflictionLocationBasedEffect> effects) implements AfflictionLocationBasedEffect {

  public static final MapCodec<AllOfLocationBasedEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          AfflictionLocationBasedEffect.CODEC.listOf().fieldOf("effects").forGetter(AllOfLocationBasedEffect::effects)
      )
      .apply(instance, AllOfLocationBasedEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionLocationBasedEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos, AfflictionInstance instance) {
    this.effects.forEach(effect -> effect.apply(world, stage, target, pos, instance));
  }

  @Override
  public void remove(ServerWorld world, int stage, Entity target, Vec3d pos, AfflictionInstance instance) {}
}
