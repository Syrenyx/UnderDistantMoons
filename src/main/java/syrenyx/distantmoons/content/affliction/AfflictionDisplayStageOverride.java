package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;

public record AfflictionDisplayStageOverride(
    Optional<Component> description,
    Optional<Identifier> icon,
    Optional<Identifier> tooltipStyle,
    Optional<ProgressionBarStyle> progressionBarStyle,
    Optional<Boolean> hidden
) {

  public static final Codec<AfflictionDisplayStageOverride> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          ComponentSerialization.CODEC.optionalFieldOf("description").forGetter(AfflictionDisplayStageOverride::description),
          Identifier.CODEC.optionalFieldOf("icon").forGetter(AfflictionDisplayStageOverride::icon),
          Identifier.CODEC.optionalFieldOf("tooltip_style").forGetter(AfflictionDisplayStageOverride::tooltipStyle),
          ProgressionBarStyle.CODEC.optionalFieldOf("progression_bar").forGetter(AfflictionDisplayStageOverride::progressionBarStyle),
          Codec.BOOL.optionalFieldOf("hidden").forGetter(AfflictionDisplayStageOverride::hidden)
      )
      .apply(instance, AfflictionDisplayStageOverride::new)
  );
}
