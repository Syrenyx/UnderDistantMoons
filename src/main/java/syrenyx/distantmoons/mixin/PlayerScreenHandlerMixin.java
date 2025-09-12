package syrenyx.distantmoons.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;
import syrenyx.distantmoons.data.persistent.PersistentStateManager;
import syrenyx.distantmoons.screen.inventory.HeartInventory;
import syrenyx.distantmoons.screen.slot.HeartSlot;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin {

  @Inject(at = @At("TAIL"), method = "<init>")
  private void init(
      PlayerInventory inventory,
      boolean onServer,
      PlayerEntity owner,
      CallbackInfo callbackInfo
  ) {
    if (owner instanceof ServerPlayerEntity
        ? !PersistentStateManager.getPlayerState(owner).heartSlotUnlocked()
        : !ClientPlayerAttachment.getOrCreate(owner).heartSlotUnlocked()
    ) return;
    ScreenHandlerInvoker screenHandlerInvoker = (ScreenHandlerInvoker) this;
    screenHandlerInvoker.invokeAddSlot(new HeartSlot(new HeartInventory(owner), 0, 77, 44));
  }
}
