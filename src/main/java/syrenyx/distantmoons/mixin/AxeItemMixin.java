package syrenyx.distantmoons.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationDefinition;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;
import syrenyx.distantmoons.utility.MixinUtil;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

  @Shadow
  private static void strip(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state, SoundEvent sound, int worldEvent) {}

  @Inject(at = @At("HEAD"), cancellable = true, method = "tryStrip")
  private void tryStrip(
      World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> callbackInfo
  ) {
    Block block = state.getBlock();
    BlockOxidizationDefinition oxidizationRules = BlockOxidizationManager.BLOCK_OXIDIZATION_MAP.get(block);
    if (oxidizationRules != null && oxidizationRules.canBeScraped()) {
      strip(world, pos, player, state, SoundEvents.ITEM_AXE_SCRAPE, BlockOxidizationManager.SCRAPE_WORLD_EVENT);
      MixinUtil.cancelAndSetReturnValue(oxidizationRules.getScrapedStateOf(state), callbackInfo);
    } else if (BlockOxidizationManager.WAXED_BLOCK_SCRAPING_MAP.containsKey(block)) {
      strip(world, pos, player, state, SoundEvents.ITEM_AXE_WAX_OFF, BlockOxidizationManager.WAX_OFF_WORLD_EVENT);
      MixinUtil.cancelAndSetReturnValue(Optional.of(BlockOxidizationManager.WAXED_BLOCK_SCRAPING_MAP.get(block).apply(state)), callbackInfo);
    }
  }
}
