package syrenyx.distantmoons.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.ErrorReporter;
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
            SpawnedEntityAfflictionEffectEntry.createRequirementsCodec(lootContextType).optionalFieldOf("requirements").forGetter(SpawnedEntityAfflictionEffectEntry::requirements),
            SpawnedEntityEffectTarget.CODEC.fieldOf("target").forGetter(SpawnedEntityAfflictionEffectEntry::target)
        )
        .apply(instance, SpawnedEntityAfflictionEffectEntry::new)
    );
  }

  public static Codec<LootCondition> createRequirementsCodec(ContextType lootContextType) {
    return LootCondition.CODEC.validate(
        condition -> {
          ErrorReporter.Impl impl = new ErrorReporter.Impl();
          LootTableReporter lootTableReporter = new LootTableReporter(impl, lootContextType);
          condition.validate(lootTableReporter);
          return !impl.isEmpty()
              ? DataResult.error(() -> "Validation error in affliction effect condition: " + impl.getErrorsAsString())
              : DataResult.success(condition);
        }
    );
  }

  public boolean test(LootContext context) {
    return this.requirements.map(lootCondition -> lootCondition.test(context)).orElse(true);
  }
}
