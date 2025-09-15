package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.SimpleRegistry;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.effect.entity.AfflictionEntityEffect;
import syrenyx.distantmoons.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.affliction.effect.value.AfflictionValueEffect;
import syrenyx.distantmoons.references.DistantMoonsRegistryKeys;

public abstract class DistantMoonsRegistries {

  public static final SimpleRegistry<ComponentType<?>> AFFLICTION_EFFECT_COMPONENT_REGISTRY = FabricRegistryBuilder.createSimple(DistantMoonsRegistryKeys.AFFLICTION_EFFECT_COMPONENT_REGISTRY_KEY).buildAndRegister();
  public static final SimpleRegistry<MapCodec<? extends AfflictionEntityEffect>> AFFLICTION_ENTITY_EFFECT_REGISTRY = FabricRegistryBuilder.createSimple(DistantMoonsRegistryKeys.AFFLICTION_ENTITY_EFFECT_REGISTRY_KEY).buildAndRegister();
  public static final SimpleRegistry<MapCodec<? extends AfflictionLocationBasedEffect>> AFFLICTION_LOCATION_BASED_EFFECT_REGISTRY = FabricRegistryBuilder.createSimple(DistantMoonsRegistryKeys.AFFLICTION_LOCATION_BASED_EFFECT_REGISTRY_KEY).buildAndRegister();
  public static final SimpleRegistry<MapCodec<? extends AfflictionValueEffect>> AFFLICTION_VALUE_EFFECT_REGISTRY = FabricRegistryBuilder.createSimple(DistantMoonsRegistryKeys.AFFLICTION_VALUE_EFFECT_REGISTRY_KEY).buildAndRegister();

  static {
    DynamicRegistries.registerSynced(DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY, Affliction.CODEC);
  }

  public static void initialize() {}
}
