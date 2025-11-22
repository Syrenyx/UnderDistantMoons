package syrenyx.distantmoons.mixin;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.noise.NoiseRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DensityFunctions.class)
public abstract class DensityFunctionsMixin {

  @Shadow
  private static DensityFunction applyBlendDensity(DensityFunction density) {
    return null;
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "createNetherNoiseRouter")
  private static void distantMoons$createNetherNoiseRouter(
      RegistryEntryLookup<DensityFunction> densityFunctionLookup, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParametersLookup, CallbackInfoReturnable<NoiseRouter> callbackInfo
  ) {
    DensityFunction shiftXFunction = DensityFunctionsAccessor.distantMoons$entryHolder(densityFunctionLookup, DensityFunctionsAccessor.distantMoons$SHIFT_X());
    DensityFunction shiftZFunction = DensityFunctionsAccessor.distantMoons$entryHolder(densityFunctionLookup, DensityFunctionsAccessor.distantMoons$SHIFT_Z());
    callbackInfo.setReturnValue(new NoiseRouter(
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.shiftedNoise(shiftXFunction, shiftZFunction, 0.25, noiseParametersLookup.getOrThrow(NoiseParametersKeys.TEMPERATURE)),
        DensityFunctionTypes.shiftedNoise(shiftXFunction, shiftZFunction, 0.25, noiseParametersLookup.getOrThrow(NoiseParametersKeys.VEGETATION)),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.zero(),
        applyBlendDensity(DensityFunctionsAccessor.distantMoons$applyCavesSlides(densityFunctionLookup, 0, 128)),
        DensityFunctionTypes.zero(),
        DensityFunctionTypes.add(DensityFunctionTypes.constant(-0.08F), DensityFunctionTypes.noise(noiseParametersLookup.getOrThrow(NoiseParametersKeys.ORE_VEIN_A))),
        DensityFunctionTypes.zero()
    ));
  }
}
