package syrenyx.distantmoons.enchantment.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.affliction.AfflictionManager;
import syrenyx.distantmoons.affliction.effect.entity.ChangeAfflictionEffectOperation;
import syrenyx.distantmoons.references.RegistryKeys;

import java.util.Optional;

public record ChangeAfflictionEffect(
    Identifier affliction,
    ChangeAfflictionEffectOperation operation,
    Optional<EnchantmentLevelBasedValue> stage,
    Optional<EnchantmentLevelBasedValue> progression
) implements EnchantmentEntityEffect {

  public static final MapCodec<ChangeAfflictionEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("affliction").forGetter(ChangeAfflictionEffect::affliction),
          ChangeAfflictionEffectOperation.CODEC.fieldOf("operation").forGetter(ChangeAfflictionEffect::operation),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("stage").forGetter(ChangeAfflictionEffect::stage),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("progression").forGetter(ChangeAfflictionEffect::progression)
      )
      .apply(instance, ChangeAfflictionEffect::new)
  );

  @Override
  public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
    if (!(target instanceof LivingEntity livingEntity)) return;
    Optional<RegistryEntry.Reference<Affliction>> afflictionEntry = target.getRegistryManager().getOptionalEntry(RegistryKey.of(RegistryKeys.AFFLICTION_REGISTRY_KEY, this.affliction));
    if (afflictionEntry.isEmpty()) return;
    switch (this.operation) {
      case ADD -> AfflictionManager.addToAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(stageBasedValue -> (int) stageBasedValue.getValue(level)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(stageBasedValue -> stageBasedValue.getValue(level)).orElse(0.0F)
      ));
      case CLEAR -> AfflictionManager.clearAffliction(livingEntity, afflictionEntry.get());
      case GIVE -> AfflictionManager.giveAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(stageBasedValue -> (int) stageBasedValue.getValue(level)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(stageBasedValue -> stageBasedValue.getValue(level)).orElse(0.0F)
      ));
      case SET -> AfflictionManager.setAffliction(livingEntity, new AfflictionInstance(
          afflictionEntry.get(),
          this.stage.map(stageBasedValue -> (int) stageBasedValue.getValue(level)).orElse(Affliction.DEFAULT_STAGE),
          this.progression.map(stageBasedValue -> stageBasedValue.getValue(level)).orElse(0.0F)
      ));
    }
  }
}
