package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public class IgniteEntry extends LootPoolSingletonContainer {

  public static final MapCodec<IgniteEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          NumberProviders.CODEC.fieldOf("duration").forGetter(entry -> entry.duration),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, IgniteEntry::new)
  );
  protected final NumberProvider duration;
  protected final EffectPoolEntryTarget target;

  protected IgniteEntry(
      NumberProvider duration,
      EffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootItemCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.duration = duration;
    this.target = target;
  }

  @Override
  public @NonNull LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.IGNITE;
  }

  @Override
  public boolean expand(@NonNull LootContext context, @NonNull Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (target == null) return true;
    target.igniteForSeconds(this.duration.getFloat(context));
    return true;
  }

  @Override
  protected void createItemStack(@NonNull Consumer<ItemStack> lootConsumer, @NonNull LootContext context) {}
}
