package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record SummonEntityEffect(
    HolderSet<EntityType<?>> entityTypes,
    boolean joinTeam
) implements AfflictionEntityEffect {

  public static final MapCodec<SummonEntityEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("entity").forGetter(SummonEntityEffect::entityTypes),
          Codec.BOOL.optionalFieldOf("join_team", false).forGetter(SummonEntityEffect::joinTeam)
      )
      .apply(instance, SummonEntityEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    BlockPos blockPos = BlockPos.containing(pos);
    if (!Level.isInSpawnableBounds(blockPos)) return;
    Optional<Holder<EntityType<?>>> optional = this.entityTypes().getRandomElement(world.getRandom());
    if (optional.isEmpty()) return;
    Entity entity = optional.get().value().spawn(world, blockPos, EntitySpawnReason.TRIGGERED);
    if (entity == null) return;
    if (entity instanceof LightningBolt lightningEntity && target instanceof ServerPlayer serverPlayerEntity) lightningEntity.setCause(serverPlayerEntity);
    if (this.joinTeam && target.getTeam() != null) world.getScoreboard().addPlayerToTeam(entity.getScoreboardName(), target.getTeam());
    entity.snapTo(pos.x, pos.y, pos.z, entity.getYRot(), entity.getXRot());
  }
}
