package syrenyx.distantmoons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;

@Mixin(net.minecraft.world.level.block.state.BlockBehaviour.class)
public abstract class BlockBehaviour {

  @Inject(at = @At("HEAD"), cancellable = true, method = "isRandomlyTicking")
  private void distantMoons$isRandomlyTicking(BlockState state, CallbackInfoReturnable<Boolean> callbackInfo) {
    if (state.is(Blocks.IRON_BLOCK)) callbackInfo.setReturnValue(true);
  }

  @Inject(at = @At("HEAD"), method = "randomTick")
  private void distantMoons$randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo callbackInfo) {
    if (random.nextFloat() >= BlockOxidizationManager.OXIDIZATION_CHANCE) return;
    BlockOxidizationManager
        .tryToOxidize((Block) (Object) this, state, world, pos, random)
        .ifPresent(blockState -> world.setBlockAndUpdate(pos, blockState));
  }
}
