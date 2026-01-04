package syrenyx.distantmoons.initializers;

import syrenyx.distantmoons.UnderDistantMoons;

import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public abstract class DistantMoonsEnchantmentEffectComponents {

  public static final DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>> USED_ITEM = register(
      "used_item", builder -> builder.persistent(ConditionalEffect.codec(EnchantmentEntityEffect.CODEC, DistantMoonsLootContextTypes.ENCHANTED_ITEM).listOf())
  );

  private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
    return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, UnderDistantMoons.identifierOf(id), builderOperator.apply(DataComponentType.builder()).build());
  }

  public static void initialize() {}
}
