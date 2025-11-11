package syrenyx.distantmoons.initializers;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.references.DistantMoonsItemMaterials;

import java.util.function.Function;

public abstract class DistantMoonsItems {

  public static final Item COKE = register(
      "coke",
      Item::new,
      new Item.Settings()
  );
  public static final Item COPPER_ROD = register(
      "copper_rod",
      Item::new,
      new Item.Settings()
  );
  public static final Item CRUDE_DEEP_IRON_CHUNK = register(
      "crude_deep_iron_chunk",
      Item::new,
      new Item.Settings().fireproof()
  );
  public static final Item DEEP_IRON_AXE = register(
      "deep_iron_axe",
      settings -> new AxeItem(ToolMaterial.IRON, 6.0F, -3.1F, settings),
      new Item.Settings().fireproof()
  );
  public static final Item DEEP_IRON_BOOTS = register(
      "deep_iron_boots",
      Item::new,
      new Item.Settings().armor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, EquipmentType.BOOTS).fireproof()
  );
  public static final Item DEEP_IRON_CHESTPLATE = register(
      "deep_iron_chestplate",
      Item::new,
      new Item.Settings().armor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, EquipmentType.CHESTPLATE).fireproof()
  );
  public static final Item DEEP_IRON_HELMET = register(
      "deep_iron_helmet",
      Item::new,
      new Item.Settings().armor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, EquipmentType.HELMET).fireproof()
  );
  public static final Item DEEP_IRON_HOE = register(
      "deep_iron_hoe",
      settings -> new HoeItem(ToolMaterial.IRON, -2.0F, -1.0F, settings),
      new Item.Settings().fireproof()
  );
  public static final Item DEEP_IRON_HORSE_ARMOR = register(
      "deep_iron_horse_armor",
      Item::new,
      new Item.Settings().horseArmor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR).fireproof()
  );
  public static final Item DEEP_IRON_LEGGINGS = register(
      "deep_iron_leggings",
      Item::new,
      new Item.Settings().armor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, EquipmentType.LEGGINGS).fireproof()
  );
  public static final Item DEEP_IRON_PICKAXE = register(
      "deep_iron_pickaxe",
      Item::new,
      new Item.Settings().fireproof().pickaxe(DistantMoonsItemMaterials.DEEP_IRON_TOOL, 1.0F, -2.8F)
  );
  public static final Item DEEP_IRON_SHOVEL = register(
      "deep_iron_shovel",
      settings -> new ShovelItem(ToolMaterial.IRON, 1.5F, -3.0F, settings),
      new Item.Settings().fireproof()
  );
  public static final Item DEEP_IRON_SWORD = register(
      "deep_iron_sword",
      Item::new,
      new Item.Settings().fireproof().sword(DistantMoonsItemMaterials.DEEP_IRON_TOOL, 3.0F, -2.4F)
  );
  public static final Item IRON_ROD = register(
      "iron_rod",
      Item::new,
      new Item.Settings()
  );
  public static final Item PALE_PRISMARINE_SHARD = register(
      "pale_prismarine_shard",
      Item::new,
      new Item.Settings()
  );
  public static final Item RAW_DEEP_IRON = register(
      "raw_deep_iron",
      Item::new,
      new Item.Settings().fireproof()
  );
  public static final Item REFINED_DEEP_IRON_INGOT = register(
      "refined_deep_iron_ingot",
      Item::new,
      new Item.Settings().fireproof()
  );
  public static final Item REFINED_DEEP_IRON_NUGGET = register(
      "refined_deep_iron_nugget",
      Item::new,
      new Item.Settings().fireproof()
  );
  public static final Item REFINED_DEEP_IRON_ROD = register(
      "refined_deep_iron_rod",
      Item::new,
      new Item.Settings().fireproof()
  );
  public static final Item ROASTED_BROWN_MUSHROOM = register(
      "roasted_brown_mushroom",
      Item::new,
      new Item.Settings()
          .component(DataComponentTypes.CONSUMABLE, ConsumableComponent.builder().build())
          .component(DataComponentTypes.FOOD, new FoodComponent(5, 6F, false))
  );
  public static final Item ROTTEN_FISH = register(
      "rotten_fish",
      Item::new,
      new Item.Settings()
          .component(DataComponentTypes.CONSUMABLE, ConsumableComponents.ROTTEN_FLESH)
          .component(DataComponentTypes.FOOD, new FoodComponent(4, 0.1F, false))
  );
  public static final Item UNDERWORLD_DUST = register(
      "underworld_dust",
      Item::new,
      new Item.Settings()
  );
  public static final Item WROUGHT_IRON_ROD = register(
      "wrought_iron_rod",
      Item::new,
      new Item.Settings()
  );

  private static Item register(String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
    RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, UnderDistantMoons.identifierOf(id));
    return Registry.register(Registries.ITEM, key, itemFactory.apply(settings.registryKey(key)));
  }

  protected static void registerBlockItem(String id, Block block, Item.Settings itemSettings) {
    RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, UnderDistantMoons.identifierOf(id));
    Registry.register(Registries.ITEM, key, new BlockItem(block, itemSettings.registryKey(key).useBlockPrefixedTranslationKey()));
  }

  public static String getStringIdOf(Item item) {
    return Registries.ITEM.getEntry(item).getIdAsString().split(":")[1];
  }

  public static void initialize() {}
}
