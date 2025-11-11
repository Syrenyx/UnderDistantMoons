package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.MixinUtil;

@Mixin(FenceBlock.class)
public abstract class FenceBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "canConnect")
  private void canConnect(
      BlockState state,
      boolean neighborIsFullSquare,
      Direction dir,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    BlockState defaultState = ((FenceBlock) (Object) this).getDefaultState();
    if (defaultState.isIn(DistantMoonsBlockTags.BRICK_FENCE)) {
      if (state.isIn(DistantMoonsBlockTags.BRICK_FENCE_NEVER_CONNECTS_TO)) MixinUtil.cancelAndSetReturnValue(false, callbackInfo);
      else if (state.isIn(DistantMoonsBlockTags.BRICK_FENCE_ALWAYS_CONNECTS_TO)) MixinUtil.cancelAndSetReturnValue(true, callbackInfo);
    }
    else if (defaultState.isIn(DistantMoonsBlockTags.WOODEN_FENCE)) {
      if (state.isIn(DistantMoonsBlockTags.WOODEN_FENCE_NEVER_CONNECTS_TO)) MixinUtil.cancelAndSetReturnValue(false, callbackInfo);
      else if (state.isIn(DistantMoonsBlockTags.WOODEN_FENCE_ALWAYS_CONNECTS_TO)) MixinUtil.cancelAndSetReturnValue(true, callbackInfo);
    }
  }
}
