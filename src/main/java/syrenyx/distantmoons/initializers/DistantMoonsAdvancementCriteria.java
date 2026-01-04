package syrenyx.distantmoons.initializers;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.advancement.criterion.UsedItemCriterion;

public abstract class DistantMoonsAdvancementCriteria {

  public static final UsedItemCriterion USED_ITEM = register("used_item", new UsedItemCriterion());

  private static <T extends CriterionTrigger<?>> T register(String id, T criterion) {
    return Registry.register(BuiltInRegistries.TRIGGER_TYPES, UnderDistantMoons.identifierOf(id), criterion);
  }

  public static void initialize() {}
}
