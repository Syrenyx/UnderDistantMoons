package syrenyx.distantmoons.predicate.condition;

import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.context.ContextParameter;

public enum TargetEntityType implements StringIdentifiable {

  THIS("this", LootContextParameters.THIS_ENTITY),
  ATTACKER("attacker", LootContextParameters.ATTACKING_ENTITY),
  DIRECT_ATTACKER("direct_attacker", LootContextParameters.DIRECT_ATTACKING_ENTITY),
  ATTACKING_PLAYER("attacking_player", LootContextParameters.LAST_DAMAGE_PLAYER),
  SPAWNED_ENTITY("spawned_entity", syrenyx.distantmoons.references.LootContextParameters.SPAWNED_ENTITY);

  public static final StringIdentifiable.EnumCodec<TargetEntityType> CODEC = StringIdentifiable.createCodec(TargetEntityType::values);
  private final String type;
  private final ContextParameter<? extends Entity> parameter;

  TargetEntityType(final String type, final ContextParameter<? extends Entity> parameter) {
    this.type = type;
    this.parameter = parameter;
  }

  @Override
  public String asString() {
    return this.type;
  }

  public ContextParameter<? extends Entity> getParameter() {
    return this.parameter;
  }
}
