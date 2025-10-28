package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum ProgressionThresholdPassingType implements StringIdentifiable {
  ANY("any"),
  DECREASING("decreasing"),
  INCREASING("increasing");

  public static final Codec<ProgressionThresholdPassingType> CODEC = StringIdentifiable.createCodec(ProgressionThresholdPassingType::values);
  private final String id;

  ProgressionThresholdPassingType(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
