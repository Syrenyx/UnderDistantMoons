package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.predicate.condition.EntityPropertiesCondition;
import syrenyx.distantmoons.predicate.condition.LocationCheck;

public abstract class DistantMoonsLootConditions {

  public static final LootConditionType ENTITY_PROPERTIES = register("entity_properties", EntityPropertiesCondition.CODEC);
  public static final LootConditionType LOCATION_CHECK = register("location_check", LocationCheck.CODEC);

  private static LootConditionType register(String id, MapCodec<? extends LootCondition> codec) {
    return Registry.register(Registries.LOOT_CONDITION_TYPE, UnderDistantMoons.identifierOf(id), new LootConditionType(codec));
  }

  public static void initialize() {}
}
