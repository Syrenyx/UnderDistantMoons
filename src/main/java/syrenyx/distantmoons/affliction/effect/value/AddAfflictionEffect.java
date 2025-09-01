package syrenyx.distantmoons.affliction.effect.value;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.effect.AfflictionValueEffect;

public record AddAfflictionEffect(float value) implements AfflictionValueEffect {

  public static final MapCodec<AddAfflictionEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Codec.FLOAT.fieldOf("value").forGetter(AddAfflictionEffect::value)
      )
      .apply(instance, AddAfflictionEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionValueEffect> getCodec() {
    return null;
  }

  @Override
  public void apply(ServerWorld world, Entity target, Vec3d pos) {

  }
}
