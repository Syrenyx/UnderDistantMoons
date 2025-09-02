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
  private float progression;
  private int stage;

  public AfflictionInstance(RegistryEntry<Affliction> affliction, float progression, int stage) {
    this.affliction = affliction;
    this.progression = progression;
    this.stage = stage;
  }

  public AfflictionInstance(RegistryEntry<Affliction> affliction, int stage) {
    this.affliction = affliction;
    this.progression = 0.0F;
    this.stage = stage;
  }

  public AfflictionInstance(RegistryEntry<Affliction> affliction) {
    this.affliction = affliction;
    this.progression = 0.0F;
    this.stage = Affliction.DEFAULT_STAGE;
  }

  public RegistryEntry<Affliction> affliction() {
    return this.affliction;
  }

  public float progression() {
    return this.progression;
  }

  public int stage() {
    return this.stage;
  }

  public void setStage(int stage) {
    this.stage = stage;
  }

  public void addToProgression(float value) {
    this.progression += value;
  }

  public void limitToAllowedValues() {
    this.progression = Math.max(0.0F, Math.min(this.progression, Affliction.MAX_PROGRESSION));
    this.stage = Math.max(1, Math.min(this.stage, this.affliction.value().maxStage()));
  }
}
