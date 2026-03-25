package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import syrenyx.distantmoons.content.particle.FadingTrailParticle;
import syrenyx.distantmoons.content.particle.UnderworldConfluxEffectParticle;
import syrenyx.distantmoons.content.particle.UnderworldParticle;
import syrenyx.distantmoons.initializers.DistantMoonsParticleTypes;
import syrenyx.distantmoons.content.particle.SparkParticle;

public abstract class DistantMoonsParticles {

  private static final ParticleFactoryRegistry REGISTRY = ParticleFactoryRegistry.getInstance();

  static {
    REGISTRY.register(DistantMoonsParticleTypes.FADING_TRAIL, FadingTrailParticle.Factory::new);
    REGISTRY.register(DistantMoonsParticleTypes.SCRAPE_RUST, SparkParticle.ScrapeRustFactory::new);
    REGISTRY.register(DistantMoonsParticleTypes.UNDERWORLD_CONFLUX_EFFECT, UnderworldConfluxEffectParticle.Factory::new);
    REGISTRY.register(DistantMoonsParticleTypes.UNDERWORLD_PARTICLE, UnderworldParticle.Factory::new);
  }

  public static void initialize() {}
}
