package syrenyx.distantmoons;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnderDistantMoons implements ModInitializer {

  public static final String MOD_ID = "distant-moons";
  public static final String MOD_NAME = "UnderDistantMoons";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Modded Content");
    LOGGER.info("Content Successfully Initialized");
	}
}