package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.effect.entity.SpawnParticlesEnchantmentEffect;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.random.Random;

public record SpawnParticlesEffect(
    ParticleEffect particle,
    SpawnParticlesEnchantmentEffect.PositionSource horizontalPosition,
    SpawnParticlesEnchantmentEffect.PositionSource verticalPosition,
    SpawnParticlesEnchantmentEffect.VelocitySource horizontalVelocity,
    SpawnParticlesEnchantmentEffect.VelocitySource verticalVelocity,
    FloatProvider speed
) implements AfflictionEntityEffect {

  public static final MapCodec<SpawnParticlesEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          ParticleTypes.TYPE_CODEC.fieldOf("particle").forGetter(SpawnParticlesEffect::particle),
          SpawnParticlesEnchantmentEffect.PositionSource.CODEC.fieldOf("horizontal_position").forGetter(SpawnParticlesEffect::horizontalPosition),
          SpawnParticlesEnchantmentEffect.PositionSource.CODEC.fieldOf("vertical_position").forGetter(SpawnParticlesEffect::verticalPosition),
          SpawnParticlesEnchantmentEffect.VelocitySource.CODEC.fieldOf("horizontal_velocity").forGetter(SpawnParticlesEffect::horizontalVelocity),
          SpawnParticlesEnchantmentEffect.VelocitySource.CODEC.fieldOf("vertical_velocity").forGetter(SpawnParticlesEffect::verticalVelocity),
          FloatProvider.VALUE_CODEC.optionalFieldOf("speed", ConstantFloatProvider.ZERO).forGetter(SpawnParticlesEffect::speed)
      )
      .apply(instance, SpawnParticlesEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    Random random = target.getRandom();
    Vec3d movement = target.getMovement();
    float width = target.getWidth();
    float height = target.getHeight();
    world.spawnParticles(
        this.particle,
        this.horizontalPosition.getPosition(pos.getX(), pos.getX(), width, random),
        this.verticalPosition.getPosition(pos.getY(), pos.getY() + height / 2.0F, height, random),
        this.horizontalPosition.getPosition(pos.getZ(), pos.getZ(), width, random),
        0,
        this.horizontalVelocity.getVelocity(movement.getX(), random),
        this.verticalVelocity.getVelocity(movement.getY(), random),
        this.horizontalVelocity.getVelocity(movement.getZ(), random),
        this.speed.get(random)
    );
  }
}
