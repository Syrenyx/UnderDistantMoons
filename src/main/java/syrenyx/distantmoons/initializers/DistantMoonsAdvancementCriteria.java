package syrenyx.distantmoons.initializers;

import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.advancement.criterion.UsedItemCriterion;

public abstract class DistantMoonsAdvancementCriteria {

  public static final UsedItemCriterion USED_ITEM = register("used_item", new UsedItemCriterion());

  private static <T extends Criterion<?>> T register(String id, T criterion) {
    return Registry.register(Registries.CRITERION, UnderDistantMoons.identifierOf(id), criterion);
  }

  public static void initialize() {}
}
