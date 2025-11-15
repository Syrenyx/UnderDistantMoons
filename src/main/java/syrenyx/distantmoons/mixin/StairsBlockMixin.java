package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.WallSlabBlock;
import syrenyx.distantmoons.utility.MixinUtil;

@Mixin(StairsBlock.class)
public abstract class StairsBlockMixin {

  @Shadow @Final
  public static EnumProperty<BlockHalf> HALF;

  @Inject(at = @At(value = "HEAD"), cancellable = true, method = "getStairShape")
  private static void distantMoons$getStairShape(
      BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<StairShape> callbackInfo
  ) {
    Direction direction = state.get(HorizontalFacingBlock.FACING);
    BlockState leftState = world.getBlockState(pos.offset(direction.rotateYCounterclockwise()));
    boolean leftLocked = distantMoons$canConnectTo(state, leftState) && leftState.get(HorizontalFacingBlock.FACING) == direction;
    BlockState rightState = world.getBlockState(pos.offset(direction.rotateYClockwise()));
    boolean rightLocked = distantMoons$canConnectTo(state, rightState) && rightState.get(HorizontalFacingBlock.FACING) == direction;
    BlockState backState = world.getBlockState(pos.offset(direction));
    BlockState frontState = world.getBlockState(pos.offset(direction.getOpposite()));
    if (distantMoons$canConnectTo(state, frontState)) {
      Direction facing = frontState.get(HorizontalFacingBlock.FACING);
      if (direction.rotateYCounterclockwise() == facing && !leftLocked) MixinUtil.cancelAndSetReturnValue(StairShape.INNER_LEFT, callbackInfo);
      if (direction.rotateYClockwise() == facing && !rightLocked) MixinUtil.cancelAndSetReturnValue(StairShape.INNER_RIGHT, callbackInfo);
    }
    else if (distantMoons$canConnectTo(state, backState)) {
      Direction facing = backState.get(HorizontalFacingBlock.FACING);
      if (direction.rotateYCounterclockwise() == facing && !rightLocked) MixinUtil.cancelAndSetReturnValue(StairShape.OUTER_LEFT, callbackInfo);
      if (direction.rotateYClockwise() == facing && !leftLocked) MixinUtil.cancelAndSetReturnValue(StairShape.OUTER_RIGHT, callbackInfo);
    }
    else MixinUtil.cancelAndSetReturnValue(StairShape.STRAIGHT, callbackInfo);
  }

  @Unique
  private static boolean distantMoons$canConnectTo(BlockState thisState, BlockState neighborState) {
    return neighborState.getBlock() instanceof WallSlabBlock || neighborState.getBlock() instanceof StairsBlock && thisState.get(HALF) == neighborState.get(HALF);
  }
}
