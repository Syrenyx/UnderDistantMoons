package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public enum EffectPoolEntryTarget implements StringRepresentable {
  ATTACKER("attacker"),
  ATTACKING_PLAYER("attacking_player"),
  DIRECT_ATTACHER("direct_attacker"),
  INTERACTING_ENTITY("interacting_entity"),
  TARGET_ENTITY("target_entity"),
  THIS("this");

  public static final Codec<EffectPoolEntryTarget> CODEC = StringRepresentable.fromEnum(EffectPoolEntryTarget::values);
  private final String id;

  <T extends Entity> EffectPoolEntryTarget(final String id) {
    this.id = id;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }

  @Nullable
  public Entity tryGettingEntityFromContext(LootContext context) {
    return switch (this) {
      case ATTACKER -> context.getOptionalParameter(LootContextParams.ATTACKING_ENTITY);
      case ATTACKING_PLAYER -> context.getOptionalParameter(LootContextParams.LAST_DAMAGE_PLAYER);
      case DIRECT_ATTACHER -> context.getOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY);
      case INTERACTING_ENTITY -> context.getOptionalParameter(LootContextParams.INTERACTING_ENTITY);
      case TARGET_ENTITY -> context.getOptionalParameter(LootContextParams.TARGET_ENTITY);
      case THIS -> context.getOptionalParameter(LootContextParams.THIS_ENTITY);
    };
  }
}
