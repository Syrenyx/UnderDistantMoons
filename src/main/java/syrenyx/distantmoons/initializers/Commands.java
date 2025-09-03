package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import syrenyx.distantmoons.command.AfflictionCommand;

public abstract class Commands {

  static {
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
      AfflictionCommand.register(dispatcher, registryAccess);
    });
  }

  public static void initialize() {}
}
