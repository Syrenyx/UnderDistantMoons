package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import syrenyx.distantmoons.references.RegistryKeys;

public record Affliction(boolean persistent) {

  public static final Codec<Affliction> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.BOOL.optionalFieldOf("persistent", false).forGetter(Affliction::persistent)
      )
      .apply(instance, Affliction::new)
  );
  public static final Codec<RegistryEntry<Affliction>> FIXED_ENTRY_CODEC = RegistryFixedCodec.of(RegistryKeys.AFFLICTION_REGISTRY_KEY);
}
