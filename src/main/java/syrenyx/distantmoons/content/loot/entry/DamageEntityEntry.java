package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public class DamageEntityEntry extends LootPoolSingletonContainer {

  public static final MapCodec<DamageEntityEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          NumberProviders.CODEC.fieldOf("min_damage").forGetter(entry -> entry.minDamage),
          NumberProviders.CODEC.fieldOf("max_damage").forGetter(entry -> entry.maxDamage),
          DamageType.CODEC.fieldOf("damage_type").forGetter(entry -> entry.damageType),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("attacker", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.attacker),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, DamageEntityEntry::new)
  );
  protected final NumberProvider minDamage;
  protected final NumberProvider maxDamage;
  protected final Holder<DamageType> damageType;
  protected final EffectPoolEntryTarget target;
  protected final OptionalEffectPoolEntryTarget attacker;

  protected DamageEntityEntry(
      NumberProvider minDamage,
      NumberProvider maxDamage,
      Holder<DamageType> damageType,
      EffectPoolEntryTarget target,
      OptionalEffectPoolEntryTarget attacker,
      int weight,
      int quality,
      List<LootItemCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.minDamage = minDamage;
    this.maxDamage = maxDamage;
    this.damageType = damageType;
    this.target = target;
    this.attacker = attacker;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.DAMAGE_ENTITY;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (target == null) return true;
    Entity attacker = this.attacker.tryGettingEntityFromContext(context);
    target.hurtServer(
        context.getLevel(),
        attacker == null
            ? new DamageSource(this.damageType, context.getOptionalParameter(LootContextParams.ORIGIN))
            : new DamageSource(this.damageType, attacker),
        Mth.randomBetween(target.getRandom(), this.minDamage.getFloat(context), this.maxDamage.getFloat(context))
    );
    return true;
  }

  @Override
  protected void createItemStack(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
