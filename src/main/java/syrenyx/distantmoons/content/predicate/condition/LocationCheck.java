package syrenyx.distantmoons.content.predicate.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.initializers.DistantMoonsLootConditions;
import syrenyx.distantmoons.content.predicate.location.LocationPredicate;

import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

public record LocationCheck(
    Optional<LocationPredicate> predicate,
    BlockPos offset
) implements LootItemCondition {

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
  public LootItemConditionType getType() {
    return DistantMoonsLootConditions.LOCATION_CHECK;
  }

  @Override
  public Set<ContextKey<?>> getReferencedContextParams() {
    return Set.of(LootContextParams.ORIGIN);
  }

  @Override
  public boolean test(LootContext context) {
    Vec3 location = context.getOptionalParameter(LootContextParams.ORIGIN);
    return location != null && (
        this.predicate.isEmpty() || this.predicate.get().test(
            context.getLevel(),
            location.x() + this.offset.getX(),
            location.y() + this.offset.getY(),
            location.z() + this.offset.getZ()
        )
    );
  }
}
