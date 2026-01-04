package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsParticleTypes {

  public static final SimpleParticleType SCRAPE_RUST = register("scrape_rust");

  private static SimpleParticleType register(String id) {
    return Registry.register(BuiltInRegistries.PARTICLE_TYPE, UnderDistantMoons.identifierOf(id), FabricParticleTypes.simple());
  }

  public static void initialize() {}
}
