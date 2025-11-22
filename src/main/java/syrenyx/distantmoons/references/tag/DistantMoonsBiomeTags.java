package syrenyx.distantmoons.references.tag;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsBiomeTags {

  public static final TagKey<Biome> HAS_DEEP_IRON_BUBBLE_FEATURE = keyOf("has_feature/deep_iron_bubble");
  public static final TagKey<Biome> HAS_DEEP_IRON_ORE_FEATURE = keyOf("has_feature/deep_iron_ore");

  private static TagKey<Biome> keyOf(String id) {
    return UnderDistantMoons.tagKeyOf(id, RegistryKeys.BIOME);
  }
}
