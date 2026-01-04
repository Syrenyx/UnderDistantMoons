package syrenyx.distantmoons.utility;

import java.util.List;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public abstract class ColorUtil {

  public static final List<DyeColor> SORTED_DYE_COLORS = List.of(
      DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
      DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
      DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
      DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
  );

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
