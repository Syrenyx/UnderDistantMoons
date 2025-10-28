package syrenyx.distantmoons.references;

import com.google.common.collect.Maps;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;

import java.util.Map;

public abstract class DistantMoonsItemMaterials {

  //ARMOR MATERIALS
  public static final ArmorMaterial DEEP_IRON_ARMOR = new ArmorMaterial(
      30, createDefenseMap(2, 5, 6, 2, 5), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, DistantMoonsItemTags.REPAIRS_DEEP_IRON_EQUIPMENT, armorAssetRegistryKeyOf("deep_iron")
  );

  //TOOL MATERIALS
  public static final ToolMaterial DEEP_IRON_TOOL = new ToolMaterial(
      BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 6.0F, 2.0F, 14, DistantMoonsItemTags.REPAIRS_DEEP_IRON_EQUIPMENT
  );

  private static Map<EquipmentType, Integer> createDefenseMap(int boots, int leggings, int chestplate, int helmet, int body) {
    return Maps.newEnumMap(
        Map.of(
            EquipmentType.BOOTS, boots,
            EquipmentType.LEGGINGS, leggings,
            EquipmentType.CHESTPLATE, chestplate,
            EquipmentType.HELMET, helmet,
            EquipmentType.BODY, body
        )
    );
  }

  private static RegistryKey<EquipmentAsset> armorAssetRegistryKeyOf(String id) {
    return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, UnderDistantMoons.identifierOf(id));
  }
}
