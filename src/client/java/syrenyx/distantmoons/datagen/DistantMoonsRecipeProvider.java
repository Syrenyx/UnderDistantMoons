package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import org.apache.logging.log4j.core.jackson.MapEntry;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

import java.util.List;
import java.util.Map;
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

        //SLAB CRAFTING
        this.createSlabRecipes(Items.ACACIA_LOG, DistantMoonsBlocks.CUT_ACACIA_LOG, "cut_log");
        this.createSlabRecipes(Items.ACACIA_WOOD, DistantMoonsBlocks.CUT_ACACIA_WOOD, "cut_wood");
        this.createSlabRecipes(Items.BAMBOO_BLOCK, DistantMoonsBlocks.CUT_BAMBOO_BLOCK, null);
        this.createSlabRecipes(Items.BASALT, DistantMoonsBlocks.CUT_BASALT, null);
        this.createSlabRecipes(Items.BIRCH_LOG, DistantMoonsBlocks.CUT_BIRCH_LOG, "cut_log");
        this.createSlabRecipes(Items.BIRCH_WOOD, DistantMoonsBlocks.CUT_BIRCH_WOOD, "cut_wood");
        this.createSlabRecipes(Items.BONE_BLOCK, DistantMoonsBlocks.CUT_BONE_BLOCK, null);
        this.createSlabRecipes(Items.CHERRY_LOG, DistantMoonsBlocks.CUT_CHERRY_LOG, "cut_log");
        this.createSlabRecipes(Items.CHERRY_WOOD, DistantMoonsBlocks.CUT_CHERRY_WOOD, "cut_wood");
        this.createSlabRecipes(Items.CRIMSON_HYPHAE, DistantMoonsBlocks.CUT_CRIMSON_HYPHAE, "cut_wood");
        this.createSlabRecipes(Items.CRIMSON_STEM, DistantMoonsBlocks.CUT_CRIMSON_STEM, "cut_log");
        this.createSlabRecipes(Items.DARK_OAK_LOG, DistantMoonsBlocks.CUT_DARK_OAK_LOG, "cut_log");
        this.createSlabRecipes(Items.DARK_OAK_WOOD, DistantMoonsBlocks.CUT_DARK_OAK_WOOD, "cut_wood");
        this.createSlabRecipes(Items.DEEPSLATE, DistantMoonsBlocks.CUT_DEEPSLATE, null);
        this.createSlabRecipes(DistantMoonsBlocks.GRAY_PRISMARINE, DistantMoonsBlocks.GRAY_PRISMARINE_SLAB, null);
        this.createSlabRecipes(Items.JUNGLE_LOG, DistantMoonsBlocks.CUT_JUNGLE_LOG, "cut_log");
        this.createSlabRecipes(Items.JUNGLE_WOOD, DistantMoonsBlocks.CUT_JUNGLE_WOOD, "cut_wood");
        this.createSlabRecipes(Items.MANGROVE_LOG, DistantMoonsBlocks.CUT_MANGROVE_LOG, "cut_log");
        this.createSlabRecipes(Items.MANGROVE_WOOD, DistantMoonsBlocks.CUT_MANGROVE_WOOD, "cut_wood");
        this.createSlabRecipes(Items.OAK_LOG, DistantMoonsBlocks.CUT_OAK_LOG, "cut_log");
        this.createSlabRecipes(Items.OAK_WOOD, DistantMoonsBlocks.CUT_OAK_WOOD, "cut_wood");
        this.createSlabRecipes(Items.PALE_OAK_LOG, DistantMoonsBlocks.CUT_PALE_OAK_LOG, "cut_log");
        this.createSlabRecipes(Items.PALE_OAK_WOOD, DistantMoonsBlocks.CUT_PALE_OAK_WOOD, "cut_wood");
        this.createSlabRecipes(Items.PURPUR_PILLAR, DistantMoonsBlocks.CUT_PURPUR_PILLAR, null);
        this.createSlabRecipes(Items.QUARTZ_PILLAR, DistantMoonsBlocks.CUT_QUARTZ_PILLAR, null);
        this.createSlabRecipes(Items.SPRUCE_LOG, DistantMoonsBlocks.CUT_SPRUCE_LOG, "cut_log");
        this.createSlabRecipes(Items.SPRUCE_WOOD, DistantMoonsBlocks.CUT_SPRUCE_WOOD, "cut_wood");
        this.createSlabRecipes(Items.WARPED_HYPHAE, DistantMoonsBlocks.CUT_WARPED_HYPHAE, "cut_wood");
        this.createSlabRecipes(Items.WARPED_STEM, DistantMoonsBlocks.CUT_WARPED_STEM, "cut_log");
        this.createSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS, DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB, null);
        this.createSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE, DistantMoonsBlocks.PALE_PRISMARINE_SLAB, null);
        this.createSlabRecipes(DistantMoonsBlocks.PALE_PRISMARINE_TILES, DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB, null);
        this.createSlabRecipes(Items.POLISHED_BASALT, DistantMoonsBlocks.POLISHED_CUT_BASALT, null);
        this.createSlabRecipes(DistantMoonsBlocks.PRISMARINE_TILES, DistantMoonsBlocks.PRISMARINE_TILE_SLAB, null);
        this.createSlabRecipes(Items.STRIPPED_ACACIA_LOG, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_ACACIA_WOOD, DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_BAMBOO_BLOCK, DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK, null);
        this.createSlabRecipes(Items.STRIPPED_BIRCH_LOG, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_BIRCH_WOOD, DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_CHERRY_LOG, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_CHERRY_WOOD, DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_CRIMSON_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_CRIMSON_STEM, DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_DARK_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_DARK_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_JUNGLE_LOG, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_JUNGLE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_MANGROVE_LOG, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_MANGROVE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_PALE_OAK_LOG, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_PALE_OAK_WOOD, DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_SPRUCE_LOG, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG, "stripped_cut_log");
        this.createSlabRecipes(Items.STRIPPED_SPRUCE_WOOD, DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_WARPED_HYPHAE, DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE, "stripped_cut_wood");
        this.createSlabRecipes(Items.STRIPPED_WARPED_STEM, DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM, "stripped_cut_log");

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
        this.createWallSlabRecipes(Items.CUT_RED_SANDSTONE, DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.CUT_SANDSTONE, DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DARK_OAK_PLANKS, DistantMoonsBlocks.DARK_OAK_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.DARK_PRISMARINE, DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DEEPSLATE_BRICKS, DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DEEPSLATE_TILES, DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.DIORITE, DistantMoonsBlocks.DIORITE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.END_STONE_BRICKS, DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.GRANITE, DistantMoonsBlocks.GRANITE_WALL_SLAB, null);
        this.createWallSlabRecipes(DistantMoonsBlocks.GRAY_PRISMARINE, DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.JUNGLE_PLANKS, DistantMoonsBlocks.JUNGLE_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.MANGROVE_PLANKS, DistantMoonsBlocks.MANGROVE_WALL_SLAB, "plank_wall_slab");
        this.createWallSlabRecipes(Items.MOSSY_COBBLESTONE, DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.MOSSY_STONE_BRICKS, DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.MUD_BRICKS, DistantMoonsBlocks.MUD_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.NETHER_BRICKS, DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB, null);
        this.createWallSlabRecipes(Items.OAK_PLANKS, DistantMoonsBlocks.OAK_WALL_SLAB, "plank_wall_slab");
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
        this.createSlabRecipes(DistantMoonsBlocks.PRISMARINE_TILES, DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB, null);
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
      }

      private void createBeamRecipes(ItemConvertible ingredient, ItemConvertible result, String group) {
        createBeamCraftingRecipe(ingredient, result, group);
        createBeamCuttingRecipe(ingredient, result);
      }

      private void createBeamCraftingRecipe(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, result, 12)
            .group(group)
            .pattern("0").pattern("0").pattern("0")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createBeamCuttingRecipe(ItemConvertible ingredient, ItemConvertible result) {
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, 4)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
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

      private void createPoleRecipes(ItemConvertible ingredient, ItemConvertible result, String group) {
        createPoleCraftingRecipe(ingredient, result, group);
        createPoleCuttingRecipe(ingredient, result);
      }

      private void createPoleCraftingRecipe(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
            .group(group)
            .pattern("0").pattern("0").pattern("0")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createPoleCuttingRecipe(ItemConvertible ingredient, ItemConvertible result) {
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, 2)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
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

      private void createSlabRecipes(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createSlabCraftingRecipe(ingredient, result, group);
        this.createSlabCuttingRecipe(ingredient, result);
      }

      private void createWallSlabRecipes(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createWallSlabCraftingRecipe(ingredient, result, group);
        this.createSlabCuttingRecipe(ingredient, result);
      }

      private void createSlabCraftingRecipe(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
            .group(group)
            .pattern("000")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createWallSlabCraftingRecipe(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
            .group(group)
            .pattern("0").pattern("0").pattern("0")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createSlabCuttingRecipe(ItemConvertible ingredient, ItemConvertible result) {
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result, 2)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createSmeltingRecipes(Map<ItemConvertible, ItemConvertible> itemMap) {
        for (ItemConvertible ingredient : itemMap.keySet()) {
          CookingRecipeJsonBuilder
              .create(Ingredient.ofItem(ingredient), RecipeCategory.BUILDING_BLOCKS, itemMap.get(ingredient), 0, DEFAULT_SMELTING_TIME, RecipeSerializer.SMELTING, SmeltingRecipe::new)
              .criterion(hasItem(ingredient), this.conditionsFromItem(ingredient))
              .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(itemMap.get(ingredient)) + "/smelting"));
        }
      }

      private void createStairsRecipes(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createStairsCraftingRecipe(ingredient, result, group);
        this.createStairsCuttingRecipe(ingredient, result);
      }

      private void createStairsCraftingRecipe(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
            .group(group)
            .pattern("0  ").pattern("00 ").pattern("000")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createStairsCuttingRecipe(ItemConvertible ingredient, ItemConvertible result) {
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private void createWallRecipes(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createWallCraftingRecipe(ingredient, result, group);
        this.createWallCuttingRecipe(ingredient, result);
      }

      private void createWallCraftingRecipe(ItemConvertible ingredient, ItemConvertible result, @Nullable String group) {
        this.createShaped(RecipeCategory.MISC, result, 6)
            .group(group)
            .pattern("000").pattern("000")
            .input('0', ingredient)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/crafting"));
      }

      private void createWallCuttingRecipe(ItemConvertible ingredient, ItemConvertible result) {
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItem(ingredient), RecipeCategory.MISC, result)
            .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
            .offerTo(this.exporter, UnderDistantMoons.withPrefixedNamespace(getItemId(result) + "/stonecutting"));
      }

      private static String getItemId(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem()).toString().split(":")[1];
      }
    };
  }
}
