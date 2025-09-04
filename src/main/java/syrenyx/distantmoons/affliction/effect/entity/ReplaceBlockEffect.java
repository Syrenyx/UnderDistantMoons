package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

import java.util.Optional;

public record ReplaceBlockEffect(
    Vec3i offset,
    Optional<BlockPredicate> predicate,
    BlockStateProvider blockState,
    Optional<RegistryEntry<GameEvent>> triggerGameEvent
) implements AfflictionEntityEffect {

  public static final MapCodec<ReplaceBlockEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(ReplaceBlockEffect::offset),
          BlockPredicate.BASE_CODEC.optionalFieldOf("predicate").forGetter(ReplaceBlockEffect::predicate),
          BlockStateProvider.TYPE_CODEC.fieldOf("block_state").forGetter(ReplaceBlockEffect::blockState),
          GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(ReplaceBlockEffect::triggerGameEvent)
      )
      .apply(instance, ReplaceBlockEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    BlockPos blockPos = BlockPos.ofFloored(pos).add(this.offset);
    if (
        this.predicate.map(predicate -> predicate.test(world, blockPos)).orElse(true)
            && world.setBlockState(blockPos, this.blockState.get(target.getRandom(), blockPos))
    ) this.triggerGameEvent.ifPresent(gameEvent -> world.emitGameEvent(target, gameEvent, blockPos));
  }
}
