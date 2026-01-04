package syrenyx.distantmoons.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(ChangeOverTimeBlock.class)
public interface ChangeOverTimeBlockMixin {

  @Inject(at = @At(value = "HEAD"), cancellable = true, method = "getNextState")
  private void distantMoons$getNextState(
      BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfoReturnable<Optional<BlockState>> callbackInfo
  ) {
    Block thisBlock = (Block) this;
    if (!(thisBlock instanceof ChangeOverTimeBlock<?> degradableBlock) || degradableBlock.getAge().getClass() != WeatheringCopper.WeatherState.class) return;
    callbackInfo.setReturnValue(BlockOxidizationManager.tryToOxidize(thisBlock, state, world, pos, random));
  }
}
