package syrenyx.distantmoons.content.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.utility.VectorUtil;

public class UnderworldParticle extends SingleQuadParticle {

  private static final int LIFETIME = 200;
  private static final int MAX_LIGHT_COLOR = 150;
  private static final float INITIAL_MOMENTUM = 0.05F;

  private final Vec3 anchor;
  private final double radius;
  private final int color;
  private boolean initiatedMomentum = false;

  protected UnderworldParticle(
      ClientLevel clientLevel,
      double x, double y, double z,
      Vec3 anchor,
      int color,
      SpriteSet spriteSet
  ) {
    super(clientLevel, x, y, z, spriteSet.first());
    this.alpha = 0.0F;
    this.anchor = anchor;
    this.color = color;
    this.hasPhysics = false;
    this.lifetime = LIFETIME;
    this.radius = anchor.distanceTo(new Vec3(x, y, z));
    this.rCol = (float) (color >> 16 & 255) / 255.0F;
    this.gCol = (float) (color >> 8 & 255) / 255.0F;
    this.bCol = (float) (color & 255) / 255.0F;
    this.setSprite(spriteSet.get(this.random));
  }

  @Override
  protected @NonNull Layer getLayer() {
    return Layer.TRANSLUCENT;
  }

  @Override
  protected int getLightColor(float tint) {
    return Math.max(MAX_LIGHT_COLOR, super.getLightColor(tint));
  }

  @Override
  public void tick() {

    //SPAWN FADING TRAIL
    if (this.age % 4 == 0 && this.alpha >= 0.5F) {
      this.level.addParticle(
          new FadingTrailOptions(this.color, 50),
          this.x, this.y, this.z,
          0, 0, 0
      );
    }

    //CHANGE ALPHA
    if (this.age++ >= this.lifetime) {
      this.alpha -= 0.02F;
      if (this.alpha <= 0.0F) {
        this.remove();
        return;
      }
    } else if (this.alpha <= 1.0F) {
      this.alpha += 0.02F;
      if (this.alpha > 1.0F) this.alpha = 1.0F;
    }

    //DETERMINE NEXT POSITION
    Vec3 currentPosition = new Vec3(this.x, this.y, this.z);
    Vec3 nextPosition;
    if (this.initiatedMomentum) {
      nextPosition = VectorUtil
          .mirrorVec3AlongAxis(new Vec3(this.xo, this.yo, this.zo).subtract(this.anchor), currentPosition.subtract(this.anchor))
          .add(this.anchor);
    } else {
      this.initiatedMomentum = true;
      nextPosition = currentPosition
          .subtract(this.anchor)
          .add(VectorUtil.randomCrossProductVector(this.random, currentPosition.subtract(this.anchor), INITIAL_MOMENTUM))
          .normalize()
          .scale(this.radius)
          .add(this.anchor);
    }

    //MOVE
    this.xo = this.x;
    this.yo = this.y;
    this.zo = this.z;
    this.move(nextPosition.x() - this.x, nextPosition.y() - this.y, nextPosition.z() - this.z);
  }

  public static class Factory implements ParticleProvider<UnderworldParticleOptions> {

    private final SpriteSet spriteSet;

    public Factory(SpriteSet spriteSet) {
      this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(
        UnderworldParticleOptions particleOptions,
        @NonNull ClientLevel clientLevel,
        double x, double y, double z,
        double velocityX, double velocityY, double velocityZ,
        @NonNull RandomSource randomSource
    ) {
      return new UnderworldParticle(
          clientLevel,
          x, y, z,
          particleOptions.anchor(),
          particleOptions.color(),
          this.spriteSet
      );
    }
  }
}
