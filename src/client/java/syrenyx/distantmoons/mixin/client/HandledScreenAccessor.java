package syrenyx.distantmoons.mixin.client;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface HandledScreenAccessor {

  @Accessor("imageWidth") int backgroundWidth();
  @Accessor("leftPos") int x();
  @Accessor("topPos") int y();
}
