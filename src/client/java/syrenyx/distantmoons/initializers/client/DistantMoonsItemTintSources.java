package syrenyx.distantmoons.initializers.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.rendering.item.tint_source.DimensionKeystoneTintSource;

public abstract class DistantMoonsItemTintSources {

  static {
    register("dimension_keystone", DimensionKeystoneTintSource.MAP_CODEC);
  }

  private static <T extends ItemTintSource> void register(String id, MapCodec<T> codec) {
    ItemTintSources.ID_MAPPER.put(UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
