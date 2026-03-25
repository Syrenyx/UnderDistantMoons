package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsPointOfInterestTypes {

  public static final PoiType UNDERWORLD_ANCHOR = register("underworld_anchor", DistantMoonsBlocks.UNDERWORLD_ANCHOR);

  private static PoiType register(String id, Block ... blocks) {
    return PointOfInterestHelper.register(UnderDistantMoons.identifierOf(id), 1, 64, blocks);
  }

  public static void initialize() {}
}
