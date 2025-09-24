package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.block.FixedLadderBlock;
import syrenyx.distantmoons.references.DistantMoonsTags;

@Mixin(PaneBlock.class)
public class PaneBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "connectsTo")
  private void connectsTo(
      BlockState state,
      boolean sideSolidFullSquare,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    PaneBlock thisPaneBlock = (PaneBlock) (Object) this;
    BlockState defaultState = thisPaneBlock.getDefaultState();
    if (defaultState.isIn(DistantMoonsTags.METAL_BARS)) {
      if (state.isIn(DistantMoonsTags.METAL_BARS_NEVER_CONNECTS_TO)) {
        callbackInfo.cancel();
        callbackInfo.setReturnValue(false);
      }
      if (state.isIn(DistantMoonsTags.METAL_BARS_ALWAYS_CONNECTS_TO)) {
        callbackInfo.cancel();
        callbackInfo.setReturnValue(true);
      }
    }
    if (defaultState.isIn(DistantMoonsTags.GLASS_PANE)) {
      if (state.isIn(DistantMoonsTags.GLASS_PANE_NEVER_CONNECTS_TO)) {
        callbackInfo.cancel();
        callbackInfo.setReturnValue(false);
      }
      if (state.isIn(DistantMoonsTags.GLASS_PANE_ALWAYS_CONNECTS_TO)) {
        callbackInfo.cancel();
        callbackInfo.setReturnValue(true);
      }
    }
  }
}
