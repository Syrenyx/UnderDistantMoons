package syrenyx.distantmoons.utility;

import static net.minecraft.util.Mth.normal;

import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public abstract class VectorUtil {

  public static Vec3 randomCrossProductVector(RandomSource random, Vec3 vector, double length) {
    Vec3 crossProduct = vector.cross(randomGaussianVector(random));
    if (crossProduct == Vec3.ZERO) {
      crossProduct = Vec3.Y_AXIS.cross(vector);
      if (crossProduct == Vec3.ZERO) crossProduct = Vec3.X_AXIS.cross(vector);
    }
    return crossProduct.normalize().scale(length);
  }

  public static Vec3 randomGaussianVector(RandomSource random) {
    return new Vec3(normal(random, 0, 1), normal(random, 0, 1), normal(random, 0, 1));
  }

  public static Vec3 randomOffsetVector(RandomSource random, Vec3 vector, double offset) {
    double length = vector.length();
    return vector.add(randomCrossProductVector(random, vector, offset)).normalize().scale(length);
  }

  public static Vec3 randomPointOnSphere(RandomSource random, float radius) {
    Vec3 vector = randomGaussianVector(random);
    if (vector.x == 0 && vector.y == 0 && vector.z == 0) return new Vec3(0, 1, 0);
    return vector.normalize().scale(radius);
  }

  public static Vec3 mirrorVec3dAlongAxis(Vec3 vector, Vec3 axis) {
    Vec3 normalAxis = axis.normalize();
    return vector.scale(-1).add(normalAxis.scale(2 * vector.dot(normalAxis)));
  }

  public static Vec3i toVec3i(Vec3 vector) {
    return new Vec3i((int) vector.x(), (int) vector.y(), (int) vector.z());
  }
}
