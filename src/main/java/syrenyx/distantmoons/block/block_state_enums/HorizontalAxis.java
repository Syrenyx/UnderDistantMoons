package syrenyx.distantmoons.block.block_state_enums;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public enum HorizontalAxis implements StringIdentifiable {
  X("x"),
  Z("z");

  private final String id;

  HorizontalAxis(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
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
