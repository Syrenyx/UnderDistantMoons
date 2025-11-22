package syrenyx.distantmoons.utility;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;

import static net.minecraft.util.math.MathHelper.nextGaussian;

public abstract class VectorUtil {

  public static Vec3d randomCrossProductVector(Random random, Vec3d vector, double length) {
    Vec3d crossProduct = vector.crossProduct(randomGaussianVector(random));
    if (crossProduct == Vec3d.ZERO) {
      crossProduct = Vec3d.Y.crossProduct(vector);
      if (crossProduct == Vec3d.ZERO) crossProduct = Vec3d.X.crossProduct(vector);
    }
    return crossProduct.normalize().multiply(length);
  }

  public static Vec3d randomGaussianVector(Random random) {
    return new Vec3d(nextGaussian(random, 0, 1), nextGaussian(random, 0, 1), nextGaussian(random, 0, 1));
  }

  public static Vec3d randomOffsetVector(Random random, Vec3d vector, double offset) {
    double length = vector.length();
    return vector.add(randomCrossProductVector(random, vector, offset)).normalize().multiply(length);
  }

  public static Vec3d randomPointOnSphere(Random random, float radius) {
    Vec3d vector = randomGaussianVector(random);
    if (vector.x == 0 && vector.y == 0 && vector.z == 0) return new Vec3d(0, 1, 0);
    return vector.normalize().multiply(radius);
  }

  public static Vec3d mirrorVec3dAlongAxis(Vec3d vector, Vec3d axis) {
    Vec3d normalAxis = axis.normalize();
    return vector.multiply(-1).add(normalAxis.multiply(2 * vector.dotProduct(normalAxis)));
  }

  public static Vec3i toVec3i(Vec3d vector) {
    return new Vec3i((int) vector.getX(), (int) vector.getY(), (int) vector.getZ());
  }
}
