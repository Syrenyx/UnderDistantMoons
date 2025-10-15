package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

public record ApplyMobEffectEffect(
    RegistryEntryList<StatusEffect> toApply,
    EnchantmentLevelBasedValue minDuration,
    EnchantmentLevelBasedValue maxDuration,
    EnchantmentLevelBasedValue minAmplifier,
    EnchantmentLevelBasedValue maxAmplifier
) implements AfflictionEntityEffect {

  public static final MapCodec<ApplyMobEffectEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.entryList(RegistryKeys.STATUS_EFFECT).fieldOf("to_apply").forGetter(ApplyMobEffectEffect::toApply),
          EnchantmentLevelBasedValue.CODEC.fieldOf("min_duration").forGetter(ApplyMobEffectEffect::minDuration),
          EnchantmentLevelBasedValue.CODEC.fieldOf("max_duration").forGetter(ApplyMobEffectEffect::maxDuration),
          EnchantmentLevelBasedValue.CODEC.fieldOf("min_amplifier").forGetter(ApplyMobEffectEffect::minAmplifier),
          EnchantmentLevelBasedValue.CODEC.fieldOf("max_amplifier").forGetter(ApplyMobEffectEffect::maxAmplifier)
      )
      .apply(instance, ApplyMobEffectEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    if (!(target instanceof LivingEntity livingEntity)) return;
    Random random = livingEntity.getRandom();
    Optional<RegistryEntry<StatusEffect>> optional = this.toApply.getRandom(random);
    if (optional.isPresent()) {
      int i = Math.round(MathHelper.nextBetween(random, this.minDuration.getValue(stage), this.maxDuration.getValue(stage)) * 20.0F);
      int j = Math.max(0, Math.round(MathHelper.nextBetween(random, this.minAmplifier.getValue(stage), this.maxAmplifier.getValue(stage))));
      livingEntity.addStatusEffect(new StatusEffectInstance(optional.get(), i, j));
    }
  }
}
