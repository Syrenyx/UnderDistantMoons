package syrenyx.distantmoons.references;

import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsLootContextParameters {

  public static final ContextKey<Float> AFFLICTION_PROGRESSION = contextParameterOf("affliction_progression");
  public static final ContextKey<Integer> AFFLICTION_STAGE = contextParameterOf("affliction_stage");
  public static final ContextKey<Entity> SPAWNED_ENTITY = contextParameterOf("spawned_entity");

  private static <T> ContextKey<T> contextParameterOf(String id) {
    return new ContextKey<>(UnderDistantMoons.identifierOf(id));
  }
}
