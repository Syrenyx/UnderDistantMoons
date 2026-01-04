package syrenyx.distantmoons.content.predicate.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.initializers.DistantMoonsLootConditions;
import syrenyx.distantmoons.content.predicate.entity.EntityPredicate;

import java.util.Optional;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record EntityPropertiesCondition(
    Optional<EntityPredicate> predicate,
    TargetEntityType entity
) implements LootItemCondition {

  public static final MapCodec<EntityPropertiesCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          EntityPredicate.CODEC.optionalFieldOf("predicate").forGetter(EntityPropertiesCondition::predicate),
          TargetEntityType.CODEC.fieldOf("entity").forGetter(EntityPropertiesCondition::entity)
      )
      .apply(instance, EntityPropertiesCondition::new)
  );

  @Override
  public LootItemConditionType getType() {
    return DistantMoonsLootConditions.ENTITY_PROPERTIES;
  }

  @Override
  public Set<ContextKey<?>> getReferencedContextParams() {
    return Set.of(LootContextParams.ORIGIN, this.entity.getParameter());
  }

  @Override
  public boolean test(LootContext context) {
    return this.predicate.isEmpty() || this.predicate.get().test(context.getLevel(), context.getOptionalParameter(LootContextParams.ORIGIN), context.getOptionalParameter(this.entity.getParameter()));
  }
}
