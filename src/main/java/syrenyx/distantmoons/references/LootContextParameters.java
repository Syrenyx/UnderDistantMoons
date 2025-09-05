package syrenyx.distantmoons.references;

import net.minecraft.util.context.ContextParameter;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class LootContextParameters {

  public static final ContextParameter<Float> AFFLICTION_PROGRESSION = contextParameterOf("affliction_progression");
  public static final ContextParameter<Integer> AFFLICTION_STAGE = contextParameterOf("affliction_stage");

  private static <T> ContextParameter<T> contextParameterOf(String id) {
    return new ContextParameter<>(UnderDistantMoons.identifierOf(id));
  }
}
