package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record SpawnedEntityAfflictionEffectEntry<T>(
    T effect,
    Optional<LootItemCondition> requirements,
    SpawnedEntityEffectTarget target
) {

  public static <T> Codec<SpawnedEntityAfflictionEffectEntry<T>> createCodec(Codec<T> effectCodec, ContextKeySet lootContextType) {
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
