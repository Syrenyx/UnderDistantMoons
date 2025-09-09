package syrenyx.distantmoons.predicate.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

public record EntityChecks(
    boolean undeadExposedToDaylightCheck
) {

  public static final Codec<EntityChecks> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.BOOL.optionalFieldOf("undead_exposed_to_daylight_check", false).forGetter(EntityChecks::undeadExposedToDaylightCheck)
      )
      .apply(instance, EntityChecks::new)
  );

  public boolean test(Entity entity, ServerWorld world, Vec3d pos) {
    if (undeadExposedToDaylightCheck) {
      if (!world.isDay() || entity.isTouchingWaterOrRain() || entity.inPowderSnow || entity.wasInPowderSnow || !world.isSkyVisible(BlockPos.ofFloored(pos))) return false;
      int brightness = world.getLightLevel(LightType.SKY, BlockPos.ofFloored(entity.getEyePos()));
      if (brightness < 8 || (brightness - 6) * 2 < entity.getRandom().nextFloat() * 450) return false;
    }
    return true;
  }
}
