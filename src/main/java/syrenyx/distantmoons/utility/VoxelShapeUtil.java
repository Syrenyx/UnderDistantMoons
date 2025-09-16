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

  public static Map<Direction, VoxelShape> createHorizontalDirectionShapeMap(VoxelShape shape) {
    Vec3d anchor = new Vec3d(0.5, 0.5, 0.5);
    return Maps.newEnumMap(
        Map.of(
            Direction.NORTH, shape,
            Direction.EAST, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R90), anchor),
            Direction.SOUTH, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R180), anchor),
            Direction.WEST, VoxelShapes.transform(shape, DirectionTransformation.fromRotations(AxisRotation.R0, AxisRotation.R270), anchor)
        )
    );
  }
}
