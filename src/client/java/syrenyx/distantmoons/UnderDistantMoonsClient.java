package syrenyx.distantmoons;

import net.fabricmc.api.ClientModInitializer;
import syrenyx.distantmoons.initializers.client.DistantMoonsAttachedData;
import syrenyx.distantmoons.initializers.client.DistantMoonsNetworking;
import syrenyx.distantmoons.initializers.client.DistantMoonsParticles;
import syrenyx.distantmoons.initializers.client.DistantMoonsRendering;

public class UnderDistantMoonsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
    UnderDistantMoons.LOGGER.info("Initializing Modded Content");
    DistantMoonsAttachedData.initialize();
    DistantMoonsNetworking.initialize();
    DistantMoonsParticles.initialize();
    DistantMoonsRendering.initialize();
    UnderDistantMoons.LOGGER.info("Content Successfully Initialized");
	}
}