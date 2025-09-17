package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.references.DistantMoonsTags;

@Mixin(FenceBlock.class)
public class FenceBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "canConnect")
  private void canConnect(
      BlockState state,
      boolean neighborIsFullSquare,
      Direction dir,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (state.isIn(DistantMoonsTags.FENCE_NEVER_CONNECTS_TO)) {
      callbackInfo.cancel();
      callbackInfo.setReturnValue(false);
    }
    if (state.isIn(DistantMoonsTags.FENCE_ALWAYS_CONNECTS_TO)) {
      callbackInfo.cancel();
      callbackInfo.setReturnValue(true);
    }
  }
}
