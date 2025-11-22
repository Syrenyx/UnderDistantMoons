package syrenyx.distantmoons.mixin;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DensityFunctions.class)
public interface DensityFunctionsAccessor {

  @Accessor("SHIFT_X")
  static RegistryKey<DensityFunction> distantMoons$SHIFT_X() {
    throw new AssertionError();
  }

  @Accessor("SHIFT_Z")
  static RegistryKey<DensityFunction> distantMoons$SHIFT_Z() {
    throw new AssertionError();
  }

  @Invoker("applyCavesSlides")
  static DensityFunction distantMoons$applyCavesSlides(RegistryEntryLookup<DensityFunction> densityFunctionLookup, int minY, int maxY) {
    throw new AssertionError();
  }

  @Invoker("entryHolder")
  static DensityFunction distantMoons$entryHolder(RegistryEntryLookup<DensityFunction> densityFunctionLookup, RegistryKey<DensityFunction> densityFunction) {
    throw new AssertionError();
  }

  @Invoker("verticalRangeChoice")
  static DensityFunction distantMoons$verticalRangeChoice(DensityFunction y, DensityFunction whenInRange, int minInclusive, int maxInclusive, int whenOutOfRange) {
    throw new AssertionError();
  }
}
