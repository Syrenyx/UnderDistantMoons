package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.enchantment.level_based_value.Direct;

public abstract class DistantMoonsEnchantmentLevelBasedValueTypes {

  static {
    register("direct", Direct.CODEC);
  }

  private static void register(String id, MapCodec<? extends EnchantmentLevelBasedValue> codec) {
    Registry.register(Registries.ENCHANTMENT_LEVEL_BASED_VALUE_TYPE, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
