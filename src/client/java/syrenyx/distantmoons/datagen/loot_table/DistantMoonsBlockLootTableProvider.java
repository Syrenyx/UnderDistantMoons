package syrenyx.distantmoons.datagen.loot_table;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ConditionReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import syrenyx.distantmoons.content.block.WallSlabBlock;
import syrenyx.distantmoons.content.block.block_state_enums.WallSlabShape;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.references.data.DistantMoonsPredicates;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBlockLootTableProvider extends FabricBlockLootTableProvider {

  public DistantMoonsBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {

    //SIMPLE LOOT TABLES
    this.dropSelf(DistantMoonsBlocks.ACACIA_BEAM);
    this.dropSelf(DistantMoonsBlocks.ACACIA_POLE);
    this.dropSelf(DistantMoonsBlocks.BAMBOO_POLE);
    this.dropSelf(DistantMoonsBlocks.BIRCH_BEAM);
    this.dropSelf(DistantMoonsBlocks.BIRCH_POLE);
    this.dropSelf(DistantMoonsBlocks.CHARCOAL_BLOCK);
    this.dropSelf(DistantMoonsBlocks.CHERRY_BEAM);
    this.dropSelf(DistantMoonsBlocks.CHERRY_POLE);
    this.dropSelf(DistantMoonsBlocks.COKE_BLOCK);
    this.dropSelf(DistantMoonsBlocks.CRIMSON_BEAM);
    this.dropSelf(DistantMoonsBlocks.CRIMSON_POLE);
    this.dropSelf(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.DARK_OAK_BEAM);
    this.dropSelf(DistantMoonsBlocks.DARK_OAK_POLE);
    this.dropSelf(DistantMoonsBlocks.DEEP_IRON_BARS);
    this.dropSelf(DistantMoonsBlocks.DEEP_IRON_CHAIN);
    this.dropSelf(DistantMoonsBlocks.DEEP_IRON_FENCE);
    this.dropSelf(DistantMoonsBlocks.DEEP_IRON_LADDER);
    this.dropSelf(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR);
    this.dropSelf(DistantMoonsBlocks.EXPOSED_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER);
    this.dropSelf(DistantMoonsBlocks.FIXED_IRON_LADDER);
    this.dropSelf(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER);
    this.dropSelf(DistantMoonsBlocks.GRAY_PRISMARINE);
    this.dropSelf(DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS);
    this.dropSelf(DistantMoonsBlocks.IRON_FENCE);
    this.dropSelf(DistantMoonsBlocks.IRON_LADDER);
    this.dropSelf(DistantMoonsBlocks.JUNGLE_BEAM);
    this.dropSelf(DistantMoonsBlocks.JUNGLE_POLE);
    this.dropSelf(DistantMoonsBlocks.MANGROVE_BEAM);
    this.dropSelf(DistantMoonsBlocks.MANGROVE_POLE);
    this.dropSelf(DistantMoonsBlocks.OAK_BEAM);
    this.dropSelf(DistantMoonsBlocks.OAK_POLE);
    this.dropSelf(DistantMoonsBlocks.PALE_OAK_BEAM);
    this.dropSelf(DistantMoonsBlocks.PALE_OAK_POLE);
    this.dropSelf(DistantMoonsBlocks.PALE_PRISMARINE);
    this.dropSelf(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS);
    this.dropSelf(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS);
    this.dropSelf(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS);
    this.dropSelf(DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS);
    this.dropSelf(DistantMoonsBlocks.PALE_PRISMARINE_TILES);
    this.dropSelf(DistantMoonsBlocks.PALE_PRISMARINE_WALL);
    this.dropSelf(DistantMoonsBlocks.PRISMARINE_TILE_STAIRS);
    this.dropSelf(DistantMoonsBlocks.PRISMARINE_TILES);
    this.dropSelf(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.ROPE_LADDER);
    this.dropSelf(DistantMoonsBlocks.RUSTED_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.SPRUCE_BEAM);
    this.dropSelf(DistantMoonsBlocks.SPRUCE_POLE);
    this.dropSelf(DistantMoonsBlocks.WARPED_BEAM);
    this.dropSelf(DistantMoonsBlocks.WARPED_POLE);
    this.dropSelf(DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.WAXED_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.WEATHERED_IRON_BLOCK);
    this.dropSelf(DistantMoonsBlocks.WROUGHT_IRON_BARS);
    this.dropSelf(DistantMoonsBlocks.WROUGHT_IRON_FENCE);
    this.dropSelf(DistantMoonsBlocks.WROUGHT_IRON_LADDER);

    //INFESTED BLOCK LOOT TABLES
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE);
    this.addInfestedBlockDrop(DistantMoonsBlocks.INFESTED_SMOOTH_STONE);

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
    this.addSlabDrop(DistantMoonsBlocks.GRAY_PRISMARINE_SLAB);
    this.addSlabDrop(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB);
    this.addSlabDrop(DistantMoonsBlocks.PALE_PRISMARINE_SLAB);
    this.addSlabDrop(DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB);
    this.addSlabDrop(DistantMoonsBlocks.POLISHED_CUT_BASALT);
    this.addSlabDrop(DistantMoonsBlocks.PRISMARINE_TILE_SLAB);
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

    DistantMoonsBlocks.DYED_PILLOWS.values().forEach(this::addSlabDrop);

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
    this.addWallSlabDrop(DistantMoonsBlocks.CUT_COPPER_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DARK_OAK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.DIORITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.GRANITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.JUNGLE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MANGROVE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.OAK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PALE_OAK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB);
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
    this.addWallSlabDrop(DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB);
    this.addWallSlabDrop(DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB);
  }

  private void addInfestedBlockDrop(Block block) {
    if (!(block instanceof InfestedBlock infestedBlock)) throw new IllegalArgumentException("Cannot register Infested Block drop for non-infested block.");
    this.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
        .setRolls(new ConstantValue(1.0F))
        .add(LootItem.lootTableItem(infestedBlock.getHostBlock().asItem()))
        .when(ConditionReference.conditionReference(DistantMoonsPredicates.SILK_TOUCH_TOOL))
    ));
  }

  private void addSlabDrop(Block block) {
    this.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
        .setRolls(new ConstantValue(1.0F))
        .add(LootItem.lootTableItem(block.asItem())
            .apply(ApplyExplosionDecay.explosionDecay())
            .apply(SetItemCountFunction.setCount(new ConstantValue(2.0F))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties()
                    .hasProperty(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE))
                )
            )
        )
    ));
  }

  private void addWallSlabDrop(Block block) {
    this.add(block, LootTable.lootTable().withPool(LootPool.lootPool()
        .setRolls(new ConstantValue(1.0F))
        .add(LootItem.lootTableItem(block.asItem())
            .apply(ApplyExplosionDecay.explosionDecay())
            .apply(SetItemCountFunction.setCount(new ConstantValue(2.0F))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties()
                    .hasProperty(WallSlabBlock.SHAPE, WallSlabShape.DOUBLE))
                )
            )
        )
    ));
  }
}
