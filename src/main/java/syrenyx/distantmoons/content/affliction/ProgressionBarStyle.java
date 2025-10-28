package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum ProgressionBarStyle implements StringIdentifiable {
  DEFAULT("default"),
  EMPTY("empty"),
  FULL("full"),
  INFINITE("infinite");

  public static final Codec<ProgressionBarStyle> CODEC = StringIdentifiable.createCodec(ProgressionBarStyle::values);
  private final String id;

  ProgressionBarStyle(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
