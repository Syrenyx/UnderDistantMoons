package syrenyx.distantmoons.content.predicate.entity;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.MovementPredicate;
import net.minecraft.advancements.criterion.NbtPredicate;
import net.minecraft.advancements.criterion.SlotsPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public record EntityPredicate(
    Optional<EntityTypePredicate> type,
    Optional<DistancePredicate> distance,
    Optional<MovementPredicate> movement,
    PositionalEntityPredicates location,
    Optional<MobEffectsPredicate> effects,
    Optional<NbtPredicate> nbt,
    Optional<EntityFlagsPredicate> flags,
    Optional<EntityEquipmentPredicate> equipment,
    Optional<EntitySubPredicate> typeSpecific,
    Optional<Integer> periodicTick,
    Optional<EntityPredicate> vehicle,
    Optional<EntityPredicate> passenger,
    Optional<EntityPredicate> targetedEntity,
    Optional<String> team,
    Optional<SlotsPredicate> slots,
    EntityPredicateExtension extension
) {

  public static final Codec<EntityPredicate> CODEC = Codec.recursive(
      "EntityPredicate",
      entityPredicateCodec -> RecordCodecBuilder.create(instance -> instance
          .group(
              EntityTypePredicate.CODEC.optionalFieldOf("type").forGetter(EntityPredicate::type),
              DistancePredicate.CODEC.optionalFieldOf("distance").forGetter(EntityPredicate::distance),
              MovementPredicate.CODEC.optionalFieldOf("movement").forGetter(EntityPredicate::movement),
              PositionalEntityPredicates.CODEC.forGetter(EntityPredicate::location),
              MobEffectsPredicate.CODEC.optionalFieldOf("effects").forGetter(EntityPredicate::effects),
              NbtPredicate.CODEC.optionalFieldOf("nbt").forGetter(EntityPredicate::nbt),
              EntityFlagsPredicate.CODEC.optionalFieldOf("flags").forGetter(EntityPredicate::flags),
              EntityEquipmentPredicate.CODEC.optionalFieldOf("equipment").forGetter(EntityPredicate::equipment),
              EntitySubPredicate.CODEC.optionalFieldOf("type_specific").forGetter(EntityPredicate::typeSpecific),
              ExtraCodecs.POSITIVE_INT.optionalFieldOf("periodic_tick").forGetter(EntityPredicate::periodicTick),
              entityPredicateCodec.optionalFieldOf("vehicle").forGetter(EntityPredicate::vehicle),
              entityPredicateCodec.optionalFieldOf("passenger").forGetter(EntityPredicate::passenger),
              entityPredicateCodec.optionalFieldOf("targeted_entity").forGetter(EntityPredicate::targetedEntity),
              Codec.STRING.optionalFieldOf("team").forGetter(EntityPredicate::team),
              SlotsPredicate.CODEC.optionalFieldOf("slots").forGetter(EntityPredicate::slots),
              EntityPredicateExtension.CODEC.forGetter(EntityPredicate::extension)
          )
          .apply(instance, EntityPredicate::new)
      )
  );

  public boolean test(ServerLevel world, Vec3 pos, Entity entity) {
    if (entity == null) return false;
    if (this.type.isPresent() && !this.type.get().matches(entity.getType())) return false;
    if (this.distance.isPresent() && (pos == null || !this.distance.get().matches(pos.x(), pos.y(), pos.z(), entity.getX(), entity.getY(), entity.getZ()))) return false;
    if (this.movement.isPresent()) {
      Vec3 vector = entity.getKnownMovement().scale(20.0F);
      if (!this.movement.get().matches(vector.x, vector.y, vector.z, entity.fallDistance)) return false;
    }
    if (this.location.located().isPresent() && !this.location.located().get().test(world, entity.getX(), entity.getY(), entity.getZ())) return false;
    if (this.location.eyeLevel().isPresent()) {
      Vec3 eyePos = entity.getEyePosition();
      if (!this.location.eyeLevel().get().test(world, eyePos.x(), eyePos.y(), eyePos.z())) return false;
    }
    if (this.location.steppingOn().isPresent()) {
      Vec3 steppingPos = Vec3.atCenterOf(entity.getOnPos());
      if (!entity.onGround() || !this.location.steppingOn().get().test(world, steppingPos.x(), steppingPos.y(), steppingPos.z())) return false;
    }
    if (this.location.affectsMovement().isPresent()) {
      Vec3 affectingPos = Vec3.atCenterOf(entity.getBlockPosBelowThatAffectsMyMovement());
      if (!entity.onGround() || !this.location.affectsMovement().get().test(world, affectingPos.x(), affectingPos.y(), affectingPos.z())) return false;
    }
    if (this.effects.isPresent() && !this.effects.get().matches(entity)) return false;
    if (this.nbt.isPresent() && !this.nbt.get().matches(entity)) return false;
    if (this.flags.isPresent() && !this.flags.get().matches(entity)) return false;
    if (this.equipment.isPresent() && !this.equipment.get().matches(entity)) return false;
    if (this.typeSpecific.isPresent() && !this.typeSpecific.get().matches(entity, world, pos)) return false;
    if (this.periodicTick.isPresent() && entity.tickCount % this.periodicTick.get() != 0) return false;
    if (this.vehicle.isPresent() && !this.vehicle.get().test(world, pos, entity.getVehicle())) return false;
    if (this.passenger.isPresent() && entity.getPassengers().stream().noneMatch(passengerEntity -> this.passenger.get().test(world, pos, passengerEntity))) return false;
    if (this.targetedEntity.isPresent() && !this.targetedEntity.get().test(world, pos, entity instanceof Mob mobEntity ? mobEntity.getTarget() : null)) return false;
    if (this.team.isPresent() && (entity.getTeam() == null || !this.team.get().equals(entity.getTeam().getName()))) return false;
    if (this.slots.isPresent() && !this.slots.get().matches(entity)) return false;
    if (!this.extension.components.test(entity)) return false;
    if (this.extension.checks.isPresent() && !this.extension.checks.get().test(entity, world, pos)) return false;
    if (this.extension.afflictions.isPresent() && !this.extension.afflictions.get().test(entity)) return false;
    if (this.extension.tags.isPresent()) {
      Set<String> entityTags = entity.getTags();
      for (List<String> tagList : this.extension.tags.get()) {
        if (Sets.intersection(entityTags, new HashSet<>(tagList)).isEmpty()) return false;
      }
    }
    return true;
  }

  public record EntityPredicateExtension(
      DataComponentMatchers components,
      Optional<EntityChecks> checks,
      Optional<AfflictionPredicate> afflictions,
      Optional<List<List<String>>> tags
  ) {

    public static final MapCodec<EntityPredicateExtension> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
        .group(
            DataComponentMatchers.CODEC.forGetter(EntityPredicateExtension::components),
            EntityChecks.CODEC.optionalFieldOf("checks").forGetter(EntityPredicateExtension::checks),
            AfflictionPredicate.CODEC.optionalFieldOf("afflictions").forGetter(EntityPredicateExtension::afflictions),
            Codec.STRING.listOf().listOf().optionalFieldOf("tags").forGetter(EntityPredicateExtension::tags)
        )
        .apply(instance, EntityPredicateExtension::new)
    );
  }
}
