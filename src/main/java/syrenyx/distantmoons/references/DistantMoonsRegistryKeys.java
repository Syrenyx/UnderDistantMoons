package syrenyx.distantmoons.references;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.content.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.content.affliction.effect.value.AfflictionValueEffect;

public abstract class DistantMoonsRegistryKeys {

  public static final ResourceKey<Registry<Affliction>> AFFLICTION_REGISTRY_KEY = keyOf("affliction");
  public static final ResourceKey<Registry<DataComponentType<?>>> AFFLICTION_EFFECT_COMPONENT_REGISTRY_KEY = keyOf("affliction_effect_component");
  public static final ResourceKey<Registry<MapCodec<? extends AfflictionEntityEffect>>> AFFLICTION_ENTITY_EFFECT_REGISTRY_KEY = keyOf("affliction_entity_effect");
  public static final ResourceKey<Registry<MapCodec<? extends AfflictionLocationBasedEffect>>> AFFLICTION_LOCATION_BASED_EFFECT_REGISTRY_KEY = keyOf("affliction_location_based_effect");
  public static final ResourceKey<Registry<MapCodec<? extends AfflictionValueEffect>>> AFFLICTION_VALUE_EFFECT_REGISTRY_KEY = keyOf("affliction_value_effect");

  private static <T> ResourceKey<Registry<T>> keyOf(String id) {
    return ResourceKey.createRegistryKey(UnderDistantMoons.identifierOf(id));
  }
}
