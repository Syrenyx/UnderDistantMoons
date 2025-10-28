package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import syrenyx.distantmoons.data.networking.ActiveAfflictionsPayload;

public abstract class DistantMoonsNetworking {

  static {
    PayloadTypeRegistry.playS2C().register(ActiveAfflictionsPayload.ID, ActiveAfflictionsPayload.CODEC);
  }

  public static void initialize() {}
}
