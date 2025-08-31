package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentMap;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;
import syrenyx.distantmoons.initializers.component.AfflictionEffectComponentTypes;
import syrenyx.distantmoons.references.RegistryKeys;

import java.util.Optional;

public record Affliction(
    Optional<AfflictionDisplay> display,
    int stages,
    int limit,
    boolean persistent,
    ComponentMap effects
) {

  public static final int MAX_STAGE = 255;
  public static final int MAX_PROGRESSION_LIMIT = 255;

  public static final Codec<Affliction> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          AfflictionDisplay.CODEC.optionalFieldOf("display").forGetter(Affliction::display),
          Codecs.rangedInt(1, MAX_STAGE).fieldOf("stages").forGetter(Affliction::stages),
          Codecs.rangedInt(1, MAX_PROGRESSION_LIMIT).fieldOf("progression_limit").forGetter(Affliction::limit),
          Codec.BOOL.optionalFieldOf("persistent", false).forGetter(Affliction::persistent),
          AfflictionEffectComponentTypes.COMPONENT_MAP_CODEC.optionalFieldOf("effects", ComponentMap.EMPTY).forGetter(Affliction::effects)
      )
      .apply(instance, Affliction::new)
  );
  public static final Codec<RegistryEntry<Affliction>> ENTRY_CODEC = RegistryElementCodec.of(RegistryKeys.AFFLICTION, CODEC);
}
