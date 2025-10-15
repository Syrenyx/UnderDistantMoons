package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.FixedLadderBlock;
import syrenyx.distantmoons.references.DistantMoonsTags;
import syrenyx.distantmoons.utility.MixinUtil;

@Mixin(WallBlock.class)
public class WallBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "shouldConnectTo")
  private void shouldConnectTo(
      BlockState state,
      boolean faceFullSquare,
      Direction side,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (state.isIn(DistantMoonsTags.WALL_NEVER_CONNECTS_TO)) MixinUtil.returnBoolean(false, callbackInfo);
    if (state.isIn(DistantMoonsTags.WALL_ALWAYS_CONNECTS_TO)) MixinUtil.returnBoolean(true, callbackInfo);
    if (state.getBlock() instanceof FixedLadderBlock) MixinUtil.returnBoolean(FixedLadderBlock.canWallConnect(state, side), callbackInfo);
  }
}
