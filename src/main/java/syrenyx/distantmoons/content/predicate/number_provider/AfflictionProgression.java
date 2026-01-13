package syrenyx.distantmoons.content.predicate.number_provider;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsLootNumberProviders;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

public record AfflictionProgression() implements NumberProvider {

  public static final MapCodec<AfflictionProgression> CODEC = MapCodec.unit(AfflictionProgression::new);

  @Override
  public @NonNull LootNumberProviderType getType() {
    return DistantMoonsLootNumberProviders.AFFLICTION_PROGRESSION;
  }

  @Override
  public float getFloat(LootContext context) {
    return context.getParameter(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION);
  }
}
