package syrenyx.distantmoons.content.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class FadingTrailParticle extends SingleQuadParticle {

  private static final float STARTING_ALPHA = 0.5F;
  private final float alphaReduction;

  protected FadingTrailParticle(
      ClientLevel clientLevel,
      double x, double y, double z,
      int color,
      int lifetime,
      SpriteSet spriteSet
  ) {
    super(clientLevel, x, y, z, spriteSet.first());
    this.lifetime = lifetime;
    this.alpha = STARTING_ALPHA;
    this.alphaReduction = STARTING_ALPHA / lifetime;
    this.rCol = (float) (color >> 16 & 255) / 255.0F;
    this.gCol = (float) (color >> 8 & 255) / 255.0F;
    this.bCol = (float) (color & 255) / 255.0F;
    this.setSprite(spriteSet.first());
  }

  @Override
  protected @NonNull Layer getLayer() {
    return Layer.TRANSLUCENT;
  }

  @Override
  public void tick() {
    super.tick();
    this.alpha -= alphaReduction;
  }

  public static class Factory implements ParticleProvider<FadingTrailOptions> {

    private final SpriteSet spriteSet;

    public Factory(SpriteSet spriteSet) {
      this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(
        FadingTrailOptions particleOptions,
        @NonNull ClientLevel clientLevel,
        double x, double y, double z,
        double velocityX, double velocityY, double velocityZ,
        @NonNull RandomSource randomSource
    ) {
      return new FadingTrailParticle(
          clientLevel,
          x, y, z,
          particleOptions.color(),
          particleOptions.duration(),
          this.spriteSet
      );
    }
  }
}
