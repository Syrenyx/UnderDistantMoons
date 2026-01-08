package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;
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
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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

public class ChangeAfflictionEntry extends LootPoolSingletonContainer {

  public static final MapCodec<ChangeAfflictionEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("affliction").forGetter(entry -> entry.affliction),
          ChangeAfflictionOperation.CODEC.fieldOf("operation").forGetter(entry -> entry.operation),
          NumberProviders.CODEC.optionalFieldOf("stage").forGetter(entry -> entry.stage),
          NumberProviders.CODEC.optionalFieldOf("progression").forGetter(entry -> entry.progression),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, ChangeAfflictionEntry::new)
  );
  protected final Identifier affliction;
  ChangeAfflictionOperation operation;
  Optional<NumberProvider> stage;
  Optional<NumberProvider> progression;
  protected final EffectPoolEntryTarget target;

  protected ChangeAfflictionEntry(
      Identifier affliction,
      ChangeAfflictionOperation operation,
      Optional<NumberProvider> stage,
      Optional<NumberProvider> progression,
      EffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootItemCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.affliction = affliction;
    this.operation = operation;
    this.stage = stage;
    this.progression = progression;
    this.target = target;
  }

  @Override
  public @NonNull LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.CHANGE_AFFLICTION;
  }

  @Override
  public boolean expand(@NonNull LootContext context, @NonNull Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (!(target instanceof LivingEntity livingEntity)) return true;
    Optional<Holder.Reference<Affliction>> afflictionEntry = target.registryAccess().get(ResourceKey.create(DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY, this.affliction));
    if (afflictionEntry.isEmpty()) return true;
    switch (this.operation) {
      case ADD -> AfflictionManager.addToAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(numberProvider -> numberProvider.getInt(context)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(numberProvider -> numberProvider.getFloat(context)).orElse(0.0F)
      ));
      case CLEAR -> AfflictionManager.clearAffliction(livingEntity, afflictionEntry.get());
      case GIVE -> AfflictionManager.giveAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(numberProvider -> numberProvider.getInt(context)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(numberProvider -> numberProvider.getFloat(context)).orElse(0.0F)
      ));
      case SET -> AfflictionManager.setAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(numberProvider -> numberProvider.getInt(context)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(numberProvider -> numberProvider.getFloat(context)).orElse(0.0F)
      ));
    }
    return true;
  }

  @Override
  protected void createItemStack(@NonNull Consumer<ItemStack> lootConsumer, @NonNull LootContext context) {}
}
