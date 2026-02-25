package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import syrenyx.distantmoons.UnderDistantMoons;

public class DistantMoonsRecipeBookCategories {

  public static final RecipeBookCategory LARGE_BLAST_FURNACE_MISC = register("large_blast_furnace_misc");

  private static RecipeBookCategory register(String id) {
    return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, UnderDistantMoons.identifierOf(id), new RecipeBookCategory());
  }
  
  public static void initialize() {}
}
