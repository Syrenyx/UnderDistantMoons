package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.enchantment.effect.entity.ChangeAfflictionEffect;

public abstract class DistantMoonsEnchantmentEntityEffects {

  static {
    register("change_affliction", ChangeAfflictionEffect.CODEC);
  }

  private static <T extends EnchantmentEntityEffect> void register(String id, MapCodec<T> codec) {
    Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
