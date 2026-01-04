package syrenyx.distantmoons.content.predicate.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public record EntityChecks(
    boolean undeadExposedToDaylightCheck
) {

  public static final Codec<EntityChecks> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.BOOL.optionalFieldOf("undead_exposed_to_daylight_check", false).forGetter(EntityChecks::undeadExposedToDaylightCheck)
      )
      .apply(instance, EntityChecks::new)
  );

  public boolean test(Entity entity, ServerLevel world, Vec3 pos) {
    if (undeadExposedToDaylightCheck) {
      if (!world.isBrightOutside() || entity.isInWaterOrRain() || entity.isInPowderSnow || entity.wasInPowderSnow || !world.canSeeSky(BlockPos.containing(pos))) return false;
      int brightness = world.getBrightness(LightLayer.SKY, BlockPos.containing(entity.getEyePosition()));
      if (brightness < 8 || (brightness - 6) * 2 < entity.getRandom().nextFloat() * 450) return false;
    }
    return true;
  }
}
