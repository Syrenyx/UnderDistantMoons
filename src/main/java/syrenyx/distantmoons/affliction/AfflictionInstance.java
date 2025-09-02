package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.dynamic.Codecs;

public class AfflictionInstance {

  public static final Codec<AfflictionInstance> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Affliction.FIXED_ENTRY_CODEC.fieldOf("id").forGetter(AfflictionInstance::affliction),
          Codecs.rangedInt(1, Affliction.MAX_STAGE).fieldOf("stage").forGetter(AfflictionInstance::stage)
      )
      .apply(instance, AfflictionInstance::new)
  );
  private final RegistryEntry<Affliction> affliction;
  private int stage;

  public AfflictionInstance(RegistryEntry<Affliction> affliction, int stage) {
    this.affliction = affliction;
    this.stage = stage;
  }

  public RegistryEntry<Affliction> affliction() {
    return this.affliction;
  }

  public int stage() {
    return this.stage;
  }

  public void setStage(int stage) {
    this.stage = stage;
  }

  public void limitToAllowedValues() {
    this.stage = Math.min(this.stage, this.affliction.value().maxStage());
  }
}
