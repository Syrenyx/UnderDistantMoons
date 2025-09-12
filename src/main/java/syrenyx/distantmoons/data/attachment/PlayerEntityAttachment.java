package syrenyx.distantmoons.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import syrenyx.distantmoons.initializers.AttachedData;

@SuppressWarnings("UnstableApiUsage")
public record PlayerEntityAttachment(
    boolean heartSlotUnlocked
) {

  public static final Codec<PlayerEntityAttachment> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.BOOL.optionalFieldOf("heart_slot_unlocked", false).forGetter(PlayerEntityAttachment::heartSlotUnlocked)
      )
      .apply(instance, PlayerEntityAttachment::new)
  );

  public PlayerEntityAttachment() {
    this(false);
  }

  public static PlayerEntityAttachment getOrCreate(PlayerEntity entity) {
    if (!entity.hasAttached(AttachedData.PLAYER_ENTITY_ATTACHMENT)) entity.setAttached(AttachedData.PLAYER_ENTITY_ATTACHMENT, new PlayerEntityAttachment());
    return entity.getAttached(AttachedData.PLAYER_ENTITY_ATTACHMENT);
  }
}
