package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record AfflictionEffectEntry<T>(T effect, Optional<LootItemCondition> requirements) {

  public static <T> Codec<AfflictionEffectEntry<T>> createCodec(Codec<T> effectCodec, ContextKeySet lootContextType) {
    return RecordCodecBuilder.create(instance -> instance
        .group(
            effectCodec.fieldOf("effect").forGetter(AfflictionEffectEntry::effect),
            AfflictionEffectEntry.createRequirementsCodec(lootContextType).optionalFieldOf("requirements").forGetter(AfflictionEffectEntry::requirements)
        )
        .apply(instance, AfflictionEffectEntry::new)
    );
  }

  public static Codec<LootItemCondition> createRequirementsCodec(ContextKeySet lootContextType) {
    return LootItemCondition.DIRECT_CODEC.validate(
        condition -> {
          ProblemReporter.Collector impl = new ProblemReporter.Collector();
          ValidationContext lootTableReporter = new ValidationContext(impl, lootContextType);
          condition.validate(lootTableReporter);
          return !impl.isEmpty()
              ? DataResult.error(() -> "Validation error in affliction effect condition: " + impl.getReport())
              : DataResult.success(condition);
        }
    );
  }

  public boolean test(LootContext context) {
    return this.requirements.map(lootCondition -> lootCondition.test(context)).orElse(true);
  }
}
