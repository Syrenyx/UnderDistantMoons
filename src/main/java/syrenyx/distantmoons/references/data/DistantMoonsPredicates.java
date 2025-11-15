package syrenyx.distantmoons.references.data;

import net.minecraft.loot.condition.LootCondition;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsPredicates {

  public static final RegistryKey<LootCondition> SILK_TOUCH_TOOL = keyOf("silk_touch_tool");

  private static RegistryKey<LootCondition> keyOf(String id) {
    return UnderDistantMoons.registryKeyOf(id, RegistryKeys.PREDICATE);
  }
}
