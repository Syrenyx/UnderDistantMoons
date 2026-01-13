package syrenyx.distantmoons.content.predicate.condition;

import net.minecraft.util.StringRepresentable;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

public enum TargetEntityType implements StringRepresentable {

  THIS("this", LootContextParams.THIS_ENTITY),
  ATTACKER("attacker", LootContextParams.ATTACKING_ENTITY),
  DIRECT_ATTACKER("direct_attacker", LootContextParams.DIRECT_ATTACKING_ENTITY),
  ATTACKING_PLAYER("attacking_player", LootContextParams.LAST_DAMAGE_PLAYER),
  SPAWNED_ENTITY("spawned_entity", DistantMoonsLootContextParameters.SPAWNED_ENTITY);

  public static final EnumCodec<TargetEntityType> CODEC = StringRepresentable.fromEnum(TargetEntityType::values);
  private final String type;
  private final ContextKey<? extends Entity> parameter;

  TargetEntityType(final String type, final ContextKey<? extends Entity> parameter) {
    this.type = type;
    this.parameter = parameter;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.type;
  }

  public ContextKey<? extends Entity> getParameter() {
    return this.parameter;
  }
}
