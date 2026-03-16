package syrenyx.distantmoons.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public abstract class BlockUtil {

  public static boolean isStairBlockFacePartiallySturdy(BlockState state, Direction direction) {
    if (direction.getAxis() == Direction.Axis.Y) return true;
    StairsShape shape = state.getValue(StairBlock.SHAPE);
    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) return true;
    Direction facing = state.getValue(StairBlock.FACING);
    if (shape == StairsShape.STRAIGHT) return facing != direction.getOpposite();
    if (facing == direction) return true;
    if (shape == StairsShape.OUTER_LEFT) return facing == direction.getClockWise();
    else return facing == direction.getCounterClockWise();
  }

  public static Direction getClosestBlockEdge(Vec3 location, BlockPos blockPos, Direction.Axis axis) {
    return getClosestBlockEdge(location.subtract(Vec3.atCenterOf(blockPos)).subtract(VectorUtil.HALF_VECTOR), axis);
  }

  public static Direction getClosestBlockEdge(Vec3 pos, Direction.Axis axis) {
    return switch (axis) {
      case X -> {
        if (Math.abs(pos.z) > Math.abs(pos.y)) yield pos.z > 0 ? Direction.SOUTH : Direction.NORTH;
        else yield pos.y > 0 ? Direction.UP : Direction.DOWN;
      }
      case Y -> {
        if (Math.abs(pos.x) > Math.abs(pos.z)) yield pos.z > 0 ? Direction.EAST : Direction.WEST;
        else yield pos.y > 0 ? Direction.SOUTH : Direction.NORTH;
      }
      case Z -> {
        if (Math.abs(pos.x) > Math.abs(pos.y)) yield pos.z > 0 ? Direction.EAST : Direction.WEST;
        else yield pos.y > 0 ? Direction.UP : Direction.DOWN;
      }
    };
  }

  public static List<Direction> getBlockEdgesByCloseness(Vec3 location, BlockPos blockPos, Direction.Axis axis) {
    return getBlockEdgesByCloseness(location.subtract(Vec3.atLowerCornerOf(blockPos)).subtract(VectorUtil.HALF_VECTOR), axis);
  }

  public static List<Direction> getBlockEdgesByCloseness(Vec3 pos, Direction.Axis axis) {
    List<Direction> edges = new ArrayList<>();
    switch (axis) {
      case X -> {
        edges.add(pos.z > 0 ? Direction.SOUTH : Direction.NORTH);
        edges.add(pos.y > 0 ? Direction.UP : Direction.DOWN);
        if (Math.abs(pos.z) < Math.abs(pos.y)) Collections.reverse(edges);
      }
      case Y -> {
        edges.add(pos.x > 0 ? Direction.EAST : Direction.WEST);
        edges.add(pos.z > 0 ? Direction.SOUTH : Direction.NORTH);
        if (Math.abs(pos.x) < Math.abs(pos.z)) Collections.reverse(edges);
      }
      case Z -> {
        edges.add(pos.x > 0 ? Direction.EAST : Direction.WEST);
        edges.add(pos.y > 0 ? Direction.UP : Direction.DOWN);
        if (Math.abs(pos.x) < Math.abs(pos.y)) Collections.reverse(edges);
      }
    }
    return edges;
  }
}
