package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.predicate.condition.EntityPropertiesCondition;
import syrenyx.distantmoons.content.predicate.condition.LocationCheck;

public abstract class DistantMoonsLootConditions {

  static {
    register("entity_properties", EntityPropertiesCondition.CODEC);
    register("location_check", LocationCheck.CODEC);
  }

  private static void register(String id, MapCodec<? extends LootItemCondition> codec) {
    Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
