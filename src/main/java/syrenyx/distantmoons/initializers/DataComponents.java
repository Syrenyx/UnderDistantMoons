package syrenyx.distantmoons.initializers;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.data.component.HeartComponent;

public abstract class DataComponents {

  public static final ComponentType<HeartComponent> HEART_COMPONENT = register("heart", HeartComponent.CODEC);

  private static <T> ComponentType<T> register(String id, Codec<T> codec) {
    return Registry.register(Registries.DATA_COMPONENT_TYPE, UnderDistantMoons.identifierOf(id), new ComponentType.Builder<T>().codec(codec).build());
  }

  public static void initialize() {}
}
