package syrenyx.distantmoons;

import net.fabricmc.api.ClientModInitializer;

public class UnderDistantMoonsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
    UnderDistantMoons.LOGGER.info("Initializing Modded Client Content");
    UnderDistantMoons.LOGGER.info("Client Content Successfully Initialized");
	}
}