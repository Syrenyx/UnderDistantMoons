package syrenyx.distantmoons;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import syrenyx.distantmoons.initializers.*;
import syrenyx.distantmoons.initializers.component.AfflictionEffectComponentTypes;

public class UnderDistantMoons implements ModInitializer {

  public static final String MOD_ID = "distant-moons";
  public static final String MOD_NAME = "UnderDistantMoons";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Modded Content");
    AfflictionEffectComponentTypes.initialize();
    AfflictionEntityEffects.initialize();
    AfflictionLocationBasedEffects.initialize();
    AttachmentTypes.initialize();
    Commands.initialize();
    EnchantmentEntityEffects.initialize();
    LootConditionTypes.initialize();
    Registries.initialize();
    LOGGER.info("Content Successfully Initialized");
	}

  public static Identifier identifierOf(String id) {
    return Identifier.of(MOD_ID, id);
  }
}