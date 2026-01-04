package syrenyx.distantmoons.references.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsBiomeTags {

  public static final TagKey<Biome> HAS_DEEP_IRON_BUBBLE_FEATURE = keyOf("has_feature/deep_iron_bubble");
  public static final TagKey<Biome> HAS_DEEP_IRON_ORE_FEATURE = keyOf("has_feature/deep_iron_ore");

  private static TagKey<Biome> keyOf(String id) {
    return UnderDistantMoons.tagKeyOf(id, Registries.BIOME);
  }
}
