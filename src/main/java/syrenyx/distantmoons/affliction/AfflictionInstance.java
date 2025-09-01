package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;

public class AfflictionInstance {

  public static final Codec<AfflictionInstance> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Affliction.FIXED_ENTRY_CODEC.fieldOf("id").forGetter(AfflictionInstance::getAfflictionType),
          Codec.INT.fieldOf("stage").forGetter(AfflictionInstance::getStage),
          Codec.FLOAT.fieldOf("progression").forGetter(AfflictionInstance::getProgression)
      )
      .apply(instance, AfflictionInstance::new)
  );

  private final RegistryEntry<Affliction> type;
  private int stage;
  private float progression;

  public AfflictionInstance(RegistryEntry<Affliction> type, int stage, float progression) {
    this.type = type;
    this.stage = stage;
    this.progression = progression;
  }

  public RegistryEntry<Affliction> getAfflictionType() {
    return this.type;
  }

  public int getStage() {
    return this.stage;
  }

  public void addToStage(int value) {
    this.stage += value;
  }

  public float getProgression() {
    return this.progression;
  }

  public void addToProgression(float value) {
    this.progression += value;
  }
}
