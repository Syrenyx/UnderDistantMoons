package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.random.Random;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;

public record PlaySoundEffect(
    RegistryEntry<SoundEvent> soundEvent,
    FloatProvider volume,
    FloatProvider pitch
) implements AfflictionEntityEffect {

  public static final MapCodec<PlaySoundEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          SoundEvent.ENTRY_CODEC.fieldOf("sound").forGetter(PlaySoundEffect::soundEvent),
          FloatProvider.createValidatedCodec(0.00001F, 10.0F).fieldOf("volume").forGetter(PlaySoundEffect::volume),
          FloatProvider.createValidatedCodec(0.00001F, 2.0F).fieldOf("pitch").forGetter(PlaySoundEffect::pitch)
      )
      .apply(instance, PlaySoundEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    if (target.isSilent()) return;
    Random random = target.getRandom();
    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), this.soundEvent, target.getSoundCategory(), this.volume.get(random), this.pitch.get(random));
  }
}
