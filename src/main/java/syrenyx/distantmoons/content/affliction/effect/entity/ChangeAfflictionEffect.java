package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.content.affliction.AfflictionManager;
import syrenyx.distantmoons.content.affliction.ChangeAfflictionOperation;
import syrenyx.distantmoons.references.DistantMoonsRegistryKeys;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.Vec3;

public record ChangeAfflictionEffect(
    Identifier affliction,
    ChangeAfflictionOperation operation,
    Optional<LevelBasedValue> stage,
    Optional<LevelBasedValue> progression
) implements AfflictionEntityEffect {

  public static final MapCodec<ChangeAfflictionEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("affliction").forGetter(ChangeAfflictionEffect::affliction),
          ChangeAfflictionOperation.CODEC.fieldOf("operation").forGetter(ChangeAfflictionEffect::operation),
          LevelBasedValue.CODEC.optionalFieldOf("stage").forGetter(ChangeAfflictionEffect::stage),
          LevelBasedValue.CODEC.optionalFieldOf("progression").forGetter(ChangeAfflictionEffect::progression)
      )
      .apply(instance, ChangeAfflictionEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int afflictionStage, Entity target, Vec3 pos) {
    if (!(target instanceof LivingEntity livingEntity)) return;
    Optional<Holder.Reference<Affliction>> afflictionEntry = target.registryAccess().get(ResourceKey.create(DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY, this.affliction));
    if (afflictionEntry.isEmpty()) return;
    switch (this.operation) {
      case ADD -> AfflictionManager.addToAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(stageBasedValue -> (int) stageBasedValue.calculate(afflictionStage)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(stageBasedValue -> stageBasedValue.calculate(afflictionStage)).orElse(0.0F)
      ));
      case CLEAR -> AfflictionManager.clearAffliction(livingEntity, afflictionEntry.get());
      case GIVE -> AfflictionManager.giveAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(stageBasedValue -> (int) stageBasedValue.calculate(afflictionStage)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(stageBasedValue -> stageBasedValue.calculate(afflictionStage)).orElse(0.0F)
      ));
      case SET -> AfflictionManager.setAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(stageBasedValue -> (int) stageBasedValue.calculate(afflictionStage)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(stageBasedValue -> stageBasedValue.calculate(afflictionStage)).orElse(0.0F)
      ));
    }
  }
}
