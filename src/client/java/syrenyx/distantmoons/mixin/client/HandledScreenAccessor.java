package syrenyx.distantmoons.mixin.client;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {

  @Accessor("backgroundWidth") int backgroundWidth();
  @Accessor("x") int x();
  @Accessor("y") int y();
}
