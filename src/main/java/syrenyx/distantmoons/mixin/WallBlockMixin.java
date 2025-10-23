package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallBlock;
import net.minecraft.block.enums.WallShape;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.FixedLadderBlock;
import syrenyx.distantmoons.content.block.block_state_enums.FixedLadderSideShape;
import syrenyx.distantmoons.references.DistantMoonsTags;
import syrenyx.distantmoons.utility.MixinUtil;

@Mixin(WallBlock.class)
public abstract class WallBlockMixin {

  @Shadow @Final public static BooleanProperty UP;
  @Shadow @Final public static EnumProperty<WallShape> NORTH_WALL_SHAPE;
  @Shadow @Final public static EnumProperty<WallShape> EAST_WALL_SHAPE;
  @Shadow @Final public static EnumProperty<WallShape> SOUTH_WALL_SHAPE;
  @Shadow @Final public static EnumProperty<WallShape> WEST_WALL_SHAPE;

  @Shadow
  protected abstract boolean shouldHavePost(BlockState state, BlockState aboveState, VoxelShape aboveShape);

  @Inject(at = @At("RETURN"), cancellable = true, method = "getStateWith(Lnet/minecraft/world/WorldView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;ZZZZ)Lnet/minecraft/block/BlockState;")
  private void getStateWith(
      WorldView world,
      BlockState state,
      BlockPos pos,
      BlockState aboveState,
      boolean north, boolean east, boolean south, boolean west,
      CallbackInfoReturnable<BlockState> callbackInfo
  ) {
    if (!(aboveState.getBlock() instanceof FixedLadderBlock)) return;
    BlockState currentState = callbackInfo.getReturnValue();
    currentState = currentState
        .with(NORTH_WALL_SHAPE, currentState.get(NORTH_WALL_SHAPE) == WallShape.NONE ? WallShape.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.NORTH) ? WallShape.TALL : WallShape.LOW))
        .with(EAST_WALL_SHAPE, currentState.get(EAST_WALL_SHAPE) == WallShape.NONE ? WallShape.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.EAST) ? WallShape.TALL : WallShape.LOW))
        .with(SOUTH_WALL_SHAPE, currentState.get(SOUTH_WALL_SHAPE) == WallShape.NONE ? WallShape.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.SOUTH) ? WallShape.TALL : WallShape.LOW))
        .with(WEST_WALL_SHAPE, currentState.get(WEST_WALL_SHAPE) == WallShape.NONE ? WallShape.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.WEST) ? WallShape.TALL : WallShape.LOW));
    callbackInfo.setReturnValue(currentState
        .with(UP,
            shouldHavePost(currentState, Blocks.AIR.getDefaultState(), VoxelShapes.empty())
                || ((aboveState.get(FixedLadderBlock.LEFT_SHAPE) == FixedLadderSideShape.NONE) != (aboveState.get(FixedLadderBlock.RIGHT_SHAPE) == FixedLadderSideShape.NONE))
                || currentState.get(NORTH_WALL_SHAPE) != WallShape.TALL
                && currentState.get(EAST_WALL_SHAPE) != WallShape.TALL
                && currentState.get(SOUTH_WALL_SHAPE) != WallShape.TALL
                && currentState.get(WEST_WALL_SHAPE) != WallShape.TALL
        )
    );
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "shouldConnectTo")
  private void shouldConnectTo(
      BlockState state,
      boolean faceFullSquare,
      Direction side,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (state.isIn(DistantMoonsTags.WALL_NEVER_CONNECTS_TO)) MixinUtil.cancelAndReturnValue(false, callbackInfo);
    if (state.isIn(DistantMoonsTags.WALL_ALWAYS_CONNECTS_TO)) MixinUtil.cancelAndReturnValue(true, callbackInfo);
    if (state.getBlock() instanceof FixedLadderBlock) MixinUtil.cancelAndReturnValue(FixedLadderBlock.canWallConnect(state, side), callbackInfo);
  }
}
