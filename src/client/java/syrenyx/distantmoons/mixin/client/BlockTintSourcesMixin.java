package syrenyx.distantmoons.mixin.client;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import syrenyx.distantmoons.content.rendering.block.ExtendedBlockTintSource;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.ColorUtil;

@Mixin(BlockTintSources.class)
public abstract class BlockTintSourcesMixin {

  @Inject(at = @At("RETURN"), cancellable = true, method = "doubleTallGrass")
  private static void distantMoons$doubleTallGrass(CallbackInfoReturnable<BlockTintSource> callbackInfo) {
    callbackInfo.setReturnValue(new ExtendedBlockTintSource(callbackInfo.getReturnValue()) {
      @Override
      public int colorInWorld(final @NonNull BlockState state, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos) {
        BlockState bottomState = level.getBlockState(pos.offset(0, state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER ? -1 : -2, 0));
        if (bottomState.is(DistantMoonsBlockTags.BLOCK_TINT_PROVIDER_MOSS)) return ColorUtil.MOSS_COLOR;
        if (bottomState.is(DistantMoonsBlockTags.BLOCK_TINT_PROVIDER_PALE_MOSS)) return ColorUtil.PALE_MOSS_COLOR;
        return super.colorInWorld(state, level, pos);
      }
    });
  }

  @Inject(at = @At("RETURN"), cancellable = true, method = "grass")
  private static void distantMoons$grass(CallbackInfoReturnable<BlockTintSource> callbackInfo) {
    callbackInfo.setReturnValue(new ExtendedBlockTintSource(callbackInfo.getReturnValue()) {
      @Override
      public int colorInWorld(final @NonNull BlockState state, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos) {
        BlockState bottomState = level.getBlockState(pos.below());
        if (bottomState.is(DistantMoonsBlockTags.BLOCK_TINT_PROVIDER_MOSS)) return ColorUtil.MOSS_COLOR;
        if (bottomState.is(DistantMoonsBlockTags.BLOCK_TINT_PROVIDER_PALE_MOSS)) return ColorUtil.PALE_MOSS_COLOR;
        return super.colorInWorld(state, level, pos);
      }
    });
  }

  @Inject(at = @At("RETURN"), cancellable = true, method = "sugarCane")
  private static void distantMoons$sugarCane(CallbackInfoReturnable<BlockTintSource> callbackInfo) {
    callbackInfo.setReturnValue(new ExtendedBlockTintSource(callbackInfo.getReturnValue()) {
      @Override
      public int colorInWorld(final @NonNull BlockState state, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos) {
        BlockPos groundPos = pos.below();
        while (level.getBlockState(groundPos).is(state.getBlock())) groundPos = groundPos.below();
        BlockState bottomState = level.getBlockState(groundPos);
        if (bottomState.is(DistantMoonsBlockTags.BLOCK_TINT_PROVIDER_MOSS)) return ColorUtil.MOSS_COLOR;
        if (bottomState.is(DistantMoonsBlockTags.BLOCK_TINT_PROVIDER_PALE_MOSS)) return ColorUtil.PALE_MOSS_COLOR;
        return super.colorInWorld(state, level, pos);
      }
    });
  }
}
