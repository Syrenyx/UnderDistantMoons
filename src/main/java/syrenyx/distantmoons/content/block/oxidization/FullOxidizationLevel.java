package syrenyx.distantmoons.content.block.oxidization;

import net.minecraft.block.MapColor;
import net.minecraft.util.StringIdentifiable;

public enum FullOxidizationLevel implements StringIdentifiable {
  UNAFFECTED("unaffected"),
  EXPOSED("exposed"),
  WEATHERED("weathered"),
  OXIDIZED("oxidized"),
  WAXED("waxed"),
  WAXED_EXPOSED("waxed_exposed"),
  WAXED_WEATHERED("waxed_weathered"),
  WAXED_OXIDIZED("waxed_oxidized");
  private final String id;

  FullOxidizationLevel(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }

  public String idPrefix(boolean rust) {
    return switch (this) {
      case UNAFFECTED -> "";
      case OXIDIZED -> rust ? "rusted_" : this.id;
      case WAXED_OXIDIZED -> rust ? "waxed_rusted_" : this.id;
      default -> this.id;
    };
  }

  public MapColor getMapColor(boolean rust) {
    return switch (this) {
      case UNAFFECTED, WAXED -> rust ? MapColor.IRON_GRAY : MapColor.ORANGE;
      case EXPOSED, WAXED_EXPOSED -> MapColor.TERRACOTTA_LIGHT_GRAY;
      case WEATHERED, WAXED_WEATHERED -> rust ? MapColor.DIRT_BROWN : MapColor.DARK_AQUA;
      case OXIDIZED, WAXED_OXIDIZED -> rust ? MapColor.BROWN : MapColor.TEAL;
    };
  }
}
