package syrenyx.distantmoons.block.block_state_enums;

import net.minecraft.util.StringIdentifiable;

public enum FixedLadderSideShape implements StringIdentifiable {
  NONE("none"),
  ATTACHED("attached"),
  CONNECTED("connected");

  private final String id;

  FixedLadderSideShape(String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
