package syrenyx.distantmoons.predicate.loot_condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.entity.EntityPredicate;
import syrenyx.distantmoons.initializers.LootConditionTypes;

import java.util.Optional;

public record EntityPropertiesLootCondition(
    LootContext.EntityTarget entity,
    Optional<EntityPredicate> predicate
) implements LootCondition {

  public static final MapCodec<EntityPropertiesLootCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(EntityPropertiesLootCondition::entity),
          EntityPredicate.CODEC.optionalFieldOf("predicate").forGetter(EntityPropertiesLootCondition::predicate)
      )
      .apply(instance, EntityPropertiesLootCondition::new)
  );

  @Override
  public LootConditionType getType() {
    return LootConditionTypes.ENTITY_PROPERTIES;
  }

  @Override
  public boolean test(LootContext lootContext) {
    return this.predicate.isEmpty() || this.predicate.get().test(
        lootContext.getWorld(),
        lootContext.get(LootContextParameters.ORIGIN),
        lootContext.get(this.entity.getParameter())
    );
  }
}
