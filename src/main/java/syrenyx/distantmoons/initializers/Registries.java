package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.references.RegistryKeys;

public abstract class Registries {

  static {
    DynamicRegistries.registerSynced(RegistryKeys.AFFLICTION_REGISTRY_KEY, Affliction.CODEC);
  }

  public static void initialize() {}
}
