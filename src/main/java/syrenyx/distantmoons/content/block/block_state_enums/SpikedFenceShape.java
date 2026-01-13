package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum SpikedFenceShape implements StringRepresentable {
  NONE("none"),
  SIDE("side"),
  TOP("top");

  private final String id;

  SpikedFenceShape(final String id) {
    this.id = id;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }
}
