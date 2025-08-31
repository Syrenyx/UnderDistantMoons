package syrenyx.distantmoons.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.context.ContextType;

import java.util.Optional;

public record TargetedAfflictionEffect<T> (
    EnchantmentEffectTarget affected,
    T effect,
    Optional<LootCondition> requirements
) {

  public static <T> Codec<TargetedAfflictionEffect<T>> createPostAttackCodec(Codec<T> effectCodec, ContextType lootContextType) {
    return RecordCodecBuilder.create(instance -> instance
        .group(
            EnchantmentEffectTarget.CODEC.fieldOf("affected").forGetter(TargetedAfflictionEffect::affected),
            effectCodec.fieldOf("effect").forGetter(TargetedAfflictionEffect::effect),
            AfflictionEffectEntry.createRequirementsCodec(lootContextType).optionalFieldOf("requirements").forGetter(TargetedAfflictionEffect::requirements)
        )
        .apply(instance, TargetedAfflictionEffect::new)
    );
  }

  public boolean test(LootContext lootContext) {
    return this.requirements.isEmpty() || this.requirements.get().test(lootContext);
  }
}
