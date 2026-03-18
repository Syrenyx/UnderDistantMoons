package syrenyx.distantmoons.content.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsParticleTypes;

public record UnderworldParticleOptions(Vec3 anchor, int color) implements ParticleOptions {

  public static final MapCodec<UnderworldParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Vec3.CODEC.fieldOf("anchor").forGetter(UnderworldParticleOptions::anchor),
          ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(UnderworldParticleOptions::color)
      )
      .apply(instance, UnderworldParticleOptions::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, UnderworldParticleOptions> STREAM_CODEC = StreamCodec.composite(
      Vec3.STREAM_CODEC, UnderworldParticleOptions::anchor,
      ByteBufCodecs.INT, UnderworldParticleOptions::color,
      UnderworldParticleOptions::new
  );

  @Override
  public @NonNull ParticleType<UnderworldParticleOptions> getType() {
    return DistantMoonsParticleTypes.UNDERWORLD_PARTICLE;
  }
}
