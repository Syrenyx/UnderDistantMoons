package syrenyx.distantmoons.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.affliction.AfflictionManager;
import syrenyx.distantmoons.affliction.ChangeAfflictionOperation;

import java.util.Optional;

public record ChangeAfflictionEffect(
    RegistryEntry<Affliction> affliction,
    Optional<EnchantmentLevelBasedValue> stage,
    Optional<EnchantmentLevelBasedValue> progression,
    ChangeAfflictionOperation operation
) implements EnchantmentEntityEffect {

  public static final MapCodec<ChangeAfflictionEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Affliction.ENTRY_CODEC.fieldOf("affliction").forGetter(ChangeAfflictionEffect::affliction),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("stage").forGetter(ChangeAfflictionEffect::stage),
          EnchantmentLevelBasedValue.CODEC.optionalFieldOf("progression").forGetter(ChangeAfflictionEffect::progression),
          StringIdentifiable.createCodec(ChangeAfflictionOperation::values).fieldOf("operation").forGetter(ChangeAfflictionEffect::operation)
      ).apply(instance, ChangeAfflictionEffect::new)
  );

  @Override
  public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
    if (!(target instanceof LivingEntity livingEntity)) return;
    if (this.operation == ChangeAfflictionOperation.CLEAR) {
      AfflictionManager.clearAffliction(livingEntity, this.affliction);
      return;
    }
    int stage = this.stage.map(levelBasedValue -> (int) levelBasedValue.getValue(level)).orElseGet(() -> this.operation == ChangeAfflictionOperation.SET ? 1 : 0);
    float progression = this.progression.map(levelBaseValue -> levelBaseValue.getValue(level)).orElseGet(() -> this.operation == ChangeAfflictionOperation.SET ? 1.0F : 0.0F);
    AfflictionInstance affliction = new AfflictionInstance(this.affliction, stage, progression);
    if (this.operation == ChangeAfflictionOperation.SET) AfflictionManager.setAffliction(livingEntity, affliction);
    else AfflictionManager.addAffliction(livingEntity, affliction);
  }
}
