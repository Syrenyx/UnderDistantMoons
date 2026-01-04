package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record PlaySoundEffect(
    Holder<SoundEvent> soundEvent,
    FloatProvider volume,
    FloatProvider pitch
) implements AfflictionEntityEffect {

  public static final MapCodec<PlaySoundEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          SoundEvent.CODEC.fieldOf("sound").forGetter(PlaySoundEffect::soundEvent),
          FloatProvider.codec(0.00001F, 10.0F).fieldOf("volume").forGetter(PlaySoundEffect::volume),
          FloatProvider.codec(0.00001F, 2.0F).fieldOf("pitch").forGetter(PlaySoundEffect::pitch)
      )
      .apply(instance, PlaySoundEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    if (target.isSilent()) return;
    RandomSource random = target.getRandom();
    world.playSound(null, pos.x(), pos.y(), pos.z(), this.soundEvent, target.getSoundSource(), this.volume.sample(random), this.pitch.sample(random));
  }
}
