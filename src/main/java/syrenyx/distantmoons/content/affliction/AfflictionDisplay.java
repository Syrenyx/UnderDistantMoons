package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.Identifier;

public record AfflictionDisplay(
    Identifier icon,
    Optional<Identifier> tooltipStyle,
    Optional<Map<String, AfflictionDisplayStageOverride>> stageOverrides
) {

  public static final Codec<AfflictionDisplay> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("icon").forGetter(AfflictionDisplay::icon),
          Identifier.CODEC.optionalFieldOf("tooltip_style").forGetter(AfflictionDisplay::tooltipStyle),
          Codec.unboundedMap(Codec.STRING, AfflictionDisplayStageOverride.CODEC).optionalFieldOf("stage_overrides").forGetter(AfflictionDisplay::stageOverrides)
      )
      .apply(instance, AfflictionDisplay::new)
  );
}
