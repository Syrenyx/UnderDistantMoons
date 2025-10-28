package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;

public record ReplaceDiskEffect(
    EnchantmentLevelBasedValue radius,
    EnchantmentLevelBasedValue height,
    Vec3i offset,
    Optional<BlockPredicate> predicate,
    BlockStateProvider blockState,
    Optional<RegistryEntry<GameEvent>> triggerGameEvent
) implements AfflictionEntityEffect {

  public static final MapCodec<ReplaceDiskEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          EnchantmentLevelBasedValue.CODEC.fieldOf("radius").forGetter(ReplaceDiskEffect::radius),
          EnchantmentLevelBasedValue.CODEC.fieldOf("height").forGetter(ReplaceDiskEffect::height),
          Vec3i.CODEC.optionalFieldOf("offset", Vec3i.ZERO).forGetter(ReplaceDiskEffect::offset),
          BlockPredicate.BASE_CODEC.optionalFieldOf("predicate").forGetter(ReplaceDiskEffect::predicate),
          BlockStateProvider.TYPE_CODEC.fieldOf("block_state").forGetter(ReplaceDiskEffect::blockState),
          GameEvent.CODEC.optionalFieldOf("trigger_game_event").forGetter(ReplaceDiskEffect::triggerGameEvent)
      )
      .apply(instance, ReplaceDiskEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    BlockPos blockPos = BlockPos.ofFloored(pos).add(this.offset);
    Random random = target.getRandom();
    int radius = (int) this.radius.getValue(stage);
    int height = (int) this.height.getValue(stage);
    for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-radius, 0, -radius), blockPos.add(radius, Math.min(height - 1, 0), radius))) {
      if (
          blockPos2.getSquaredDistanceFromCenter(pos.getX(), blockPos2.getY() + 0.5, pos.getZ()) < MathHelper.square(radius)
              && this.predicate.map(predicate -> predicate.test(world, blockPos2)).orElse(true)
              && world.setBlockState(blockPos2, this.blockState.get(random, blockPos2))
      ) this.triggerGameEvent.ifPresent(gameEvent -> world.emitGameEvent(target, gameEvent, blockPos2));
    }
  }
}
