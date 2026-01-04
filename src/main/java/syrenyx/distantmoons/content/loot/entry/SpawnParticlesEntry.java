package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.content.affliction.effect.entity.SpawnParticlesEffect;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class SpawnParticlesEntry extends LootPoolSingletonContainer {

  public static final MapCodec<SpawnParticlesEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          ParticleTypes.CODEC.fieldOf("particle").forGetter(entry -> entry.particle),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource.CODEC.fieldOf("horizontal_position").forGetter(entry -> entry.horizontalPosition),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource.CODEC.fieldOf("vertical_position").forGetter(entry -> entry.verticalPosition),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource.CODEC.fieldOf("horizontal_velocity").forGetter(entry -> entry.horizontalVelocity),
          net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource.CODEC.fieldOf("vertical_velocity").forGetter(entry -> entry.verticalVelocity),
          FloatProvider.CODEC.optionalFieldOf("speed", ConstantFloat.ZERO).forGetter(entry -> entry.speed),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("target", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, SpawnParticlesEntry::new)
  );
  protected final ParticleOptions particle;
  protected final net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource horizontalPosition;
  protected final net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource verticalPosition;
  protected final net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource horizontalVelocity;
  protected final net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource verticalVelocity;
  protected final FloatProvider speed;
  protected final OptionalEffectPoolEntryTarget target;

  protected SpawnParticlesEntry(
      ParticleOptions particle,
      net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource horizontalPosition,
      net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.PositionSource verticalPosition,
      net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource horizontalVelocity,
      net.minecraft.world.item.enchantment.effects.SpawnParticlesEffect.VelocitySource verticalVelocity,
      FloatProvider speed,
      OptionalEffectPoolEntryTarget target,
      int weight,
      int quality,
      List<LootItemCondition> conditions
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
  public boolean expand(LootContext context, Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    if (this.target == OptionalEffectPoolEntryTarget.NONE) spawnStaticParticles(context);
    else spawnEntityParticles(context);
    return true;
  }

  private void spawnEntityParticles(LootContext context) {
    Entity entity = this.target.tryGettingEntityFromContext(context);
    if (entity == null) return;
    Vec3 movement = entity.getKnownMovement();
    float width = entity.getBbWidth();
    float height = entity.getBbHeight();
    Vec3 pos = entity.getPositionCodec().getBase();
    RandomSource random = context.getRandom();
    context.getLevel().sendParticles(
        this.particle,
        this.horizontalPosition.getCoordinate(pos.x(), pos.x(), width, random),
        this.verticalPosition.getCoordinate(pos.y(), pos.y() + height / 2.0F, height, random),
        this.horizontalPosition.getCoordinate(pos.z(), pos.z(), width, random),
        0,
        this.horizontalVelocity.getVelocity(movement.x(), random),
        this.verticalVelocity.getVelocity(movement.y(), random),
        this.horizontalVelocity.getVelocity(movement.z(), random),
        this.speed.sample(random)
    );
  }

  private void spawnStaticParticles(LootContext context) {
    Vec3 pos = context.getOptionalParameter(LootContextParams.ORIGIN);
    if (pos == null) return;
    RandomSource random = context.getRandom();
    context.getLevel().sendParticles(
        this.particle,
        this.horizontalPosition.getCoordinate(pos.x(), pos.x(), 1.0F, random),
        this.verticalPosition.getCoordinate(pos.y(), pos.y(), 1.0F, random),
        this.horizontalPosition.getCoordinate(pos.z(), pos.z(), 1.0F, random),
        0,
        this.horizontalVelocity.getVelocity(0.0F, random),
        this.verticalVelocity.getVelocity(0.0F, random),
        this.horizontalVelocity.getVelocity(0.0F, random),
        this.speed.sample(random)
    );
  }

  @Override
  protected void createItemStack(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
