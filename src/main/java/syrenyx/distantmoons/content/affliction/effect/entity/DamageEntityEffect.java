package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.Vec3;

public record DamageEntityEffect(
    LevelBasedValue minDamage,
    LevelBasedValue maxDamage,
    Holder<DamageType> damageType
) implements AfflictionEntityEffect {

  public static final MapCodec<DamageEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LevelBasedValue.CODEC.fieldOf("min_damage").forGetter(DamageEntityEffect::minDamage),
          LevelBasedValue.CODEC.fieldOf("max_damage").forGetter(DamageEntityEffect::maxDamage),
          DamageType.CODEC.fieldOf("damage_type").forGetter(DamageEntityEffect::damageType)
      )
      .apply(instance, DamageEntityEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    target.hurtServer(
        world,
        new DamageSource(this.damageType, pos),
        Mth.randomBetween(target.getRandom(), this.minDamage.calculate(stage), this.maxDamage.calculate(stage))
    );
  }
}
