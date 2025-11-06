package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

public enum EffectPoolEntryTarget implements StringIdentifiable {
  ATTACKER("attacker"),
  ATTACKING_PLAYER("attacking_player"),
  DIRECT_ATTACHER("direct_attacker"),
  INTERACTING_ENTITY("interacting_entity"),
  TARGET_ENTITY("target_entity"),
  THIS("this");

  public static final Codec<EffectPoolEntryTarget> CODEC = StringIdentifiable.createCodec(EffectPoolEntryTarget::values);
  private final String id;

  <T extends Entity> EffectPoolEntryTarget(final String id) {
    this.id = id;
  }

  @Override
  public String asString() {
    return this.id;
  }

  @Nullable
  public Entity tryGettingEntityFromContext(LootContext context) {
    return switch (this) {
      case ATTACKER -> context.get(LootContextParameters.ATTACKING_ENTITY);
      case ATTACKING_PLAYER -> context.get(LootContextParameters.LAST_DAMAGE_PLAYER);
      case DIRECT_ATTACHER -> context.get(LootContextParameters.DIRECT_ATTACKING_ENTITY);
      case INTERACTING_ENTITY -> context.get(LootContextParameters.INTERACTING_ENTITY);
      case TARGET_ENTITY -> context.get(LootContextParameters.TARGET_ENTITY);
      case THIS -> context.get(LootContextParameters.THIS_ENTITY);
    };
  }
}
