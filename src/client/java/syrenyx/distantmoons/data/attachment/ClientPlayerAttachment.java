package syrenyx.distantmoons.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.network.ClientPlayerEntity;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.initializers.client.AttachedData;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public record ClientPlayerAttachment(
    List<AfflictionInstance> activeAfflictions,
    boolean heartSlotUnlocked
) {

  public static Codec<ClientPlayerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          AfflictionInstance.CODEC.listOf().optionalFieldOf("active_afflictions", new ArrayList<>()).forGetter(ClientPlayerAttachment::activeAfflictions),
          Codec.BOOL.optionalFieldOf("heart_slot_unlocked", false).forGetter(ClientPlayerAttachment::heartSlotUnlocked)
      )
      .apply(instance, ClientPlayerAttachment::new)
  );

  public ClientPlayerAttachment() {
    this(new ArrayList<>(), false);
  }

  public static ClientPlayerAttachment getOrCreate(ClientPlayerEntity player) {
    if (!player.hasAttached(AttachedData.CLIENT_PLAYER_ATTACHMENT)) {
      player.setAttached(AttachedData.CLIENT_PLAYER_ATTACHMENT, new ClientPlayerAttachment());
    }
    return player.getAttached(AttachedData.CLIENT_PLAYER_ATTACHMENT);
  }
}
