package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;

public record ReplaceDiskEffect(
    LevelBasedValue radius,
    LevelBasedValue height,
    Vec3i offset,
    Optional<BlockPredicate> predicate,
    BlockStateProvider blockState,
    Optional<Holder<GameEvent>> triggerGameEvent
) implements AfflictionEntityEffect {

  public static final MapCodec<ReplaceDiskEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          LevelBasedValue.CODEC.fieldOf("radius").forGetter(ReplaceDiskEffect::radius),
          LevelBasedValue.CODEC.fieldOf("height").forGetter(ReplaceDiskEffect::height),
          Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(ReplaceDiskEffect::offset),
          BlockPredicate.CODEC.optionalFieldOf("predicate").forGetter(ReplaceDiskEffect::predicate),
          BlockStateProvider.CODEC.fieldOf("block_state").forGetter(ReplaceDiskEffect::blockState),
          GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(ReplaceDiskEffect::triggerGameEvent)
      )
      .apply(instance, ReplaceDiskEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    BlockPos blockPos = BlockPos.containing(pos).offset(this.offset);
    RandomSource random = target.getRandom();
    int radius = (int) this.radius.calculate(stage);
    int height = (int) this.height.calculate(stage);
    for (BlockPos blockPos2 : BlockPos.betweenClosed(blockPos.offset(-radius, 0, -radius), blockPos.offset(radius, Math.min(height - 1, 0), radius))) {
      if (
          blockPos2.distToCenterSqr(pos.x(), blockPos2.getY() + 0.5, pos.z()) < Mth.square(radius)
              && this.predicate.map(predicate -> predicate.test(world, blockPos2)).orElse(true)
              && world.setBlockAndUpdate(blockPos2, this.blockState.getState(random, blockPos2))
      ) this.triggerGameEvent.ifPresent(gameEvent -> world.gameEvent(target, gameEvent, blockPos2));
    }
  }
}
