package syrenyx.distantmoons.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationDefinition;
import syrenyx.distantmoons.content.block.oxidization.BlockOxidizationManager;
import syrenyx.distantmoons.initializers.DistantMoonsParticleTypes;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

  @Shadow
  private static void spawnSoundAndParticle(Level world, BlockPos pos, @Nullable Player player, BlockState state, SoundEvent sound, int worldEvent) {}

  @Inject(at = @At("HEAD"), cancellable = true, method = "evaluateNewBlockState")
  private void distantMoons$evaluateNewBlockState(
      Level world, BlockPos pos, @Nullable Player player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> callbackInfo
  ) {
    Block block = state.getBlock();
    BlockOxidizationDefinition oxidizationRules = BlockOxidizationManager.BLOCK_OXIDIZATION_MAP.get(block);
    if (oxidizationRules != null && oxidizationRules.canBeScraped()) {
      if (oxidizationRules.rust()) distantMoons$scrapeRust(world, pos, player, state);
      else spawnSoundAndParticle(world, pos, player, state, SoundEvents.AXE_SCRAPE, BlockOxidizationManager.SCRAPE_WORLD_EVENT);
      callbackInfo.setReturnValue(oxidizationRules.getScrapedStateOf(state));
    } else if (BlockOxidizationManager.WAXED_BLOCK_SCRAPING_MAP.containsKey(block)) {
      spawnSoundAndParticle(world, pos, player, state, SoundEvents.AXE_WAX_OFF, BlockOxidizationManager.WAX_OFF_WORLD_EVENT);
      callbackInfo.setReturnValue(Optional.of(BlockOxidizationManager.WAXED_BLOCK_SCRAPING_MAP.get(block).apply(state)));
    }
  }

  @Unique
  private static void distantMoons$scrapeRust(Level world, BlockPos pos, @Nullable Player player, BlockState state) {
    world.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
    ParticleUtils.spawnParticlesOnBlockFaces(world, pos, DistantMoonsParticleTypes.SCRAPE_RUST, UniformInt.of(3, 5));
  }
}
