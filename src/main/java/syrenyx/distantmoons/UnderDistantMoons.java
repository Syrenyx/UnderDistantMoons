package syrenyx.distantmoons;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import syrenyx.distantmoons.initializers.*;

public class UnderDistantMoons implements ModInitializer {

  public static final String MOD_ID = "distant-moons";
  public static final String MOD_NAME = "UnderDistantMoons";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
    LOGGER.info("Initializing Modded Content");
    DistantMoonsAfflictionEffectComponents.initialize();
    DistantMoonsAfflictionEntityEffects.initialize();
    DistantMoonsAfflictionLocationBasedEffects.initialize();
    DistantMoonsAfflictionValueEffects.initialize();
    DistantMoonsAttachedData.initialize();
    DistantMoonsBlocks.initialize();
    DistantMoonsCommands.initialize();
    DistantMoonsEnchantmentEffectComponents.initialize();
    DistantMoonsEnchantmentEntityEffects.initialize();
    DistantMoonsEnchantmentLevelBasedValueTypes.initialize();
    DistantMoonsItemGroups.initialize();
    DistantMoonsItems.initialize();
    DistantMoonsLootConditions.initialize();
    DistantMoonsLootContextTypes.initialize();
    DistantMoonsLootNumberProviders.initialize();
    DistantMoonsNetworking.initialize();
    Registries.initialize();
    LOGGER.info("Content Successfully Initialized");
	}

  public static Identifier identifierOf(String string) {
    return Identifier.of(MOD_ID, string);
  }
}