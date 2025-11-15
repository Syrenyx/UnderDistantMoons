package syrenyx.distantmoons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationDefinition;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;

import java.util.Optional;

@Mixin(HoneycombItem.class)
public abstract class HoneycombItemMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "getWaxedState")
  private static void distantMoons$getWaxedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> callbackInfo) {
    BlockOxidizationDefinition oxidizationRules = BlockOxidizationManager.BLOCK_OXIDIZATION_MAP.get(state.getBlock());
    if (oxidizationRules == null || !oxidizationRules.canBeWaxed()) return;
    callbackInfo.setReturnValue(oxidizationRules.getWaxedStateOf(state));
  }
}
