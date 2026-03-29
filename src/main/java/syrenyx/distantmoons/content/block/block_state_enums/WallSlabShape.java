package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum WallSlabShape implements StringRepresentable {

  FLAT("flat", false),
  INNER_LEFT("inner_left", true),
  INNER_RIGHT("inner_right", true),
  OUTER_LEFT("outer_left", false),
  OUTER_RIGHT("outer_right", false),
  DOUBLE("double", true);
  private final String id;
  private final boolean allSidesPartiallySturdy;

  WallSlabShape(final String id, final boolean allSidesPartiallySturdy) {
    this.id = id;
    this.allSidesPartiallySturdy = allSidesPartiallySturdy;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }

  public boolean allSidesPartiallySturdy() {
    return this.allSidesPartiallySturdy;
  }

  public WallSlabShape mirror() {
    return switch (this) {
      case INNER_LEFT -> INNER_RIGHT;
      case INNER_RIGHT -> INNER_LEFT;
      case OUTER_LEFT -> OUTER_RIGHT;
      case OUTER_RIGHT -> OUTER_LEFT;
      default -> this;
    };
  }
}
