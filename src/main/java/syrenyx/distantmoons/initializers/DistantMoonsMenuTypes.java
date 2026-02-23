package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import syrenyx.distantmoons.content.menu.block.LargeBlastFurnaceMenu;

public abstract class DistantMoonsMenuTypes {

  public static final MenuType<LargeBlastFurnaceMenu> LARGE_BLAST_FURNACE_DEFAULT = register("large_blast_furnace_default", (id, inventory) -> new LargeBlastFurnaceMenu(id, inventory, false));
  public static final MenuType<LargeBlastFurnaceMenu> LARGE_BLAST_FURNACE_MIRRORED = register("large_blast_furnace_mirrored", (id, inventory) -> new LargeBlastFurnaceMenu(id, inventory, true));

  private static <T extends AbstractContainerMenu> MenuType<T> register(String id, MenuType.MenuSupplier<T> menuSupplier) {
    return Registry.register(BuiltInRegistries.MENU, id, new MenuType<>(menuSupplier, FeatureFlags.VANILLA_SET));
  }

  public static void initialize() {}
}
