package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum ChangeAfflictionEffectOperation implements StringIdentifiable {
  ADD("add"),
  CLEAR("clear"),
  GIVE("give"),
  SET("set");

  public static final Codec<ChangeAfflictionEffectOperation> CODEC = StringIdentifiable.createCodec(ChangeAfflictionEffectOperation::values);
  private final String id;

  ChangeAfflictionEffectOperation(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
