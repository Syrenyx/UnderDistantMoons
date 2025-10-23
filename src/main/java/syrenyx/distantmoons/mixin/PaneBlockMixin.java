package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.FixedLadderBlock;
import syrenyx.distantmoons.content.block.SpikedFenceBlock;
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
    if (defaultState.isIn(BlockTags.BARS)) {
      if (state.isIn(DistantMoonsTags.BARS_NEVER_CONNECTS_TO)) MixinUtil.cancelAndReturnValue(false, callbackInfo);
      if (state.isIn(DistantMoonsTags.BARS_ALWAYS_CONNECTS_TO)) MixinUtil.cancelAndReturnValue(true, callbackInfo);
    }
    if (defaultState.isIn(DistantMoonsTags.GLASS_PANE)) {
      if (state.isIn(DistantMoonsTags.GLASS_PANE_NEVER_CONNECTS_TO)) MixinUtil.cancelAndReturnValue(false, callbackInfo);
      if (state.isIn(DistantMoonsTags.GLASS_PANE_ALWAYS_CONNECTS_TO)) MixinUtil.cancelAndReturnValue(true, callbackInfo);
    }
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "isSideInvisible")
  private void isSideInvisible(
      BlockState state,
      BlockState stateFrom,
      Direction direction,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (
        state.isIn(BlockTags.BARS)
            && (stateFrom.getBlock() instanceof FixedLadderBlock
            || stateFrom.getBlock() instanceof SpikedFenceBlock)
    ) MixinUtil.cancelAndReturnValue(true, callbackInfo);
  }
}
