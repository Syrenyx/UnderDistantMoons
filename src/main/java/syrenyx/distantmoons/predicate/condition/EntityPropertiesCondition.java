package syrenyx.distantmoons.predicate.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextParameter;
import syrenyx.distantmoons.initializers.DistantMoonsLootConditions;
import syrenyx.distantmoons.predicate.entity.EntityPredicate;

import java.util.Optional;
import java.util.Set;

public record EntityPropertiesCondition(
    Optional<EntityPredicate> predicate,
    TargetEntityType entity
) implements LootCondition {

  public static final MapCodec<EntityPropertiesCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          EntityPredicate.CODEC.optionalFieldOf("predicate").forGetter(EntityPropertiesCondition::predicate),
          TargetEntityType.CODEC.fieldOf("entity").forGetter(EntityPropertiesCondition::entity)
      )
      .apply(instance, EntityPropertiesCondition::new)
  );

  @Override
  public LootConditionType getType() {
    return DistantMoonsLootConditions.ENTITY_PROPERTIES;
  }

  @Override
  public Set<ContextParameter<?>> getAllowedParameters() {
    return Set.of(LootContextParameters.ORIGIN, this.entity.getParameter());
  }

  @Override
  public boolean test(LootContext context) {
    return this.predicate.isEmpty() || this.predicate.get().test(context.getWorld(), context.get(LootContextParameters.ORIGIN), context.get(this.entity.getParameter()));
  }
}
