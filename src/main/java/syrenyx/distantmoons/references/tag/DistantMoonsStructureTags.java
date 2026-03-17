package syrenyx.distantmoons.references.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsStructureTags {

  public static final TagKey<Structure> UNDERWORLD_COMPASS_TARGET = keyOf("underworld_compasss_target");

  private static TagKey<Structure> keyOf(String id) {
    return UnderDistantMoons.tagKeyOf(id, Registries.STRUCTURE);
  }
}
