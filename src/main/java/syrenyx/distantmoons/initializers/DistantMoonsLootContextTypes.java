package syrenyx.distantmoons.initializers;

import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.mixin.LootContextTypesAccessor;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

import java.util.function.Consumer;

public abstract class DistantMoonsLootContextTypes {

  public static final ContextType AFFLICTED_ATTACK = register("afflicted_attack", builder -> builder
      .require(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .require(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .require(LootContextParameters.DAMAGE_SOURCE)
      .require(LootContextParameters.ORIGIN)
      .require(LootContextParameters.THIS_ENTITY)
      .allow(LootContextParameters.ATTACKING_ENTITY)
      .allow(LootContextParameters.DIRECT_ATTACKING_ENTITY)
  );
  public static final ContextType AFFLICTED_BLOCK = register("afflicted_block", builder -> builder
      .require(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .require(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .require(LootContextParameters.BLOCK_STATE)
      .require(LootContextParameters.ORIGIN)
      .require(LootContextParameters.THIS_ENTITY)
  );
  public static final ContextType AFFLICTED_ENTITY = register("afflicted_entity", builder -> builder
      .require(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .require(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .require(LootContextParameters.ORIGIN)
      .require(LootContextParameters.THIS_ENTITY)
  );
  public static final ContextType AFFLICTED_ITEM = register("afflicted_item", builder -> builder
      .require(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .require(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .require(LootContextParameters.ORIGIN)
      .require(LootContextParameters.THIS_ENTITY)
      .require(LootContextParameters.TOOL)
  );
  public static final ContextType AFFLICTED_PROJECTILE = register("afflicted_projectile", builder -> builder
      .require(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .require(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .require(DistantMoonsLootContextParameters.SPAWNED_ENTITY)
      .require(LootContextParameters.ORIGIN)
      .require(LootContextParameters.THIS_ENTITY)
  );
  public static final ContextType ENCHANTED_ITEM = register("enchanted_item", builder -> builder
      .require(LootContextParameters.ENCHANTMENT_LEVEL)
      .require(LootContextParameters.ORIGIN)
      .require(LootContextParameters.THIS_ENTITY)
      .require(LootContextParameters.TOOL)
  );

  private static ContextType register(String id, Consumer<ContextType.Builder> type) {
    ContextType.Builder builder = new ContextType.Builder();
    type.accept(builder);
    ContextType contextType = builder.build();
    LootContextTypesAccessor.MAP().forcePut(UnderDistantMoons.identifierOf(id), contextType);
    return contextType;
  }

  public static void initialize() {}
}
