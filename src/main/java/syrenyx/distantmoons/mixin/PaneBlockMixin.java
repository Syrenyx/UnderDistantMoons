package syrenyx.distantmoons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
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

  @WrapOperation(
      at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isSideSolidFullSquare(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"),
      method = {"getPlacementState", "getStateForNeighborUpdate"}
  ) private boolean determineConnectionStates(
      BlockState instance, BlockView blockView, BlockPos blockPos, Direction direction, Operation<Boolean> wrappedCall
  ) {
    BlockState defaultState = ((PaneBlock) (Object) this).getDefaultState();
    BlockState connectedState = blockView.getBlockState(blockPos);
    if (defaultState.isIn(BlockTags.BARS)) {
      if (connectedState.isIn(DistantMoonsTags.BARS_NEVER_CONNECTS_TO)) return false;
      if (connectedState.isIn(DistantMoonsTags.BARS_ALWAYS_CONNECTS_TO)) return true;
    }
    if (defaultState.isIn(DistantMoonsTags.GLASS_PANE)) {
      if (connectedState.isIn(DistantMoonsTags.GLASS_PANE_NEVER_CONNECTS_TO)) return false;
      if (connectedState.isIn(DistantMoonsTags.GLASS_PANE_ALWAYS_CONNECTS_TO)) return true;
    }
    return wrappedCall.call(instance, blockView, blockPos, direction.getOpposite())
        || connectedState.getBlock() instanceof FixedLadderBlock && FixedLadderBlock.canWallConnect(connectedState, direction.getOpposite());
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "isSideInvisible")
  private void isSideInvisible(
      BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (
        state.isIn(BlockTags.BARS)
            && (stateFrom.getBlock() instanceof FixedLadderBlock
            || stateFrom.getBlock() instanceof SpikedFenceBlock)
    ) MixinUtil.cancelAndReturnValue(true, callbackInfo);
  }
}
