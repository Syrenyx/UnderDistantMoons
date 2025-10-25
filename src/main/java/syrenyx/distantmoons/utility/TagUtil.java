package syrenyx.distantmoons.utility;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class TagUtil {

  public static <T> TagKey<T> generateKey(RegistryKey<Registry<T>> registryKey, String id) {
    return TagKey.of(registryKey, UnderDistantMoons.identifierOf(id));
  }
}
