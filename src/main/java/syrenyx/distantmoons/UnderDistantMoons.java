package syrenyx.distantmoons;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import syrenyx.distantmoons.initializers.*;
import syrenyx.distantmoons.initializers.AfflictionEffectComponents;

public class UnderDistantMoons implements ModInitializer {

  public static final String MOD_ID = "distant-moons";
  public static final String MOD_NAME = "UnderDistantMoons";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
    LOGGER.info("Initializing Modded Content");
    AfflictionEffectComponents.initialize();
    AfflictionEntityEffects.initialize();
    AfflictionLocationBasedEffects.initialize();
    AfflictionValueEffects.initialize();
    AttachedData.initialize();
    Commands.initialize();
    EnchantmentEffectComponents.initialize();
    EnchantmentEntityEffects.initialize();
    EnchantmentLevelBasedValueTypes.initialize();
    LootConditions.initialize();
    LootContextTypes.initialize();
    LootNumberProviders.initialize();
    Networking.initialize();
    Registries.initialize();
    LOGGER.info("Content Successfully Initialized");
	}

  public static Identifier identifierOf(String string) {
    return Identifier.of(MOD_ID, string);
  }
}