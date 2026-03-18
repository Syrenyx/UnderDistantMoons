package syrenyx.distantmoons.content.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public record DimensionKeystoneComponent(Identifier dimension, int color) {

  public static final int DEFAULT_COLOR = 16761661;

  public static final Codec<DimensionKeystoneComponent> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("dimension").forGetter(DimensionKeystoneComponent::dimension),
          ExtraCodecs.RGB_COLOR_CODEC.optionalFieldOf("color", DEFAULT_COLOR).forGetter(DimensionKeystoneComponent::color)
      )
      .apply(instance, DimensionKeystoneComponent::new)
  );
}
