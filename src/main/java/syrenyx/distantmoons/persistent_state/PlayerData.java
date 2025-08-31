package syrenyx.distantmoons.persistent_state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionInstance;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {

  public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.unboundedMap(Affliction.ENTRY_CODEC, AfflictionInstance.CODEC).fieldOf("activeAfflictions").forGetter(PlayerData::getActiveAfflictions)
      )
      .apply(instance, PlayerData::new)
  );
  public Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions = new HashMap<>();

  PlayerData() {}

  PlayerData(Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions) {
    this.activeAfflictions = activeAfflictions;
  }

  public Map<RegistryEntry<Affliction>, AfflictionInstance> getActiveAfflictions() {
    return this.activeAfflictions;
  }
}
