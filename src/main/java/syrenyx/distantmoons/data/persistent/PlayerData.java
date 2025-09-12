package syrenyx.distantmoons.data.persistent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import syrenyx.distantmoons.data.attachment.LivingEntityAttachment;

public record PlayerData(
    LivingEntityAttachment livingEntityAttachment
) {

  public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          LivingEntityAttachment.CODEC.optionalFieldOf("living_entity_attachment", new LivingEntityAttachment()).forGetter(PlayerData::livingEntityAttachment)
      )
      .apply(instance, PlayerData::new)
  );

  public PlayerData() {
    this(new LivingEntityAttachment());
  }
}
