package syrenyx.distantmoons.references.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsPredicates {

  public static final ResourceKey<LootItemCondition> SILK_TOUCH_TOOL = keyOf("silk_touch_tool");

  private static ResourceKey<LootItemCondition> keyOf(String id) {
    return UnderDistantMoons.resourceKeyOf(id, Registries.PREDICATE);
  }
}
