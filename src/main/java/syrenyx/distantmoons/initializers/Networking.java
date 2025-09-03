package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import syrenyx.distantmoons.payload.ActiveAfflictionsPayload;

public abstract class Networking {

  static {
    PayloadTypeRegistry.playS2C().register(ActiveAfflictionsPayload.ID, ActiveAfflictionsPayload.CODEC);
  }

  public static void initialize() {}
}
