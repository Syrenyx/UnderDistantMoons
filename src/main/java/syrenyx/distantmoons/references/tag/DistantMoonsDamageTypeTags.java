package syrenyx.distantmoons.references.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.biome.Biome;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsDamageTypeTags {

  public static final TagKey<DamageType> IS_EXPLOSION_OR_FIRE = keyOf("is_explosion_or_fire");

  private static TagKey<DamageType> keyOf(String id) {
    return UnderDistantMoons.tagKeyOf(id, Registries.DAMAGE_TYPE);
  }
}
