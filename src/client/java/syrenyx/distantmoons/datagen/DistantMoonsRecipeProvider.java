package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DistantMoonsRecipeProvider extends FabricRecipeProvider {

  public static final int DEFAULT_SMELTING_TIME = 200;

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

        //COOKING
        this.createCookingRecipes(Items.BROWN_MUSHROOM, DistantMoonsItems.ROASTED_BROWN_MUSHROOM, 0.35F, DEFAULT_SMELTING_TIME);

        //SLAB CRAFTING
        this.createSlabRecipes(Items.ACACIA_LOG, DistantMoonsBlocks.CUT_ACACIA_LOG);
        this.createSlabRecipes(Items.ACACIA_WOOD, DistantMoonsBlocks.CUT_ACACIA_WOOD);
        this.createSlabRecipes(Items.BAMBOO_BLOCK, DistantMoonsBlocks.CUT_BAMBOO_BLOCK);
        this.createSlabRecipes(Items.BIRCH_LOG, DistantMoonsBlocks.CUT_BIRCH_LOG);
        this.createSlabRecipes(Items.BIRCH_WOOD, DistantMoonsBlocks.CUT_BIRCH_WOOD);
        this.createSlabRecipes(Items.CHERRY_LOG, DistantMoonsBlocks.CUT_CHERRY_LOG);
        this.createSlabRecipes(Items.CHERRY_WOOD, DistantMoonsBlocks.CUT_CHERRY_WOOD);
        this.createSlabRecipes(Items.CRIMSON_HYPHAE, DistantMoonsBlocks.CUT_CRIMSON_HYPHAE);
        this.createSlabRecipes(Items.CRIMSON_STEM, DistantMoonsBlocks.CUT_CRIMSON_STEM);
        this.createSlabRecipes(Items.DARK_OAK_LOG, DistantMoonsBlocks.CUT_DARK_OAK_LOG);
        this.createSlabRecipes(Items.DARK_OAK_WOOD, DistantMoonsBlocks.CUT_DARK_OAK_WOOD);
        this.createSlabRecipes(Items.JUNGLE_LOG, DistantMoonsBlocks.CUT_JUNGLE_LOG);
        this.createSlabRecipes(Items.JUNGLE_WOOD, DistantMoonsBlocks.CUT_JUNGLE_WOOD);
        this.createSlabRecipes(Items.MANGROVE_LOG, DistantMoonsBlocks.CUT_MANGROVE_LOG);
        this.createSlabRecipes(Items.MANGROVE_WOOD, DistantMoonsBlocks.CUT_MANGROVE_WOOD);
        this.createSlabRecipes(Items.OAK_LOG, DistantMoonsBlocks.CUT_OAK_LOG);
        this.createSlabRecipes(Items.OAK_WOOD, DistantMoonsBlocks.CUT_OAK_WOOD);
        this.createSlabRecipes(Items.PALE_OAK_LOG, DistantMoonsBlocks.CUT_PALE_OAK_LOG);
        this.createSlabRecipes(Items.PALE_OAK_WOOD, DistantMoonsBlocks.CUT_PALE_OAK_WOOD);
        this.createSlabRecipes(Items.SPRUCE_LOG, DistantMoonsBlocks.CUT_SPRUCE_LOG);
        this.createSlabRecipes(Items.SPRUCE_WOOD, DistantMoonsBlocks.CUT_SPRUCE_WOOD);
        this.createSlabRecipes(Items.WARPED_HYPHAE, DistantMoonsBlocks.CUT_WARPED_HYPHAE);
        this.createSlabRecipes(Items.WARPED_STEM, DistantMoonsBlocks.CUT_WARPED_STEM);
        this.createSlabRecipes(Items.STRIPPED_ACACIA_LOG, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG);
        this.createSlabRecipes(Items.STRIPPED_ACACIA_WOOD, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD);
        this.createSlabRecipes(Items.STRIPPED_BAMBOO_BLOCK, DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK);
        this.createSlabRecipes(Items.STRIPPED_BIRCH_LOG, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG);
        this.createSlabRecipes(Items.STRIPPED_BIRCH_WOOD, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD);
        this.createSlabRecipes(Items.STRIPPED_CHERRY_LOG, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG);
        this.createSlabRecipes(Items.STRIPPED_CHERRY_WOOD, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD);
        this.createSlabRecipes(Items.STRIPPED_CRIMSON_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE);
        this.createSlabRecipes(Items.STRIPPED_CRIMSON_STEM, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM);
        this.createSlabRecipes(Items.STRIPPED_DARK_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG);
        this.createSlabRecipes(Items.STRIPPED_DARK_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD);
        this.createSlabRecipes(Items.STRIPPED_JUNGLE_LOG, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG);
        this.createSlabRecipes(Items.STRIPPED_JUNGLE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD);
        this.createSlabRecipes(Items.STRIPPED_MANGROVE_LOG, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG);
        this.createSlabRecipes(Items.STRIPPED_MANGROVE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD);
        this.createSlabRecipes(Items.STRIPPED_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG);
        this.createSlabRecipes(Items.STRIPPED_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD);
        this.createSlabRecipes(Items.STRIPPED_PALE_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG);
        this.createSlabRecipes(Items.STRIPPED_PALE_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD);
        this.createSlabRecipes(Items.STRIPPED_SPRUCE_LOG, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG);
        this.createSlabRecipes(Items.STRIPPED_SPRUCE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD);
        this.createSlabRecipes(Items.STRIPPED_WARPED_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE);
        this.createSlabRecipes(Items.STRIPPED_WARPED_STEM, DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM);

        //MISCELLANEOUS SMELTING
        this.createMetalSmeltingRecipes(DistantMoonsItems.IRON_ROD, DistantMoonsItems.WROUGHT_IRON_ROD, 0.0F, DEFAULT_SMELTING_TIME);

        //RESOURCE COMPRESSION
        this.createResourceCompressionRecipes(Items.CHARCOAL, DistantMoonsBlocks.CHARCOAL_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.COKE, DistantMoonsBlocks.COKE_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.RAW_DEEP_IRON, DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, DistantMoonsItems.REFINED_DEEP_IRON_INGOT, DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);

        //ORE SMELTING
        this.createOreSmeltingRecipes(
            List.of(DistantMoonsItems.RAW_DEEP_IRON, DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE),
            DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK,
            1.0F,
            DEFAULT_SMELTING_TIME * 2
        );
      }

      private void createCookingRecipes(ItemConvertible ingredient, ItemConvertible result, float experience, int cookingTime) {
        CookingRecipeJsonBuilder
            .create(Ingredient.ofItem(ingredient), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.SMELTING, SmeltingRecipe::new)
            .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smelting_" + getItemId(ingredient)));
        CookingRecipeJsonBuilder
            .create(Ingredient.ofItem(ingredient), RecipeCategory.FOOD, result, experience, cookingTime / 2, RecipeSerializer.SMOKING, SmokingRecipe::new)
            .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smoking_" + getItemId(ingredient)));
        CookingRecipeJsonBuilder
            .create(Ingredient.ofItem(ingredient), RecipeCategory.FOOD, result, experience, cookingTime * 3, RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new)
            .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/campfire_cooking_" + getItemId(ingredient)));
      }

      private void createMetalSmeltingRecipes(ItemConvertible ingredient, ItemConvertible result, float experience, int cookingTime) {
        CookingRecipeJsonBuilder
            .create(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, experience, cookingTime, RecipeSerializer.SMELTING, SmeltingRecipe::new)
            .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smelting_" + getItemId(ingredient)));
        CookingRecipeJsonBuilder
            .create(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, experience, cookingTime / 2, RecipeSerializer.BLASTING, BlastingRecipe::new)
            .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/blasting_" + getItemId(ingredient)));
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

      private void createSlabCraftingRecipe(ItemConvertible ingredient, ItemConvertible result) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
            .pattern("000")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createSlabCuttingRecipe(ItemConvertible ingredient, ItemConvertible result) {
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, 2)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createSlabRecipes(ItemConvertible ingredient, ItemConvertible result) {
        this.createSlabCraftingRecipe(ingredient, result);
        this.createSlabCuttingRecipe(ingredient, result);
      }

      private static String getItemId(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem()).toString().split(":")[1];
      }
    };
  }
}
