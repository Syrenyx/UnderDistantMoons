package syrenyx.distantmoons.content.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BlastFurnaceFuelComponent(
    int heat
) {

  public static final Codec<BlastFurnaceFuelComponent> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.INT.fieldOf("heat").forGetter(BlastFurnaceFuelComponent::heat)
      )
      .apply(instance, BlastFurnaceFuelComponent::new)
  );
}
