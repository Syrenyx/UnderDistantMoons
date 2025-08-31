package syrenyx.distantmoons.initializers.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.affliction.effect.*;
import syrenyx.distantmoons.initializers.Registries;

import java.util.List;
import java.util.function.UnaryOperator;

public class AfflictionEffectComponentTypes {

  public static final Codec<ComponentType<?>> COMPONENT_TYPE_CODEC = Codec.lazyInitialized(Registries.AFFLICTION_EFFECT_COMPONENT_TYPE::getCodec);
  public static final Codec<ComponentMap> COMPONENT_MAP_CODEC = ComponentMap.createCodec(COMPONENT_TYPE_CODEC);

  //VALUE_EFFECT_COMPONENTS
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> ARMOR_EFFECTIVENESS = register(
      "armor_effectiveness", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> DAMAGE = register(
      "damage", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> DAMAGE_IMMUNITY = register(
      "damage_immunity", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> DAMAGE_PROTECTION = register(
      "damage_protection", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> KNOCKBACK = register(
      "knockback", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionValueEffect>>> MOB_EXPERIENCE = register(
      "mob_experience", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionValueEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
  );

  //ENTITY_EFFECT_COMPONENTS
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> HIT_BLOCK = register(
      "hit_block", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.HIT_BLOCK).listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> TICK = register(
      "tick", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionEntityEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf())
  );

  //UNIQUE_EFFECT_COMPONENTSS
  public static final ComponentType<List<AfflictionAttributeEffect>> ATTRIBUTES = register(
      "attributes", builder -> builder.codec(AfflictionAttributeEffect.CODEC.codec().listOf())
  );
  public static final ComponentType<List<AfflictionEffectEntry<AfflictionLocationBasedEffect>>> LOCATION_CHANGED = register(
      "location_changed", builder -> builder.codec(AfflictionEffectEntry.createCodec(AfflictionLocationBasedEffect.CODEC, LootContextTypes.ENCHANTED_LOCATION).listOf())
  );
  public static final ComponentType<List<TargetedAfflictionEffect<AfflictionEntityEffect>>> POST_ATTACK = register(
      "post_attack", builder -> builder.codec(TargetedAfflictionEffect.createPostAttackCodec(AfflictionEntityEffect.CODEC, LootContextTypes.ENCHANTED_DAMAGE).listOf())
  );

  public static void initialize() {}

  private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
    return Registry.register(Registries.AFFLICTION_EFFECT_COMPONENT_TYPE, id, (builderOperator.apply(ComponentType.builder())).build());
  }
}
