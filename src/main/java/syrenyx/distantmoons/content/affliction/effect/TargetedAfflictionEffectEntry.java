package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.context.ContextType;

import java.util.Optional;

public record TargetedAfflictionEffectEntry<T>(
    T effect,
    Optional<LootCondition> requirements,
    EnchantmentEffectTarget afflicted,
    EnchantmentEffectTarget affected
) {

  public static <T> Codec<TargetedAfflictionEffectEntry<T>> createCodec(Codec<T> effectCodec, ContextType lootContextType) {
    return RecordCodecBuilder.create(instance -> instance
        .group(
            effectCodec.fieldOf("effect").forGetter(TargetedAfflictionEffectEntry::effect),
            AfflictionEffectEntry.createRequirementsCodec(lootContextType).optionalFieldOf("requirements").forGetter(TargetedAfflictionEffectEntry::requirements),
            EnchantmentEffectTarget.CODEC.fieldOf("afflicted").forGetter(TargetedAfflictionEffectEntry::afflicted),
            EnchantmentEffectTarget.CODEC.fieldOf("affected").forGetter(TargetedAfflictionEffectEntry::affected)
        )
        .apply(instance, TargetedAfflictionEffectEntry::new)
    );
  }

  public boolean test(LootContext context) {
    return this.requirements.map(lootCondition -> lootCondition.test(context)).orElse(true);
  }
}
