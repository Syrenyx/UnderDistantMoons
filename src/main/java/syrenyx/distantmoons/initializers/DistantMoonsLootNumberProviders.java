package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.predicate.number_provider.AfflictionProgression;
import syrenyx.distantmoons.content.predicate.number_provider.AfflictionStage;

public abstract class DistantMoonsLootNumberProviders {

  public static final LootNumberProviderType AFFLICTION_PROGRESSION = register("affliction_progression", AfflictionProgression.CODEC);
  public static final LootNumberProviderType AFFLICTION_STAGE = register("affliction_stage", AfflictionStage.CODEC);

  private static LootNumberProviderType register(String id, MapCodec<? extends NumberProvider> codec) {
    return Registry.register(BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE, UnderDistantMoons.identifierOf(id), new LootNumberProviderType(codec));
  }

  public static void initialize() {}
}
