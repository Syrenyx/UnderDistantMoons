package syrenyx.distantmoons.content.predicate.number_provider;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsLootNumberProviders;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

public record AfflictionStage(
    LevelBasedValue stageValue
) implements NumberProvider {

  public static final MapCodec<AfflictionStage> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LevelBasedValue.CODEC.fieldOf("amount").forGetter(AfflictionStage::stageValue)
      )
      .apply(instance, AfflictionStage::new)
  );

  @Override
  public @NonNull LootNumberProviderType getType() {
    return DistantMoonsLootNumberProviders.AFFLICTION_STAGE;
  }

  @Override
  public float getFloat(LootContext context) {
    return stageValue.calculate(context.getParameter(DistantMoonsLootContextParameters.AFFLICTION_STAGE));
  }
}
