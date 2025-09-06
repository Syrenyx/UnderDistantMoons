package syrenyx.distantmoons.predicate.location;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import java.util.Optional;

public record LightPredicate(
    Optional<NumberRange.IntRange> blockLight,
    Optional<NumberRange.IntRange> skyLight,
    Optional<NumberRange.IntRange> timeAdjustedSkyLight,
    Optional<NumberRange.IntRange> visibleLight,
    Optional<Unit> affectedByDaylight
) {

  public static final Codec<LightPredicate> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          NumberRange.IntRange.CODEC.optionalFieldOf("block_light").forGetter(LightPredicate::blockLight),
          NumberRange.IntRange.CODEC.optionalFieldOf("sky_light").forGetter(LightPredicate::skyLight),
          NumberRange.IntRange.CODEC.optionalFieldOf("time_adjusted_sky_light").forGetter(LightPredicate::timeAdjustedSkyLight),
          NumberRange.IntRange.CODEC.optionalFieldOf("visible_light").forGetter(LightPredicate::visibleLight),
          Unit.CODEC.optionalFieldOf("affected_by_daylight").forGetter(LightPredicate::affectedByDaylight)
      )
      .apply(instance, LightPredicate::new)
  );

  public boolean test(ServerWorld world, BlockPos blockPos) {
    if (!world.isPosLoaded(blockPos)) return false;
    if (this.blockLight.isPresent() && !this.blockLight.get().test(world.getLightLevel(LightType.BLOCK, blockPos))) return false;
    if (this.skyLight.isPresent() && !this.skyLight.get().test(world.getLightLevel(LightType.SKY, blockPos))) return false;
    if (this.timeAdjustedSkyLight.isPresent() && !this.timeAdjustedSkyLight.get().test(world.getLightLevel(blockPos, world.getAmbientDarkness()))) return false;
    if (this.visibleLight.isPresent() && !this.visibleLight.get().test(world.getLightLevel(blockPos))) return false;
    return true;
  }
}
