package syrenyx.distantmoons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.WallSlabBlock;

@Mixin(StairBlock.class)
public abstract class StairBlockMixin {

  @Shadow @Final
  public static EnumProperty<Half> HALF;

  @Inject(at = @At(value = "HEAD"), cancellable = true, method = "getStairsShape")
  private static void distantMoons$getStairShape(
      BlockState state, BlockGetter world, BlockPos pos, CallbackInfoReturnable<StairsShape> callbackInfo
  ) {
    Direction direction = state.getValue(HorizontalDirectionalBlock.FACING);
    BlockState leftState = world.getBlockState(pos.relative(direction.getCounterClockWise()));
    boolean leftLocked = distantMoons$canConnectTo(state, leftState) && leftState.getValue(HorizontalDirectionalBlock.FACING) == direction;
    BlockState rightState = world.getBlockState(pos.relative(direction.getClockWise()));
    boolean rightLocked = distantMoons$canConnectTo(state, rightState) && rightState.getValue(HorizontalDirectionalBlock.FACING) == direction;
    BlockState backState = world.getBlockState(pos.relative(direction));
    BlockState frontState = world.getBlockState(pos.relative(direction.getOpposite()));
    if (distantMoons$canConnectTo(state, frontState)) {
      Direction facing = frontState.getValue(HorizontalDirectionalBlock.FACING);
      if (direction.getCounterClockWise() == facing && !leftLocked) callbackInfo.setReturnValue(StairsShape.INNER_LEFT);
      if (direction.getClockWise() == facing && !rightLocked) callbackInfo.setReturnValue(StairsShape.INNER_RIGHT);
    }
    else if (distantMoons$canConnectTo(state, backState)) {
      Direction facing = backState.getValue(HorizontalDirectionalBlock.FACING);
      if (direction.getCounterClockWise() == facing && !rightLocked) callbackInfo.setReturnValue(StairsShape.OUTER_LEFT);
      if (direction.getClockWise() == facing && !leftLocked) callbackInfo.setReturnValue(StairsShape.OUTER_RIGHT);
    }
    else callbackInfo.setReturnValue(StairsShape.STRAIGHT);
  }

  @Unique
  private static boolean distantMoons$canConnectTo(BlockState thisState, BlockState neighborState) {
    return neighborState.getBlock() instanceof WallSlabBlock || neighborState.getBlock() instanceof StairBlock && thisState.getValue(HALF) == neighborState.getValue(HALF);
  }
}
