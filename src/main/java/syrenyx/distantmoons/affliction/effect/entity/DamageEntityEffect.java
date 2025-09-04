package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

public record DamageEntityEffect(
    EnchantmentLevelBasedValue minDamage,
    EnchantmentLevelBasedValue maxDamage,
    RegistryEntry<DamageType> damageType
) implements AfflictionEntityEffect {

  public static final MapCodec<DamageEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          EnchantmentLevelBasedValue.CODEC.fieldOf("min_damage").forGetter(DamageEntityEffect::minDamage),
          EnchantmentLevelBasedValue.CODEC.fieldOf("max_damage").forGetter(DamageEntityEffect::maxDamage),
          DamageType.ENTRY_CODEC.fieldOf("damage_type").forGetter(DamageEntityEffect::damageType)
      )
      .apply(instance, DamageEntityEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    float f = MathHelper.nextBetween(target.getRandom(), this.minDamage.getValue(stage), this.maxDamage.getValue(stage));
    target.damage(world, new DamageSource(this.damageType, pos), f);
  }
}
