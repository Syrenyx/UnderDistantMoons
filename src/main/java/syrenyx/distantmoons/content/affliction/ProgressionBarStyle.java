package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum ProgressionBarStyle implements StringRepresentable {
  DEFAULT("default"),
  EMPTY("empty"),
  FULL("full"),
  INFINITE("infinite");

  public static final Codec<ProgressionBarStyle> CODEC = StringRepresentable.fromEnum(ProgressionBarStyle::values);
  private final String id;

  ProgressionBarStyle(final String id) {
    this.id = id;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }
}
