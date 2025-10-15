package syrenyx.distantmoons.content.enchantment.level_based_value;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;

public record Direct() implements EnchantmentLevelBasedValue {

  public static final MapCodec<Direct> CODEC = MapCodec.unit(Direct::new);

  @Override
  public MapCodec<? extends EnchantmentLevelBasedValue> getCodec() {
    return CODEC;
  }

  @Override
  public float getValue(int level) {
    return level;
  }
}
