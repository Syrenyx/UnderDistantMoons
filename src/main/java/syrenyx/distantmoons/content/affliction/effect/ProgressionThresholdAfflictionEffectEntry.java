package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record ProgressionThresholdAfflictionEffectEntry<T>(
    T effect,
    Optional<LootItemCondition> requirements,
    int threshold,
    ProgressionThresholdPassingType type
) {

  public static <T> Codec<ProgressionThresholdAfflictionEffectEntry<T>> createCodec(Codec<T> effectCodec, ContextKeySet lootContextType) {
    return RecordCodecBuilder.create(instance -> instance
        .group(
            effectCodec.fieldOf("effect").forGetter(ProgressionThresholdAfflictionEffectEntry::effect),
            AfflictionEffectEntry.createRequirementsCodec(lootContextType).optionalFieldOf("requirements").forGetter(ProgressionThresholdAfflictionEffectEntry::requirements),
            Codec.INT.fieldOf("threshold").forGetter(ProgressionThresholdAfflictionEffectEntry::threshold),
            ProgressionThresholdPassingType.CODEC.optionalFieldOf("type", ProgressionThresholdPassingType.ANY).forGetter(ProgressionThresholdAfflictionEffectEntry::type)
        )
        .apply(instance, ProgressionThresholdAfflictionEffectEntry::new)
    );
  }

  public boolean test(LootContext context) {
    return this.requirements.map(lootCondition -> lootCondition.test(context)).orElse(true);
  }
}
