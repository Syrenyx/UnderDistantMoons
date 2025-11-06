package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.content.affliction.AfflictionManager;
import syrenyx.distantmoons.content.affliction.ChangeAfflictionOperation;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;
import syrenyx.distantmoons.references.DistantMoonsRegistryKeys;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ChangeAfflictionEntry extends LeafEntry {

  public static final MapCodec<ChangeAfflictionEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("affliction").forGetter(entry -> entry.affliction),
          ChangeAfflictionOperation.CODEC.fieldOf("operation").forGetter(entry -> entry.operation),
          LootNumberProviderTypes.CODEC.optionalFieldOf("stage").forGetter(entry -> entry.stage),
          LootNumberProviderTypes.CODEC.optionalFieldOf("progression").forGetter(entry -> entry.progression),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, ChangeAfflictionEntry::new)
  );
  protected final Identifier affliction;
  ChangeAfflictionOperation operation;
  Optional<LootNumberProvider> stage;
  Optional<LootNumberProvider> progression;
  protected final EffectPoolEntryTarget target;

  protected ChangeAfflictionEntry(
      Identifier affliction,
      ChangeAfflictionOperation operation,
      Optional<LootNumberProvider> stage,
      Optional<LootNumberProvider> progression,
      EffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.affliction = affliction;
    this.operation = operation;
    this.stage = stage;
    this.progression = progression;
    this.target = target;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.CHANGE_AFFLICTION;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (!(target instanceof LivingEntity livingEntity)) return true;
    Optional<RegistryEntry.Reference<Affliction>> afflictionEntry = target.getRegistryManager().getOptionalEntry(RegistryKey.of(DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY, this.affliction));
    if (afflictionEntry.isEmpty()) return true;
    switch (this.operation) {
      case ADD -> AfflictionManager.addToAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(numberProvider -> numberProvider.nextInt(context)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(numberProvider -> numberProvider.nextFloat(context)).orElse(0.0F)
      ));
      case CLEAR -> AfflictionManager.clearAffliction(livingEntity, afflictionEntry.get());
      case GIVE -> AfflictionManager.giveAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(numberProvider -> numberProvider.nextInt(context)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(numberProvider -> numberProvider.nextFloat(context)).orElse(0.0F)
      ));
      case SET -> AfflictionManager.setAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(numberProvider -> numberProvider.nextInt(context)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(numberProvider -> numberProvider.nextFloat(context)).orElse(0.0F)
      ));
    }
    return true;
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
