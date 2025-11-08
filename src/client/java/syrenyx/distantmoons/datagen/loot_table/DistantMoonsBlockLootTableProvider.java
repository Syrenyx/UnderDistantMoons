package syrenyx.distantmoons.datagen.loot_table;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.enums.SlabType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import syrenyx.distantmoons.content.block.WallSlabBlock;
import syrenyx.distantmoons.content.block.block_state_enums.WallSlabShape;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBlockLootTableProvider extends FabricBlockLootTableProvider {

  public DistantMoonsBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {

    //SIMPLE LOOT TABLES
    this.addDrop(DistantMoonsBlocks.ACACIA_BEAM);
    this.addDrop(DistantMoonsBlocks.ACACIA_POLE);
    this.addDrop(DistantMoonsBlocks.BAMBOO_POLE);
    this.addDrop(DistantMoonsBlocks.BIRCH_BEAM);
    this.addDrop(DistantMoonsBlocks.BIRCH_POLE);
    this.addDrop(DistantMoonsBlocks.CHARCOAL_BLOCK);
    this.addDrop(DistantMoonsBlocks.CHERRY_BEAM);
    this.addDrop(DistantMoonsBlocks.CHERRY_POLE);
    this.addDrop(DistantMoonsBlocks.COKE_BLOCK);
    this.addDrop(DistantMoonsBlocks.CRIMSON_BEAM);
    this.addDrop(DistantMoonsBlocks.CRIMSON_POLE);
    this.addDrop(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
    this.addDrop(DistantMoonsBlocks.DARK_OAK_BEAM);
    this.addDrop(DistantMoonsBlocks.DARK_OAK_POLE);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_BARS);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_CHAIN);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_FENCE);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR);
    this.addDrop(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.FIXED_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.IRON_FENCE);
    this.addDrop(DistantMoonsBlocks.IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.JUNGLE_BEAM);
    this.addDrop(DistantMoonsBlocks.JUNGLE_POLE);
    this.addDrop(DistantMoonsBlocks.MANGROVE_BEAM);
    this.addDrop(DistantMoonsBlocks.MANGROVE_POLE);
    this.addDrop(DistantMoonsBlocks.OAK_BEAM);
    this.addDrop(DistantMoonsBlocks.OAK_POLE);
    this.addDrop(DistantMoonsBlocks.PALE_OAK_BEAM);
    this.addDrop(DistantMoonsBlocks.PALE_OAK_POLE);
    this.addDrop(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
    this.addDrop(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);
    this.addDrop(DistantMoonsBlocks.ROPE_LADDER);
    this.addDrop(DistantMoonsBlocks.SPRUCE_BEAM);
    this.addDrop(DistantMoonsBlocks.SPRUCE_POLE);
    this.addDrop(DistantMoonsBlocks.WARPED_BEAM);
    this.addDrop(DistantMoonsBlocks.WARPED_POLE);
    this.addDrop(DistantMoonsBlocks.WROUGHT_IRON_BARS);
    this.addDrop(DistantMoonsBlocks.WROUGHT_IRON_FENCE);
    this.addDrop(DistantMoonsBlocks.WROUGHT_IRON_LADDER);

    //SLAB LOOT TABLES
    this.addSlabDrop(DistantMoonsBlocks.CUT_ACACIA_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_ACACIA_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_BAMBOO_BLOCK);
    this.addSlabDrop(DistantMoonsBlocks.CUT_BASALT);
    this.addSlabDrop(DistantMoonsBlocks.CUT_BIRCH_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_BIRCH_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_BONE_BLOCK);
    this.addSlabDrop(DistantMoonsBlocks.CUT_CHERRY_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_CHERRY_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE);
    this.addSlabDrop(DistantMoonsBlocks.CUT_CRIMSON_STEM);
    this.addSlabDrop(DistantMoonsBlocks.CUT_DARK_OAK_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_DARK_OAK_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_DEEPSLATE);
    this.addSlabDrop(DistantMoonsBlocks.CUT_JUNGLE_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_JUNGLE_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_MANGROVE_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_MANGROVE_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_OAK_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_OAK_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_PALE_OAK_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_PALE_OAK_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_PURPUR_PILLAR);
    this.addSlabDrop(DistantMoonsBlocks.CUT_QUARTZ_PILLAR);
    this.addSlabDrop(DistantMoonsBlocks.CUT_SPRUCE_LOG);
    this.addSlabDrop(DistantMoonsBlocks.CUT_SPRUCE_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.CUT_WARPED_HYPHAE);
    this.addSlabDrop(DistantMoonsBlocks.CUT_WARPED_STEM);
    this.addSlabDrop(DistantMoonsBlocks.POLISHED_CUT_BASALT);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE);
    this.addSlabDrop(DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM);

    //WALL SLAB LOOT TABLES
    this.addWallSlabDrop(DistantMoonsBlocks.ACACIA_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.ANDESITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.BAMBOO_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.BIRCH_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.BLACKSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.CHERRY_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.COBBLESTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.CRIMSON_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DARK_OAK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DIORITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.GRANITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.JUNGLE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MANGROVE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.OAK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PALE_OAK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PRISMARINE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PURPUR_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.QUARTZ_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.SPRUCE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.STONE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.STONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.TUFF_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.WARPED_WALL_SLAB);
  }

  private void addSlabDrop(Block block) {
    this.addDrop(block, LootTable.builder().pool(LootPool.builder()
        .rolls(new ConstantLootNumberProvider(1.0F))
        .with(ItemEntry.builder(block.asItem())
            .apply(ExplosionDecayLootFunction.builder())
            .apply(SetCountLootFunction.builder(new ConstantLootNumberProvider(2.0F))
                .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create()
                    .exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE))
                )
            )
        )
    ));
  }

  private void addWallSlabDrop(Block block) {
    this.addDrop(block, LootTable.builder().pool(LootPool.builder()
        .rolls(new ConstantLootNumberProvider(1.0F))
        .with(ItemEntry.builder(block.asItem())
            .apply(ExplosionDecayLootFunction.builder())
            .apply(SetCountLootFunction.builder(new ConstantLootNumberProvider(2.0F))
                .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create()
                    .exactMatch(WallSlabBlock.SHAPE, WallSlabShape.DOUBLE))
                )
            )
        )
    ));
  }
}
