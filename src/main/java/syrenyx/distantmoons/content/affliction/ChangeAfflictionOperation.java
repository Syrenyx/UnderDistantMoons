package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum ChangeAfflictionOperation implements StringIdentifiable {
  ADD("add"),
  CLEAR("clear"),
  GIVE("give"),
  SET("set");

  public static final Codec<ChangeAfflictionOperation> CODEC = StringIdentifiable.createCodec(ChangeAfflictionOperation::values);
  private final String id;

  ChangeAfflictionOperation(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
