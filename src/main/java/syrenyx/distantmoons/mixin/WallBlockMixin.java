package syrenyx.distantmoons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.FixedLadderBlock;
import syrenyx.distantmoons.content.block.block_state_enums.FixedLadderSideShape;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

@Mixin(WallBlock.class)
public abstract class WallBlockMixin {

  @Shadow @Final public static BooleanProperty UP;
  @Shadow @Final public static EnumProperty<WallSide> NORTH;
  @Shadow @Final public static EnumProperty<WallSide> EAST;
  @Shadow @Final public static EnumProperty<WallSide> SOUTH;
  @Shadow @Final public static EnumProperty<WallSide> WEST;

  @Shadow
  protected abstract boolean shouldRaisePost(BlockState state, BlockState aboveState, VoxelShape aboveShape);

  @Inject(at = @At("RETURN"), cancellable = true, method = "updateShape(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;ZZZZ)Lnet/minecraft/world/level/block/state/BlockState;")
  private void distantMoons$updateShape(
      LevelReader world,
      BlockState state,
      BlockPos pos,
      BlockState aboveState,
      boolean north, boolean east, boolean south, boolean west,
      CallbackInfoReturnable<BlockState> callbackInfo
  ) {
    if (!(aboveState.getBlock() instanceof FixedLadderBlock)) return;
    BlockState currentState = callbackInfo.getReturnValue();
    currentState = currentState
        .setValue(NORTH, currentState.getValue(NORTH) == WallSide.NONE ? WallSide.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.NORTH) ? WallSide.TALL : WallSide.LOW))
        .setValue(EAST, currentState.getValue(EAST) == WallSide.NONE ? WallSide.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.EAST) ? WallSide.TALL : WallSide.LOW))
        .setValue(SOUTH, currentState.getValue(SOUTH) == WallSide.NONE ? WallSide.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.SOUTH) ? WallSide.TALL : WallSide.LOW))
        .setValue(WEST, currentState.getValue(WEST) == WallSide.NONE ? WallSide.NONE : (FixedLadderBlock.blocksTop(aboveState, Direction.WEST) ? WallSide.TALL : WallSide.LOW));
    callbackInfo.setReturnValue(currentState
        .setValue(UP,
            shouldRaisePost(currentState, Blocks.AIR.defaultBlockState(), Shapes.empty())
                || ((aboveState.getValue(FixedLadderBlock.LEFT_SHAPE) == FixedLadderSideShape.NONE) != (aboveState.getValue(FixedLadderBlock.RIGHT_SHAPE) == FixedLadderSideShape.NONE))
                || currentState.getValue(NORTH) != WallSide.TALL
                && currentState.getValue(EAST) != WallSide.TALL
                && currentState.getValue(SOUTH) != WallSide.TALL
                && currentState.getValue(WEST) != WallSide.TALL
        )
    );
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "connectsTo")
  private void distantMoons$connectsTo(
      BlockState state,
      boolean faceFullSquare,
      Direction side,
      CallbackInfoReturnable<Boolean> callbackInfo
  ) {
    if (state.is(DistantMoonsBlockTags.WALL_NEVER_CONNECTS_TO)) callbackInfo.setReturnValue(false);
    else if (state.is(DistantMoonsBlockTags.WALL_ALWAYS_CONNECTS_TO)) callbackInfo.setReturnValue(true);
    else if (state.getBlock() instanceof FixedLadderBlock) callbackInfo.setReturnValue(FixedLadderBlock.canWallConnect(state, side));
  }
}
