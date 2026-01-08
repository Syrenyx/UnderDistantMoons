package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum ChangeAfflictionOperation implements StringRepresentable {
  ADD("add"),
  CLEAR("clear"),
  GIVE("give"),
  SET("set");

  public static final Codec<ChangeAfflictionOperation> CODEC = StringRepresentable.fromEnum(ChangeAfflictionOperation::values);
  private final String id;

  ChangeAfflictionOperation(final String id) {
    this.id = id;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }
}
