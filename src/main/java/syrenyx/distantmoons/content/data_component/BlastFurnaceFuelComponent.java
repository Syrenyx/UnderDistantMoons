package syrenyx.distantmoons.content.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BlastFurnaceFuelComponent(
    int burnTime,
    int heat
) {

  public static final int DEFAULT_BURN_TIME = 400;
  public static final int COKE_HEAT_VALUE = 8;
  public static final int COAL_HEAT_VALUE = 6;

  public BlastFurnaceFuelComponent(int heat) {
    this(DEFAULT_BURN_TIME, heat);
  }

  public static final Codec<BlastFurnaceFuelComponent> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.INT.fieldOf("burn_time").forGetter(BlastFurnaceFuelComponent::burnTime),
          Codec.INT.fieldOf("heat").forGetter(BlastFurnaceFuelComponent::heat)
      )
      .apply(instance, BlastFurnaceFuelComponent::new)
  );
}
