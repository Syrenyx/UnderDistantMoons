package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import syrenyx.distantmoons.references.RegistryKeys;

import java.util.Optional;

public record Affliction(Text description, boolean persistent, Optional<RegistryEntryList<EntityType<?>>> immuneEntities) {

  public static final Codec<Affliction> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          TextCodecs.CODEC.fieldOf("description").forGetter(Affliction::description),
          Codec.BOOL.optionalFieldOf("persistent", false).forGetter(Affliction::persistent),
          RegistryCodecs.entryList(net.minecraft.registry.RegistryKeys.ENTITY_TYPE).optionalFieldOf("immune_entities").forGetter(Affliction::immuneEntities)
      )
      .apply(instance, Affliction::new)
  );
  public static final Codec<RegistryEntry<Affliction>> FIXED_ENTRY_CODEC = RegistryFixedCodec.of(RegistryKeys.AFFLICTION_REGISTRY_KEY);
  public static final int MAX_STAGE = 255;
  public static final int DEFAULT_STAGE = 1;
}
