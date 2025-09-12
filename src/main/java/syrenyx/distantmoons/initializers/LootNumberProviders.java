package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.predicate.number_provider.AfflictionProgression;
import syrenyx.distantmoons.predicate.number_provider.AfflictionStage;

public abstract class LootNumberProviders {

  public static final LootNumberProviderType AFFLICTION_PROGRESSION = register("affliction_progression", AfflictionProgression.CODEC);
  public static final LootNumberProviderType AFFLICTION_STAGE = register("affliction_stage", AfflictionStage.CODEC);

  private static LootNumberProviderType register(String id, MapCodec<? extends LootNumberProvider> codec) {
    return Registry.register(Registries.LOOT_NUMBER_PROVIDER_TYPE, UnderDistantMoons.identifierOf(id), new LootNumberProviderType(codec));
  }

  public static void initialize() {}
}
