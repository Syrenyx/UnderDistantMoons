package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public record AllOfEntityEffect(List<AfflictionEntityEffect> effects) implements AfflictionEntityEffect {

  public static final MapCodec<AllOfEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          AfflictionEntityEffect.CODEC.listOf().fieldOf("effects").forGetter(AllOfEntityEffect::effects)
      )
      .apply(instance, AllOfEntityEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    this.effects.forEach(effect -> effect.apply(world, stage, target, pos));
  }
}
