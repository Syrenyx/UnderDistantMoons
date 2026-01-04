package syrenyx.distantmoons.content.predicate.location;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;

public record LightPredicate(
    Optional<MinMaxBounds.Ints> blockLight,
    Optional<MinMaxBounds.Ints> skyLight,
    Optional<MinMaxBounds.Ints> timeAdjustedSkyLight,
    Optional<MinMaxBounds.Ints> visibleLight
) {

  public static final Codec<LightPredicate> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          MinMaxBounds.Ints.CODEC.optionalFieldOf("block_light").forGetter(LightPredicate::blockLight),
          MinMaxBounds.Ints.CODEC.optionalFieldOf("sky_light").forGetter(LightPredicate::skyLight),
          MinMaxBounds.Ints.CODEC.optionalFieldOf("time_adjusted_sky_light").forGetter(LightPredicate::timeAdjustedSkyLight),
          MinMaxBounds.Ints.CODEC.optionalFieldOf("visible_light").forGetter(LightPredicate::visibleLight)
      )
      .apply(instance, LightPredicate::new)
  );

  public boolean test(ServerLevel world, BlockPos blockPos) {
    if (!world.isLoaded(blockPos)) return false;
    if (this.blockLight.isPresent() && !this.blockLight.get().matches(world.getBrightness(LightLayer.BLOCK, blockPos))) return false;
    if (this.skyLight.isPresent() && !this.skyLight.get().matches(world.getBrightness(LightLayer.SKY, blockPos))) return false;
    if (this.timeAdjustedSkyLight.isPresent() && !this.timeAdjustedSkyLight.get().matches(world.getMaxLocalRawBrightness(blockPos, world.getSkyDarken()))) return false;
    if (this.visibleLight.isPresent() && !this.visibleLight.get().matches(world.getMaxLocalRawBrightness(blockPos))) return false;
    return true;
  }
}
