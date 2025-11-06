package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DamageEntityEntry extends LeafEntry {

  public static final MapCodec<DamageEntityEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LootNumberProviderTypes.CODEC.fieldOf("min_damage").forGetter(entry -> entry.minDamage),
          LootNumberProviderTypes.CODEC.fieldOf("max_damage").forGetter(entry -> entry.maxDamage),
          DamageType.ENTRY_CODEC.fieldOf("damage_type").forGetter(entry -> entry.damageType),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("attacker", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.attacker),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, DamageEntityEntry::new)
  );
  protected final LootNumberProvider minDamage;
  protected final LootNumberProvider maxDamage;
  protected final RegistryEntry<DamageType> damageType;
  protected final EffectPoolEntryTarget target;
  protected final OptionalEffectPoolEntryTarget attacker;

  protected DamageEntityEntry(
      LootNumberProvider minDamage,
      LootNumberProvider maxDamage,
      RegistryEntry<DamageType> damageType,
      EffectPoolEntryTarget target,
      OptionalEffectPoolEntryTarget attacker,
      int weight,
      int quality,
      List<LootCondition> conditions
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
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (target == null) return true;
    Entity attacker = this.attacker.tryGettingEntityFromContext(context);
    target.damage(
        context.getWorld(),
        attacker == null
            ? new DamageSource(this.damageType, context.get(LootContextParameters.ORIGIN))
            : new DamageSource(this.damageType, attacker),
        MathHelper.nextBetween(target.getRandom(), this.minDamage.nextFloat(context), this.maxDamage.nextFloat(context))
    );
    return true;
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
