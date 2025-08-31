package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import syrenyx.distantmoons.command.AfflictionCommand;

public class Commands {

  public static void initialize() {
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
      AfflictionCommand.register(dispatcher, registryAccess);
    });
  }
}
