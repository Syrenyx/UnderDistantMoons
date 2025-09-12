package syrenyx.distantmoons.mixin.client;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.UnderDistantMoons;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin {

  @Unique private static final Identifier SLOT_TEXTURE = UnderDistantMoons.identifierOf("textures/gui/container/slot.png");

  @Shadow
  private static ItemGroup selectedTab;

  @Inject(at = @At("TAIL"), method = "drawBackground")
  private void drawBackground(
      DrawContext context,
      float deltaTicks,
      int mouseX,
      int mouseY,
      CallbackInfo callbackInfo
  ) {
    if (selectedTab.getType() != ItemGroup.Type.INVENTORY) return;
    HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor) this;
    int x = handledScreenAccessor.x() + 126;
    int y = handledScreenAccessor.y() + 19;
    context.drawTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, x, y, 0.0F, 0.0F, 18, 18, 18, 18);
  }
}
