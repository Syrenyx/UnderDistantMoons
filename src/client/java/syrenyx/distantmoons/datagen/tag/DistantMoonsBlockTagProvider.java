package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBlockTagProvider extends FabricTagProvider.BlockTagProvider {

  public DistantMoonsBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.@NonNull Provider provider) {

    //BLOCK GROUPS
    this.valueLookupBuilder(DistantMoonsBlockTags.BRICK_FENCE).add(Blocks.NETHER_BRICK_FENCE);
    this.valueLookupBuilder(DistantMoonsBlockTags.CHAIN).add(DistantMoonsBlocks.DEEP_IRON_CHAIN);
    this.valueLookupBuilder(DistantMoonsBlockTags.FIXED_LADDER).add(
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER,
        DistantMoonsBlocks.FIXED_IRON_LADDER,
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.GLASS)
        .addTag(DistantMoonsBlockTags.COLORED_STAINED_GLASS)
        .add(Blocks.GLASS, Blocks.TINTED_GLASS);
    this.valueLookupBuilder(DistantMoonsBlockTags.GLASS_PANE)
        .addTag(DistantMoonsBlockTags.COLORED_STAINED_GLASS_PANE)
        .add(Blocks.GLASS_PANE);
    this.valueLookupBuilder(DistantMoonsBlockTags.METAL_BAR_DOOR).add(
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR,
        DistantMoonsBlocks.IRON_BAR_DOOR,
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.BARS).add(
        DistantMoonsBlocks.DEEP_IRON_BARS,
        DistantMoonsBlocks.WROUGHT_IRON_BARS
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.SPIKED_FENCE).add(
        DistantMoonsBlocks.DEEP_IRON_FENCE,
        DistantMoonsBlocks.IRON_FENCE,
        DistantMoonsBlocks.WROUGHT_IRON_FENCE
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.WALL).add(
        DistantMoonsBlocks.PALE_PRISMARINE_WALL
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.WOODEN_BEAM).add(
        DistantMoonsBlocks.ACACIA_BEAM,
        DistantMoonsBlocks.BIRCH_BEAM,
        DistantMoonsBlocks.CHERRY_BEAM,
        DistantMoonsBlocks.CRIMSON_BEAM,
        DistantMoonsBlocks.DARK_OAK_BEAM,
        DistantMoonsBlocks.JUNGLE_BEAM,
        DistantMoonsBlocks.MANGROVE_BEAM,
        DistantMoonsBlocks.OAK_BEAM,
        DistantMoonsBlocks.PALE_OAK_BEAM,
        DistantMoonsBlocks.SPRUCE_BEAM,
        DistantMoonsBlocks.WARPED_BEAM
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.WOODEN_FENCE).add(
        Blocks.ACACIA_FENCE,
        Blocks.BAMBOO_FENCE,
        Blocks.BIRCH_FENCE,
        Blocks.CHERRY_FENCE,
        Blocks.CRIMSON_FENCE,
        Blocks.DARK_OAK_FENCE,
        Blocks.JUNGLE_FENCE,
        Blocks.MANGROVE_FENCE,
        Blocks.OAK_FENCE,
        Blocks.PALE_OAK_FENCE,
        Blocks.SPRUCE_FENCE,
        Blocks.WARPED_FENCE
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.WOODEN_POLE).add(
        DistantMoonsBlocks.ACACIA_POLE,
        DistantMoonsBlocks.BAMBOO_POLE,
        DistantMoonsBlocks.BIRCH_POLE,
        DistantMoonsBlocks.CHERRY_POLE,
        DistantMoonsBlocks.CRIMSON_POLE,
        DistantMoonsBlocks.DARK_OAK_POLE,
        DistantMoonsBlocks.JUNGLE_POLE,
        DistantMoonsBlocks.MANGROVE_POLE,
        DistantMoonsBlocks.OAK_POLE,
        DistantMoonsBlocks.PALE_OAK_POLE,
        DistantMoonsBlocks.SPRUCE_POLE,
        DistantMoonsBlocks.WARPED_POLE
    );

    //SYED BLOCK GROUPS
    this.valueLookupBuilder(DistantMoonsBlockTags.DYED_PILLOW).addAll(
        DistantMoonsBlocks.DYED_PILLOWS.values()
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.COLORED_STAINED_GLASS).add(
        Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS,
        Blocks.GRAY_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS,
        Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS,
        Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.COLORED_STAINED_GLASS_PANE).add(
        Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE,
        Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
        Blocks.LIME_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS_PANE,
        Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE
    );

    //OXIDIZABLE BLOCK GROUPS
    this.valueLookupBuilder(DistantMoonsBlockTags.OXIDIZABLE_CUT_COPPER_WALL_SLAB).add(
        DistantMoonsBlocks.CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB,
        DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB, DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.OXIDIZABLE_IRON_BLOCK).add(
        Blocks.IRON_BLOCK, DistantMoonsBlocks.EXPOSED_IRON_BLOCK, DistantMoonsBlocks.WEATHERED_IRON_BLOCK, DistantMoonsBlocks.RUSTED_IRON_BLOCK,
        DistantMoonsBlocks.WAXED_IRON_BLOCK, DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK, DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK, DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK
    );

    //CONNECTION TARGETS
    this.valueLookupBuilder(DistantMoonsBlockTags.NEVER_CONNECT_TO)
        .forceAddTag(BlockTags.SHULKER_BOXES)
        .add(Blocks.BARRIER, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.PUMPKIN);

    this.valueLookupBuilder(DistantMoonsBlockTags.BARS_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.GLASS_PANE)
        .addTag(DistantMoonsBlockTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsBlockTags.BARS_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsBlockTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.BEAM_ALWAYS_CONNECTS_TO);
    this.valueLookupBuilder(DistantMoonsBlockTags.BEAM_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.BRICK_FENCE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.BRICK_FENCE);
    this.valueLookupBuilder(DistantMoonsBlockTags.BRICK_FENCE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsBlockTags.FIXED_LADDER)
        .addTag(DistantMoonsBlockTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.FIXED_LADDER_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.GLASS_PANE)
        .addTag(DistantMoonsBlockTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsBlockTags.FIXED_LADDER_ATTACHES_TO)
        .addTag(DistantMoonsBlockTags.FIXED_LADDER)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsBlockTags.FIXED_LADDER_CAPPED_TO)
        .forceAddTag(BlockTags.BARS);
    this.valueLookupBuilder(DistantMoonsBlockTags.FIXED_LADDER_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.GLASS_PANE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.GLASS_PANE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsBlockTags.GLASS_PANE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.METAL_BAR_DOOR_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.POLE_ALWAYS_CONNECTS_TO);
    this.valueLookupBuilder(DistantMoonsBlockTags.POLE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.SPIKED_FENCE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsBlockTags.SPIKED_FENCE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsBlockTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);
    this.valueLookupBuilder(DistantMoonsBlockTags.SPIKED_FENCE_NOT_BLOCKED_BY)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO)
        .forceAddTag(BlockTags.CHAINS)
        .forceAddTag(BlockTags.LEAVES)
        .forceAddTag(BlockTags.ICE)
        .add(Blocks.SLIME_BLOCK, Blocks.SNOW_BLOCK);

    this.valueLookupBuilder(DistantMoonsBlockTags.WALL_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.GLASS_PANE)
        .addTag(DistantMoonsBlockTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsBlockTags.WALL_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsBlockTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    this.valueLookupBuilder(DistantMoonsBlockTags.WOODEN_FENCE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.WOODEN_FENCE);
    this.valueLookupBuilder(DistantMoonsBlockTags.WOODEN_FENCE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsBlockTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsBlockTags.FIXED_LADDER)
        .addTag(DistantMoonsBlockTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsBlockTags.NEVER_CONNECT_TO);

    //IMMUNITIES
    this.valueLookupBuilder(DistantMoonsBlockTags.IMMUNE_TO_DRAGON);
    this.valueLookupBuilder(DistantMoonsBlockTags.IMMUNE_TO_WITHER);

    //MINING TOOL GROUPS
    this.valueLookupBuilder(DistantMoonsBlockTags.MINING_TIER_DIAMOND);
    this.valueLookupBuilder(DistantMoonsBlockTags.MINING_TIER_IRON);
    this.valueLookupBuilder(DistantMoonsBlockTags.MINING_TIER_STONE)
        .addTag(DistantMoonsBlockTags.OXIDIZABLE_CUT_COPPER_WALL_SLAB)
        .addTag(DistantMoonsBlockTags.OXIDIZABLE_IRON_BLOCK)
        .add(
            DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE,
            DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK,
            DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE,
            DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE,
            DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK,
            DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK
        );
    this.valueLookupBuilder(DistantMoonsBlockTags.MINING_TYPE_AXE).add(
        DistantMoonsBlocks.ACACIA_WALL_SLAB,
        DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB,
        DistantMoonsBlocks.BAMBOO_WALL_SLAB,
        DistantMoonsBlocks.BIRCH_WALL_SLAB,
        DistantMoonsBlocks.CHERRY_WALL_SLAB,
        DistantMoonsBlocks.CRIMSON_WALL_SLAB,
        DistantMoonsBlocks.CUT_ACACIA_LOG,
        DistantMoonsBlocks.CUT_ACACIA_WOOD,
        DistantMoonsBlocks.CUT_BAMBOO_BLOCK,
        DistantMoonsBlocks.CUT_BIRCH_LOG,
        DistantMoonsBlocks.CUT_BIRCH_WOOD,
        DistantMoonsBlocks.CUT_CHERRY_LOG,
        DistantMoonsBlocks.CUT_CHERRY_WOOD,
        DistantMoonsBlocks.CUT_CRIMSON_HYPHAE,
        DistantMoonsBlocks.CUT_CRIMSON_STEM,
        DistantMoonsBlocks.CUT_DARK_OAK_LOG,
        DistantMoonsBlocks.CUT_DARK_OAK_WOOD,
        DistantMoonsBlocks.CUT_JUNGLE_LOG,
        DistantMoonsBlocks.CUT_JUNGLE_WOOD,
        DistantMoonsBlocks.CUT_MANGROVE_LOG,
        DistantMoonsBlocks.CUT_MANGROVE_WOOD,
        DistantMoonsBlocks.CUT_OAK_LOG,
        DistantMoonsBlocks.CUT_OAK_WOOD,
        DistantMoonsBlocks.CUT_PALE_OAK_LOG,
        DistantMoonsBlocks.CUT_PALE_OAK_WOOD,
        DistantMoonsBlocks.CUT_SPRUCE_LOG,
        DistantMoonsBlocks.CUT_SPRUCE_WOOD,
        DistantMoonsBlocks.CUT_WARPED_HYPHAE,
        DistantMoonsBlocks.CUT_WARPED_STEM,
        DistantMoonsBlocks.DARK_OAK_WALL_SLAB,
        DistantMoonsBlocks.JUNGLE_WALL_SLAB,
        DistantMoonsBlocks.MANGROVE_WALL_SLAB,
        DistantMoonsBlocks.OAK_WALL_SLAB,
        DistantMoonsBlocks.PALE_OAK_WALL_SLAB,
        DistantMoonsBlocks.ROPE_LADDER,
        DistantMoonsBlocks.SPRUCE_WALL_SLAB,
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK,
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE,
        DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM,
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG,
        DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD,
        DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE,
        DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM,
        DistantMoonsBlocks.WARPED_WALL_SLAB
    );
    this.valueLookupBuilder(DistantMoonsBlockTags.MINING_TYPE_HOE);
    this.valueLookupBuilder(DistantMoonsBlockTags.MINING_TYPE_PICKAXE)
        .addTag(DistantMoonsBlockTags.OXIDIZABLE_CUT_COPPER_WALL_SLAB)
        .addTag(DistantMoonsBlockTags.OXIDIZABLE_IRON_BLOCK)
        .add(
            DistantMoonsBlocks.ANDESITE_WALL_SLAB,
            DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE,
            DistantMoonsBlocks.BLACKSTONE_WALL_SLAB,
            DistantMoonsBlocks.BRICK_WALL_SLAB,
            DistantMoonsBlocks.CHARCOAL_BLOCK,
            DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB,
            DistantMoonsBlocks.COBBLESTONE_WALL_SLAB,
            DistantMoonsBlocks.COKE_BLOCK,
            DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK,
            DistantMoonsBlocks.CUT_BASALT,
            DistantMoonsBlocks.CUT_BONE_BLOCK,
            DistantMoonsBlocks.CUT_DEEPSLATE,
            DistantMoonsBlocks.CUT_PURPUR_PILLAR,
            DistantMoonsBlocks.CUT_QUARTZ_PILLAR,
            DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB,
            DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB,
            DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB,
            DistantMoonsBlocks.DEEP_IRON_BAR_DOOR,
            DistantMoonsBlocks.DEEP_IRON_BARS,
            DistantMoonsBlocks.DEEP_IRON_CHAIN,
            DistantMoonsBlocks.DEEP_IRON_DOOR,
            DistantMoonsBlocks.DEEP_IRON_FENCE,
            DistantMoonsBlocks.DEEP_IRON_LADDER,
            DistantMoonsBlocks.DEEP_IRON_TRAPDOOR,
            DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB,
            DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE,
            DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB,
            DistantMoonsBlocks.DIORITE_WALL_SLAB,
            DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB,
            DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER,
            DistantMoonsBlocks.FIXED_IRON_LADDER,
            DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER,
            DistantMoonsBlocks.GRANITE_WALL_SLAB,
            DistantMoonsBlocks.GRAY_PRISMARINE,
            DistantMoonsBlocks.GRAY_PRISMARINE_SLAB,
            DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS,
            DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB,
            DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE,
            DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE,
            DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS,
            DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES,
            DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS,
            DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES,
            DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE,
            DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE,
            DistantMoonsBlocks.INFESTED_SMOOTH_STONE,
            DistantMoonsBlocks.IRON_BAR_DOOR,
            DistantMoonsBlocks.IRON_FENCE,
            DistantMoonsBlocks.IRON_LADDER,
            DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB,
            DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB,
            DistantMoonsBlocks.MUD_BRICK_WALL_SLAB,
            DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB,
            DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE,
            DistantMoonsBlocks.PALE_PRISMARINE,
            DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB,
            DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS,
            DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB,
            DistantMoonsBlocks.PALE_PRISMARINE_BRICKS,
            DistantMoonsBlocks.PALE_PRISMARINE_SLAB,
            DistantMoonsBlocks.PALE_PRISMARINE_STAIRS,
            DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB,
            DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS,
            DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB,
            DistantMoonsBlocks.PALE_PRISMARINE_TILES,
            DistantMoonsBlocks.PALE_PRISMARINE_WALL,
            DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB,
            DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB,
            DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB,
            DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB,
            DistantMoonsBlocks.POLISHED_CUT_BASALT,
            DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB,
            DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB,
            DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB,
            DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB,
            DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB,
            DistantMoonsBlocks.PRISMARINE_TILE_SLAB,
            DistantMoonsBlocks.PRISMARINE_TILE_STAIRS,
            DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB,
            DistantMoonsBlocks.PRISMARINE_TILES,
            DistantMoonsBlocks.PRISMARINE_WALL_SLAB,
            DistantMoonsBlocks.PURPUR_WALL_SLAB,
            DistantMoonsBlocks.QUARTZ_WALL_SLAB,
            DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK,
            DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB,
            DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB,
            DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK,
            DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB,
            DistantMoonsBlocks.SANDSTONE_WALL_SLAB,
            DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB,
            DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB,
            DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB,
            DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB,
            DistantMoonsBlocks.STONE_BRICK_WALL_SLAB,
            DistantMoonsBlocks.STONE_WALL_SLAB,
            DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB,
            DistantMoonsBlocks.TUFF_WALL_SLAB,
            DistantMoonsBlocks.UNDERWORLD_LANTERN,
            DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR,
            DistantMoonsBlocks.WROUGHT_IRON_BARS,
            DistantMoonsBlocks.WROUGHT_IRON_FENCE,
            DistantMoonsBlocks.WROUGHT_IRON_LADDER
        );
    this.valueLookupBuilder(DistantMoonsBlockTags.MINING_TYPE_SHOVEL);

    //MISCELLANEOUS
    this.valueLookupBuilder(DistantMoonsBlockTags.CLIMBABLE)
        .forceAddTag(BlockTags.CHAINS)
        .add(
            DistantMoonsBlocks.IRON_LADDER,
            DistantMoonsBlocks.DEEP_IRON_LADDER,
            DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER,
            DistantMoonsBlocks.FIXED_IRON_LADDER,
            DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER,
            DistantMoonsBlocks.ROPE_LADDER,
            DistantMoonsBlocks.WROUGHT_IRON_LADDER
        );
    this.valueLookupBuilder(DistantMoonsBlockTags.DAMPENS_VIBRATIONS)
        .addTag(DistantMoonsBlockTags.DYED_PILLOW);
    this.valueLookupBuilder(DistantMoonsBlockTags.LEAVES_WITHOUT_LEAF_PARTICLES)
        .add(Blocks.SPRUCE_LEAVES);
    this.valueLookupBuilder(DistantMoonsBlockTags.OCCLUDES_VIBRATIONS)
        .addTag(DistantMoonsBlockTags.DYED_PILLOW);

    //SUPPORT BLOCKS
    this.valueLookupBuilder(DistantMoonsBlockTags.SUPPORT_BLOCK_FOR_SITTING_SPOT)
        .addTag(DistantMoonsBlockTags.DYED_PILLOW);

    //VANILLA TAG REDIRECTS
    this.valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE).addTag(DistantMoonsBlockTags.MINING_TYPE_AXE);
    this.valueLookupBuilder(BlockTags.BARS).addTag(DistantMoonsBlockTags.BARS);
    this.valueLookupBuilder(BlockTags.CHAINS).addTag(DistantMoonsBlockTags.CHAIN);
    this.valueLookupBuilder(BlockTags.CLIMBABLE).addTag(DistantMoonsBlockTags.CLIMBABLE);
    this.valueLookupBuilder(BlockTags.DRAGON_IMMUNE).addTag(DistantMoonsBlockTags.IMMUNE_TO_DRAGON);
    this.valueLookupBuilder(BlockTags.MINEABLE_WITH_HOE).addTag(DistantMoonsBlockTags.MINING_TYPE_HOE);
    this.valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL).addTag(DistantMoonsBlockTags.MINING_TIER_DIAMOND);
    this.valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL).addTag(DistantMoonsBlockTags.MINING_TIER_IRON);
    this.valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL).addTag(DistantMoonsBlockTags.MINING_TIER_STONE);
    this.valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addTag(DistantMoonsBlockTags.MINING_TYPE_PICKAXE);
    this.valueLookupBuilder(BlockTags.MINEABLE_WITH_SHOVEL).addTag(DistantMoonsBlockTags.MINING_TYPE_SHOVEL);
    this.valueLookupBuilder(BlockTags.WITHER_IMMUNE).addTag(DistantMoonsBlockTags.IMMUNE_TO_WITHER);
    this.valueLookupBuilder(BlockTags.WALLS).addTag(DistantMoonsBlockTags.WALL);
  }
}
