package syrenyx.distantmoons.affliction;

import com.google.common.collect.ComparisonChain;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.affliction.effect.location_based.AfflictionLocationBasedEffect;

import java.util.NoSuchElementException;
import java.util.Set;

public class AfflictionInstance implements Comparable<AfflictionInstance> {

  public static final Codec<AfflictionInstance> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Affliction.FIXED_ENTRY_CODEC.fieldOf("id").forGetter(AfflictionInstance::affliction),
          Codecs.rangedInt(1, Affliction.MAX_STAGE).fieldOf("stage").forGetter(AfflictionInstance::stage),
          Codecs.rangedInclusiveFloat(0, Affliction.MAX_PROGRESSION).optionalFieldOf("progression", 0.0F).forGetter(AfflictionInstance::progression)
      )
      .apply(instance, AfflictionInstance::new)
  );
  private final RegistryEntry<Affliction> affliction;
  private float progression;
  private int stage;
  public Set<AfflictionLocationBasedEffect> activeLocationBasedEffects;

  public AfflictionInstance(RegistryEntry<Affliction> affliction, int stage, float progression) {
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

  public void setStage(int value) {
    this.stage = value;
  }

  public void addToStage(int value) {
    this.stage += value;
  }

  public void setProgression(float value) {
    this.progression = value;
  }

  public void addToProgression(float value) {
    this.progression += value;
  }

  public void limitToAllowedValues() {
    this.progression = Math.max(0.0F, Math.min(this.progression, Affliction.MAX_PROGRESSION));
    this.stage = Math.max(1, Math.min(this.stage, this.affliction.value().maxStage()));
  }

  public Text getDescription() {
    Affliction affliction = this.affliction().value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).description().orElseThrow();
    } catch (NoSuchElementException e) {
      return affliction.description().copy().append(ScreenTexts.SPACE).append(Text.translatable("enchantment.level." + (this.stage())));
    }
  }

  public @Nullable Identifier getIcon() {
    Affliction affliction = this.affliction().value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).icon().orElseThrow();
    } catch (NoSuchElementException e) {
      if (affliction.display().isPresent()) return affliction.display().get().icon();
      return null;
    }
  }

  public ProgressionBarStyle getProgressionBarStyle() {
    Affliction affliction = this.affliction().value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).progressionBarStyle().orElseThrow();
    } catch (NoSuchElementException e) {
      if (affliction.display().isPresent()) return affliction.tickProgression().isPresent() ? ProgressionBarStyle.DEFAULT : ProgressionBarStyle.INFINITE;
      return null;
    }
  }

  public @Nullable Identifier getTooltipStyle() {
    Affliction affliction = this.affliction().value();
    try {
      return affliction.display().orElseThrow().stageOverrides().orElseThrow().get(String.valueOf(this.stage())).tooltipStyle().orElseThrow();
    } catch (NoSuchElementException e) {
      if (affliction.display().isPresent()) return affliction.display().get().tooltipStyle().orElse(null);
      return null;
    }
  }

  @Override
  public int compareTo(@NotNull AfflictionInstance other) {
    Affliction thisAffliction = this.affliction.value();
    Affliction otherAffliction = other.affliction.value();
    return ComparisonChain
        .start()
        .compareTrueFirst(thisAffliction.persistent(), otherAffliction.persistent())
        .compare(this.stage, other.stage)
        .compare(thisAffliction.maxStage(), otherAffliction.maxStage())
        .result();
  }
}
