package syrenyx.distantmoons.references.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import syrenyx.distantmoons.utility.TagUtil;

public abstract class DistantMoonsItemTags {

  public static final TagKey<Item> REPAIRS_DEEP_IRON_EQUIPMENT = TagUtil.generateKey(RegistryKeys.ITEM, "repair_material/deep_iron");
  public static final TagKey<Item> SMELTING_FUEL_WOOD_BLOCK = TagUtil.generateKey(RegistryKeys.ITEM, "fuel/smelting/wood");
  public static final TagKey<Item> SMELTING_FUEL_WOOD_SLAB = TagUtil.generateKey(RegistryKeys.ITEM, "fuel/smelting/wood_slab");
  public static final TagKey<Item> SWORD = TagUtil.generateKey(RegistryKeys.ITEM, "sword");
}
