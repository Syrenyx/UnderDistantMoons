package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import syrenyx.distantmoons.initializers.DistantMoonsParticleTypes;
import syrenyx.distantmoons.particle.SparkParticle;

public class DistantMoonsParticles {

  static {
    ParticleFactoryRegistry.getInstance().register(DistantMoonsParticleTypes.SCRAPE_RUST, SparkParticle.ScrapeRustFactory::new);
  }

  public static void initialize() {}
}
