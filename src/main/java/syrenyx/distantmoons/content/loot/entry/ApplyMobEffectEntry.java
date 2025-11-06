package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ApplyMobEffectEntry extends LeafEntry {

  public static final MapCodec<ApplyMobEffectEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.entryList(RegistryKeys.STATUS_EFFECT).fieldOf("to_apply").forGetter(entry -> entry.toApply),
          LootNumberProviderTypes.CODEC.fieldOf("min_duration").forGetter(entry -> entry.minDuration),
          LootNumberProviderTypes.CODEC.fieldOf("max_duration").forGetter(entry -> entry.maxDuration),
          LootNumberProviderTypes.CODEC.fieldOf("min_amplifier").forGetter(entry -> entry.minAmplifier),
          LootNumberProviderTypes.CODEC.fieldOf("max_amplifier").forGetter(entry -> entry.maxAmplifier),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, ApplyMobEffectEntry::new)
  );
  protected final RegistryEntryList<StatusEffect> toApply;
  protected final LootNumberProvider minDuration;
  protected final LootNumberProvider maxDuration;
  protected final LootNumberProvider minAmplifier;
  protected final LootNumberProvider maxAmplifier;
  protected final EffectPoolEntryTarget target;

  protected ApplyMobEffectEntry(
      RegistryEntryList<StatusEffect> toApply,
      LootNumberProvider minDuration,
      LootNumberProvider maxDuration,
      LootNumberProvider minAmplifier,
      LootNumberProvider maxAmplifier,
      EffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.toApply = toApply;
    this.minDuration = minDuration;
    this.maxDuration = maxDuration;
    this.minAmplifier = minAmplifier;
    this.maxAmplifier = maxAmplifier;
    this.target = target;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.APPLY_MOB_EFFECT;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (!(target instanceof LivingEntity livingEntity)) return true;
    Random random = livingEntity.getRandom();
    Optional<RegistryEntry<StatusEffect>> optional = this.toApply.getRandom(random);
    if (optional.isEmpty()) return true;
    int duration = Math.round(MathHelper.nextBetween(random, this.minDuration.nextFloat(context), this.maxDuration.nextFloat(context)) * 20.0F);
    int amplifier = Math.max(0, Math.round(MathHelper.nextBetween(random, this.minAmplifier.nextFloat(context), this.maxAmplifier.nextFloat(context))));
    livingEntity.addStatusEffect(new StatusEffectInstance(optional.get(), duration, amplifier));
    return true;
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
