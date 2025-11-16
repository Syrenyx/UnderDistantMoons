package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "spawnLeafParticle(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V")
  private void distantMoons$spawnLeafParticles(World world, BlockPos pos, Random random, BlockState state, BlockPos posBelow, CallbackInfo callbackInfo) {
    if (world.getBlockState(pos).isIn(DistantMoonsBlockTags.LEAVES_WITHOUT_LEAF_PARTICLES)) callbackInfo.cancel();
  }
}
