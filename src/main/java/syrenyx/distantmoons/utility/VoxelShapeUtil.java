package syrenyx.distantmoons.utility;

import com.google.common.collect.Maps;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.DirectionTransformation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Map;

public abstract class VoxelShapeUtil {

  public static final Vec3d BLOCK_CENTER_ANCHOR = new Vec3d(0.5, 0.5, 0.5);

  public static Map<Direction.Axis, VoxelShape> createAxisShapeMap(VoxelShape shape) {
    return Maps.newEnumMap(Map.of(
        Direction.Axis.X, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R90, AxisRotation.R90), BLOCK_CENTER_ANCHOR),
        Direction.Axis.Y, shape,
        Direction.Axis.Z, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R90, AxisRotation.R0), BLOCK_CENTER_ANCHOR)
    ));
  }

  public static Map<Direction, VoxelShape> createHorizontalDirectionShapeMap(VoxelShape shape) {
    return Maps.newEnumMap(Map.of(
        Direction.NORTH, shape,
        Direction.EAST, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R90), BLOCK_CENTER_ANCHOR),
        Direction.SOUTH, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R180), BLOCK_CENTER_ANCHOR),
        Direction.WEST, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R270), BLOCK_CENTER_ANCHOR)
    ));
  }
}
