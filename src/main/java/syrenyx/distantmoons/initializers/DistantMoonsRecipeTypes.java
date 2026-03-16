package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.recipe.LargeBlastFurnaceRecipe;

public abstract class DistantMoonsRecipeTypes {

  public static RecipeType<LargeBlastFurnaceRecipe> BLASTING = register("blasting");

  private static <T extends Recipe<?>> RecipeType<T> register(String id) {
    return Registry.register(BuiltInRegistries.RECIPE_TYPE, UnderDistantMoons.identifierOf(id), new RecipeType<T>() {
      @Override
      public String toString() {
        return id;
      }
    });
  }

  public static void initialize() {}
}
