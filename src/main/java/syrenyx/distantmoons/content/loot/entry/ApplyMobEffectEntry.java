package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public class ApplyMobEffectEntry extends LootPoolSingletonContainer {

  public static final MapCodec<ApplyMobEffectEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.homogeneousList(Registries.MOB_EFFECT).fieldOf("to_apply").forGetter(entry -> entry.toApply),
          NumberProviders.CODEC.fieldOf("min_duration").forGetter(entry -> entry.minDuration),
          NumberProviders.CODEC.fieldOf("max_duration").forGetter(entry -> entry.maxDuration),
          NumberProviders.CODEC.fieldOf("min_amplifier").forGetter(entry -> entry.minAmplifier),
          NumberProviders.CODEC.fieldOf("max_amplifier").forGetter(entry -> entry.maxAmplifier),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, ApplyMobEffectEntry::new)
  );
  protected final HolderSet<MobEffect> toApply;
  protected final NumberProvider minDuration;
  protected final NumberProvider maxDuration;
  protected final NumberProvider minAmplifier;
  protected final NumberProvider maxAmplifier;
  protected final EffectPoolEntryTarget target;

  protected ApplyMobEffectEntry(
      HolderSet<MobEffect> toApply,
      NumberProvider minDuration,
      NumberProvider maxDuration,
      NumberProvider minAmplifier,
      NumberProvider maxAmplifier,
      EffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootItemCondition> conditions
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
  public @NonNull LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.APPLY_MOB_EFFECT;
  }

  @Override
  public boolean expand(@NonNull LootContext context, @NonNull Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (!(target instanceof LivingEntity livingEntity)) return true;
    RandomSource random = livingEntity.getRandom();
    Optional<Holder<MobEffect>> optional = this.toApply.getRandomElement(random);
    if (optional.isEmpty()) return true;
    int duration = Math.round(Mth.randomBetween(random, this.minDuration.getFloat(context), this.maxDuration.getFloat(context)) * 20.0F);
    int amplifier = Math.max(0, Math.round(Mth.randomBetween(random, this.minAmplifier.getFloat(context), this.maxAmplifier.getFloat(context))));
    livingEntity.addEffect(new MobEffectInstance(optional.get(), duration, amplifier));
    return true;
  }

  @Override
  protected void createItemStack(@NonNull Consumer<ItemStack> lootConsumer, @NonNull LootContext context) {}
}
