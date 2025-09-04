package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.entity.Entity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.event.GameEvent;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

import java.util.Optional;

public record SetBlockPropertiesEffect(
    BlockStateComponent properties,
    Vec3i offset,
    Optional<RegistryEntry<GameEvent>> triggerGameEvent
) implements AfflictionEntityEffect {

  public static final MapCodec<SetBlockPropertiesEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          BlockStateComponent.CODEC.fieldOf("properties").forGetter(SetBlockPropertiesEffect::properties),
          Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(SetBlockPropertiesEffect::offset),
          GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(SetBlockPropertiesEffect::triggerGameEvent)
      )
      .apply(instance, SetBlockPropertiesEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    BlockPos blockPos = BlockPos.ofFloored(pos).add(this.offset);
    BlockState originalBlockState = target.getWorld().getBlockState(blockPos);
    BlockState newBlockState = this.properties.applyToState(originalBlockState);
    if (originalBlockState != newBlockState && target.getWorld().setBlockState(blockPos, newBlockState, Block.NOTIFY_ALL)) {
      this.triggerGameEvent.ifPresent(gameEvent -> world.emitGameEvent(target, gameEvent, blockPos));
    }
  }
}
