package syrenyx.distantmoons.references;

import com.mojang.serialization.MapCodec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;
import syrenyx.distantmoons.affliction.effect.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.affliction.effect.AfflictionValueEffect;

public class RegistryKeys {

  public static final RegistryKey<Registry<Affliction>> AFFLICTION = keyOf("affliction");
  public static final RegistryKey<Registry<ComponentType<?>>> AFFLICTION_EFFECT_COMPONENT_TYPE = keyOf("affliction_effect_component_type");
  public static final RegistryKey<Registry<MapCodec<? extends AfflictionEntityEffect>>> AFFLICTION_ENTITY_EFFECT_TYPE = keyOf("affliction_entity_effect_type");
  public static final RegistryKey<Registry<MapCodec<? extends AfflictionLocationBasedEffect>>> AFFLICTION_LOCATION_BASED_EFFECT_TYPE = keyOf("affliction_location_based_effect_type");
  public static final RegistryKey<Registry<MapCodec<? extends AfflictionValueEffect>>> AFFLICTION_VALUE_EFFECT_TYPE = keyOf("affliction_value_effect_type");

  private static <T> RegistryKey<Registry<T>> keyOf(String id) {
    return RegistryKey.ofRegistry(UnderDistantMoons.identifierOf(id));
  }
}
