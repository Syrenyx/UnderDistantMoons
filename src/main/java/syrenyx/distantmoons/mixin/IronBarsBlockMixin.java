package syrenyx.distantmoons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.FixedLadderBlock;
import syrenyx.distantmoons.content.block.SpikedFenceBlock;
import syrenyx.distantmoons.content.block.WallSlabBlock;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.BlockUtil;

@Mixin(IronBarsBlock.class)
public abstract class IronBarsBlockMixin {

  @WrapOperation(
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"),
      method = {"getStateForPlacement", "updateShape"}
  ) private boolean distantMoons$determineConnectionStates(
      BlockState state, BlockGetter blockView, BlockPos blockPos, Direction direction, Operation<Boolean> wrappedCall
  ) {
    BlockState defaultState = ((IronBarsBlock) (Object) this).defaultBlockState();
    BlockState connectedState = blockView.getBlockState(blockPos);
    if (defaultState.is(BlockTags.BARS)) {
      if (connectedState.is(DistantMoonsBlockTags.BARS_NEVER_CONNECTS_TO)) return false;
      if (connectedState.is(DistantMoonsBlockTags.BARS_ALWAYS_CONNECTS_TO)) return true;
    }
    if (defaultState.is(DistantMoonsBlockTags.GLASS_PANE)) {
      if (connectedState.is(DistantMoonsBlockTags.GLASS_PANE_NEVER_CONNECTS_TO)) return false;
      if (connectedState.is(DistantMoonsBlockTags.GLASS_PANE_ALWAYS_CONNECTS_TO)) return true;
    }
    if (connectedState.getBlock() instanceof FixedLadderBlock) return FixedLadderBlock.canWallConnect(connectedState, direction);
    if (connectedState.getBlock() instanceof StairBlock) return BlockUtil.isStairBlockFacePartiallySturdy(connectedState, direction);
    if (connectedState.getBlock() instanceof WallSlabBlock) return WallSlabBlock.isFacePartiallySturdy(connectedState, direction);
    return wrappedCall.call(state, blockView, blockPos, direction);
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "skipRendering")
  private void distantMoons$skipRendering(
      BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (!state.is(BlockTags.BARS)) return;
    if (
        stateFrom.getBlock() instanceof FixedLadderBlock
            || stateFrom.getBlock() instanceof SpikedFenceBlock
            || stateFrom.getBlock() instanceof StairBlock
            || stateFrom.getBlock() instanceof WallSlabBlock
    ) callbackInfo.setReturnValue(true);
  }
}
