package syrenyx.distantmoons.references.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsEntityTypeTags {

  public static final TagKey<EntityType<?>> IGNORED_BY_UNDERWORLD_CONFLUX = keyOf("ignored_by_underworld_conflux");

  private static TagKey<EntityType<?>> keyOf(String id) {
    return UnderDistantMoons.tagKeyOf(id, Registries.ENTITY_TYPE);
  }
}
