package syrenyx.distantmoons.references;

import com.google.common.collect.Maps;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;

import java.util.Map;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public abstract class DistantMoonsItemMaterials {

  //ARMOR MATERIALS
  public static final ArmorMaterial DEEP_IRON_ARMOR = new ArmorMaterial(
      30, createDefenseMap(2, 5, 6, 2, 5), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, DistantMoonsItemTags.REPAIRS_DEEP_IRON_EQUIPMENT, armorAssetRegistryKeyOf("deep_iron")
  );

  //TOOL MATERIALS
  public static final ToolMaterial DEEP_IRON_TOOL = new ToolMaterial(
      BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 6.0F, 2.0F, 14, DistantMoonsItemTags.REPAIRS_DEEP_IRON_EQUIPMENT
  );

  private static Map<ArmorType, Integer> createDefenseMap(int boots, int leggings, int chestplate, int helmet, int body) {
    return Maps.newEnumMap(
        Map.of(
            ArmorType.BOOTS, boots,
            ArmorType.LEGGINGS, leggings,
            ArmorType.CHESTPLATE, chestplate,
            ArmorType.HELMET, helmet,
            ArmorType.BODY, body
        )
    );
  }

  private static ResourceKey<EquipmentAsset> armorAssetRegistryKeyOf(String id) {
    return ResourceKey.create(EquipmentAssets.ROOT_ID, UnderDistantMoons.identifierOf(id));
  }
}
