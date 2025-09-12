package syrenyx.distantmoons.affliction.effect;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum SpawnedEntityEffectTarget implements StringIdentifiable {
  ORIGINATOR("originator"),
  SPAWNED_ENTITY("spawned_entity");

  public static final Codec<SpawnedEntityEffectTarget> CODEC = StringIdentifiable.createCodec(SpawnedEntityEffectTarget::values);
  private final String id;

  SpawnedEntityEffectTarget(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }
}
