package syrenyx.distantmoons.utility;

import static net.minecraft.util.Mth.normal;

import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public abstract class VectorUtil {

  public static final Vec3 HALF_VECTOR = new Vec3(0.5, 0.5, 0.5);

  public static Vec3 randomCrossProductVector(RandomSource randomSource, Vec3 vector, double length) {
    Vec3 crossProduct = vector.cross(randomGaussianVector(randomSource));
    if (crossProduct == Vec3.ZERO) {
      crossProduct = Vec3.Y_AXIS.cross(vector);
      if (crossProduct == Vec3.ZERO) crossProduct = Vec3.X_AXIS.cross(vector);
    }
    return crossProduct.normalize().scale(length);
  }

  public static Vec3 randomGaussianVector(RandomSource randomSource) {
    return new Vec3(normal(randomSource, 0, 1), normal(randomSource, 0, 1), normal(randomSource, 0, 1));
  }

  public static Vec3 randomOffsetVector(RandomSource randomSource, Vec3 vector, double offset) {
    double length = vector.length();
    return vector.add(randomCrossProductVector(randomSource, vector, offset)).normalize().scale(length);
  }

  public static Vec3 randomPointOnSphere(RandomSource randomSource, float radius) {
    Vec3 vector = randomGaussianVector(randomSource);
    if (vector.x == 0 && vector.y == 0 && vector.z == 0) return new Vec3(0, 1, 0);
    return vector.normalize().scale(radius);
  }

  public static Vec3 mirrorVec3AlongAxis(Vec3 vector, Vec3 axis) {
    Vec3 normalAxis = axis.normalize();
    return vector.scale(-1).add(normalAxis.scale(2 * vector.dot(normalAxis)));
  }

  public static Vec3i toVec3i(Vec3 vector) {
    return new Vec3i((int) vector.x(), (int) vector.y(), (int) vector.z());
  }
}
