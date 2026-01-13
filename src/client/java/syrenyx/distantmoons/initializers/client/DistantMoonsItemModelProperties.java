package syrenyx.distantmoons.initializers.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.rendering.item.properties.conditional.UnderworldDimension;

public abstract class DistantMoonsItemModelProperties {

  static {
    register("underworld_dimension", UnderworldDimension.MAP_CODEC);
  }

  private static <T extends ConditionalItemModelProperty> void register(String id, MapCodec<T> codec) {
    ConditionalItemModelProperties.ID_MAPPER.put(UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
