package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

import java.util.Optional;

public record ChangeAfflictionProgressionEffect(
    RegistryEntry<Affliction> affliction,
    Optional<Integer> stage,
    Optional<Float> progression
) implements AfflictionEntityEffect {

  public static final MapCodec<ChangeAfflictionProgressionEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Affliction.ENTRY_CODEC.fieldOf("affliction").forGetter(ChangeAfflictionProgressionEffect::affliction),
          Codecs.rangedInt(-Affliction.MAX_STAGE, Affliction.MAX_STAGE).optionalFieldOf("stage").forGetter(ChangeAfflictionProgressionEffect::stage),
          Codecs.rangedInclusiveFloat(-Affliction.MAX_PROGRESSION_LIMIT, Affliction.MAX_PROGRESSION_LIMIT).optionalFieldOf("progression").forGetter(ChangeAfflictionProgressionEffect::progression)
      )
      .apply(instance, ChangeAfflictionProgressionEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, Entity target, Vec3d pos, boolean newlyApplied) {
    //TODO: target.changeAfflictionProgress();
  }

  @Override
  public void apply(ServerWorld world, Entity target, Vec3d pos) {
    //TODO: target.changeAfflictionProgress();
  }
}
