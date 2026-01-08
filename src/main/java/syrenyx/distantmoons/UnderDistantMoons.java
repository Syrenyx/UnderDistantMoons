package syrenyx.distantmoons;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
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
    DistantMoonsEntityTypes.initialize();
    DistantMoonsEnvironmentAttributes.initialize();
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
    return Identifier.fromNamespaceAndPath(MOD_ID, string);
  }

  public static <T> ResourceKey<T> registryKeyOf(String string, ResourceKey<Registry<T>> key) {
    return ResourceKey.create(key, identifierOf(string));
  }

  public static <T> TagKey<T> tagKeyOf(String string, ResourceKey<Registry<T>> key) {
    return TagKey.create(key, identifierOf(string));
  }
}