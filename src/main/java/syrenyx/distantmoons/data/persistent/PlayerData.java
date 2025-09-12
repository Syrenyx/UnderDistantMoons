package syrenyx.distantmoons.data.persistent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.data.attachment.LivingEntityAttachment;
import syrenyx.distantmoons.data.attachment.PlayerEntityAttachment;

public record PlayerData(
    LivingEntityAttachment livingEntityAttachment,
    PlayerEntityAttachment playerEntityAttachment
) {

  public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          LivingEntityAttachment.CODEC.optionalFieldOf("living_entity_attachment", new LivingEntityAttachment()).forGetter(PlayerData::livingEntityAttachment),
          PlayerEntityAttachment.CODEC.optionalFieldOf("player_entity_attachment", new PlayerEntityAttachment()).forGetter(PlayerData::playerEntityAttachment)
      )
      .apply(instance, PlayerData::new)
  );

  public PlayerData() {
    this(new LivingEntityAttachment(), new PlayerEntityAttachment());
  }
}
