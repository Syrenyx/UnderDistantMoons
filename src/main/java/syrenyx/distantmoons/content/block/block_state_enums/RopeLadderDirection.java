package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public enum RopeLadderDirection implements StringIdentifiable {
  NORTH("north", false),
  EAST("east", false),
  SOUTH("south", false),
  WEST("west", false),
  X("x", true),
  Z("z", true);

  private final String id;
  private final boolean center;

  RopeLadderDirection(final String id, final boolean center) {
    this.id = id;
    this.center = center;
  }

  @Override
  public String asString() {
    return this.id;
  }

  public boolean isCenter() {
    return this.center;
  }

  @Nullable
  public Direction getDirection() {
    return switch (this) {
      case NORTH -> Direction.NORTH;
      case EAST -> Direction.EAST;
      case SOUTH -> Direction.SOUTH;
      case WEST -> Direction.WEST;
      default -> null;
    };
  }

  @Nullable
  public static RopeLadderDirection fromAxis(Direction.Axis axis) {
    return switch (axis) {
      case X -> X;
      case Z -> Z;
      default -> null;
    };
  }

  @Nullable
  public static RopeLadderDirection fromDirection(Direction direction) {
    return switch (direction) {
      case NORTH -> NORTH;
      case EAST -> EAST;
      case SOUTH -> SOUTH;
      case WEST -> WEST;
      default -> null;
    };
  }

  public RopeLadderDirection rotate(BlockRotation rotation) {
    return switch (rotation) {
      case NONE -> this;
      case CLOCKWISE_90 -> switch (this) {
        case NORTH -> EAST;
        case EAST -> SOUTH;
        case SOUTH -> WEST;
        case WEST -> NORTH;
        case X -> Z;
        case Z -> X;
      };
      case CLOCKWISE_180 -> switch (this) {
        case NORTH -> SOUTH;
        case EAST -> WEST;
        case SOUTH -> NORTH;
        case WEST -> EAST;
        default ->  this;
      };
      case COUNTERCLOCKWISE_90 -> switch (this) {
        case NORTH -> WEST;
        case EAST -> NORTH;
        case SOUTH -> EAST;
        case WEST -> SOUTH;
        case X -> Z;
        case Z -> X;
      };
    };
  }

  public RopeLadderDirection mirror(BlockMirror mirror) {
    return switch (mirror) {
      case NONE -> this;
      case LEFT_RIGHT -> switch (this) {
        case NORTH -> SOUTH;
        case SOUTH -> NORTH;
        default -> this;
      };
      case FRONT_BACK -> switch (this) {
        case EAST -> WEST;
        case WEST -> EAST;
        default -> this;
      };
    };
  }
}
