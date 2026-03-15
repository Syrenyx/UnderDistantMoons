package syrenyx.distantmoons.content.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BlastFurnaceFuelComponent(
    int burnTime,
    int heat,
    boolean soulFuel
) {

  public static final int DEFAULT_BURN_TIME = 400;
  public static final int COKE_HEAT_VALUE = 800;
  public static final int COAL_HEAT_VALUE = 600;

  public BlastFurnaceFuelComponent(int heat) {
    this(DEFAULT_BURN_TIME, heat, false);
  }

  public BlastFurnaceFuelComponent(int burnTime, int heat) {
    this(burnTime, heat, false);
  }

  public BlastFurnaceFuelComponent(int heat, boolean soulFuel) {
    this(DEFAULT_BURN_TIME, heat, soulFuel);
  }

  public static final Codec<BlastFurnaceFuelComponent> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.INT.fieldOf("burn_time").forGetter(BlastFurnaceFuelComponent::burnTime),
          Codec.INT.fieldOf("heat").forGetter(BlastFurnaceFuelComponent::heat),
          Codec.BOOL.fieldOf("soul_fuel").forGetter(BlastFurnaceFuelComponent::soulFuel)
      )
      .apply(instance, BlastFurnaceFuelComponent::new)
  );
}
