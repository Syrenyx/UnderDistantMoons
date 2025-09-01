package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.dynamic.Codecs;
import syrenyx.distantmoons.references.RegistryKeys;

public record Affliction(
    int stages,
    int limit,
    boolean persistent
) {

  public static final int MAX_STAGE = 255;
  public static final int MAX_PROGRESSION_LIMIT = 255;

  public static final Codec<Affliction> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codecs.rangedInt(1, MAX_STAGE).fieldOf("stages").forGetter(Affliction::stages),
          Codecs.rangedInt(1, MAX_PROGRESSION_LIMIT).fieldOf("progression_limit").forGetter(Affliction::limit),
          Codec.BOOL.optionalFieldOf("persistent", false).forGetter(Affliction::persistent)
      )
      .apply(instance, Affliction::new)
  );
  public static final Codec<RegistryEntry<Affliction>> DYNAMIC_ENTRY_CODEC = RegistryElementCodec.of(RegistryKeys.AFFLICTION, CODEC);
  public static final Codec<RegistryEntry<Affliction>> FIXED_ENTRY_CODEC = RegistryFixedCodec.of(RegistryKeys.AFFLICTION);
}
