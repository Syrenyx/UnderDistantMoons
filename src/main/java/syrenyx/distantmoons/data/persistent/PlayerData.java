package syrenyx.distantmoons.data.persistent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import syrenyx.distantmoons.data.attachment.LivingEntityAttachment;
import syrenyx.distantmoons.initializers.Items;

public record PlayerData(
    LivingEntityAttachment livingEntityAttachment,
    boolean heartSlotUnlocked,
    ItemStack heartItem
) {

  public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          LivingEntityAttachment.CODEC.optionalFieldOf("living_entity_attachment", new LivingEntityAttachment()).forGetter(PlayerData::livingEntityAttachment),
          Codec.BOOL.optionalFieldOf("heart_slot_unlocked", false).forGetter(PlayerData::heartSlotUnlocked),
          ItemStack.CODEC.optionalFieldOf("heart_item", Items.MORTAL_HEART.getDefaultStack()).forGetter(PlayerData::heartItem)
      )
      .apply(instance, PlayerData::new)
  );

  public PlayerData() {
    this(
        new LivingEntityAttachment(),
        false,
        Items.MORTAL_HEART.getDefaultStack()
    );
  }
}
