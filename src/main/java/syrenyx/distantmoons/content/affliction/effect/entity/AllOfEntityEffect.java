package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

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
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    this.effects.forEach(effect -> effect.apply(world, stage, target, pos));
  }
}
