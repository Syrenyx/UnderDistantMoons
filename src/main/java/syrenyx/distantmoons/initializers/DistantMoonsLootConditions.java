package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.predicate.condition.EntityPropertiesCondition;
import syrenyx.distantmoons.content.predicate.condition.LocationCheck;

public abstract class DistantMoonsLootConditions {

  public static final LootItemConditionType ENTITY_PROPERTIES = register("entity_properties", EntityPropertiesCondition.CODEC);
  public static final LootItemConditionType LOCATION_CHECK = register("location_check", LocationCheck.CODEC);

  private static LootItemConditionType register(String id, MapCodec<? extends LootItemCondition> codec) {
    return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, UnderDistantMoons.identifierOf(id), new LootItemConditionType(codec));
  }

  public static void initialize() {}
}
