package syrenyx.distantmoons.content.predicate.number_provider;

import com.mojang.serialization.MapCodec;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderType;
import syrenyx.distantmoons.initializers.DistantMoonsLootNumberProviders;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

public record AfflictionProgression() implements LootNumberProvider {

  public static final MapCodec<AfflictionProgression> CODEC = MapCodec.unit(AfflictionProgression::new);

  @Override
  public LootNumberProviderType getType() {
    return DistantMoonsLootNumberProviders.AFFLICTION_PROGRESSION;
  }

  @Override
  public float nextFloat(LootContext context) {
    return context.getOrThrow(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION);
  }
}
