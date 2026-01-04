package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.Vec3;

public record ApplyMobEffectEffect(
    HolderSet<MobEffect> toApply,
    LevelBasedValue minDuration,
    LevelBasedValue maxDuration,
    LevelBasedValue minAmplifier,
    LevelBasedValue maxAmplifier
) implements AfflictionEntityEffect {

  public static final MapCodec<ApplyMobEffectEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.homogeneousList(Registries.MOB_EFFECT).fieldOf("to_apply").forGetter(ApplyMobEffectEffect::toApply),
          LevelBasedValue.CODEC.fieldOf("min_duration").forGetter(ApplyMobEffectEffect::minDuration),
          LevelBasedValue.CODEC.fieldOf("max_duration").forGetter(ApplyMobEffectEffect::maxDuration),
          LevelBasedValue.CODEC.fieldOf("min_amplifier").forGetter(ApplyMobEffectEffect::minAmplifier),
          LevelBasedValue.CODEC.fieldOf("max_amplifier").forGetter(ApplyMobEffectEffect::maxAmplifier)
      )
      .apply(instance, ApplyMobEffectEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    if (!(target instanceof LivingEntity livingEntity)) return;
    RandomSource random = livingEntity.getRandom();
    Optional<Holder<MobEffect>> optional = this.toApply.getRandomElement(random);
    if (optional.isEmpty()) return;
    int duration = Math.round(Mth.randomBetween(random, this.minDuration.calculate(stage), this.maxDuration.calculate(stage)) * 20.0F);
    int amplifier = Math.max(0, Math.round(Mth.randomBetween(random, this.minAmplifier.calculate(stage), this.maxAmplifier.calculate(stage))));
    livingEntity.addEffect(new MobEffectInstance(optional.get(), duration, amplifier));
  }
}
