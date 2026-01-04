package syrenyx.distantmoons.content.enchantment.level_based_value;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record Direct() implements LevelBasedValue {

  public static final MapCodec<Direct> CODEC = MapCodec.unit(Direct::new);

  @Override
  public MapCodec<? extends LevelBasedValue> codec() {
    return CODEC;
  }

  @Override
  public float calculate(int level) {
    return level;
  }
}
