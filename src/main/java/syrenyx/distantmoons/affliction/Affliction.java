package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.dynamic.Codecs;
import syrenyx.distantmoons.affliction.effect.AfflictionEffectEntry;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;
import syrenyx.distantmoons.initializers.LootContextTypes;
import syrenyx.distantmoons.initializers.Registries;
import syrenyx.distantmoons.references.RegistryKeys;

import java.util.List;
import java.util.Optional;

public record Affliction(
    Text description,
    Optional<AfflictionDisplay> display,
    Optional<RegistryEntryList<EntityType<?>>> immuneEntities,
    int maxStage,
    boolean persistent,
    Optional<EnchantmentLevelBasedValue> tickProgression,
    ComponentMap effects
) {

  public static final Codec<ComponentMap> COMPONENT_MAP_CODEC = ComponentMap.createCodec(Codec.lazyInitialized(Registries.AFFLICTION_EFFECT_COMPONENT_REGISTRY::getCodec));
  public static final Codec<Affliction> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          TextCodecs.CODEC.fieldOf("description").forGetter(Affliction::description),
          AfflictionDisplay.CODEC.optionalFieldOf("display").forGetter(Affliction::display),
          RegistryCodecs.entryList(net.minecraft.registry.RegistryKeys.ENTITY_TYPE).optionalFieldOf("immune_entities").forGetter(Affliction::immuneEntities),
          Codecs.rangedInt(1, Affliction.MAX_STAGE).fieldOf("max_stage").forGetter(Affliction::maxStage),
          Codec.BOOL.optionalFieldOf("persistent", false).forGetter(Affliction::persistent),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("tick_progression").forGetter(Affliction::tickProgression),
          COMPONENT_MAP_CODEC.optionalFieldOf("effects", ComponentMap.EMPTY).forGetter(Affliction::effects)
      )
      .apply(instance, Affliction::new)
  );
  public static final Codec<RegistryEntry<Affliction>> FIXED_ENTRY_CODEC = RegistryFixedCodec.of(RegistryKeys.AFFLICTION_REGISTRY_KEY);
  public static final int MAX_PROGRESSION = 100;
  public static final int MAX_STAGE = 255;
  public static final int DEFAULT_STAGE = 1;

  public static void processEntityEffects(Entity entity, AfflictionInstance afflictionInstance, ComponentType<List<AfflictionEffectEntry<AfflictionEntityEffect>>> componentType) {
    List<AfflictionEffectEntry<AfflictionEntityEffect>> effectEntries = afflictionInstance.affliction().value().effects.getOrDefault(componentType, List.of());
    LootContext lootContext = getLootContext(entity, afflictionInstance.stage(), afflictionInstance.progression());
    for (AfflictionEffectEntry<AfflictionEntityEffect> effectEntry : effectEntries) {
      if (effectEntry.test(lootContext)) effectEntry.effect().apply((ServerWorld) entity.getWorld(), afflictionInstance.stage(), entity, entity.getPos());
    }
  }

  private static LootContext getLootContext(Entity entity, int stage, float progression) {
    return new LootContext.Builder(
        new LootWorldContext
            .Builder((ServerWorld) entity.getWorld())
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_PROGRESSION, progression)
            .add(syrenyx.distantmoons.references.LootContextParameters.AFFLICTION_STAGE, stage)
            .add(LootContextParameters.ORIGIN, entity.getPos())
            .add(LootContextParameters.THIS_ENTITY, entity)
            .build(LootContextTypes.AFFLICTED_ENTITY)
    ).build(Optional.empty());
  }
}
