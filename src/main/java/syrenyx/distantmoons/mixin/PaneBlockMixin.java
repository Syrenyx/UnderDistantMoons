package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.references.DistantMoonsTags;
import syrenyx.distantmoons.utility.MixinUtil;

@Mixin(PaneBlock.class)
public class PaneBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "connectsTo")
  private void connectsTo(
      BlockState state,
      boolean sideSolidFullSquare,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    BlockState defaultState = ((PaneBlock) (Object) this).getDefaultState();
    if (defaultState.isIn(DistantMoonsTags.METAL_BARS)) {
      if (state.isIn(DistantMoonsTags.METAL_BARS_NEVER_CONNECTS_TO)) MixinUtil.returnBoolean(false, callbackInfo);
      if (state.isIn(DistantMoonsTags.METAL_BARS_ALWAYS_CONNECTS_TO)) MixinUtil.returnBoolean(true, callbackInfo);
    }
    if (defaultState.isIn(DistantMoonsTags.GLASS_PANE)) {
      if (state.isIn(DistantMoonsTags.GLASS_PANE_NEVER_CONNECTS_TO)) MixinUtil.returnBoolean(false, callbackInfo);
      if (state.isIn(DistantMoonsTags.GLASS_PANE_ALWAYS_CONNECTS_TO)) MixinUtil.returnBoolean(true, callbackInfo);
    }
  }
}
