package syrenyx.distantmoons.predicate.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.entity.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public record EntityPredicate(
    Optional<EntityTypePredicate> type,
    Optional<DistancePredicate> distance,
    Optional<MovementPredicate> movement,
    PositionalEntityPredicates location,
    Optional<EntityEffectPredicate> effects,
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
              EntityEffectPredicate.CODEC.optionalFieldOf("effects").forGetter(EntityPredicate::effects),
              NbtPredicate.CODEC.optionalFieldOf("nbt").forGetter(EntityPredicate::nbt),
              EntityFlagsPredicate.CODEC.optionalFieldOf("flags").forGetter(EntityPredicate::flags),
              EntityEquipmentPredicate.CODEC.optionalFieldOf("equipment").forGetter(EntityPredicate::equipment),
              EntitySubPredicate.CODEC.optionalFieldOf("type_specific").forGetter(EntityPredicate::typeSpecific),
              Codecs.POSITIVE_INT.optionalFieldOf("periodic_tick").forGetter(EntityPredicate::periodicTick),
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

  public boolean test(ServerWorld world, Vec3d pos, Entity entity) {
    if (entity == null) return false;
    if (this.type.isPresent() && !this.type.get().matches(entity.getType())) return false;
    if (this.distance.isPresent() && (pos == null || !this.distance.get().test(pos.getX(), pos.getY(), pos.getZ(), entity.getX(), entity.getY(), entity.getZ()))) return false;
    if (this.movement.isPresent()) {
      Vec3d vector = entity.getMovement().multiply(20.0F);
      if (!this.movement.get().test(vector.x, vector.y, vector.z, entity.fallDistance)) return false;
    }
    if (this.location.located().isPresent() && !this.location.located().get().test(world, entity.getX(), entity.getY(), entity.getZ())) return false;
    if (this.location.eyeLevel().isPresent()) {
      Vec3d eyePos = entity.getEyePos();
      if (!this.location.eyeLevel().get().test(world, eyePos.getX(), eyePos.getY(), eyePos.getZ())) return false;
    }
    if (this.location.steppingOn().isPresent()) {
      Vec3d steppingPos = Vec3d.ofCenter(entity.getSteppingPos());
      if (!entity.isOnGround() || !this.location.steppingOn().get().test(world, steppingPos.getX(), steppingPos.getY(), steppingPos.getZ())) return false;
    }
    if (this.location.affectsMovement().isPresent()) {
      Vec3d affectingPos = Vec3d.ofCenter(entity.getVelocityAffectingPos());
      if (!entity.isOnGround() || !this.location.affectsMovement().get().test(world, affectingPos.getX(), affectingPos.getY(), affectingPos.getZ())) return false;
    }
    if (this.effects.isPresent() && !this.effects.get().test(entity)) return false;
    if (this.nbt.isPresent() && !this.nbt.get().test(entity)) return false;
    if (this.flags.isPresent() && !this.flags.get().test(entity)) return false;
    if (this.equipment.isPresent() && !this.equipment.get().test(entity)) return false;
    if (this.typeSpecific.isPresent() && !this.typeSpecific.get().test(entity, world, pos)) return false;
    if (this.periodicTick.isPresent() && entity.age % this.periodicTick.get() != 0) return false;
    if (this.vehicle.isPresent() && !this.vehicle.get().test(world, pos, entity.getVehicle())) return false;
    if (this.passenger.isPresent() && entity.getPassengerList().stream().noneMatch(passengerEntity -> this.passenger.get().test(world, pos, passengerEntity))) return false;
    if (this.targetedEntity.isPresent() && !this.targetedEntity.get().test(world, pos, entity instanceof MobEntity mobEntity ? mobEntity.getTarget() : null)) return false;
    if (this.team.isPresent() && (entity.getScoreboardTeam() == null || !this.team.get().equals(entity.getScoreboardTeam().getName()))) return false;
    if (this.slots.isPresent() && !this.slots.get().matches(entity)) return false;
    if (!this.extension.components.test(entity)) return false;
    if (this.extension.checks.isPresent() && !this.extension.checks.get().test(entity, world, pos)) return false;
    if (this.extension.afflictions.isPresent() && !this.extension.afflictions.get().test(entity)) return false;
    return true;
  }

  public record EntityPredicateExtension(
      ComponentsPredicate components,
      Optional<EntityChecks> checks,
      Optional<AfflictionPredicate> afflictions
  ) {

    public static final MapCodec<EntityPredicateExtension> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
        .group(
            ComponentsPredicate.CODEC.forGetter(EntityPredicateExtension::components),
            EntityChecks.CODEC.optionalFieldOf("checks").forGetter(EntityPredicateExtension::checks),
            AfflictionPredicate.CODEC.optionalFieldOf("afflictions").forGetter(EntityPredicateExtension::afflictions)
        )
        .apply(instance, EntityPredicateExtension::new)
    );
  }
}
