package syrenyx.distantmoons.initializers;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import syrenyx.distantmoons.UnderDistantMoons;

import java.util.function.Function;

public abstract class DistantMoonsItems {

  public static final Item COKE = register(
      "coke",
      Item::new,
      new Item.Settings()
  );
  public static final Item CRUDE_DEEP_IRON_CHUNK = register(
      "crude_deep_iron_chunk",
      Item::new,
      new Item.Settings()
          .fireproof()
  );
  public static final Item IRON_ROD = register(
      "iron_rod",
      Item::new,
      new Item.Settings()
  );
  public static final Item RAW_DEEP_IRON = register(
      "raw_deep_iron",
      Item::new,
      new Item.Settings()
          .fireproof()
  );
  public static final Item REFINED_DEEP_IRON_INGOT = register(
      "refined_deep_iron_ingot",
      Item::new,
      new Item.Settings()
          .fireproof()
  );
  public static final Item REFINED_DEEP_IRON_NUGGET = register(
      "refined_deep_iron_nugget",
      Item::new,
      new Item.Settings()
          .fireproof()
  );
  public static final Item REFINED_DEEP_IRON_ROD = register(
      "refined_deep_iron_rod",
      Item::new,
      new Item.Settings()
          .fireproof()
  );
  public static final Item ROASTED_BROWN_MUSHROOM = register(
      "roasted_brown_mushroom",
      Item::new,
      new Item.Settings()
          .component(DataComponentTypes.CONSUMABLE, ConsumableComponent.builder().build())
          .component(DataComponentTypes.FOOD, new FoodComponent(5, 6F, false))
  );
  public static final Item UNDERWORLD_DUST = register(
      "underworld_dust",
      Item::new,
      new Item.Settings()
  );

  private static Item register(String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
    RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, UnderDistantMoons.identifierOf(id));
    return Registry.register(Registries.ITEM, key, itemFactory.apply(settings.registryKey(key)));
  }

  public static void initialize() {}
}
