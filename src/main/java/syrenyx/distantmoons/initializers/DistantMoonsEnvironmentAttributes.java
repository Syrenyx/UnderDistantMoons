package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsEnvironmentAttributes {

  public static EnvironmentAttribute<Boolean> UNDERWORLD = register("gameplay/underworld", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));

  private static <T> EnvironmentAttribute<T> register(String id, EnvironmentAttribute.Builder<T> builder) {
    return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, UnderDistantMoons.identifierOf(id), builder.build());
  }

  public static void initialize() {}
}
