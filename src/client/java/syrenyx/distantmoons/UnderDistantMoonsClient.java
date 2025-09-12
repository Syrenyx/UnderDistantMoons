package syrenyx.distantmoons;

import net.fabricmc.api.ClientModInitializer;
import syrenyx.distantmoons.initializers.client.Networking;

public class UnderDistantMoonsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
    UnderDistantMoons.LOGGER.info("Initializing Modded Content");
    Networking.initialize();
    UnderDistantMoons.LOGGER.info("Content Successfully Initialized");
	}
}