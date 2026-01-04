package syrenyx.distantmoons.content.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.content.affliction.effect.*;
import syrenyx.distantmoons.initializers.DistantMoonsRegistries;
import syrenyx.distantmoons.references.DistantMoonsRegistryKeys;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public record Affliction(
    Component description,
    Optional<AfflictionDisplay> display,
    Optional<HolderSet<EntityType<?>>> immuneEntities,
    int maxStage,
    boolean persistent,
    Optional<LevelBasedValue> tickProgression,
    DataComponentMap effects
) {

  public static final Codec<DataComponentMap> COMPONENT_MAP_CODEC = DataComponentMap.makeCodec(Codec.lazyInitialized(DistantMoonsRegistries.AFFLICTION_EFFECT_COMPONENT_REGISTRY::byNameCodec));
  public static final Codec<Affliction> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          ComponentSerialization.CODEC.fieldOf("description").forGetter(Affliction::description),
          AfflictionDisplay.CODEC.optionalFieldOf("display").forGetter(Affliction::display),
          RegistryCodecs.homogeneousList(net.minecraft.core.registries.Registries.ENTITY_TYPE).optionalFieldOf("immune_entities").forGetter(Affliction::immuneEntities),
          ExtraCodecs.intRange(1, Affliction.MAX_STAGE).fieldOf("max_stage").forGetter(Affliction::maxStage),
          Codec.BOOL.optionalFieldOf("persistent", false).forGetter(Affliction::persistent),
          LevelBasedValue.CODEC.optionalFieldOf("tick_progression").forGetter(Affliction::tickProgression),
          COMPONENT_MAP_CODEC.optionalFieldOf("effects", DataComponentMap.EMPTY).forGetter(Affliction::effects)
      )
      .apply(instance, Affliction::new)
  );
  public static final Codec<Holder<Affliction>> FIXED_ENTRY_CODEC = RegistryFixedCodec.create(DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY);
  public static final int MAX_PROGRESSION = 100;
  public static final int MAX_STAGE = 255;
  public static final int DEFAULT_STAGE = 1;
}
