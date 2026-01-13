package syrenyx.distantmoons.content.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsAdvancementCriteria;

import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class UsedItemCriterion extends SimpleCriterionTrigger<UsedItemCriterion.Conditions> {

  @Override
  public @NonNull Codec<Conditions> codec() {
    return Conditions.CODEC;
  }

  public void trigger(ServerPlayer player, ItemStack stack) {
    this.trigger(player, conditions -> conditions.test(stack));
  }

  public record Conditions(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance {

    public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
            ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Conditions::item)
        )
        .apply(instance, Conditions::new)
    );

    public static Criterion<Conditions> create(EntityPredicate.Builder player, ItemPredicate.Builder item) {
      return DistantMoonsAdvancementCriteria.USED_ITEM.createCriterion(new Conditions(Optional.of(EntityPredicate.wrap(player)), Optional.of(item.build())));
    }

    public boolean test(ItemStack stack) {
      return this.item.isEmpty() || this.item.get().test(stack);
    }
  }
}
