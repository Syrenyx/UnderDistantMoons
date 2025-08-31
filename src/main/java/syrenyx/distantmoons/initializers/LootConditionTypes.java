package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.predicate.loot_condition.EntityPropertiesLootCondition;

public class LootConditionTypes {

  public static final LootConditionType ENTITY_PROPERTIES = register("entity_properties", EntityPropertiesLootCondition.CODEC);

  public static void initialize() {}

  private static LootConditionType register(String id, MapCodec<? extends LootCondition> codec) {
    return Registry.register(Registries.LOOT_CONDITION_TYPE, UnderDistantMoons.identifierOf(id), new LootConditionType(codec));
  }
}
