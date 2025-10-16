package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.references.DistantMoonsTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBlockTagProvider extends FabricTagProvider.BlockTagProvider {

  public DistantMoonsBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

    //BLOCK GROUPS
    this.valueLookupBuilder(DistantMoonsTags.BRICK_FENCE).add(
        Blocks.NETHER_BRICK_FENCE
    );
    this.valueLookupBuilder(DistantMoonsTags.CHAIN).add(
        DistantMoonsBlocks.DEEP_IRON_CHAIN
    );
    this.valueLookupBuilder(DistantMoonsTags.FIXED_LADDER).add(
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER, DistantMoonsBlocks.FIXED_IRON_LADDER, DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER
    );
    this.valueLookupBuilder(DistantMoonsTags.GLASS)
        .addTag(DistantMoonsTags.COLORED_STAINED_GLASS)
        .add(Blocks.GLASS, Blocks.TINTED_GLASS);
    this.valueLookupBuilder(DistantMoonsTags.GLASS_PANE)
        .addTag(DistantMoonsTags.COLORED_STAINED_GLASS_PANE)
        .add(Blocks.GLASS_PANE);
    this.valueLookupBuilder(DistantMoonsTags.METAL_BAR_DOOR).add(
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR, DistantMoonsBlocks.IRON_BAR_DOOR, DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR
    );
    this.valueLookupBuilder(DistantMoonsTags.BARS).add(
        DistantMoonsBlocks.DEEP_IRON_BARS, DistantMoonsBlocks.WROUGHT_IRON_BARS
    );
    this.valueLookupBuilder(DistantMoonsTags.SPIKED_FENCE).add(
        DistantMoonsBlocks.DEEP_IRON_FENCE, DistantMoonsBlocks.IRON_FENCE, DistantMoonsBlocks.WROUGHT_IRON_FENCE
    );
    this.valueLookupBuilder(DistantMoonsTags.WOODEN_FENCE).add(
        Blocks.ACACIA_FENCE, Blocks.BAMBOO_FENCE, Blocks.BIRCH_FENCE, Blocks.CHERRY_FENCE,
        Blocks.CRIMSON_FENCE, Blocks.DARK_OAK_FENCE, Blocks.JUNGLE_FENCE, Blocks.MANGROVE_FENCE,
        Blocks.OAK_FENCE, Blocks.PALE_OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.WARPED_FENCE
    );

    //COLORED BLOCK GROUPS
    this.valueLookupBuilder(DistantMoonsTags.COLORED_STAINED_GLASS).add(
        Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS,
        Blocks.GRAY_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS,
        Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS,
        Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS
    );
    this.valueLookupBuilder(DistantMoonsTags.COLORED_STAINED_GLASS_PANE).add(
        Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE,
        Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
        Blocks.LIME_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS_PANE,
        Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE
    );

    //CONNECTION TARGETS
    this.valueLookupBuilder(DistantMoonsTags.BRICK_FENCE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.BRICK_FENCE);
    this.valueLookupBuilder(DistantMoonsTags.BRICK_FENCE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsTags.FIXED_LADDER)
        .addTag(DistantMoonsTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO);
    this.valueLookupBuilder(DistantMoonsTags.FIXED_LADDER_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.GLASS_PANE)
        .addTag(DistantMoonsTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsTags.FIXED_LADDER_ATTACHES_TO)
        .addTag(DistantMoonsTags.FIXED_LADDER)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsTags.FIXED_LADDER_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO);
    this.valueLookupBuilder(DistantMoonsTags.GLASS_PANE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.GLASS_PANE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsTags.GLASS_PANE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO);
    this.valueLookupBuilder(DistantMoonsTags.BARS_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.GLASS_PANE)
        .addTag(DistantMoonsTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsTags.METAL_BAR_DOOR_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO);
    this.valueLookupBuilder(DistantMoonsTags.BARS_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO)
        .forceAddTag(BlockTags.BARS);
    this.valueLookupBuilder(DistantMoonsTags.NEVER_CONNECT_TO)
        .forceAddTag(BlockTags.SHULKER_BOXES)
        .add(Blocks.BARRIER, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, Blocks.MELON, Blocks.PUMPKIN);
    this.valueLookupBuilder(DistantMoonsTags.SPIKED_FENCE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsTags.SPIKED_FENCE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO);
    this.valueLookupBuilder(DistantMoonsTags.SPIKED_FENCE_NOT_BLOCKED_BY)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO)
        .forceAddTag(BlockTags.LEAVES)
        .forceAddTag(BlockTags.ICE)
        .add(Blocks.SLIME_BLOCK, Blocks.SNOW_BLOCK);
    this.valueLookupBuilder(DistantMoonsTags.WALL_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.GLASS_PANE)
        .addTag(DistantMoonsTags.SPIKED_FENCE)
        .forceAddTag(BlockTags.BARS)
        .forceAddTag(BlockTags.WALLS);
    this.valueLookupBuilder(DistantMoonsTags.WALL_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO);
    this.valueLookupBuilder(DistantMoonsTags.WOODEN_FENCE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.WOODEN_FENCE);
    this.valueLookupBuilder(DistantMoonsTags.WOODEN_FENCE_NEVER_CONNECTS_TO)
        .addTag(DistantMoonsTags.COLORED_STAINED_GLASS)
        .addTag(DistantMoonsTags.FIXED_LADDER)
        .addTag(DistantMoonsTags.METAL_BAR_DOOR)
        .addTag(DistantMoonsTags.NEVER_CONNECT_TO);

    //IMMUNITIES
    this.valueLookupBuilder(DistantMoonsTags.IMMUNE_TO_DRAGON);
    this.valueLookupBuilder(DistantMoonsTags.IMMUNE_TO_WITHER);

    //MINING TOOL GROUPS
    this.valueLookupBuilder(DistantMoonsTags.MINING_TIER_DIAMOND);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TIER_IRON);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TIER_STONE).add(
        DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE, DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK, DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE,
        DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK, DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK
    );
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_AXE);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_HOE);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_PICKAXE).add(
        DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE, DistantMoonsBlocks.CHARCOAL_BLOCK, DistantMoonsBlocks.COKE_BLOCK, DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK,
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR, DistantMoonsBlocks.DEEP_IRON_BARS, DistantMoonsBlocks.DEEP_IRON_DOOR, DistantMoonsBlocks.DEEP_IRON_FENCE,
        DistantMoonsBlocks.DEEP_IRON_LADDER, DistantMoonsBlocks.DEEP_IRON_TRAPDOOR, DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER,
        DistantMoonsBlocks.FIXED_IRON_LADDER, DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER, DistantMoonsBlocks.IRON_BAR_DOOR, DistantMoonsBlocks.IRON_FENCE,
        DistantMoonsBlocks.IRON_LADDER, DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK, DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK,
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR, DistantMoonsBlocks.WROUGHT_IRON_BARS, DistantMoonsBlocks.WROUGHT_IRON_FENCE, DistantMoonsBlocks.WROUGHT_IRON_LADDER
    );
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_SHOVEL);

    //MISCELLANEOUS
    this.valueLookupBuilder(DistantMoonsTags.CLIMBABLE)
        .forceAddTag(BlockTags.CHAINS)
        .add(
            DistantMoonsBlocks.IRON_LADDER, DistantMoonsBlocks.DEEP_IRON_LADDER, DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER, DistantMoonsBlocks.FIXED_IRON_LADDER,
            DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER, DistantMoonsBlocks.WROUGHT_IRON_LADDER
        );

    //VANILLA TAG REDIRECTS
    this.valueLookupBuilder(BlockTags.AXE_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_AXE);
    this.valueLookupBuilder(BlockTags.BARS).addTag(DistantMoonsTags.BARS);
    this.valueLookupBuilder(BlockTags.CHAINS).addTag(DistantMoonsTags.CHAIN);
    this.valueLookupBuilder(BlockTags.CLIMBABLE).addTag(DistantMoonsTags.CLIMBABLE);
    this.valueLookupBuilder(BlockTags.DRAGON_IMMUNE).addTag(DistantMoonsTags.IMMUNE_TO_DRAGON);
    this.valueLookupBuilder(BlockTags.HOE_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_HOE);
    this.valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL).addTag(DistantMoonsTags.MINING_TIER_DIAMOND);
    this.valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL).addTag(DistantMoonsTags.MINING_TIER_IRON);
    this.valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL).addTag(DistantMoonsTags.MINING_TIER_STONE);
    this.valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_PICKAXE);
    this.valueLookupBuilder(BlockTags.SHOVEL_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_SHOVEL);
    this.valueLookupBuilder(BlockTags.WITHER_IMMUNE).addTag(DistantMoonsTags.IMMUNE_TO_WITHER);
  }
}
