package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum BalustradeSideState implements StringRepresentable {
  NONE("none", false, false),
  BOTTOM_CAPPED("bottom_capped", true, false),
  DOUBLE_CAPPED("double_capped", true, true),
  TOP_CAPPED("top_capped", false, true),
  UNCAPPED("uncapped", false, false);

  private final String id;
  private final boolean bottomCapped;
  private final boolean topCapped;

  BalustradeSideState(final String id, final boolean bottomCapped, final boolean topCapped) {
    this.id = id;
    this.bottomCapped = bottomCapped;
    this.topCapped = topCapped;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }

  public boolean bottomCapped() {
    return this.bottomCapped;
  }

  public boolean topCapped() {
    return this.topCapped;
  }
}
