package syrenyx.distantmoons.initializers;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.enchantment.effect.ChangeAfflictionEffect;

public class EnchantmentEntityEffects {

  public static void initialize() {
    Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, UnderDistantMoons.identifierOf("change_affliction_progress"), ChangeAfflictionEffect.CODEC);
  }
}
