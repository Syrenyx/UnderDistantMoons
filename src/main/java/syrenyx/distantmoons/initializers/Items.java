package syrenyx.distantmoons.initializers;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.data.component.HeartComponent;

import java.util.function.Function;

public abstract class Items {

  public static final Item MORTAL_HEART = register(
      "mortal_heart",
      Item::new,
      new Item.Settings()
          .component(DataComponents.HEART_COMPONENT, new HeartComponent())
  );

  private static Item register(String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
    RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, UnderDistantMoons.identifierOf(id));
    return Registry.register(Registries.ITEM, itemKey, itemFactory.apply(settings.registryKey(itemKey)));
  }

  public static void initialize() {}
}
