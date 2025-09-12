package syrenyx.distantmoons.mixin.client;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;

@Mixin(InventoryScreen.class)
@SuppressWarnings("resource")
public abstract class InventoryScreenMixin {

  @Unique private static final Identifier SLOT_TEXTURE = UnderDistantMoons.identifierOf("textures/gui/container/slot.png");

  @Inject(at = @At("TAIL"), method = "drawBackground")
  private void drawBackground(
      DrawContext context,
      float deltaTicks,
      int mouseX,
      int mouseY,
      CallbackInfo callbackInfo
  ) {
    ClientPlayerEntity player = ((ScreenAccessor) this).client().player;
    if (player == null || !ClientPlayerAttachment.getOrCreate(player).heartSlotUnlocked()) return;
    HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor) this;
    int x = handledScreenAccessor.x() + 76;
    int y = handledScreenAccessor.y() + 43;
    context.drawTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, x, y, 0.0F, 0.0F, 18, 18, 18, 18);
  }
}
