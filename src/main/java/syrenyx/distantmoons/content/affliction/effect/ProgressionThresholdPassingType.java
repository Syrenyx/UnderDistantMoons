package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum ProgressionThresholdPassingType implements StringRepresentable {
  ANY("any"),
  DECREASING("decreasing"),
  INCREASING("increasing");

  public static final Codec<ProgressionThresholdPassingType> CODEC = StringRepresentable.fromEnum(ProgressionThresholdPassingType::values);
  private final String id;

  ProgressionThresholdPassingType(final String id) {
    this.id = id;
  }

  @Override
  public String getSerializedName() {
    return this.id;
  }
}
