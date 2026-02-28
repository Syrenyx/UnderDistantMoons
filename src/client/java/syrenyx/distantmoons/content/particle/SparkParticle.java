package syrenyx.distantmoons.content.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class SparkParticle extends SingleQuadParticle {

  private final SpriteSet spriteProvider;

  protected SparkParticle(
      ClientLevel level,
      double x, double y, double z,
      double velocityX, double velocityY, double velocityZ,
      SpriteSet spriteSet
  ) {
    super(level, x, y, z, velocityX, velocityY, velocityZ, spriteSet.first());
    this.friction = 0.96F;
    this.speedUpWhenYMotionIsBlocked = true;
    this.spriteProvider = spriteSet;
    this.quadSize *= 0.75F;
    this.hasPhysics = false;
    this.setSpriteFromAge(spriteSet);
  }

  @Override
  public SingleQuadParticle.@NonNull Layer getLayer() {
    return SingleQuadParticle.Layer.TRANSLUCENT;
  }

  @Override
  public int getLightColor(float tint) {
    float f = Mth.clamp((this.age + tint) / this.lifetime, 0.0F, 1.0F);
    int i = super.getLightColor(tint);
    int j = i & 0xFF;
    int k = i >> 16 & 0xFF;
    j += (int) (f * 15.0F * 16.0F);
    if (j > 240) j = 240;
    return j | k << 16;
  }

  @Override
  public void tick() {
    super.tick();
    this.setSpriteFromAge(this.spriteProvider);
  }

  public static class ScrapeRustFactory implements ParticleProvider<SimpleParticleType> {
    private static final double VELOCITY_FACTOR = 0.01;
    private static final int MIN_AGE = 10;
    private static final int MAX_AGE = 40;
    private final SpriteSet spriteProvider;

    public ScrapeRustFactory(SpriteSet spriteProvider) {
      this.spriteProvider = spriteProvider;
    }

    @Override
    public Particle createParticle(
        SimpleParticleType simpleParticleType,
        @NonNull ClientLevel clientLevel,
        double x, double y, double z,
        double velocityX, double velocityY, double velocityZ,
        RandomSource random
    ) {
      SparkParticle particle = new SparkParticle(clientLevel, x, y, z, 0.0, 0.0, 0.0, this.spriteProvider);
      if (random.nextBoolean()) particle.setColor(0.65F, 0.16F, 0.16F);
      else particle.setColor(1.0F, 0.5F, 0.3F);
      particle.setParticleSpeed(velocityX * VELOCITY_FACTOR, velocityY * VELOCITY_FACTOR, velocityZ * VELOCITY_FACTOR);
      particle.setLifetime(random.nextInt(MAX_AGE - MIN_AGE) + MIN_AGE);
      return particle;
    }
  }
}
