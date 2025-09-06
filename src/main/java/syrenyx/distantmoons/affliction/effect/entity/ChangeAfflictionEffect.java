package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.affliction.AfflictionManager;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

import java.util.Optional;

public record ChangeAfflictionEffect(
    RegistryEntry<Affliction> affliction,
    ChangeAfflictionEffectOperation operation,
    Optional<EnchantmentLevelBasedValue> stage,
    Optional<EnchantmentLevelBasedValue> progression
) implements AfflictionEntityEffect {

  public static final MapCodec<ChangeAfflictionEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Affliction.FIXED_ENTRY_CODEC.fieldOf("affliction").forGetter(ChangeAfflictionEffect::affliction),
          ChangeAfflictionEffectOperation.CODEC.fieldOf("operation").forGetter(ChangeAfflictionEffect::operation),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("stage").forGetter(ChangeAfflictionEffect::stage),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("progression").forGetter(ChangeAfflictionEffect::progression)
      )
      .apply(instance, ChangeAfflictionEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int afflictionStage, Entity target, Vec3d pos) {
    if (!(target instanceof LivingEntity livingEntity)) return;
    switch (this.operation) {
      case ADD -> AfflictionManager.addToAffliction(livingEntity, new AfflictionInstance(
          this.affliction,
          this.stage.map(stageBasedValue -> (int) stageBasedValue.getValue(afflictionStage)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(stageBasedValue -> stageBasedValue.getValue(afflictionStage)).orElse(0.0F)
      ));
      case CLEAR -> AfflictionManager.clearAffliction(livingEntity, this.affliction);
      case GIVE -> AfflictionManager.giveAffliction(livingEntity, new AfflictionInstance(
          this.affliction,
          this.stage.map(stageBasedValue -> (int) stageBasedValue.getValue(afflictionStage)).orElse(Affliction.DEFAULT_STAGE),
      this.progression.map(stageBasedValue -> stageBasedValue.getValue(afflictionStage)).orElse(0.0F)
      ));
      case SET -> AfflictionManager.setAffliction(livingEntity, new AfflictionInstance(
          this.affliction,
          this.stage.map(stageBasedValue -> (int) stageBasedValue.getValue(afflictionStage)).orElse(Affliction.DEFAULT_STAGE),
      this.progression.map(stageBasedValue -> stageBasedValue.getValue(afflictionStage)).orElse(0.0F)
      ));
    }
  }
}
