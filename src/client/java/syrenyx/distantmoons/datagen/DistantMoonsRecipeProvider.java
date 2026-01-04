package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;
import syrenyx.distantmoons.utility.ColorUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DistantMoonsRecipeProvider extends FabricRecipeProvider {

  public static final int DEFAULT_SMELTING_TIME = 200;

  public DistantMoonsRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public String getName() {
    return "DistantMoonsRecipeProvider";
  }

  @Override
  protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput recipeExporter) {

    return new RecipeProvider(registryLookup, recipeExporter) {

      //private final RegistryWrapper.Impl<Item> itemLookup = this.registries.getOrThrow(RegistryKeys.ITEM);

      @Override
      public void buildRecipes() {

        //BEAM CRAFTING
        this.createBeamRecipes(Items.STRIPPED_ACACIA_LOG, DistantMoonsBlocks.ACACIA_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_BIRCH_LOG, DistantMoonsBlocks.BIRCH_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_CHERRY_LOG, DistantMoonsBlocks.CHERRY_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_CRIMSON_STEM, DistantMoonsBlocks.CRIMSON_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_DARK_OAK_LOG, DistantMoonsBlocks.DARK_OAK_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_JUNGLE_LOG, DistantMoonsBlocks.JUNGLE_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_MANGROVE_LOG, DistantMoonsBlocks.MANGROVE_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_OAK_LOG, DistantMoonsBlocks.OAK_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_PALE_OAK_LOG, DistantMoonsBlocks.PALE_OAK_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_SPRUCE_LOG, DistantMoonsBlocks.SPRUCE_BEAM, "wooden_beam");
        this.createBeamRecipes(Items.STRIPPED_WARPED_STEM, DistantMoonsBlocks.WARPED_BEAM, "wooden_beam");

        //COOKING
        this.createCookingRecipes(Items.BROWN_MUSHROOM, DistantMoonsItems.ROASTED_BROWN_MUSHROOM, 0.35F, DEFAULT_SMELTING_TIME);

        //DYEING
        this.createDyeingRecipes(DistantMoonsBlocks.DYED_PILLOWS, DistantMoonsItemTags.DYED_PILLOW, true);

        //SLAB CRAFTING
        this.createSlabRecipes(Items.ACACIA_LOG, DistantMoonsBlocks.CUT_ACACIA_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.ACACIA_WOOD, DistantMoonsBlocks.CUT_ACACIA_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.BAMBOO_BLOCK, DistantMoonsBlocks.CUT_BAMBOO_BLOCK, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.BASALT, DistantMoonsBlocks.CUT_BASALT, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.BIRCH_LOG, DistantMoonsBlocks.CUT_BIRCH_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.BIRCH_WOOD, DistantMoonsBlocks.CUT_BIRCH_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.BONE_BLOCK, DistantMoonsBlocks.CUT_BONE_BLOCK, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.CHERRY_LOG, DistantMoonsBlocks.CUT_CHERRY_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.CHERRY_WOOD, DistantMoonsBlocks.CUT_CHERRY_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.CRIMSON_HYPHAE, DistantMoonsBlocks.CUT_CRIMSON_HYPHAE, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.CRIMSON_STEM, DistantMoonsBlocks.CUT_CRIMSON_STEM, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.DARK_OAK_LOG, DistantMoonsBlocks.CUT_DARK_OAK_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.DARK_OAK_WOOD, DistantMoonsBlocks.CUT_DARK_OAK_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.DEEPSLATE, DistantMoonsBlocks.CUT_DEEPSLATE, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(DistantMoonsBlocks.GRAY_PRISMARINE, DistantMoonsBlocks.GRAY_PRISMARINE_SLAB, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.JUNGLE_LOG, DistantMoonsBlocks.CUT_JUNGLE_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.JUNGLE_WOOD, DistantMoonsBlocks.CUT_JUNGLE_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.MANGROVE_LOG, DistantMoonsBlocks.CUT_MANGROVE_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.MANGROVE_WOOD, DistantMoonsBlocks.CUT_MANGROVE_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.OAK_LOG, DistantMoonsBlocks.CUT_OAK_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.OAK_WOOD, DistantMoonsBlocks.CUT_OAK_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.PALE_OAK_LOG, DistantMoonsBlocks.CUT_PALE_OAK_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.PALE_OAK_WOOD, DistantMoonsBlocks.CUT_PALE_OAK_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.PURPUR_PILLAR, DistantMoonsBlocks.CUT_PURPUR_PILLAR, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.QUARTZ_PILLAR, DistantMoonsBlocks.CUT_QUARTZ_PILLAR, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.SPRUCE_LOG, DistantMoonsBlocks.CUT_SPRUCE_LOG, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(Items.SPRUCE_WOOD, DistantMoonsBlocks.CUT_SPRUCE_WOOD, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.WARPED_HYPHAE, DistantMoonsBlocks.CUT_WARPED_HYPHAE, RecipeCategory.BUILDING_BLOCKS, "cut_wood");
        this.createSlabRecipes(Items.WARPED_STEM, DistantMoonsBlocks.CUT_WARPED_STEM, RecipeCategory.BUILDING_BLOCKS, "cut_log");
        this.createSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS, DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE, DistantMoonsBlocks.PALE_PRISMARINE_SLAB, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE_TILES, DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.POLISHED_BASALT, DistantMoonsBlocks.POLISHED_CUT_BASALT, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(DistantMoonsBlocks.PRISMARINE_TILES, DistantMoonsBlocks.PRISMARINE_TILE_SLAB, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.STRIPPED_ACACIA_LOG, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_ACACIA_WOOD, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_BAMBOO_BLOCK, DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK, RecipeCategory.BUILDING_BLOCKS, null);
        this.createSlabRecipes(Items.STRIPPED_BIRCH_LOG, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_BIRCH_WOOD, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_CHERRY_LOG, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_CHERRY_WOOD, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_CRIMSON_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_CRIMSON_STEM, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_DARK_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_DARK_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_JUNGLE_LOG, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_JUNGLE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_MANGROVE_LOG, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_MANGROVE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_PALE_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_PALE_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_SPRUCE_LOG, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_SPRUCE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_WARPED_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_WARPED_STEM, DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM, RecipeCategory.BUILDING_BLOCKS, "stripped_cut_log");

        this.createSlabRecipes(Items.WHITE_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.WHITE), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.LIGHT_GRAY_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.LIGHT_GRAY), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.GRAY_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.GRAY), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.BLACK_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.BLACK), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.BROWN_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.BROWN), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.RED_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.RED), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.ORANGE_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.ORANGE), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.YELLOW_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.YELLOW), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.LIME_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.LIME), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.GREEN_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.GREEN), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.CYAN_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.CYAN), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.LIGHT_BLUE_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.LIGHT_BLUE), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.BLUE_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.BLUE), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.PURPLE_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.PURPLE), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.MAGENTA_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.MAGENTA), RecipeCategory.MISC, "pillow");
        this.createSlabRecipes(Items.PINK_WOOL, DistantMoonsBlocks.DYED_PILLOWS.get(DyeColor.PINK), RecipeCategory.MISC, "pillow");


        //MISCELLANEOUS SMELTING
        this.createMetalSmeltingRecipes(DistantMoonsItems.IRON_ROD, DistantMoonsItems.WROUGHT_IRON_ROD, 0.0F, DEFAULT_SMELTING_TIME);

        //RESOURCE COMPRESSION
        this.createResourceCompressionRecipes(Items.CHARCOAL, DistantMoonsBlocks.CHARCOAL_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.COKE, DistantMoonsBlocks.COKE_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.RAW_DEEP_IRON, DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
        this.createResourceCompressionRecipes(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, DistantMoonsItems.REFINED_DEEP_IRON_INGOT, DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);

        this.createResourceDecompressionRecipe(DistantMoonsBlocks.WAXED_IRON_BLOCK, Items.IRON_INGOT, "iron_ingot");

        //ORE SMELTING
        this.createOreSmeltingRecipes(
            List.of(DistantMoonsItems.RAW_DEEP_IRON, DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE),
            DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK,
            1.0F,
            DEFAULT_SMELTING_TIME * 2
        );

        //POLE CRAFTING
        this.createPoleRecipes(Items.ACACIA_SLAB, DistantMoonsBlocks.ACACIA_POLE, "wooden_pole");
        this.createPoleRecipes(Items.BAMBOO_SLAB, DistantMoonsBlocks.BAMBOO_POLE, "wooden_pole");
        this.createPoleRecipes(Items.BIRCH_SLAB, DistantMoonsBlocks.BIRCH_POLE, "wooden_pole");
        this.createPoleRecipes(Items.CHERRY_SLAB, DistantMoonsBlocks.CHERRY_POLE, "wooden_pole");
        this.createPoleRecipes(Items.CRIMSON_SLAB, DistantMoonsBlocks.CRIMSON_POLE, "wooden_pole");
        this.createPoleRecipes(Items.DARK_OAK_SLAB, DistantMoonsBlocks.DARK_OAK_POLE, "wooden_pole");
        this.createPoleRecipes(Items.JUNGLE_SLAB, DistantMoonsBlocks.JUNGLE_POLE, "wooden_pole");
        this.createPoleRecipes(Items.MANGROVE_SLAB, DistantMoonsBlocks.MANGROVE_POLE, "wooden_pole");
        this.createPoleRecipes(Items.OAK_SLAB, DistantMoonsBlocks.OAK_POLE, "wooden_pole");
        this.createPoleRecipes(Items.PALE_OAK_SLAB, DistantMoonsBlocks.PALE_OAK_POLE, "wooden_pole");
        this.createPoleRecipes(Items.SPRUCE_SLAB, DistantMoonsBlocks.SPRUCE_POLE, "wooden_pole");
        this.createPoleRecipes(Items.WARPED_SLAB, DistantMoonsBlocks.WARPED_POLE, "wooden_pole");

        //SMELTING
        this.createSmeltingRecipes(Map.ofEntries(
            Map.entry(Items.DARK_PRISMARINE, DistantMoonsBlocks.GRAY_PRISMARINE),
            Map.entry(Items.DARK_PRISMARINE_SLAB, DistantMoonsBlocks.GRAY_PRISMARINE_SLAB),
            Map.entry(Items.DARK_PRISMARINE_STAIRS, DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS),
            Map.entry(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB, DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB),
            Map.entry(Items.PRISMARINE, DistantMoonsBlocks.PALE_PRISMARINE),
            Map.entry(Items.PRISMARINE_BRICK_SLAB, DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB),
            Map.entry(Items.PRISMARINE_BRICK_STAIRS, DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS),
            Map.entry(DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB, DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB),
            Map.entry(Items.PRISMARINE_BRICKS, DistantMoonsBlocks.PALE_PRISMARINE_BRICKS),
            Map.entry(Items.PRISMARINE_SHARD, DistantMoonsItems.PALE_PRISMARINE_SHARD),
            Map.entry(Items.PRISMARINE_SLAB, DistantMoonsBlocks.PALE_PRISMARINE_SLAB),
            Map.entry(Items.PRISMARINE_STAIRS, DistantMoonsBlocks.PALE_PRISMARINE_STAIRS),
            Map.entry(DistantMoonsBlocks.PRISMARINE_TILE_SLAB, DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB),
            Map.entry(DistantMoonsBlocks.PRISMARINE_TILE_STAIRS, DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS),
            Map.entry(DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB, DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB),
            Map.entry(DistantMoonsBlocks.PRISMARINE_TILES, DistantMoonsBlocks.PALE_PRISMARINE_TILES),
            Map.entry(Items.PRISMARINE_WALL, DistantMoonsBlocks.PALE_PRISMARINE_WALL),
            Map.entry(DistantMoonsBlocks.PRISMARINE_WALL_SLAB, DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB),
            Map.entry(Items.SEA_LANTERN, DistantMoonsBlocks.PALE_SEA_LANTERN)
        ));

        //STAIRS CRAFTING
        this.createStairsRecipes(DistantMoonsBlocks.GRAY_PRISMARINE, DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS, null);
        this.createStairsRecipes(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS, DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS, null);
        this.createStairsRecipes(DistantMoonsBlocks.PALE_PRISMARINE, DistantMoonsBlocks.PALE_PRISMARINE_STAIRS, null);
        this.createStairsRecipes(DistantMoonsBlocks.PALE_PRISMARINE_TILES, DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS, null);
        this.createStairsRecipes(DistantMoonsBlocks.PRISMARINE_TILES, DistantMoonsBlocks.PRISMARINE_TILE_STAIRS, null);

        //WALL CRAFTING
        this.createWallRecipes(DistantMoonsBlocks.PALE_PRISMARINE, DistantMoonsBlocks.PALE_PRISMARINE_WALL, null);

        //WALL SLAB CRAFTING
        this.createWallSlabRecipes(Items.ACACIA_PLANKS, DistantMoonsBlocks.ACACIA_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.ANDESITE, DistantMoonsBlocks.ANDESITE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.BAMBOO_MOSAIC, DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.BAMBOO_PLANKS, DistantMoonsBlocks.BAMBOO_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.BIRCH_PLANKS, DistantMoonsBlocks.BIRCH_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.BLACKSTONE, DistantMoonsBlocks.BLACKSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.BRICKS, DistantMoonsBlocks.BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.CHERRY_PLANKS, DistantMoonsBlocks.CHERRY_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.COBBLED_DEEPSLATE, DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.COBBLESTONE, DistantMoonsBlocks.COBBLESTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.CRIMSON_PLANKS, DistantMoonsBlocks.CRIMSON_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.CUT_COPPER, DistantMoonsBlocks.CUT_COPPER_WALL_SLAB, "cut_copper_wall_slab");
        this.createWallSlabRecipes(Items.CUT_RED_SANDSTONE, DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.CUT_SANDSTONE, DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DARK_OAK_PLANKS, DistantMoonsBlocks.DARK_OAK_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.DARK_PRISMARINE, DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DEEPSLATE_BRICKS, DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DEEPSLATE_TILES, DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DIORITE, DistantMoonsBlocks.DIORITE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.END_STONE_BRICKS, DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.EXPOSED_CUT_COPPER, DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB, "exposed_cut_copper_wall_slab");
        this.createWallSlabRecipes(Items.GRANITE, DistantMoonsBlocks.GRANITE_WALL_SLAB, null);
        this.createWallSlabRecipes(DistantMoonsBlocks.GRAY_PRISMARINE, DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.JUNGLE_PLANKS, DistantMoonsBlocks.JUNGLE_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.MANGROVE_PLANKS, DistantMoonsBlocks.MANGROVE_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.MOSSY_COBBLESTONE, DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.MOSSY_STONE_BRICKS, DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.MUD_BRICKS, DistantMoonsBlocks.MUD_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.NETHER_BRICKS, DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.OAK_PLANKS, DistantMoonsBlocks.OAK_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.OXIDIZED_CUT_COPPER, DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB, "oxidized_cut_copper_wall_slab");
        this.createWallSlabRecipes(Items.PALE_OAK_PLANKS, DistantMoonsBlocks.PALE_OAK_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS, DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE_TILES, DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB, null);
        this.createWallSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE, DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.POLISHED_ANDESITE, DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.POLISHED_BLACKSTONE_BRICKS, DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.POLISHED_BLACKSTONE, DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.POLISHED_DEEPSLATE, DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.POLISHED_DIORITE, DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.POLISHED_GRANITE, DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.POLISHED_TUFF, DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.PRISMARINE_BRICKS, DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(DistantMoonsBlocks.PRISMARINE_TILES, DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.PRISMARINE, DistantMoonsBlocks.PRISMARINE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.PURPUR_BLOCK, DistantMoonsBlocks.PURPUR_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.QUARTZ_BLOCK, DistantMoonsBlocks.QUARTZ_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.RED_NETHER_BRICKS, DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.RED_SANDSTONE, DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.RESIN_BRICKS, DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.SANDSTONE, DistantMoonsBlocks.SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.SMOOTH_QUARTZ, DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.SMOOTH_RED_SANDSTONE, DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.SMOOTH_SANDSTONE, DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.SMOOTH_STONE, DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.SPRUCE_PLANKS, DistantMoonsBlocks.SPRUCE_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.STONE_BRICKS, DistantMoonsBlocks.STONE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.STONE, DistantMoonsBlocks.STONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.TUFF_BRICKS, DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.TUFF, DistantMoonsBlocks.TUFF_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.WARPED_PLANKS, DistantMoonsBlocks.WARPED_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.WAXED_CUT_COPPER, DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB, "waxed_cut_copper_wall_slab");
        this.createWallSlabRecipes(Items.WAXED_EXPOSED_CUT_COPPER, DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB, "waxed_exposed_cut_copper_wall_slab");
        this.createWallSlabRecipes(Items.WAXED_OXIDIZED_CUT_COPPER, DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB, "waxed_oxidized_cut_copper_wall_slab");
        this.createWallSlabRecipes(Items.WAXED_WEATHERED_CUT_COPPER, DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB, "waxed_weathered_cut_copper_wall_slab");
        this.createWallSlabRecipes(Items.WEATHERED_CUT_COPPER, DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB, "weathered_cut_copper_wall_slab");

        //WAXING RECIPES
        this.createWaxingRecipe(DistantMoonsBlocks.CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB);
        this.createWaxingRecipe(DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB);
        this.createWaxingRecipe(DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB);
        this.createWaxingRecipe(DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB);
        this.createWaxingRecipe(Blocks.IRON_BLOCK, DistantMoonsBlocks.WAXED_IRON_BLOCK);
        this.createWaxingRecipe(DistantMoonsBlocks.EXPOSED_IRON_BLOCK, DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK);
        this.createWaxingRecipe(DistantMoonsBlocks.WEATHERED_IRON_BLOCK, DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK);
        this.createWaxingRecipe(DistantMoonsBlocks.RUSTED_IRON_BLOCK, DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK);
      }

      private void createBeamRecipes(ItemLike ingredient, ItemLike result, String group) {
        createBeamCraftingRecipe(ingredient, result, group);
        createBeamCuttingRecipe(ingredient, result);
      }

      private void createBeamCraftingRecipe(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 12)
            .group(group)
            .pattern("0").pattern("0").pattern("0")
            .define('0', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createBeamCuttingRecipe(ItemLike ingredient, ItemLike result) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.MISC, result, 4)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createCookingRecipes(ItemLike ingredient, ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
            .generic(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
            .unlockedBy(getHasName(ingredient), this.has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smelting_" + getItemId(ingredient)));
        SimpleCookingRecipeBuilder
            .generic(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, cookingTime / 2, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new)
            .unlockedBy(getHasName(ingredient), this.has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smoking_" + getItemId(ingredient)));
        SimpleCookingRecipeBuilder
            .generic(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, cookingTime * 3, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new)
            .unlockedBy(getHasName(ingredient), this.has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/campfire_cooking_" + getItemId(ingredient)));
      }

      private void createDyeingRecipes(Map<DyeColor, Block> blocks, TagKey<Item> ingredient, boolean single) {
        for (DyeColor dyeColor : DyeColor.values()) {
          ItemLike result = blocks.get(dyeColor);
          ItemLike dye = ColorUtil.getDyeItemByColor(dyeColor);
          String group = "dyed_" + getItemId(result);
          String path = UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/dyeing");
          if (single) this.shapeless(RecipeCategory.MISC, result)
              .group(group)
              .requires(dye)
              .requires(ingredient)
              .unlockedBy(getHasName(dye), this.has(dye))
              .save(this.output, path);
          else this.shaped(RecipeCategory.MISC, result, 8)
              .group(group)
              .pattern("000").pattern("010").pattern("000")
              .define('0', ingredient).define('1', dye)
              .unlockedBy(getHasName(dye), this.has(dye))
              .save(this.output, path);
        }
      }

      private void createMetalSmeltingRecipes(ItemLike ingredient, ItemLike result, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder
            .generic(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
            .unlockedBy(getHasName(ingredient), this.has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smelting_" + getItemId(ingredient)));
        SimpleCookingRecipeBuilder
            .generic(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookingTime / 2, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new)
            .unlockedBy(getHasName(ingredient), this.has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/blasting_" + getItemId(ingredient)));
      }

      private void createOreSmeltingRecipes(List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime) {
        for (ItemLike ingredient : ingredients) {
          SimpleCookingRecipeBuilder
              .generic(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookingTime, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
              .group(getItemId(result) + "_from_ore")
              .unlockedBy(getHasName(ingredient), this.has(ingredient))
              .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/smelting_" + getItemId(ingredient)));
          SimpleCookingRecipeBuilder
              .generic(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookingTime / 2, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new)
              .group(getItemId(result) + "_from_ore")
              .unlockedBy(getHasName(ingredient), this.has(ingredient))
              .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/blasting_" + getItemId(ingredient)));
        }
      }

      private void createPoleRecipes(ItemLike ingredient, ItemLike result, String group) {
        createPoleCraftingRecipe(ingredient, result, group);
        createPoleCuttingRecipe(ingredient, result);
      }

      private void createPoleCraftingRecipe(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
            .group(group)
            .pattern("0").pattern("0").pattern("0")
            .define('0', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createPoleCuttingRecipe(ItemLike ingredient, ItemLike result) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.MISC, result, 2)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createResourceCompressionRecipes(ItemLike... items) {
        ItemLike current = null;
        for (ItemLike next : items) {
          if (current != null) {
            this.createResourceCompressionRecipe(current, next, null);
            this.createResourceDecompressionRecipe(next, current, null);
          }
          current = next;
        }
      }

      private void createResourceCompressionRecipe(ItemLike ingredient, ItemLike result, @Nullable String groupOverride) {
        this.shaped(result instanceof Block ? RecipeCategory.BUILDING_BLOCKS : RecipeCategory.MISC, result)
            .group(groupOverride != null ? groupOverride : getItemId(result) + "_from_resource_compression")
            .pattern("000").pattern("000").pattern("000")
            .define('0', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/compressing_" + getItemId(ingredient)));
      }

      private void createResourceDecompressionRecipe(ItemLike ingredient, ItemLike result, @Nullable String groupOverride) {
        this.shapeless(result instanceof Block ? RecipeCategory.BUILDING_BLOCKS : RecipeCategory.MISC, result, 9)
            .group(groupOverride != null ? groupOverride : getItemId(result) + "_from_resource_compression")
            .requires(ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/decompressing_" + getItemId(ingredient)));
      }

      private void createSlabRecipes(ItemLike ingredient, ItemLike result, RecipeCategory category, @Nullable String group) {
        this.createSlabCraftingRecipe(ingredient, result, category, group);
        this.createSlabCuttingRecipe(ingredient, result);
      }

      private void createWallSlabRecipes(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.createWallSlabCraftingRecipe(ingredient, result, group);
        this.createSlabCuttingRecipe(ingredient, result);
      }

      private void createSlabCraftingRecipe(ItemLike ingredient, ItemLike result, RecipeCategory category, @Nullable String group) {
        this.shaped(category, result, 6)
            .group(group)
            .pattern("000")
            .define('0', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createWallSlabCraftingRecipe(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
            .group(group)
            .pattern("0").pattern("0").pattern("0")
            .define('0', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createSlabCuttingRecipe(ItemLike ingredient, ItemLike result) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.MISC, result, 2)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createSmeltingRecipes(Map<ItemLike, ItemLike> itemMap) {
        for (ItemLike ingredient : itemMap.keySet()) {
          SimpleCookingRecipeBuilder
              .generic(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, itemMap.get(ingredient), 0, DEFAULT_SMELTING_TIME, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
              .unlockedBy(getHasName(ingredient), this.has(ingredient))
              .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(itemMap.get(ingredient)) + "/smelting"));
        }
      }

      private void createStairsRecipes(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.createStairsCraftingRecipe(ingredient, result, group);
        this.createStairsCuttingRecipe(ingredient, result);
      }

      private void createStairsCraftingRecipe(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
            .group(group)
            .pattern("0  ").pattern("00 ").pattern("000")
            .define('0', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createStairsCuttingRecipe(ItemLike ingredient, ItemLike result) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.MISC, result)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createWallRecipes(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.createWallCraftingRecipe(ingredient, result, group);
        this.createWallCuttingRecipe(ingredient, result);
      }

      private void createWallCraftingRecipe(ItemLike ingredient, ItemLike result, @Nullable String group) {
        this.shaped(RecipeCategory.MISC, result, 6)
            .group(group)
            .pattern("000").pattern("000")
            .define('0', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createWallCuttingRecipe(ItemLike ingredient, ItemLike result) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.MISC, result)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createWaxingRecipe(ItemLike ingredient, ItemLike result) {
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, result)
            .group(getItemId(result))
            .requires(ingredient)
            .requires(Items.HONEYCOMB)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(this.output, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/waxing"));
      }

      private static String getItemId(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).toString().split(":")[1];
      }
    };
  }
}
