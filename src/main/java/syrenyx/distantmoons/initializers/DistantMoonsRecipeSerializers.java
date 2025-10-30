package syrenyx.distantmoons.initializers;

import net.minecraft.recipe.RecipeSerializer;
import syrenyx.distantmoons.content.recipe.SeededShapedRecipe;

public abstract class DistantMoonsRecipeSerializers {

  public static RecipeSerializer<SeededShapedRecipe> SEEDED_SHAPED = RecipeSerializer.register("seeded_shaped", new SeededShapedRecipe.Serializer());

  public static void initialize() {}
}
