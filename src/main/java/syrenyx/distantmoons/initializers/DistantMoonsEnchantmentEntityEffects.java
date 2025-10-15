package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.enchantment.effect.entity.ChangeAfflictionEffect;

public abstract class DistantMoonsEnchantmentEntityEffects {

  static {
    register("change_affliction", ChangeAfflictionEffect.CODEC);
  }

  private static <T extends EnchantmentEntityEffect> void register(String id, MapCodec<T> codec) {
    Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
