package syrenyx.distantmoons.initializers;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.data_component.CoiledBlockComponent;

public abstract class DistantMoonsDataComponentTypes {

  public static final ComponentType<CoiledBlockComponent> COILED_BLOCK = register("coiled_block", CoiledBlockComponent.CODEC);

  private static <T> ComponentType<T> register(String id, Codec<T> codec) {
    return Registry.register(Registries.DATA_COMPONENT_TYPE, UnderDistantMoons.identifierOf(id), ComponentType.<T>builder().codec(codec).build());
  }

  public static void initialize() {}
}
