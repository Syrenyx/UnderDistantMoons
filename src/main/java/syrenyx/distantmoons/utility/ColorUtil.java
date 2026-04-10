package syrenyx.distantmoons.utility;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ARGB;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.MapColor;

public abstract class ColorUtil {

  public static final int MOSS_COLOR = ARGB.opaque(8756797);
  public static final int PALE_MOSS_COLOR = ARGB.opaque(7832178);
  public static final int UNTINTED = -1;

  public static final List<DyeColor> SORTED_DYE_COLORS = List.of(
      DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
      DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
      DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
      DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
  );
  public static final Map<DyeColor, MapColor> DEFAULT_MAP_COLORS;
  public static final Map<DyeColor, MapColor> TERRACOTTA_MAP_COLORS = Map.ofEntries(
      Map.entry(DyeColor.WHITE, MapColor.TERRACOTTA_WHITE),
      Map.entry(DyeColor.LIGHT_GRAY, MapColor.TERRACOTTA_LIGHT_GRAY),
      Map.entry(DyeColor.GRAY, MapColor.TERRACOTTA_GRAY),
      Map.entry(DyeColor.BLACK, MapColor.TERRACOTTA_BLACK),
      Map.entry(DyeColor.BROWN, MapColor.TERRACOTTA_BROWN),
      Map.entry(DyeColor.RED, MapColor.TERRACOTTA_RED),
      Map.entry(DyeColor.ORANGE, MapColor.TERRACOTTA_ORANGE),
      Map.entry(DyeColor.YELLOW, MapColor.TERRACOTTA_YELLOW),
      Map.entry(DyeColor.LIME, MapColor.TERRACOTTA_LIGHT_GREEN),
      Map.entry(DyeColor.GREEN, MapColor.TERRACOTTA_GREEN),
      Map.entry(DyeColor.CYAN, MapColor.TERRACOTTA_CYAN),
      Map.entry(DyeColor.LIGHT_BLUE, MapColor.TERRACOTTA_LIGHT_BLUE),
      Map.entry(DyeColor.BLUE, MapColor.TERRACOTTA_BLUE),
      Map.entry(DyeColor.PURPLE, MapColor.TERRACOTTA_PURPLE),
      Map.entry(DyeColor.MAGENTA, MapColor.TERRACOTTA_MAGENTA),
      Map.entry(DyeColor.PINK, MapColor.TERRACOTTA_PINK)
  );

  static {
    final Map<DyeColor, MapColor> colors = new HashMap<>();
    SORTED_DYE_COLORS.forEach(dyeColor -> colors.put(dyeColor, dyeColor.getMapColor()));
    DEFAULT_MAP_COLORS = Collections.unmodifiableMap(colors);
  }

  public static Item getDyeItemByColor(DyeColor color) {
    return switch (color) {
      case WHITE -> Items.WHITE_DYE;
      case ORANGE -> Items.ORANGE_DYE;
      case MAGENTA -> Items.MAGENTA_DYE;
      case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
      case YELLOW -> Items.YELLOW_DYE;
      case LIME -> Items.LIME_DYE;
      case PINK -> Items.PINK_DYE;
      case GRAY -> Items.GRAY_DYE;
      case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
      case CYAN -> Items.CYAN_DYE;
      case PURPLE -> Items.PURPLE_DYE;
      case BLUE -> Items.BLUE_DYE;
      case BROWN -> Items.BROWN_DYE;
      case GREEN -> Items.GREEN_DYE;
      case RED -> Items.RED_DYE;
      case BLACK -> Items.BLACK_DYE;
    };
  }
}
