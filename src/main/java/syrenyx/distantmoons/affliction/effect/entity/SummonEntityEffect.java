package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public record SummonEntityEffect(
    RegistryEntryList<EntityType<?>> entityTypes,
    boolean joinTeam
) implements AfflictionEntityEffect {

  public static final MapCodec<SummonEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.entryList(RegistryKeys.ENTITY_TYPE).fieldOf("entity").forGetter(SummonEntityEffect::entityTypes),
          Codec.BOOL.optionalFieldOf("join_team", false).forGetter(SummonEntityEffect::joinTeam)
      )
      .apply(instance, SummonEntityEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    BlockPos blockPos = BlockPos.ofFloored(pos);
    if (!World.isValid(blockPos)) return;
    Optional<RegistryEntry<EntityType<?>>> optional = this.entityTypes().getRandom(world.getRandom());
    if (optional.isEmpty()) return;
    Entity entity = optional.get().value().spawn(world, blockPos, SpawnReason.TRIGGERED);
    if (entity == null) return;
    if (entity instanceof LightningEntity lightningEntity && target instanceof ServerPlayerEntity serverPlayerEntity) lightningEntity.setChanneler(serverPlayerEntity);
    if (this.joinTeam && target.getScoreboardTeam() != null) world.getScoreboard().addScoreHolderToTeam(entity.getNameForScoreboard(), target.getScoreboardTeam());
    entity.refreshPositionAndAngles(pos.x, pos.y, pos.z, entity.getYaw(), entity.getPitch());
  }
}
