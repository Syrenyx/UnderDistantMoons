package syrenyx.distantmoons.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.player.LocalPlayer;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.initializers.client.DistantMoonsAttachedData;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public record ClientPlayerAttachment(List<AfflictionInstance> activeAfflictions) {

  public static Codec<ClientPlayerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          AfflictionInstance.CODEC.listOf().fieldOf("active_afflictions").forGetter(ClientPlayerAttachment::activeAfflictions)
      )
      .apply(instance, ClientPlayerAttachment::new)
  );

  public static ClientPlayerAttachment getOrCreate(LocalPlayer player) {
    if (!player.hasAttached(DistantMoonsAttachedData.CLIENT_PLAYER_ATTACHMENT)) {
      player.setAttached(DistantMoonsAttachedData.CLIENT_PLAYER_ATTACHMENT, new ClientPlayerAttachment(
          new ArrayList<>()
      ));
    }
    return player.getAttached(DistantMoonsAttachedData.CLIENT_PLAYER_ATTACHMENT);
  }
}
