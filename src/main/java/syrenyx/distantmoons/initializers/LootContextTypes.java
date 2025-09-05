package syrenyx.distantmoons.initializers;

import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.mixin.LootContextTypesAccessor;
import syrenyx.distantmoons.references.LootContextParameters;

import java.util.function.Consumer;

public abstract class LootContextTypes {

  public static final ContextType AFFLICTED_ATTACK = register("afflicted_attack", builder -> builder
      .require(LootContextParameters.AFFLICTION_PROGRESSION)
      .require(LootContextParameters.AFFLICTION_STAGE)
      .require(net.minecraft.loot.context.LootContextParameters.DAMAGE_SOURCE)
      .require(net.minecraft.loot.context.LootContextParameters.ORIGIN)
      .require(net.minecraft.loot.context.LootContextParameters.THIS_ENTITY)
      .allow(net.minecraft.loot.context.LootContextParameters.ATTACKING_ENTITY)
      .allow(net.minecraft.loot.context.LootContextParameters.DIRECT_ATTACKING_ENTITY)
  );
  public static final ContextType AFFLICTED_ENTITY = register("afflicted_entity", builder -> builder
      .require(LootContextParameters.AFFLICTION_PROGRESSION)
      .require(LootContextParameters.AFFLICTION_STAGE)
      .require(net.minecraft.loot.context.LootContextParameters.ORIGIN)
      .require(net.minecraft.loot.context.LootContextParameters.THIS_ENTITY)
  );
  public static final ContextType AFFLICTED_PROJECTILE = register("afflicted_projectile", builder -> builder
      .require(LootContextParameters.AFFLICTION_PROGRESSION)
      .require(LootContextParameters.AFFLICTION_STAGE)
      .require(net.minecraft.loot.context.LootContextParameters.DIRECT_ATTACKING_ENTITY)
      .require(net.minecraft.loot.context.LootContextParameters.ORIGIN)
      .require(net.minecraft.loot.context.LootContextParameters.THIS_ENTITY)
  );

  private static ContextType register(String id, Consumer<ContextType.Builder> type) {
    ContextType.Builder builder = new ContextType.Builder();
    type.accept(builder);
    ContextType contextType = builder.build();
    Identifier identifier = UnderDistantMoons.identifierOf(id);
    LootContextTypesAccessor.MAP().forcePut(identifier, contextType);
    return contextType;
  }

  public static void initialize() {}
}
