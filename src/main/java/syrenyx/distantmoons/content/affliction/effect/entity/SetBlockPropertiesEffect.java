package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public record SetBlockPropertiesEffect(
    BlockItemStateProperties properties,
    Vec3i offset,
    Optional<Holder<GameEvent>> triggerGameEvent
) implements AfflictionEntityEffect {

  public static final MapCodec<SetBlockPropertiesEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          BlockItemStateProperties.CODEC.fieldOf("properties").forGetter(SetBlockPropertiesEffect::properties),
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
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    BlockPos blockPos = BlockPos.containing(pos).offset(this.offset);
    BlockState originalBlockState = target.level().getBlockState(blockPos);
    BlockState newBlockState = this.properties.apply(originalBlockState);
    if (originalBlockState != newBlockState && target.level().setBlock(blockPos, newBlockState, Block.UPDATE_ALL)) {
      this.triggerGameEvent.ifPresent(gameEvent -> world.gameEvent(target, gameEvent, blockPos));
    }
  }
}
