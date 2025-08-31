package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;
import syrenyx.distantmoons.affliction.effect.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.affliction.effect.AfflictionValueEffect;
import syrenyx.distantmoons.references.RegistryKeys;

public class Registries {

  public static void initialize() {
    DynamicRegistries.registerSynced(RegistryKeys.AFFLICTION, Affliction.CODEC);
  }

  public static final SimpleRegistry<ComponentType<?>> AFFLICTION_EFFECT_COMPONENT_TYPE = FabricRegistryBuilder.createSimple(RegistryKeys.AFFLICTION_EFFECT_COMPONENT_TYPE).buildAndRegister();
  public static final SimpleRegistry<MapCodec<? extends AfflictionEntityEffect>> AFFLICTION_ENTITY_EFFECT_TYPE = FabricRegistryBuilder.createSimple(RegistryKeys.AFFLICTION_ENTITY_EFFECT_TYPE).buildAndRegister();
  public static final SimpleRegistry<MapCodec<? extends AfflictionLocationBasedEffect>> AFFLICTION_LOCATION_BASED_EFFECT_TYPE = FabricRegistryBuilder.createSimple(RegistryKeys.AFFLICTION_LOCATION_BASED_EFFECT_TYPE).buildAndRegister();
  public static final SimpleRegistry<MapCodec<? extends AfflictionValueEffect>> AFFLICTION_VALUE_EFFECT_TYPE = FabricRegistryBuilder.createSimple(RegistryKeys.AFFLICTION_VALUE_EFFECT_TYPE).buildAndRegister();
}
