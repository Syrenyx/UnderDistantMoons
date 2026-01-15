package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.utility.BlockUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BlockCorner implements StringRepresentable {
  TOP_NORTH_EAST("top_north_east", true, true, true),
  TOP_NORTH_WEST("top_north_west", true, true, false),
  TOP_SOUTH_EAST("top_south_east", true, false, true),
  TOP_SOUTH_WEST("top_south_west", true, false, false),
  BOTTOM_NORTH_EAST("bottom_north_east", false, true, true),
  BOTTOM_NORTH_WEST("bottom_north_west", false, true, false),
  BOTTOM_SOUTH_EAST("bottom_south_east", false, false, true),
  BOTTOM_SOUTH_WEST("bottom_south_west", false, false, false);

  private final String id;
  private final boolean top;
  private final boolean north;
  private final boolean east;

  BlockCorner(final String id, final boolean top, final boolean north, final boolean east) {
    this.id = id;
    this.top = top;
    this.north = north;
    this.east = east;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }

  public static @NonNull BlockCorner getFrom(boolean top, boolean north, boolean east) {
    if (top) {
      if (north) {
        if (east) return TOP_NORTH_EAST;
        else return TOP_NORTH_WEST;
      } else {
        if (east) return TOP_SOUTH_EAST;
        else return TOP_SOUTH_WEST;
      }
    } else {
      if (north) {
        if (east) return BOTTOM_NORTH_EAST;
        else return BOTTOM_NORTH_WEST;
      } else {
        if (east) return BOTTOM_SOUTH_EAST;
        else return BOTTOM_SOUTH_WEST;
      }
    }
  }

  public static @NonNull BlockCorner getFrom(@NonNull BlockPlaceContext context) {
    Direction clickedFace = context.getClickedFace();
    return getFrom(clickedFace, BlockUtil.getBlockEdgesByCloseness(context.getClickLocation(), context.getClickedPos(), clickedFace.getAxis()));
  }

  public static @NonNull BlockCorner getFrom(@NonNull Direction clickedFace, List<Direction> closestEdges) {
    boolean top = closestEdges.contains(Direction.DOWN);
    boolean north = closestEdges.contains(Direction.SOUTH);
    boolean east = closestEdges.contains(Direction.WEST);
    return switch (clickedFace) {
      case NORTH -> getFrom(top, false, east);
      case EAST -> getFrom(top, north, false);
      case SOUTH -> getFrom(top, true, east);
      case WEST -> getFrom(top, north, true);
      case UP -> getFrom(false, north, east);
      case DOWN -> getFrom(true, north, east);
    };
  }

  public @NonNull Map<BlockPos, BlockCorner> getCornersForPositionsInBlock(BlockPos pos) {
    BlockPos topNorthEastPos = pos.offset(this.east ? 0 : 1, this.top ? 0 : 1, this.north ? 0 : -1);
    Map<BlockPos, BlockCorner> positions = new HashMap<>(Map.of(topNorthEastPos, TOP_NORTH_EAST));
    positions.put(topNorthEastPos.offset(-1, 0, 0), TOP_NORTH_WEST);
    positions.put(topNorthEastPos.offset(0, 0, 1), TOP_SOUTH_EAST);
    positions.put(topNorthEastPos.offset(-1, 0, 1), TOP_SOUTH_WEST);
    positions.put(topNorthEastPos.offset(0, -1, 0), BOTTOM_NORTH_EAST);
    positions.put(topNorthEastPos.offset(-1, -1, 0), BOTTOM_NORTH_WEST);
    positions.put(topNorthEastPos.offset(0, -1, 1), BOTTOM_SOUTH_EAST);
    positions.put(topNorthEastPos.offset(-1, -1, 1), BOTTOM_SOUTH_WEST);
    return positions;
  }
}
