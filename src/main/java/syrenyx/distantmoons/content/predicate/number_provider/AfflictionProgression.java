package syrenyx.distantmoons.content.predicate.number_provider;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

public record AfflictionProgression() implements NumberProvider {

  public static final MapCodec<AfflictionProgression> CODEC = MapCodec.unit(AfflictionProgression::new);

  @Override
  public float getFloat(LootContext context) {
    return context.getParameter(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION);
  }

  @Override
  public @NonNull MapCodec<? extends NumberProvider> codec() {
    return CODEC;
  }
}
