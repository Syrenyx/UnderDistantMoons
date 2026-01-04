package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;

public record ReplaceBlockEffect(
    Vec3i offset,
    Optional<BlockPredicate> predicate,
    BlockStateProvider blockState,
    Optional<Holder<GameEvent>> triggerGameEvent
) implements AfflictionEntityEffect {

  public static final MapCodec<ReplaceBlockEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(ReplaceBlockEffect::offset),
          BlockPredicate.CODEC.optionalFieldOf("predicate").forGetter(ReplaceBlockEffect::predicate),
          BlockStateProvider.CODEC.fieldOf("block_state").forGetter(ReplaceBlockEffect::blockState),
          GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(ReplaceBlockEffect::triggerGameEvent)
      )
      .apply(instance, ReplaceBlockEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    BlockPos blockPos = BlockPos.containing(pos).offset(this.offset);
    if (
        this.predicate.map(predicate -> predicate.test(world, blockPos)).orElse(true)
            && world.setBlockAndUpdate(blockPos, this.blockState.getState(target.getRandom(), blockPos))
    ) this.triggerGameEvent.ifPresent(gameEvent -> world.gameEvent(target, gameEvent, blockPos));
  }
}
