package syrenyx.distantmoons.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.util.TreeMap;

public abstract class EnchantmentLevelUtil {

  private static final TreeMap<Integer, String> map = new TreeMap<>();

  static {
    map.put(1000, "M");
    map.put(900, "CM");
    map.put(500, "D");
    map.put(400, "CD");
    map.put(100, "C");
    map.put(90, "XC");
    map.put(50, "L");
    map.put(40, "XL");
    map.put(10, "X");
    map.put(9, "IX");
    map.put(5, "V");
    map.put(4, "IV");
    map.put(1, "I");
  }

  public static void generateEnchantmentLevels(FabricLanguageProvider.TranslationBuilder builder, int from, int to) {
    for (int i = from; i <= to; i++) {
      builder.add("enchantment.level." + i, toRomanNumeral(i));
    }
  }

  private static String toRomanNumeral(int i) {
    int result = map.floorKey(i);
    if (i == result) return map.get(i);
    return map.get(result) + toRomanNumeral(i - result);
  }
}
