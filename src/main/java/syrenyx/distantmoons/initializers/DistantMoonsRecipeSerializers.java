package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.recipe.LargeBlastFurnaceRecipe;

public abstract class DistantMoonsRecipeSerializers {

  public static final RecipeSerializer<LargeBlastFurnaceRecipe> BLASTING_RECIPE = register("blasting", new LargeBlastFurnaceRecipe.Serializer());

  private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
    return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, UnderDistantMoons.identifierOf(id), serializer);
  }

  public static void initialize() {}
}
