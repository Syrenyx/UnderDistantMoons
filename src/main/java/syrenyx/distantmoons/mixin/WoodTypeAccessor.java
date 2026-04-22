package syrenyx.distantmoons.mixin;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(WoodType.class)
public interface WoodTypeAccessor {

  @Accessor("TYPES")
  static Map<String, WoodType> TYPES() {
    throw new AssertionError();
  }
}
