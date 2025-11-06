package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class IgniteEntry extends LeafEntry {

  public static final MapCodec<IgniteEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LootNumberProviderTypes.CODEC.fieldOf("duration").forGetter(entry -> entry.duration),
          EffectPoolEntryTarget.CODEC.fieldOf("target").forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, IgniteEntry::new)
  );
  protected final LootNumberProvider duration;
  protected final EffectPoolEntryTarget target;

  protected IgniteEntry(
      LootNumberProvider duration,
      EffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.duration = duration;
    this.target = target;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.IGNITE;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    Entity target = this.target.tryGettingEntityFromContext(context);
    if (target == null) return true;
    target.setOnFireFor(this.duration.nextFloat(context));
    return true;
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
