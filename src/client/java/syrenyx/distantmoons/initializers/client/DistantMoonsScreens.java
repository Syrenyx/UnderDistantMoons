package syrenyx.distantmoons.initializers.client;

import net.minecraft.client.gui.screens.MenuScreens;
import syrenyx.distantmoons.content.gui.screen.world.LargeBlastFurnaceScreen;
import syrenyx.distantmoons.initializers.DistantMoonsMenuTypes;

public abstract class DistantMoonsScreens {

  static {
    MenuScreens.register(DistantMoonsMenuTypes.LARGE_BLAST_FURNACE_DEFAULT, LargeBlastFurnaceScreen::new);
    MenuScreens.register(DistantMoonsMenuTypes.LARGE_BLAST_FURNACE_MIRRORED, LargeBlastFurnaceScreen::new);
  }

  public static void initialize() {}
}
