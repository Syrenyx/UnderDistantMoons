package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum FixedLadderSideShape implements StringRepresentable {
  NONE("none"),
  ATTACHED("attached"),
  CONNECTED("connected");

  private final String id;

  FixedLadderSideShape(final String id) {
    this.id = id;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }
}
