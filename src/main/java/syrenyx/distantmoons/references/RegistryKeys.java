package syrenyx.distantmoons.references;

import com.mojang.serialization.MapCodec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

public abstract class RegistryKeys {

  public static final RegistryKey<Registry<Affliction>> AFFLICTION_REGISTRY_KEY = keyOf("affliction");
  public static final RegistryKey<Registry<ComponentType<?>>> AFFLICTION_EFFECT_COMPONENT_REGISTRY_KEY = keyOf("affliction_effect_component");
  public static final RegistryKey<Registry<MapCodec<? extends AfflictionEntityEffect>>> AFFLICTION_ENTITY_EFFECT_REGISTRY_KEY = keyOf("affliction_entity_effect");

  private static <T> RegistryKey<Registry<T>> keyOf(String id) {
    return RegistryKey.ofRegistry(UnderDistantMoons.identifierOf(id));
  }
}
