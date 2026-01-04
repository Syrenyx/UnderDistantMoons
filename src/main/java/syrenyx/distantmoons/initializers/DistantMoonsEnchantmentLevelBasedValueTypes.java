package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.enchantment.level_based_value.Direct;

public abstract class DistantMoonsEnchantmentLevelBasedValueTypes {

  static {
    register("direct", Direct.CODEC);
  }

  private static void register(String id, MapCodec<? extends LevelBasedValue> codec) {
    Registry.register(BuiltInRegistries.ENCHANTMENT_LEVEL_BASED_VALUE_TYPE, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
