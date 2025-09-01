package syrenyx.distantmoons.references;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.Affliction;

public class RegistryKeys {

  public static final RegistryKey<Registry<Affliction>> AFFLICTION = keyOf("affliction");

  private static <T> RegistryKey<Registry<T>> keyOf(String id) {
    return RegistryKey.ofRegistry(UnderDistantMoons.identifierOf(id));
  }
}
