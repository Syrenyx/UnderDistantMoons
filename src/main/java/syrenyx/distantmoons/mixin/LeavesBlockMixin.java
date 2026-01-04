package syrenyx.distantmoons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "makeFallingLeavesParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V")
  private void distantMoons$makeFallingLeavesParticles(Level world, BlockPos pos, RandomSource random, BlockState state, BlockPos posBelow, CallbackInfo callbackInfo) {
    if (world.getBlockState(pos).is(DistantMoonsBlockTags.LEAVES_WITHOUT_LEAF_PARTICLES)) callbackInfo.cancel();
  }
}
