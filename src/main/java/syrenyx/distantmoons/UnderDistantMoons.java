package syrenyx.distantmoons;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
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
    DistantMoonsAdvancementCriteria.initialize();
    DistantMoonsAfflictionEffectComponents.initialize();
    DistantMoonsAfflictionEntityEffects.initialize();
    DistantMoonsAfflictionLocationBasedEffects.initialize();
    DistantMoonsAfflictionValueEffects.initialize();
    DistantMoonsAttachedData.initialize();
    DistantMoonsBiomeModifications.initialize();
    DistantMoonsBlockModifications.initialize();
    DistantMoonsBlocks.initialize();
    DistantMoonsCommands.initialize();
    DistantMoonsDataComponentTypes.initialize();
    DistantMoonsEnchantmentEffectComponents.initialize();
    DistantMoonsEnchantmentEntityEffects.initialize();
    DistantMoonsEnchantmentLevelBasedValueTypes.initialize();
    DistantMoonsFeatures.initialize();
    DistantMoonsItemGroups.initialize();
    DistantMoonsItemModifications.initialize();
    DistantMoonsItems.initialize();
    DistantMoonsLootConditions.initialize();
    DistantMoonsLootContextTypes.initialize();
    DistantMoonsLootPoolEntryTypes.initialize();
    DistantMoonsLootNumberProviders.initialize();
    DistantMoonsNetworking.initialize();
    DistantMoonsParticleTypes.initialize();
    DistantMoonsRegistries.initialize();
    DistantMoonsSoundEvents.initialize();
    LOGGER.info("Content Successfully Initialized");
	}

  public static String withPrefixedNamespace(String string) {
    return MOD_ID + ":" + string;
  }

  public static Identifier identifierOf(String string) {
    return Identifier.of(MOD_ID, string);
  }

  public static <T> RegistryKey<T> registryKeyOf(String string, RegistryKey<Registry<T>> key) {
    return RegistryKey.of(key, identifierOf(string));
  }

  public static <T> TagKey<T> tagKeyOf(String string, RegistryKey<Registry<T>> key) {
    return TagKey.of(key, identifierOf(string));
  }
}