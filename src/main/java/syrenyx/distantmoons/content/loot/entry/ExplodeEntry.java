package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.phys.Vec3;

public class ExplodeEntry extends LootPoolSingletonContainer {

  public static final MapCodec<ExplodeEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          DamageType.CODEC.optionalFieldOf("damage_type").forGetter(entry -> entry.damageType),
          NumberProviders.CODEC.optionalFieldOf("knockback_multiplier").forGetter(entry -> entry.knockbackMultiplier),
          RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("immune_blocks").forGetter(entry -> entry.immuneBlocks),
          Vec3.CODEC.optionalFieldOf("offset", Vec3.ZERO).forGetter(entry -> entry.offset),
          NumberProviders.CODEC.fieldOf("radius").forGetter(entry -> entry.radius),
          Codec.BOOL.optionalFieldOf("create_fire", false).forGetter(entry -> entry.createFire),
          Level.ExplosionInteraction.CODEC.fieldOf("block_interaction").forGetter(entry -> entry.blockInteraction),
          ParticleTypes.CODEC.fieldOf("small_particle").forGetter(entry -> entry.smallParticle),
          ParticleTypes.CODEC.fieldOf("large_particle").forGetter(entry -> entry.largeParticle),
          WeightedList.codec(ExplosionParticleInfo.CODEC).optionalFieldOf("block_particles", WeightedList.of()).forGetter(entry -> entry.blockParticles),
          SoundEvent.CODEC.fieldOf("sound").forGetter(entry -> entry.sound),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("user", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.user),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, ExplodeEntry::new)
  );
  protected final Optional<Holder<DamageType>> damageType;
  protected final Optional<NumberProvider> knockbackMultiplier;
  protected final Optional<HolderSet<Block>> immuneBlocks;
  protected final Vec3 offset;
  protected final NumberProvider radius;
  protected final boolean createFire;
  protected final Level.ExplosionInteraction blockInteraction;
  protected final ParticleOptions smallParticle;
  protected final ParticleOptions largeParticle;
  protected final WeightedList<ExplosionParticleInfo> blockParticles;
  protected final Holder<SoundEvent> sound;
  protected final OptionalEffectPoolEntryTarget user;

  protected ExplodeEntry(
      Optional<Holder<DamageType>> damageType,
      Optional<NumberProvider> knockbackMultiplier,
      Optional<HolderSet<Block>> immuneBlocks,
      Vec3 offset,
      NumberProvider radius,
      boolean createFire,
      Level.ExplosionInteraction blockInteraction,
      ParticleOptions smallParticle,
      ParticleOptions largeParticle,
      WeightedList<ExplosionParticleInfo> blockParticles,
      Holder<SoundEvent> sound,
      OptionalEffectPoolEntryTarget user,
      int weight,
      int quality,
      List<LootItemCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.damageType = damageType;
    this.knockbackMultiplier = knockbackMultiplier;
    this.immuneBlocks = immuneBlocks;
    this.offset = offset;
    this.radius = radius;
    this.createFire = createFire;
    this.blockInteraction = blockInteraction;
    this.smallParticle = smallParticle;
    this.largeParticle = largeParticle;
    this.blockParticles = blockParticles;
    this.sound = sound;
    this.user = user;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.EXPLODE;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    Vec3 pos = context.getOptionalParameter(LootContextParams.ORIGIN);
    if (pos == null) return true;
    Vec3 offsetPosition = pos.add(this.offset);
    Entity entity = this.user.tryGettingEntityFromContext(context);
    context.getLevel().explode(
        entity,
        this.getDamageSource(entity, offsetPosition),
        new SimpleExplosionDamageCalculator(
            this.blockInteraction != Level.ExplosionInteraction.NONE,
            this.damageType.isPresent(),
            this.knockbackMultiplier.map(knockbackMultiplier -> knockbackMultiplier.getFloat(context)),
            this.immuneBlocks
        ),
        offsetPosition.x(), offsetPosition.y(), offsetPosition.z(),
        Math.max(this.radius.getFloat(context), 0.0F),
        this.createFire,
        this.blockInteraction,
        this.smallParticle,
        this.largeParticle,
        this.blockParticles,
        this.sound
    );
    return true;
  }

  @Nullable
  private DamageSource getDamageSource(Entity user, Vec3 pos) {
    return this.damageType.map(damageTypeRegistryEntry -> user != null
        ? new DamageSource(damageTypeRegistryEntry, user)
        : new DamageSource(damageTypeRegistryEntry, pos)
    ).orElse(null);
  }

  @Override
  protected void createItemStack(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
