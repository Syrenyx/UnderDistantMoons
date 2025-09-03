package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.Optional;

public record AfflictionDisplay(Identifier icon, Optional<Identifier> tooltipStyle) {

  public static final Codec<AfflictionDisplay> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("icon").forGetter(AfflictionDisplay::icon),
          Identifier.CODEC.optionalFieldOf("tooltip_style").forGetter(AfflictionDisplay::tooltipStyle)
      )
      .apply(instance, AfflictionDisplay::new)
  );
}
