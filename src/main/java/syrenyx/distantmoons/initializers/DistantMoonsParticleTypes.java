package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsParticleTypes {

  public static final SimpleParticleType SCRAPE_RUST = register("scrape_rust");

  private static SimpleParticleType register(String id) {
    return Registry.register(Registries.PARTICLE_TYPE, UnderDistantMoons.identifierOf(id), FabricParticleTypes.simple());
  }

  public static void initialize() {}
}
