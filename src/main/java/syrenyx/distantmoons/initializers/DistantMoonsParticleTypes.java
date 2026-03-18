package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.particle.FadingTrailOptions;
import syrenyx.distantmoons.content.particle.UnderworldConfluxEffectOptions;
import syrenyx.distantmoons.content.particle.UnderworldParticleOptions;

public abstract class DistantMoonsParticleTypes {

  //SIMPLE PARTICLE TYPES
  public static final SimpleParticleType SCRAPE_RUST = register("scrape_rust");

  //COMPLEX PARTICLE TYPES
  public static final ParticleType<FadingTrailOptions> FADING_TRAIL = register("fading_trail", FadingTrailOptions.CODEC, FadingTrailOptions.STREAM_CODEC);
  public static final ParticleType<UnderworldConfluxEffectOptions> UNDERWORLD_CONFLUX_EFFECT = register("underworld_conflux_effect", UnderworldConfluxEffectOptions.CODEC, UnderworldConfluxEffectOptions.STREAM_CODEC);
  public static final ParticleType<UnderworldParticleOptions> UNDERWORLD_PARTICLE = register("underworld_particle", UnderworldParticleOptions.CODEC, UnderworldParticleOptions.STREAM_CODEC);

  private static SimpleParticleType register(String id) {
    return Registry.register(BuiltInRegistries.PARTICLE_TYPE, UnderDistantMoons.identifierOf(id), FabricParticleTypes.simple());
  }

  private static <T extends ParticleOptions> ParticleType<T> register(String id, MapCodec<T> mapCodec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
    return Registry.register(BuiltInRegistries.PARTICLE_TYPE, UnderDistantMoons.identifierOf(id), FabricParticleTypes.complex(mapCodec, streamCodec));
  }

  public static void initialize() {}
}
