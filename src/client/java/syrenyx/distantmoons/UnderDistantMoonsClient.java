package syrenyx.distantmoons;

import net.fabricmc.api.ClientModInitializer;
import syrenyx.distantmoons.initializers.client.*;

public class UnderDistantMoonsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
    UnderDistantMoons.LOGGER.info("Initializing Modded Content");
    DistantMoonsAttachedData.initialize();
    DistantMoonsNetworking.initialize();
    DistantMoonsParticles.initialize();
    DistantMoonsRendering.initialize();
    DistantMoonsItemModelProperties.initialize();
    UnderDistantMoons.LOGGER.info("Content Successfully Initialized");
	}
}