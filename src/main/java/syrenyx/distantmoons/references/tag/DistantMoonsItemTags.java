package syrenyx.distantmoons.references.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsItemTags {

  public static final TagKey<Item> CORUNDUM = keyOf("corundum");
  public static final TagKey<Item> CORUNDUM_BLOCK = keyOf("corundum_block");
  public static final TagKey<Item> DEEP_IRON_BLOCK = keyOf("deep_iron_block");
  public static final TagKey<Item> DEEP_IRON_INGOT = keyOf("deep_iron_ingot");
  public static final TagKey<Item> DYED_CONCRETE_POWDER = keyOf("dyed/concrete_powder");
  public static final TagKey<Item> DYED_CONCRETE_SLAB = keyOf("dyed/concrete_slab");
  public static final TagKey<Item> DYED_CONCRETE_STAIRS = keyOf("dyed/concrete_stairs");
  public static final TagKey<Item> DYED_CONCRETE_WALL_SLAB = keyOf("dyed/concrete_wall_slab");
  public static final TagKey<Item> DYED_PILLOW = keyOf("dyed/pillow");
  public static final TagKey<Item> DYED_TERRACOTTA_SLAB = keyOf("dyed/terracotta_slab");
  public static final TagKey<Item> DYED_TERRACOTTA_STAIRS = keyOf("dyed/terracotta_stairs");
  public static final TagKey<Item> DYED_TERRACOTTA_WALL_SLAB = keyOf("dyed/terracotta_wall_slab");
  public static final TagKey<Item> REPAIRS_DEEP_IRON_EQUIPMENT = keyOf("repair_material/deep_iron");
  public static final TagKey<Item> SMELTING_FUEL_WOOD_BLOCK = keyOf("fuel/smelting/wood_block");
  public static final TagKey<Item> SMELTING_FUEL_WOOD_HALF_BLOCK = keyOf("fuel/smelting/wood_half_block");
  public static final TagKey<Item> SWORD = keyOf("sword");

  private static TagKey<Item> keyOf(String id) {
    return UnderDistantMoons.tagKeyOf(id, Registries.ITEM);
  }
}
