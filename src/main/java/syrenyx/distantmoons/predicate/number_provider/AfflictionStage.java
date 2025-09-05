package syrenyx.distantmoons.predicate.number_provider;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderType;
import syrenyx.distantmoons.initializers.LootNumberProviders;
import syrenyx.distantmoons.references.LootContextParameters;

public record AfflictionStage(
    EnchantmentLevelBasedValue stageValue
) implements LootNumberProvider {

  public static final MapCodec<AfflictionStage> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(AfflictionStage::stageValue)
      )
      .apply(instance, AfflictionStage::new)
  );

  @Override
  public LootNumberProviderType getType() {
    return LootNumberProviders.AFFLICTION_STAGE;
  }

  @Override
  public float nextFloat(LootContext context) {
    return stageValue.getValue(context.getOrThrow(LootContextParameters.AFFLICTION_STAGE));
  }
}
