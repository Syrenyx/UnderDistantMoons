package syrenyx.distantmoons.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class SparkParticle extends BillboardParticle {

  private final SpriteProvider spriteProvider;

  protected SparkParticle(
      ClientWorld world,
      double x, double y, double z,
      double velocityX, double velocityY, double velocityZ,
      SpriteProvider spriteProvider
  ) {
    super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider.getFirst());
    this.velocityMultiplier = 0.96F;
    this.ascending = true;
    this.spriteProvider = spriteProvider;
    this.scale *= 0.75F;
    this.collidesWithWorld = false;
    this.updateSprite(spriteProvider);
  }

  @Override
  public BillboardParticle.RenderType getRenderType() {
    return BillboardParticle.RenderType.PARTICLE_ATLAS_TRANSLUCENT;
  }

  @Override
  public int getBrightness(float tint) {
    float f = (this.age + tint) / this.maxAge;
    f = MathHelper.clamp(f, 0.0F, 1.0F);
    int i = super.getBrightness(tint);
    int j = i & 0xFF;
    int k = i >> 16 & 0xFF;
    j += (int) (f * 15.0F * 16.0F);
    if (j > 240) {
      j = 240;
    }
    return j | k << 16;
  }

  @Override
  public void tick() {
    super.tick();
    this.updateSprite(this.spriteProvider);
  }

  public static class ScrapeRustFactory implements ParticleFactory<SimpleParticleType> {
    private static final double VELOCITY_FACTOR = 0.01;
    private static final int MIN_AGE = 10;
    private static final int MAX_AGE = 40;
    private final SpriteProvider spriteProvider;

    public ScrapeRustFactory(SpriteProvider spriteProvider) {
      this.spriteProvider = spriteProvider;
    }

    @Override
    public Particle createParticle(
        SimpleParticleType simpleParticleType,
        ClientWorld clientWorld,
        double x, double y, double z,
        double velocityX, double velocityY, double velocityZ,
        Random random
    ) {
      SparkParticle particle = new SparkParticle(clientWorld, x, y, z, 0.0, 0.0, 0.0, this.spriteProvider);
      if (random.nextBoolean()) particle.setColor(0.65F, 0.16F, 0.16F);
      else particle.setColor(1.0F, 0.5F, 0.3F);
      particle.setVelocity(velocityX * VELOCITY_FACTOR, velocityY * VELOCITY_FACTOR, velocityZ * VELOCITY_FACTOR);
      particle.setMaxAge(random.nextInt(MAX_AGE - MIN_AGE) + MIN_AGE);
      return particle;
    }
  }
}
