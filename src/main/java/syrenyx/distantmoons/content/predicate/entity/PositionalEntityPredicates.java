package syrenyx.distantmoons.content.predicate.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.content.predicate.location.LocationPredicate;

import java.util.Optional;

public record PositionalEntityPredicates(
    Optional<LocationPredicate> located,
    Optional<LocationPredicate> eyeLevel,
    Optional<LocationPredicate> steppingOn,
    Optional<LocationPredicate> affectsMovement
) {

  public static final MapCodec<PositionalEntityPredicates> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LocationPredicate.CODEC.optionalFieldOf("location").forGetter(PositionalEntityPredicates::located),
          LocationPredicate.CODEC.optionalFieldOf("eye_level").forGetter(PositionalEntityPredicates::eyeLevel),
          LocationPredicate.CODEC.optionalFieldOf("stepping_on").forGetter(PositionalEntityPredicates::steppingOn),
          LocationPredicate.CODEC.optionalFieldOf("movement_affected_by").forGetter(PositionalEntityPredicates::affectsMovement)
      )
      .apply(instance, PositionalEntityPredicates::new)
  );
}
