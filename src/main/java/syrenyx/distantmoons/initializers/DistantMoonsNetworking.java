package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import syrenyx.distantmoons.data.networking.ActiveAfflictionsPayload;

public abstract class DistantMoonsNetworking {

  static {
    PayloadTypeRegistry.clientboundPlay().register(ActiveAfflictionsPayload.ID, ActiveAfflictionsPayload.CODEC);
  }

  public static void initialize() {}
}
