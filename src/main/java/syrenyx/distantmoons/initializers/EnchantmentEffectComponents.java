package syrenyx.distantmoons.initializers;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class EnchantmentEffectComponents {

  public static final ComponentType<List<EnchantmentEffectEntry<EnchantmentEntityEffect>>> USED_ITEM = register(
      "used_item", builder -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentEntityEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf())
  );

  private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
    return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, UnderDistantMoons.identifierOf(id), builderOperator.apply(ComponentType.builder()).build());
  }

  public static void initialize() {}
}
