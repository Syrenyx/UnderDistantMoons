package syrenyx.distantmoons.data.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record HeartComponent(boolean placeholder) {

  public static final Codec<HeartComponent> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.BOOL.optionalFieldOf("placeholder", true).forGetter(HeartComponent::placeholder)
      )
      .apply(instance, HeartComponent::new)
  );

  public HeartComponent() {
    this(true);
  }
}
