package syrenyx.distantmoons.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

@Mixin(FenceBlock.class)
public abstract class FenceBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "connectsTo")
  private void distantMoons$connectsTo(
      BlockState state,
      boolean neighborIsFullSquare,
      Direction dir,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    BlockState defaultState = ((FenceBlock) (Object) this).defaultBlockState();
    if (defaultState.is(DistantMoonsBlockTags.BRICK_FENCE)) {
      if (state.is(DistantMoonsBlockTags.BRICK_FENCE_NEVER_CONNECTS_TO)) callbackInfo.setReturnValue(false);
      else if (state.is(DistantMoonsBlockTags.BRICK_FENCE_ALWAYS_CONNECTS_TO)) callbackInfo.setReturnValue(true);
    }
    else if (defaultState.is(DistantMoonsBlockTags.WOODEN_FENCE)) {
      if (state.is(DistantMoonsBlockTags.WOODEN_FENCE_NEVER_CONNECTS_TO)) callbackInfo.setReturnValue(false);
      else if (state.is(DistantMoonsBlockTags.WOODEN_FENCE_ALWAYS_CONNECTS_TO)) callbackInfo.setReturnValue(true);
    }
  }
}
