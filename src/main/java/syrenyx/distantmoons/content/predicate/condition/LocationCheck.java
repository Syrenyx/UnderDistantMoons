package syrenyx.distantmoons.content.predicate.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextParameter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import syrenyx.distantmoons.initializers.DistantMoonsLootConditions;
import syrenyx.distantmoons.content.predicate.location.LocationPredicate;

import java.util.Optional;
import java.util.Set;

public record LocationCheck(
    Optional<LocationPredicate> predicate,
    BlockPos offset
) implements LootCondition {

  private static final MapCodec<BlockPos> OFFSET_CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Codec.INT.optionalFieldOf("offsetX", 0).forGetter(Vec3i::getX),
          Codec.INT.optionalFieldOf("offsetY", 0).forGetter(Vec3i::getY),
          Codec.INT.optionalFieldOf("offsetZ", 0).forGetter(Vec3i::getZ)
      )
      .apply(instance, BlockPos::new)
  );
  public static final MapCodec<LocationCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LocationPredicate.CODEC.optionalFieldOf("predicate").forGetter(LocationCheck::predicate),
          OFFSET_CODEC.forGetter(LocationCheck::offset)
      )
      .apply(instance, LocationCheck::new)
  );

  @Override
  public LootConditionType getType() {
    return DistantMoonsLootConditions.LOCATION_CHECK;
  }

  @Override
  public Set<ContextParameter<?>> getAllowedParameters() {
    return Set.of(LootContextParameters.ORIGIN);
  }

  @Override
  public boolean test(LootContext context) {
    Vec3d location = context.get(LootContextParameters.ORIGIN);
    return location != null && (
        this.predicate.isEmpty() || this.predicate.get().test(
            context.getWorld(),
            location.getX() + this.offset.getX(),
            location.getY() + this.offset.getY(),
            location.getZ() + this.offset.getZ()
        )
    );
  }
}
