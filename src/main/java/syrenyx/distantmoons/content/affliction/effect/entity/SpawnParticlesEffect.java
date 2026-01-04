package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record SpawnParticlesEffect(
    ParticleOptions particle,
    net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource horizontalPosition,
    net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource verticalPosition,
    net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource horizontalVelocity,
    net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource verticalVelocity,
    FloatProvider speed
) implements AfflictionEntityEffect {

  public static final MapCodec<SpawnParticlesEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          ParticleTypes.CODEC.fieldOf("particle").forGetter(SpawnParticlesEffect::particle),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource.CODEC.fieldOf("horizontal_position").forGetter(SpawnParticlesEffect::horizontalPosition),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource.CODEC.fieldOf("vertical_position").forGetter(SpawnParticlesEffect::verticalPosition),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource.CODEC.fieldOf("horizontal_velocity").forGetter(SpawnParticlesEffect::horizontalVelocity),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource.CODEC.fieldOf("vertical_velocity").forGetter(SpawnParticlesEffect::verticalVelocity),
          FloatProvider.CODEC.optionalFieldOf("speed", ConstantFloat.ZERO).forGetter(SpawnParticlesEffect::speed)
      )
      .apply(instance, SpawnParticlesEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    RandomSource random = target.getRandom();
    Vec3 movement = target.getKnownMovement();
    float width = target.getBbWidth();
    float height = target.getBbHeight();
    world.sendParticles(
        this.particle,
        this.horizontalPosition.getCoordinate(pos.x(), pos.x(), width, random),
        this.verticalPosition.getCoordinate(pos.y(), pos.y() + height / 2.0F, height, random),
        this.horizontalPosition.getCoordinate(pos.z(), pos.z(), width, random),
        0,
        this.horizontalVelocity.getVelocity(movement.x(), random),
        this.verticalVelocity.getVelocity(movement.y(), random),
        this.horizontalVelocity.getVelocity(movement.z(), random),
        this.speed.sample(random)
    );
  }
}
