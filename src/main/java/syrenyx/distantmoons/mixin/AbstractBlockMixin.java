package syrenyx.distantmoons.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;
import syrenyx.distantmoons.utility.MixinUtil;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "hasRandomTicks")
  private void hasRandomTicks(BlockState state, CallbackInfoReturnable<Boolean> callbackInfo) {
    if (state.isOf(Blocks.IRON_BLOCK)) MixinUtil.cancelAndSetReturnValue(true, callbackInfo);
  }

  @Inject(at = @At("HEAD"), method = "randomTick")
  private void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo callbackInfo) {
    if (random.nextFloat() >= BlockOxidizationManager.OXIDIZATION_CHANCE) return;
    BlockOxidizationManager
        .tryToOxidize((Block) (Object) this, state, world, pos, random)
        .ifPresent(blockState -> world.setBlockState(pos, blockState));
  }
}
