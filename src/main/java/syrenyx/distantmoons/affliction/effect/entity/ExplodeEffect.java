package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

import java.util.Optional;

public record ExplodeEffect(
    boolean attributeToUser,
    Optional<RegistryEntry<DamageType>> damageType,
    Optional<EnchantmentLevelBasedValue> knockbackMultiplier,
    Optional<RegistryEntryList<Block>> immuneBlocks,
    Vec3d offset,
    EnchantmentLevelBasedValue radius,
    boolean createFire,
    World.ExplosionSourceType blockInteraction,
    ParticleEffect smallParticle,
    ParticleEffect largeParticle,
    RegistryEntry<SoundEvent> sound
) implements AfflictionEntityEffect {

  public static final MapCodec<ExplodeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Codec.BOOL.optionalFieldOf("attribute_to_user", false).forGetter(ExplodeEffect::attributeToUser),
          DamageType.ENTRY_CODEC.optionalFieldOf("damage_type").forGetter(ExplodeEffect::damageType),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("knockback_multiplier").forGetter(ExplodeEffect::knockbackMultiplier),
          RegistryCodecs.entryList(RegistryKeys.BLOCK).optionalFieldOf("immune_blocks").forGetter(ExplodeEffect::immuneBlocks),
          Vec3d.CODEC.optionalFieldOf("offset", Vec3d.ZERO).forGetter(ExplodeEffect::offset),
          EnchantmentLevelBasedValue.CODEC.fieldOf("radius").forGetter(ExplodeEffect::radius),
          Codec.BOOL.optionalFieldOf("create_fire", false).forGetter(ExplodeEffect::createFire),
          World.ExplosionSourceType.CODEC.fieldOf("block_interaction").forGetter(ExplodeEffect::blockInteraction),
          ParticleTypes.TYPE_CODEC.fieldOf("small_particle").forGetter(ExplodeEffect::smallParticle),
          ParticleTypes.TYPE_CODEC.fieldOf("large_particle").forGetter(ExplodeEffect::largeParticle),
          SoundEvent.ENTRY_CODEC.fieldOf("sound").forGetter(ExplodeEffect::sound)
      )
      .apply(instance, ExplodeEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    Vec3d offsetPosition = pos.add(this.offset);
    world.createExplosion(
        this.attributeToUser ? target : null,
        this.damageType.map(damageTypeRegistryEntry -> this.attributeToUser
            ? new DamageSource(damageTypeRegistryEntry, target)
            : new DamageSource(damageTypeRegistryEntry, offsetPosition)).orElse(null),
        new AdvancedExplosionBehavior(
            this.blockInteraction != World.ExplosionSourceType.NONE,
            this.damageType.isPresent(),
            this.knockbackMultiplier.map(knockbackMultiplier -> knockbackMultiplier.getValue(stage)),
            this.immuneBlocks
        ),
        offsetPosition.getX(), offsetPosition.getY(), offsetPosition.getZ(),
        Math.max(this.radius.getValue(stage), 0.0F),
        this.createFire,
        this.blockInteraction,
        this.smallParticle,
        this.largeParticle,
        this.sound
    );
  }

  @Nullable
  private DamageSource getDamageSource(Entity user, Vec3d pos) {
    return this.damageType.map(damageTypeRegistryEntry -> this.attributeToUser
        ? new DamageSource(damageTypeRegistryEntry, user)
        : new DamageSource(damageTypeRegistryEntry, pos)).orElse(null);
  }
}
