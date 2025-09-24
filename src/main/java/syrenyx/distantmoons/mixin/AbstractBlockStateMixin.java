package syrenyx.distantmoons.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.block.FixedLadderBlock;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "isSideSolidFullSquare")
  private void isSideSolidFullSquare(
      BlockView world,
      BlockPos pos,
      Direction direction,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    AbstractBlock.AbstractBlockState thisState = (AbstractBlock.AbstractBlockState) (Object) this;
    if (
        thisState.getBlock() instanceof FixedLadderBlock
            && (thisState.get(FixedLadderBlock.ROTATED) && direction.getAxis() == Direction.Axis.Z
            || !thisState.get(FixedLadderBlock.ROTATED) && direction.getAxis() == Direction.Axis.X)
    ) {
      callbackInfo.cancel();
      callbackInfo.setReturnValue(true);
    }
  }
}
