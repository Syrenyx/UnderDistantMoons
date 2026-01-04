package syrenyx.distantmoons.content.affliction.effect.location_based;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;

import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

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
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos, AfflictionInstance instance) {
    this.effects.forEach(effect -> effect.apply(world, stage, target, pos, instance));
  }

  @Override
  public void remove(ServerLevel world, int stage, Entity target, Vec3 pos, AfflictionInstance instance) {}
}
