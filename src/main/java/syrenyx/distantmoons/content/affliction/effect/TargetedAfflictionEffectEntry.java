package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record TargetedAfflictionEffectEntry<T>(
    T effect,
    Optional<LootItemCondition> requirements,
    EnchantmentTarget afflicted,
    EnchantmentTarget affected
) {

  public static <T> Codec<TargetedAfflictionEffectEntry<T>> createCodec(Codec<T> effectCodec, ContextKeySet lootContextType) {
    return RecordCodecBuilder.create(instance -> instance
        .group(
            effectCodec.fieldOf("effect").forGetter(TargetedAfflictionEffectEntry::effect),
            AfflictionEffectEntry.createRequirementsCodec(lootContextType).optionalFieldOf("requirements").forGetter(TargetedAfflictionEffectEntry::requirements),
            EnchantmentTarget.CODEC.fieldOf("afflicted").forGetter(TargetedAfflictionEffectEntry::afflicted),
            EnchantmentTarget.CODEC.fieldOf("affected").forGetter(TargetedAfflictionEffectEntry::affected)
        )
        .apply(instance, TargetedAfflictionEffectEntry::new)
    );
  }

  public boolean test(LootContext context) {
    return this.requirements.map(lootCondition -> lootCondition.test(context)).orElse(true);
  }
}
