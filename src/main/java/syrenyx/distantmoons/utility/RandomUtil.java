package syrenyx.distantmoons.utility;

import net.minecraft.util.RandomSource;

public abstract class RandomUtil {

  public static float nextFloatBetween(int min, int max, RandomSource random) {
    return random.nextFloat() * (max - min) + min;
  }

  public static float nextFloatBetween(float min, float max, RandomSource random) {
    return random.nextFloat() * (max - min) + min;
  }
}
