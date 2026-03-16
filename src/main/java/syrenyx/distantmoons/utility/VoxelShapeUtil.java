package syrenyx.distantmoons.utility;

import com.google.common.collect.Maps;
import com.mojang.math.OctahedralGroup;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class VoxelShapeUtil {

  public static final Vec3 BLOCK_CENTER_ANCHOR = VectorUtil.HALF_VECTOR;

  public static Map<Direction.Axis, VoxelShape> createAxisShapeMap(VoxelShape shape) {
    return Maps.newEnumMap(Map.of(
        Direction.Axis.X, Shapes.rotate(shape, OctahedralGroup.ROT_90_Z_NEG, BLOCK_CENTER_ANCHOR),
        Direction.Axis.Y, shape,
        Direction.Axis.Z, Shapes.rotate(shape, OctahedralGroup.ROT_90_X_NEG, BLOCK_CENTER_ANCHOR)
    ));
  }

  public static Map<Direction, VoxelShape> createHorizontalDirectionShapeMap(VoxelShape shape) {
    return Maps.newEnumMap(Map.of(
        Direction.NORTH, shape,
        Direction.EAST, Shapes.rotate(shape, OctahedralGroup.ROT_90_Y_NEG, BLOCK_CENTER_ANCHOR),
        Direction.SOUTH, Shapes.rotate(shape, OctahedralGroup.ROT_180_FACE_XZ, BLOCK_CENTER_ANCHOR),
        Direction.WEST, Shapes.rotate(shape, OctahedralGroup.ROT_90_Y_POS, BLOCK_CENTER_ANCHOR)
    ));
  }
}
