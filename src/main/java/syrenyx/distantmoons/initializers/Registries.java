package syrenyx.distantmoons.initializers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.SimpleRegistry;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;
import syrenyx.distantmoons.initializers.component.AfflictionEffectComponents;
import syrenyx.distantmoons.references.RegistryKeys;

public abstract class Registries {

  public static final SimpleRegistry<ComponentType<?>> AFFLICTION_EFFECT_COMPONENT_REGISTRY = FabricRegistryBuilder.createSimple(RegistryKeys.AFFLICTION_EFFECT_COMPONENT_REGISTRY_KEY).buildAndRegister();
  public static final SimpleRegistry<MapCodec<? extends AfflictionEntityEffect>> AFFLICTION_ENTITY_EFFECT_REGISTRY = FabricRegistryBuilder.createSimple(RegistryKeys.AFFLICTION_ENTITY_EFFECT_REGISTRY_KEY).buildAndRegister();

  static {
    DynamicRegistries.registerSynced(RegistryKeys.AFFLICTION_REGISTRY_KEY, Affliction.CODEC);
  }

  public static void initialize() {}
}
