package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.util.StringRepresentable;

public enum WallSlabShape implements StringRepresentable {

  FLAT("flat"),
  INNER_LEFT("inner_left"),
  INNER_RIGHT("inner_right"),
  OUTER_LEFT("outer_left"),
  OUTER_RIGHT("outer_right"),
  DOUBLE("double");
  private final String id;

  WallSlabShape(final String id) {
    this.id = id;
  }

  @Override
  public String getSerializedName() {
    return this.id;
  }
}
