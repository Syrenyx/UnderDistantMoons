package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public record AfflictionDisplayStageOverride(
    Optional<Text> description,
    Optional<Identifier> icon,
    Optional<Identifier> tooltipStyle,
    Optional<ProgressionBarStyle> progressionBarStyle,
    Optional<Boolean> hidden
) {

  public static final Codec<AfflictionDisplayStageOverride> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          TextCodecs.CODEC.optionalFieldOf("description").forGetter(AfflictionDisplayStageOverride::description),
          Identifier.CODEC.optionalFieldOf("icon").forGetter(AfflictionDisplayStageOverride::icon),
          Identifier.CODEC.optionalFieldOf("tooltip_style").forGetter(AfflictionDisplayStageOverride::tooltipStyle),
          ProgressionBarStyle.CODEC.optionalFieldOf("progression_bar").forGetter(AfflictionDisplayStageOverride::progressionBarStyle),
          Codec.BOOL.optionalFieldOf("hidden").forGetter(AfflictionDisplayStageOverride::hidden)
      )
      .apply(instance, AfflictionDisplayStageOverride::new)
  );
}
