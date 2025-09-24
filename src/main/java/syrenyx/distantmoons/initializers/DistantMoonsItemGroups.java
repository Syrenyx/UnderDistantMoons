package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;

import java.util.List;

public abstract class DistantMoonsItemGroups {

  static {

    //BUILDING BLOCKS
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.PURPUR_SLAB, List.of(
        Items.GLASS.getDefaultStack(),
        Items.TINTED_GLASS.getDefaultStack(),
        Items.BONE_BLOCK.getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.COAL_BLOCK, List.of(
        DistantMoonsBlocks.CHARCOAL_BLOCK.asItem().getDefaultStack(),
        DistantMoonsBlocks.COKE_BLOCK.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.IRON_BARS, List.of(
        DistantMoonsBlocks.IRON_FENCE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.IRON_DOOR, List.of(
        DistantMoonsBlocks.IRON_BAR_DOOR.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, List.of(
        DistantMoonsBlocks.IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_IRON_LADDER.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.BUILDING_BLOCKS, Items.CHAIN, List.of(
        DistantMoonsBlocks.WROUGHT_IRON_BARS.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_FENCE.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK.asItem().getDefaultStack(),
        DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_BARS.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_FENCE.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_DOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_BAR_DOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_TRAPDOOR.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem().getDefaultStack()
    ));

    //COLORED BLOCKS

    //NATURAL
    addToGroup(ItemGroups.NATURAL, Items.NETHER_QUARTZ_ORE, List.of(
        DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE.asItem().getDefaultStack(),
        DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.NATURAL, Items.RAW_GOLD_BLOCK, List.of(
        DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK.asItem().getDefaultStack()
    ));

    //FUNCTIONAL
    addToGroup(ItemGroups.FUNCTIONAL, Items.LADDER, List.of(
        DistantMoonsBlocks.IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.DEEP_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER.asItem().getDefaultStack(),
        DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER.asItem().getDefaultStack()
    ));
    addToGroup(ItemGroups.FUNCTIONAL, Items.DRAGON_EGG, List.of(
        Items.SPAWNER.getDefaultStack(),
        Items.TRIAL_SPAWNER.getDefaultStack(),
        Items.CREAKING_HEART.getDefaultStack()
    ));

    //REDSTONE

    //TOOLS
    addToGroup(ItemGroups.TOOLS, Items.IRON_HOE, List.of(
        DistantMoonsItems.DEEP_IRON_SHOVEL.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_PICKAXE.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_AXE.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_HOE.getDefaultStack()
    ));

    //COMBAT
    addToGroup(ItemGroups.COMBAT, Items.IRON_SWORD, List.of(
        DistantMoonsItems.DEEP_IRON_SWORD.getDefaultStack()
    ));
    addToGroup(ItemGroups.COMBAT, Items.IRON_AXE, List.of(
        DistantMoonsItems.DEEP_IRON_AXE.getDefaultStack()
    ));
    addToGroup(ItemGroups.COMBAT, Items.IRON_BOOTS, List.of(
        DistantMoonsItems.DEEP_IRON_HELMET.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_CHESTPLATE.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_LEGGINGS.getDefaultStack(),
        DistantMoonsItems.DEEP_IRON_BOOTS.getDefaultStack()
    ));
    addToGroup(ItemGroups.COMBAT, Items.IRON_HORSE_ARMOR, List.of(
        DistantMoonsItems.DEEP_IRON_HORSE_ARMOR.getDefaultStack()
    ));

    //FOOD AND DRINK
    addToGroup(ItemGroups.FOOD_AND_DRINK, Items.MELON_SLICE, List.of(
        Items.GLISTERING_MELON_SLICE.getDefaultStack()
    ));
    addToGroup(ItemGroups.FOOD_AND_DRINK, Items.DRIED_KELP, List.of(
        Items.BROWN_MUSHROOM.getDefaultStack(),
        DistantMoonsItems.ROASTED_BROWN_MUSHROOM.getDefaultStack(),
        Items.RED_MUSHROOM.getDefaultStack(),
        Items.CRIMSON_FUNGUS.getDefaultStack(),
        Items.WARPED_FUNGUS.getDefaultStack()
    ));

    //INGREDIENTS
    addToGroup(ItemGroups.INGREDIENTS, Items.CHARCOAL, List.of(
            DistantMoonsItems.COKE.getDefaultStack()
    ));
    addToGroup(ItemGroups.INGREDIENTS, Items.DIAMOND, List.of(
        DistantMoonsItems.RAW_DEEP_IRON.getDefaultStack()
    ));
    addToGroup(ItemGroups.INGREDIENTS, Items.GOLD_NUGGET, List.of(
        DistantMoonsItems.REFINED_DEEP_IRON_NUGGET.getDefaultStack()
    ));
    addToGroup(ItemGroups.INGREDIENTS, Items.GOLD_INGOT, List.of(
        DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK.getDefaultStack(),
        DistantMoonsItems.REFINED_DEEP_IRON_INGOT.getDefaultStack()
    ));
    addToGroup(ItemGroups.INGREDIENTS, Items.NETHERITE_INGOT, List.of(
        DistantMoonsItems.IRON_ROD.getDefaultStack(),
        DistantMoonsItems.WROUGHT_IRON_ROD.getDefaultStack(),
        DistantMoonsItems.REFINED_DEEP_IRON_ROD.getDefaultStack()
    ));
    addToGroup(ItemGroups.INGREDIENTS, Items.BOOK, List.of(
        DistantMoonsItems.UNDERWORLD_DUST.getDefaultStack()
    ));

    //SPAWN EGGS
    addToGroup(ItemGroups.SPAWN_EGGS, Items.CREAKING_HEART, List.of(
        Items.SCULK_SHRIEKER.getDefaultStack(),
        Items.INFESTED_STONE.getDefaultStack(),
        Items.INFESTED_COBBLESTONE.getDefaultStack(),
        Items.INFESTED_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_MOSSY_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_CRACKED_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_CHISELED_STONE_BRICKS.getDefaultStack(),
        Items.INFESTED_DEEPSLATE.getDefaultStack()
    ));

    //OPERATOR
  }

  private static void addToGroup(
      RegistryKey<ItemGroup> groupKey,
      Item anchor,
      List<ItemStack> itemStacks
  ) {
    ItemGroupEvents
        .modifyEntriesEvent(groupKey)
        .register(group -> group.addAfter(anchor, itemStacks));
  }

  public static void initialize() {}
}
