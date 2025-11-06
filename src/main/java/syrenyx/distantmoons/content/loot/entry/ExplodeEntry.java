package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.particle.BlockParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ExplodeEntry extends LeafEntry {

  public static final MapCodec<ExplodeEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          DamageType.ENTRY_CODEC.optionalFieldOf("damage_type").forGetter(entry -> entry.damageType),
          LootNumberProviderTypes.CODEC.optionalFieldOf("knockback_multiplier").forGetter(entry -> entry.knockbackMultiplier),
          RegistryCodecs.entryList(RegistryKeys.BLOCK).optionalFieldOf("immune_blocks").forGetter(entry -> entry.immuneBlocks),
          Vec3d.CODEC.optionalFieldOf("offset", Vec3d.ZERO).forGetter(entry -> entry.offset),
          LootNumberProviderTypes.CODEC.fieldOf("radius").forGetter(entry -> entry.radius),
          Codec.BOOL.optionalFieldOf("create_fire", false).forGetter(entry -> entry.createFire),
          World.ExplosionSourceType.CODEC.fieldOf("block_interaction").forGetter(entry -> entry.blockInteraction),
          ParticleTypes.TYPE_CODEC.fieldOf("small_particle").forGetter(entry -> entry.smallParticle),
          ParticleTypes.TYPE_CODEC.fieldOf("large_particle").forGetter(entry -> entry.largeParticle),
          Pool.createCodec(BlockParticleEffect.CODEC).optionalFieldOf("block_particles", Pool.empty()).forGetter(entry -> entry.blockParticles),
          SoundEvent.ENTRY_CODEC.fieldOf("sound").forGetter(entry -> entry.sound),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("user", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.user),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, ExplodeEntry::new)
  );
  protected final Optional<RegistryEntry<DamageType>> damageType;
  protected final Optional<LootNumberProvider> knockbackMultiplier;
  protected final Optional<RegistryEntryList<Block>> immuneBlocks;
  protected final Vec3d offset;
  protected final LootNumberProvider radius;
  protected final boolean createFire;
  protected final World.ExplosionSourceType blockInteraction;
  protected final ParticleEffect smallParticle;
  protected final ParticleEffect largeParticle;
  protected final Pool<BlockParticleEffect> blockParticles;
  protected final RegistryEntry<SoundEvent> sound;
  protected final OptionalEffectPoolEntryTarget user;

  protected ExplodeEntry(
      Optional<RegistryEntry<DamageType>> damageType,
      Optional<LootNumberProvider> knockbackMultiplier,
      Optional<RegistryEntryList<Block>> immuneBlocks,
      Vec3d offset,
      LootNumberProvider radius,
      boolean createFire,
      World.ExplosionSourceType blockInteraction,
      ParticleEffect smallParticle,
      ParticleEffect largeParticle,
      Pool<BlockParticleEffect> blockParticles,
      RegistryEntry<SoundEvent> sound,
      OptionalEffectPoolEntryTarget user,
      int weight,
      int quality,
      List<LootCondition> conditions
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
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    Vec3d pos = context.get(LootContextParameters.ORIGIN);
    if (pos == null) return true;
    Vec3d offsetPosition = pos.add(this.offset);
    Entity entity = this.user.tryGettingEntityFromContext(context);
    context.getWorld().createExplosion(
        entity,
        this.getDamageSource(entity, offsetPosition),
        new AdvancedExplosionBehavior(
            this.blockInteraction != World.ExplosionSourceType.NONE,
            this.damageType.isPresent(),
            this.knockbackMultiplier.map(knockbackMultiplier -> knockbackMultiplier.nextFloat(context)),
            this.immuneBlocks
        ),
        offsetPosition.getX(), offsetPosition.getY(), offsetPosition.getZ(),
        Math.max(this.radius.nextFloat(context), 0.0F),
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
  private DamageSource getDamageSource(Entity user, Vec3d pos) {
    return this.damageType.map(damageTypeRegistryEntry -> user != null
        ? new DamageSource(damageTypeRegistryEntry, user)
        : new DamageSource(damageTypeRegistryEntry, pos)
    ).orElse(null);
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
