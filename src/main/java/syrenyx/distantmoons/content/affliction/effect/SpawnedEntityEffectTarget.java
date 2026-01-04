package syrenyx.distantmoons.content.affliction.effect;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum SpawnedEntityEffectTarget implements StringRepresentable {
  ORIGINATOR("originator"),
  SPAWNED_ENTITY("spawned_entity");

  public static final Codec<SpawnedEntityEffectTarget> CODEC = StringRepresentable.fromEnum(SpawnedEntityEffectTarget::values);
  private final String id;

  SpawnedEntityEffectTarget(final String id) {
    this.id = id;
  }

  @Override
  public String getSerializedName() {
    return this.id;
  }
}
