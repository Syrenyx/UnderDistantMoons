package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import syrenyx.distantmoons.content.menu.block.LargeBlastFurnaceMenu;

public abstract class DistantMoonsMenuTypes {

  public static final MenuType<LargeBlastFurnaceMenu> LARGE_BLAST_FURNACE = register("large_blast_furnace", LargeBlastFurnaceMenu::new);

  private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType.MenuSupplier<T> menuSupplier) {
    return Registry.register(BuiltInRegistries.MENU, id, new MenuType<>(menuSupplier, FeatureFlags.VANILLA_SET));
  }

  public static void initialize() {}
}
