package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.effect.entity.SpawnParticlesEnchantmentEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TrackedPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.random.Random;
import syrenyx.distantmoons.content.affliction.effect.entity.SpawnParticlesEffect;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class SpawnParticlesEntry extends LeafEntry {

  public static final MapCodec<SpawnParticlesEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          ParticleTypes.TYPE_CODEC.fieldOf("particle").forGetter(entry -> entry.particle),
          SpawnParticlesEnchantmentEffect.PositionSource.CODEC.fieldOf("horizontal_position").forGetter(entry -> entry.horizontalPosition),
          SpawnParticlesEnchantmentEffect.PositionSource.CODEC.fieldOf("vertical_position").forGetter(entry -> entry.verticalPosition),
          SpawnParticlesEnchantmentEffect.VelocitySource.CODEC.fieldOf("horizontal_velocity").forGetter(entry -> entry.horizontalVelocity),
          SpawnParticlesEnchantmentEffect.VelocitySource.CODEC.fieldOf("vertical_velocity").forGetter(entry -> entry.verticalVelocity),
          FloatProvider.VALUE_CODEC.optionalFieldOf("speed", ConstantFloatProvider.ZERO).forGetter(entry -> entry.speed),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("target", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, SpawnParticlesEntry::new)
  );
  protected final ParticleEffect particle;
  protected final SpawnParticlesEnchantmentEffect.PositionSource horizontalPosition;
  protected final SpawnParticlesEnchantmentEffect.PositionSource verticalPosition;
  protected final SpawnParticlesEnchantmentEffect.VelocitySource horizontalVelocity;
  protected final SpawnParticlesEnchantmentEffect.VelocitySource verticalVelocity;
  protected final FloatProvider speed;
  protected final OptionalEffectPoolEntryTarget target;

  protected SpawnParticlesEntry(
      ParticleEffect particle,
      SpawnParticlesEnchantmentEffect.PositionSource horizontalPosition,
      SpawnParticlesEnchantmentEffect.PositionSource verticalPosition,
      SpawnParticlesEnchantmentEffect.VelocitySource horizontalVelocity,
      SpawnParticlesEnchantmentEffect.VelocitySource verticalVelocity,
      FloatProvider speed,
      OptionalEffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.particle = particle;
    this.horizontalPosition = horizontalPosition;
    this.verticalPosition = verticalPosition;
    this.horizontalVelocity = horizontalVelocity;
    this.verticalVelocity = verticalVelocity;
    this.speed = speed;
    this.target = target;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.SPAWN_PARTICLES;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    if (this.target == OptionalEffectPoolEntryTarget.NONE) spawnStaticParticles(context);
    else spawnEntityParticles(context);
    return true;
  }

  private void spawnEntityParticles(LootContext context) {
    Entity entity = this.target.tryGettingEntityFromContext(context);
    if (entity == null) return;
    Vec3d movement = entity.getMovement();
    float width = entity.getWidth();
    float height = entity.getHeight();
    Vec3d pos = entity.getTrackedPosition().getPos();
    Random random = context.getRandom();
    context.getWorld().spawnParticles(
        this.particle,
        this.horizontalPosition.getPosition(pos.getX(), pos.getX(), width, random),
        this.verticalPosition.getPosition(pos.getY(), pos.getY() + height / 2.0F, height, random),
        this.horizontalPosition.getPosition(pos.getZ(), pos.getZ(), width, random),
        0,
        this.horizontalVelocity.getVelocity(movement.getX(), random),
        this.verticalVelocity.getVelocity(movement.getY(), random),
        this.horizontalVelocity.getVelocity(movement.getZ(), random),
        this.speed.get(random)
    );
  }

  private void spawnStaticParticles(LootContext context) {
    Vec3d pos = context.get(LootContextParameters.ORIGIN);
    if (pos == null) return;
    Random random = context.getRandom();
    context.getWorld().spawnParticles(
        this.particle,
        this.horizontalPosition.getPosition(pos.getX(), pos.getX(), 1.0F, random),
        this.verticalPosition.getPosition(pos.getY(), pos.getY(), 1.0F, random),
        this.horizontalPosition.getPosition(pos.getZ(), pos.getZ(), 1.0F, random),
        0,
        this.horizontalVelocity.getVelocity(0.0F, random),
        this.verticalVelocity.getVelocity(0.0F, random),
        this.horizontalVelocity.getVelocity(0.0F, random),
        this.speed.get(random)
    );
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
