package syrenyx.distantmoons.references;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.Affliction;

public abstract class RegistryKeys {

  public static final RegistryKey<Registry<Affliction>> AFFLICTION_REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.of(UnderDistantMoons.MOD_ID, "affliction"));
}
