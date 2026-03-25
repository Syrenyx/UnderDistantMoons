package syrenyx.distantmoons.content.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsParticleTypes;

public record FadingTrailOptions(int color, int duration) implements ParticleOptions {

  public static final MapCodec<FadingTrailOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(FadingTrailOptions::color),
          ExtraCodecs.POSITIVE_INT.fieldOf("duration").forGetter(FadingTrailOptions::duration)
      )
      .apply(instance, FadingTrailOptions::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, FadingTrailOptions> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, FadingTrailOptions::color,
      ByteBufCodecs.INT, FadingTrailOptions::duration,
      FadingTrailOptions::new
  );

  @Override
  public @NonNull ParticleType<FadingTrailOptions> getType() {
    return DistantMoonsParticleTypes.FADING_TRAIL;
  }
}
