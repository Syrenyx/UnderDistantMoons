package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;

import java.util.List;

public abstract class DistantMoonsItemGroups {

  static {

    //BUILDING BLOCKS
    addToGroup(
        ItemGroups.BUILDING_BLOCKS,
        Items.PURPUR_SLAB,
        List.of(
            Items.GLASS.getDefaultStack(),
            Items.TINTED_GLASS.getDefaultStack(),
            Items.BONE_BLOCK.getDefaultStack()
        )
    );
    addToGroup(
        ItemGroups.BUILDING_BLOCKS,
        Items.DIAMOND_BLOCK,
        List.of(
            DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK.asItem().getDefaultStack(),
            DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK.asItem().getDefaultStack()
        )
    );

    //COLORED BLOCKS

    //NATURAL
    addToGroup(
        ItemGroups.NATURAL,
        Items.NETHER_QUARTZ_ORE,
        List.of(
            DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE.asItem().getDefaultStack(),
            DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE.asItem().getDefaultStack(),
            DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE.asItem().getDefaultStack()
        )
    );
    addToGroup(
        ItemGroups.NATURAL,
        Items.RAW_GOLD_BLOCK,
        List.of(
            DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK.asItem().getDefaultStack()
        )
    );

    //FUNCTIONAL
    addToGroup(
        ItemGroups.FUNCTIONAL,
        Items.DRAGON_EGG,
        List.of(
            Items.SPAWNER.getDefaultStack(),
            Items.TRIAL_SPAWNER.getDefaultStack(),
            Items.CREAKING_HEART.getDefaultStack()
        )
    );

    //REDSTONE

    //TOOLS

    //COMBAT

    //FOOD AND DRINK

    //INGREDIENTS
    addToGroup(
        ItemGroups.INGREDIENTS,
        Items.DIAMOND,
        List.of(
            DistantMoonsItems.RAW_DEEP_IRON.getDefaultStack()
        )
    );
    addToGroup(
        ItemGroups.INGREDIENTS,
        Items.GOLD_NUGGET,
        List.of(
            DistantMoonsItems.REFINED_DEEP_IRON_NUGGET.getDefaultStack()
        )
    );
    addToGroup(
        ItemGroups.INGREDIENTS,
        Items.GOLD_INGOT,
        List.of(
            DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK.getDefaultStack(),
            DistantMoonsItems.REFINED_DEEP_IRON_INGOT.getDefaultStack()
        )
    );

    //SPAWN EGGS
    addToGroup(
        ItemGroups.SPAWN_EGGS,
        Items.CREAKING_HEART,
        List.of(
            Items.SCULK_SHRIEKER.getDefaultStack(),
            Items.INFESTED_STONE.getDefaultStack(),
            Items.INFESTED_COBBLESTONE.getDefaultStack(),
            Items.INFESTED_STONE_BRICKS.getDefaultStack(),
            Items.INFESTED_MOSSY_STONE_BRICKS.getDefaultStack(),
            Items.INFESTED_CRACKED_STONE_BRICKS.getDefaultStack(),
            Items.INFESTED_CHISELED_STONE_BRICKS.getDefaultStack(),
            Items.INFESTED_DEEPSLATE.getDefaultStack()
        )
    );

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
