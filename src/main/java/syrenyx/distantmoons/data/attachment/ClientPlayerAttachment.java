package syrenyx.distantmoons.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.initializers.AttachedData;
import syrenyx.distantmoons.initializers.Items;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public record ClientPlayerAttachment(
    List<AfflictionInstance> activeAfflictions,
    boolean heartSlotUnlocked,
    ItemStack heartItem
) {

  public static Codec<ClientPlayerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          AfflictionInstance.CODEC.listOf().optionalFieldOf("active_afflictions", new ArrayList<>()).forGetter(ClientPlayerAttachment::activeAfflictions),
          Codec.BOOL.optionalFieldOf("heart_slot_unlocked", false).forGetter(ClientPlayerAttachment::heartSlotUnlocked),
          ItemStack.CODEC.optionalFieldOf("heart_item", Items.MORTAL_HEART.getDefaultStack()).forGetter(ClientPlayerAttachment::heartItem)
      )
      .apply(instance, ClientPlayerAttachment::new)
  );

  public ClientPlayerAttachment() {
    this(
        new ArrayList<>(),
        false,
        Items.MORTAL_HEART.getDefaultStack()
    );
  }

  public static ClientPlayerAttachment getOrCreate(PlayerEntity player) {
    if (!player.hasAttached(AttachedData.CLIENT_PLAYER_ATTACHMENT)) {
      player.setAttached(AttachedData.CLIENT_PLAYER_ATTACHMENT, new ClientPlayerAttachment());
    }
    return player.getAttached(AttachedData.CLIENT_PLAYER_ATTACHMENT);
  }
}
