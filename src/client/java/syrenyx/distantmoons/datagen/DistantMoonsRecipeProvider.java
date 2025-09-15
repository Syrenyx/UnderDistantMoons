package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.BlastingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DistantMoonsRecipeProvider extends FabricRecipeProvider {

  public DistantMoonsRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public String getName() {
    return "DistantMoonsRecipeProvider";
  }

  @Override
  protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter recipeExporter) {
    return new RecipeGenerator(registryLookup, recipeExporter) {

      //private final RegistryWrapper.Impl<Item> itemLookup = this.registries.getOrThrow(RegistryKeys.ITEM);

      @Override
      public void generate() {

        //CRAFTING
        this.createResourceCompressionRecipes(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.RAW_DEEP_IRON, DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, DistantMoonsItems.REFINED_DEEP_IRON_INGOT, DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);

        //SMELTING
        this.createOreSmeltingRecipes(
            List.of(DistantMoonsItems.RAW_DEEP_IRON, DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE),
            DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK,
            1.0F,
            400
        );
      }

      private void createOreSmeltingRecipes(List<ItemConvertible> ingredients, ItemConvertible result, float experience, int cookingTime) {
        for (ItemConvertible ingredient : ingredients) {
          CookingRecipeJsonBuilder
              .create(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, experience, cookingTime, RecipeSerializer.SMELTING, SmeltingRecipe::new)
              .group(getItemId(result) + "_from_ore")
              .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
              .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smelting_" + getItemId(ingredient)));
          CookingRecipeJsonBuilder
              .create(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, experience, cookingTime / 2, RecipeSerializer.BLASTING, BlastingRecipe::new)
              .group(getItemId(result) + "_from_ore")
              .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
              .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/blasting_" + getItemId(ingredient)));
        }
      }

      private void createResourceCompressionRecipes(ItemConvertible... items) {
        ItemConvertible current = null;
        for (ItemConvertible next : items) {
          if (current != null) {
            this.createResourceCompressionRecipe(current, next);
            this.createResourceDecompressionRecipe(next, current);
          }
          current = next;
        }
      }

      private void createResourceCompressionRecipe(ItemConvertible ingredient, ItemConvertible result) {
        this.createShaped(result instanceof Block ? RecipeCategory.BUILDING_BLOCKS : RecipeCategory.MISC, result)
            .group(getItemId(result) + "_from_resource_compression")
            .pattern("000").pattern("000").pattern("000")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/compressing_" + getItemId(ingredient)));
      }

      private void createResourceDecompressionRecipe(ItemConvertible ingredient, ItemConvertible result) {
        this.createShapeless(result instanceof Block ? RecipeCategory.BUILDING_BLOCKS : RecipeCategory.MISC, result, 9)
            .group(getItemId(result) + "_from_resource_compression")
            .input(ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/decompressing_" + getItemId(ingredient)));
      }

      private static String getItemId(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem()).toString().split(":")[1];
      }
    };
  }
}
