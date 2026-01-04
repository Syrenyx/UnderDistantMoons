package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;

public enum HorizontalAxis implements StringRepresentable {
  X("x"),
  Z("z");

  private final String id;

  HorizontalAxis(final String id) {
    this.id = id;
  }

  @Override
  public String getSerializedName() {
    return this.id;
  }

  public HorizontalAxis opposite() {
    return this == HorizontalAxis.X ? Z : X;
  }

  @Nullable
  public static HorizontalAxis fromDirectionAxis(Direction.Axis axis) {
    return switch (axis) {
      case X -> X;
      case Y -> null;
      case Z -> Z;
    };
  }
}
