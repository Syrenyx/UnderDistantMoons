package syrenyx.distantmoons.content.particle;

import com.mojang.serialization.Codec;
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

public record UnderworldConfluxEffectOptions(float angle, boolean collision, int color, Vec3 target) implements ParticleOptions {

  public static final MapCodec<UnderworldConfluxEffectOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Codec.FLOAT.fieldOf("angle").forGetter(UnderworldConfluxEffectOptions::angle),
          Codec.BOOL.fieldOf("collision").forGetter(UnderworldConfluxEffectOptions::collision),
          ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(UnderworldConfluxEffectOptions::color),
          Vec3.CODEC.fieldOf("target").forGetter(UnderworldConfluxEffectOptions::target)

      )
      .apply(instance, UnderworldConfluxEffectOptions::new)
  );
  public static final StreamCodec<RegistryFriendlyByteBuf, UnderworldConfluxEffectOptions> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.FLOAT, UnderworldConfluxEffectOptions::angle,
      ByteBufCodecs.BOOL, UnderworldConfluxEffectOptions::collision,
      ByteBufCodecs.INT, UnderworldConfluxEffectOptions::color,
      Vec3.STREAM_CODEC, UnderworldConfluxEffectOptions::target,
      UnderworldConfluxEffectOptions::new
  );

  @Override
  public @NonNull ParticleType<UnderworldConfluxEffectOptions> getType() {
    return DistantMoonsParticleTypes.UNDERWORLD_CONFLUX_EFFECT;
  }
}
