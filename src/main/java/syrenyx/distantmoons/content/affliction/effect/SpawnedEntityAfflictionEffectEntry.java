package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.context.ContextType;

import java.util.Optional;

public record SpawnedEntityAfflictionEffectEntry<T>(
    T effect,
    Optional<LootCondition> requirements,
    SpawnedEntityEffectTarget target
) {

  public static <T> Codec<SpawnedEntityAfflictionEffectEntry<T>> createCodec(Codec<T> effectCodec, ContextType lootContextType) {
    return RecordCodecBuilder.create(instance -> instance
        .group(
            effectCodec.fieldOf("effect").forGetter(SpawnedEntityAfflictionEffectEntry::effect),
            AfflictionEffectEntry.createRequirementsCodec(lootContextType).optionalFieldOf("requirements").forGetter(SpawnedEntityAfflictionEffectEntry::requirements),
            SpawnedEntityEffectTarget.CODEC.fieldOf("target").forGetter(SpawnedEntityAfflictionEffectEntry::target)
        )
        .apply(instance, SpawnedEntityAfflictionEffectEntry::new)
    );
  }

  public boolean test(LootContext context) {
    return this.requirements.map(lootCondition -> lootCondition.test(context)).orElse(true);
  }
}
