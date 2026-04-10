package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.DamageResistant;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.data_component.*;
import syrenyx.distantmoons.content.item.*;
import syrenyx.distantmoons.references.DistantMoonsItemMaterials;
import syrenyx.distantmoons.references.tag.DistantMoonsDamageTypeTags;

import java.util.function.Function;

public abstract class DistantMoonsItems {

  public static final Item ABYSS_KEYSTONE = register(
      "abyss_keystone",
      Item::new,
      new Item.Properties()
          .component(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE, new DimensionKeystoneComponent(Level.NETHER.identifier(), DimensionKeystoneComponent.ABYSS_COLOR, UnderDistantMoons.identifierOf("underworld_conflux_keystone/abyss")))
          .fireResistant()
          .stacksTo(1)
  );
  public static final Item COILED_ROPE_LADDER = register(
      "coiled_rope_ladder",
      settings -> new CoilItem(DistantMoonsBlocks.ROPE_LADDER, settings),
      new Item.Properties()
          .component(DistantMoonsDataComponentTypes.COILED_BLOCK, new CoiledBlockComponent.Builder().amount(9).build())
          .stacksTo(16)
  );
  public static final Item COKE = register(
      "coke",
      Item::new,
      new Item.Properties()
          .component(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL, new BlastFurnaceFuelComponent(BlastFurnaceFuelComponent.COKE_HEAT_VALUE))
  );
  public static final Item COPPER_ROD = register(
      "copper_rod",
      Item::new,
      new Item.Properties()
  );
  public static final Item CRUDE_DEEP_IRON_CHUNK = register(
      "crude_deep_iron_chunk",
      Item::new,
      new Item.Properties()
          .fireResistant()
  );
  public static final Item DEEP_IRON_AXE = register(
      "deep_iron_axe",
      settings -> new AxeItem(ToolMaterial.IRON, 6.0F, -3.1F, settings),
      new Item.Properties()
          .fireResistant()
  );
  public static final Item DEEP_IRON_BOOTS = register(
      "deep_iron_boots",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .humanoidArmor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, ArmorType.BOOTS)
  );
  public static final Item DEEP_IRON_CHESTPLATE = register(
      "deep_iron_chestplate",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .humanoidArmor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, ArmorType.CHESTPLATE)
  );
  public static final Item DEEP_IRON_HELMET = register(
      "deep_iron_helmet",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .humanoidArmor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, ArmorType.HELMET)
  );
  public static final Item DEEP_IRON_HOE = register(
      "deep_iron_hoe",
      settings -> new HoeItem(DistantMoonsItemMaterials.DEEP_IRON_TOOL, -2.0F, -1.0F, settings),
      new Item.Properties()
          .fireResistant()
  );
  public static final Item DEEP_IRON_HORSE_ARMOR = register(
      "deep_iron_horse_armor",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .horseArmor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR)
  );
  public static final Item DEEP_IRON_LEGGINGS = register(
      "deep_iron_leggings",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .humanoidArmor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR, ArmorType.LEGGINGS)
  );
  public static final Item DEEP_IRON_NAUTILUS_ARMOR = register(
      "deep_iron_nautilus_armor",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .nautilusArmor(DistantMoonsItemMaterials.DEEP_IRON_ARMOR)
  );
  public static final Item DEEP_IRON_PICKAXE = register(
      "deep_iron_pickaxe",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .pickaxe(DistantMoonsItemMaterials.DEEP_IRON_TOOL, 1.0F, -2.8F)
  );
  public static final Item DEEP_IRON_SHOVEL = register(
      "deep_iron_shovel",
      settings -> new ShovelItem(DistantMoonsItemMaterials.DEEP_IRON_TOOL, 1.5F, -3.0F, settings),
      new Item.Properties()
          .fireResistant()
  );
  public static final Item DEEP_IRON_SPEAR = register(
      "deep_iron_spear",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .spear(DistantMoonsItemMaterials.DEEP_IRON_TOOL, 0.95F, 0.95F, 0.6F, 2.5F, 8.0F, 6.75F, 5.1F, 11.25F, 4.6F)
  );
  public static final Item DEEP_IRON_SWORD = register(
      "deep_iron_sword",
      Item::new,
      new Item.Properties()
          .fireResistant()
          .sword(DistantMoonsItemMaterials.DEEP_IRON_TOOL, 3.0F, -2.4F)
  );
  public static final Item FIRE_BRICK = register(
      "fire_brick",
      Item::new,
      new Item.Properties()
          .delayedComponent(DataComponents.DAMAGE_RESISTANT, context -> new DamageResistant(context.getOrThrow(DistantMoonsDamageTypeTags.IS_EXPLOSION_OR_FIRE)))
  );
  public static final Item IRON_ROD = register(
      "iron_rod",
      Item::new,
      new Item.Properties()
  );
  public static final Item NETHER_KEYSTONE = register(
      "nether_keystone",
      Item::new,
      new Item.Properties()
          .component(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE, new DimensionKeystoneComponent(Level.NETHER.identifier(), DimensionKeystoneComponent.NETHER_COLOR, UnderDistantMoons.identifierOf("underworld_conflux_keystone/nether")))
          .fireResistant()
          .stacksTo(1)
  );
  public static final Item PALE_PRISMARINE_SHARD = register(
      "pale_prismarine_shard",
      Item::new,
      new Item.Properties()
  );
  public static final Item RAW_DEEP_IRON = register(
      "raw_deep_iron",
      Item::new,
      new Item.Properties()
          .fireResistant()
  );
  public static final Item REFINED_DEEP_IRON_INGOT = register(
      "refined_deep_iron_ingot",
      Item::new,
      new Item.Properties()
          .fireResistant()
  );
  public static final Item REFINED_DEEP_IRON_NUGGET = register(
      "refined_deep_iron_nugget",
      Item::new,
      new Item.Properties()
          .fireResistant()
  );
  public static final Item REFINED_DEEP_IRON_ROD = register(
      "refined_deep_iron_rod",
      Item::new,
      new Item.Properties()
          .fireResistant()
  );
  public static final Item ROASTED_BROWN_MUSHROOM = register(
      "roasted_brown_mushroom",
      Item::new,
      new Item.Properties()
          .component(DataComponents.CONSUMABLE, Consumable.builder().build())
          .component(DataComponents.FOOD, new FoodProperties(5, 6F, false))
  );
  public static final Item ROTTEN_FISH = register(
      "rotten_fish",
      Item::new,
      new Item.Properties()
          .component(DataComponents.CONSUMABLE, Consumables.ROTTEN_FLESH)
          .component(DataComponents.FOOD, new FoodProperties(4, 0.1F, false))
  );
  public static final Item RUBY = register(
      "ruby",
      Item::new,
      new Item.Properties()
  );
  public static final Item SAPPHIRE = register(
      "sapphire",
      Item::new,
      new Item.Properties()
  );
  public static final Item UNDERWORLD_COMPASS = register(
      "underworld_compass",
      UnderworldCompassItem::new,
      new Item.Properties()
          .fireResistant()
  );
  public static final Item UNDERWORLD_DUST = register(
      "underworld_dust",
      Item::new,
      new Item.Properties()
  );
  public static final Item UNDERWORLD_PEARL = register(
      "underworld_pearl",
      Item::new,
      new Item.Properties()
  );
  public static final Item WROUGHT_IRON_ROD = register(
      "wrought_iron_rod",
      Item::new,
      new Item.Properties()
  );

  private static Item register(String id, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
    ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, UnderDistantMoons.identifierOf(id));
    return Registry.register(BuiltInRegistries.ITEM, key, itemFactory.apply(settings.setId(key)));
  }

  public static String getStringIdOf(Item item) {
    return BuiltInRegistries.ITEM.wrapAsHolder(item).getRegisteredName().split(":")[1];
  }

  public static void initialize() {}
}
