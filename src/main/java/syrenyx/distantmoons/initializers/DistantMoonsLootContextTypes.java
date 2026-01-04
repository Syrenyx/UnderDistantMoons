package syrenyx.distantmoons.initializers;

import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.mixin.LootContextParamSetsAccessor;
import syrenyx.distantmoons.references.DistantMoonsLootContextParameters;

import java.util.function.Consumer;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public abstract class DistantMoonsLootContextTypes {

  public static final ContextKeySet AFFLICTED_ATTACK = register("afflicted_attack", builder -> builder
      .required(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .required(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .required(LootContextParams.DAMAGE_SOURCE)
      .required(LootContextParams.ORIGIN)
      .required(LootContextParams.THIS_ENTITY)
      .optional(LootContextParams.ATTACKING_ENTITY)
      .optional(LootContextParams.DIRECT_ATTACKING_ENTITY)
  );
  public static final ContextKeySet AFFLICTED_BLOCK = register("afflicted_block", builder -> builder
      .required(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .required(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .required(LootContextParams.BLOCK_STATE)
      .required(LootContextParams.ORIGIN)
      .required(LootContextParams.THIS_ENTITY)
  );
  public static final ContextKeySet AFFLICTED_ENTITY = register("afflicted_entity", builder -> builder
      .required(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .required(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .required(LootContextParams.ORIGIN)
      .required(LootContextParams.THIS_ENTITY)
  );
  public static final ContextKeySet AFFLICTED_ITEM = register("afflicted_item", builder -> builder
      .required(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .required(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .required(LootContextParams.ORIGIN)
      .required(LootContextParams.THIS_ENTITY)
      .required(LootContextParams.TOOL)
  );
  public static final ContextKeySet AFFLICTED_PROJECTILE = register("afflicted_projectile", builder -> builder
      .required(DistantMoonsLootContextParameters.AFFLICTION_PROGRESSION)
      .required(DistantMoonsLootContextParameters.AFFLICTION_STAGE)
      .required(DistantMoonsLootContextParameters.SPAWNED_ENTITY)
      .required(LootContextParams.ORIGIN)
      .required(LootContextParams.THIS_ENTITY)
  );
  public static final ContextKeySet ENCHANTED_ITEM = register("enchanted_item", builder -> builder
      .required(LootContextParams.ENCHANTMENT_LEVEL)
      .required(LootContextParams.ORIGIN)
      .required(LootContextParams.THIS_ENTITY)
      .required(LootContextParams.TOOL)
  );

  private static ContextKeySet register(String id, Consumer<ContextKeySet.Builder> type) {
    ContextKeySet.Builder builder = new ContextKeySet.Builder();
    type.accept(builder);
    ContextKeySet contextType = builder.build();
    LootContextParamSetsAccessor.REGISTRY().forcePut(UnderDistantMoons.identifierOf(id), contextType);
    return contextType;
  }

  public static void initialize() {}
}
