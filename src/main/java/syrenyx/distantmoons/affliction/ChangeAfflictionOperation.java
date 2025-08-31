package syrenyx.distantmoons.affliction;

import net.minecraft.util.StringIdentifiable;

public enum ChangeAfflictionOperation implements StringIdentifiable {
  CLEAR("clear"),
  SET("set"),
  ADD("add");

  private final String id;

  ChangeAfflictionOperation(String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
