package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

public record AfflictionDisplay(Identifier icon, int color) {

  public static final Codec<AfflictionDisplay> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("icon").forGetter(AfflictionDisplay::icon),
          Codecs.RGB.fieldOf("color").forGetter(AfflictionDisplay::color)
      )
      .apply(instance, AfflictionDisplay::new)
  );
}
