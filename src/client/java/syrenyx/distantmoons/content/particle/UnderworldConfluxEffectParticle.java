package syrenyx.distantmoons.content.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class UnderworldConfluxEffectParticle extends SimpleAnimatedParticle {

  private static final int LIFETIME = 120;

  private final Vec3 direction;
  private final Vec3 target;

  protected UnderworldConfluxEffectParticle(
      ClientLevel clientLevel,
      double x, double y, double z,
      float angle,
      boolean collision,
      int color,
      Vec3 target,
      SpriteSet spriteSet
  ) {
    super(clientLevel, x, y, z, spriteSet, 0.0F);
    this.target = target;
    this.direction = target.subtract(x, y, z).normalize();
    this.lifetime = LIFETIME;
    this.roll = angle;
    this.hasPhysics = collision;
    this.rCol = (float) (color >> 16 & 255) / 255.0F;
    this.gCol = (float) (color >> 8 & 255) / 255.0F;
    this.bCol = (float) (color & 255) / 255.0F;
    this.setSpriteFromAge(spriteSet);
  }

  @Override
  public void tick() {
    Vec3 position = new Vec3(this.x, this.y, this.z);
    double speed = position.distanceTo(this.target) / 10;
    Vec3 velocity = (this.hasPhysics ? this.target.subtract(position).normalize() : this.direction).scale(speed);
    this.xd = velocity.x();
    this.yd = velocity.y();
    this.zd = velocity.z();
    super.tick();
  }

  @Override
  public void extract(@NonNull QuadParticleRenderState quadParticleRenderState, @NonNull Camera camera, float tickProgress) {
    Quaternionf quaternion = new Quaternionf();
    this.getFacingCameraMode().setRotation(quaternion, camera, tickProgress);
    quaternion.rotateZ(this.roll);
    this.extractRotatedQuad(quadParticleRenderState, camera, quaternion, tickProgress);
  }

  public static class Factory implements ParticleProvider<UnderworldConfluxEffectOptions> {

    private final SpriteSet spriteSet;

    public Factory(SpriteSet spriteSet) {
      this.spriteSet = spriteSet;
    }

    @Override
    public @Nullable Particle createParticle(
        UnderworldConfluxEffectOptions particleOptions,
        @NonNull ClientLevel clientLevel,
        double x, double y, double z,
        double velocityX, double velocityY, double velocityZ,
        @NonNull RandomSource randomSource
    ) {
      return new UnderworldConfluxEffectParticle(
          clientLevel,
          x, y, z,
          particleOptions.angle(),
          particleOptions.collision(),
          particleOptions.color(),
          particleOptions.target(),
          this.spriteSet
      );
    }
  }
}
