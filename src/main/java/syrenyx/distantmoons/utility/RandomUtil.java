package syrenyx.distantmoons.utility;

import net.minecraft.util.math.random.Random;

public abstract class RandomUtil {

  public static float nextFloatBetween(int min, int max, Random random) {
    return random.nextFloat() * (max - min) + min;
  }

  public static float nextFloatBetween(float min, float max, Random random) {
    return random.nextFloat() * (max - min) + min;
  }
}
