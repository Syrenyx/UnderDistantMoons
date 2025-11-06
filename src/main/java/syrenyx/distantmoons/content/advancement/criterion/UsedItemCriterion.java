package syrenyx.distantmoons.content.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import syrenyx.distantmoons.initializers.DistantMoonsAdvancementCriteria;

import java.util.Optional;

public class UsedItemCriterion extends AbstractCriterion<UsedItemCriterion.Conditions> {

  @Override
  public Codec<UsedItemCriterion.Conditions> getConditionsCodec() {
    return UsedItemCriterion.Conditions.CODEC;
  }

  public void trigger(ServerPlayerEntity player, ItemStack stack) {
    this.trigger(player, conditions -> conditions.test(stack));
  }

  public record Conditions(Optional<LootContextPredicate> player, Optional<ItemPredicate> item) implements AbstractCriterion.Conditions {

    public static final Codec<UsedItemCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(UsedItemCriterion.Conditions::player),
            ItemPredicate.CODEC.optionalFieldOf("item").forGetter(UsedItemCriterion.Conditions::item)
        )
        .apply(instance, UsedItemCriterion.Conditions::new)
    );

    public static AdvancementCriterion<UsedItemCriterion.Conditions> create(EntityPredicate.Builder player, ItemPredicate.Builder item) {
      return DistantMoonsAdvancementCriteria.USED_ITEM.create(new UsedItemCriterion.Conditions(Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(player)), Optional.of(item.build())));
    }

    public boolean test(ItemStack stack) {
      return this.item.isEmpty() || this.item.get().test(stack);
    }
  }
}
