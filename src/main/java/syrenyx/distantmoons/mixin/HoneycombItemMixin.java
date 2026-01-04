package syrenyx.distantmoons.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationDefinition;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;

import java.util.Optional;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(HoneycombItem.class)
public abstract class HoneycombItemMixin {

  @Inject(at = @At("HEAD"), cancellable = true, method = "getWaxed")
  private static void distantMoons$getWaxed(BlockState state, CallbackInfoReturnable<Optional<BlockState>> callbackInfo) {
    BlockOxidizationDefinition oxidizationRules = BlockOxidizationManager.BLOCK_OXIDIZATION_MAP.get(state.getBlock());
    if (oxidizationRules == null || !oxidizationRules.canBeWaxed()) return;
    callbackInfo.setReturnValue(oxidizationRules.getWaxedStateOf(state));
  }
}
