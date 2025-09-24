package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.block.FixedLadderBlock;
import syrenyx.distantmoons.references.DistantMoonsTags;

@Mixin(WallBlock.class)
public class WallBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "shouldConnectTo")
  private void shouldConnectTo(
      BlockState state,
      boolean faceFullSquare,
      Direction side,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (state.isIn(DistantMoonsTags.WALL_NEVER_CONNECTS_TO)) {
      callbackInfo.cancel();
      callbackInfo.setReturnValue(false);
    }
    if (state.isIn(DistantMoonsTags.WALL_ALWAYS_CONNECTS_TO)) {
      callbackInfo.cancel();
      callbackInfo.setReturnValue(true);
    }
    if (state.getBlock() instanceof FixedLadderBlock) {
      callbackInfo.cancel();
      callbackInfo.setReturnValue(FixedLadderBlock.canWallConnect(state, side));
    }
  }
}
