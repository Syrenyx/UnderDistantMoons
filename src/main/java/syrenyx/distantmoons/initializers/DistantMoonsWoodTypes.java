package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.state.properties.WoodType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.mixin.WoodTypeAccessor;
import syrenyx.distantmoons.references.DistantMoonsBlockSetTypes;

public abstract class DistantMoonsWoodTypes {

  public static final WoodType ABYSS_TEAR = WoodTypeBuilder.copyOf(WoodType.OAK)
      .build(UnderDistantMoons.identifierOf("abyss_tear"), DistantMoonsBlockSetTypes.ABYSS_TEAR);

  static {
    WoodTypeAccessor.TYPES().put(ABYSS_TEAR.name(), ABYSS_TEAR);
  }

  public static void initialize() {}
}
