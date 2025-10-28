package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.util.StringIdentifiable;

public enum SpikedFenceShape implements StringIdentifiable {
  NONE("none"),
  SIDE("side"),
  TOP("top");

  private final String id;

  SpikedFenceShape(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
