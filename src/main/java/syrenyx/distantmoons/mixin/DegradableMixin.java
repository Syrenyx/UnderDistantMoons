package syrenyx.distantmoons.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Degradable;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;
import syrenyx.distantmoons.utility.MixinUtil;

import java.util.Optional;

@Mixin(Degradable.class)
public interface DegradableMixin {

  @Inject(at = @At(value = "HEAD"), cancellable = true, method = "tryDegrade")
  private void tryDegrade(
      BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfoReturnable<Optional<BlockState>> callbackInfo
  ) {
    Block thisBlock = (Block) this;
    if (!(thisBlock instanceof Degradable<?> degradableBlock) || degradableBlock.getDegradationLevel().getClass() != Oxidizable.OxidationLevel.class) return;
    MixinUtil.cancelAndSetReturnValue(BlockOxidizationManager.tryToOxidize(thisBlock, state, world, pos, random), callbackInfo);
  }
}
