package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.references.RegistryKeys;

public class Registries {

  public static void initialize() {
    DynamicRegistries.registerSynced(RegistryKeys.AFFLICTION, Affliction.CODEC);
  }
}
