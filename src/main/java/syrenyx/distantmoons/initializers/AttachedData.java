package syrenyx.distantmoons.initializers;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.data.attachment.LivingEntityAttachment;
import syrenyx.distantmoons.data.attachment.PlayerEntityAttachment;

@SuppressWarnings("UnstableApiUsage")
public abstract class AttachedData {

  public static final AttachmentType<LivingEntityAttachment> LIVING_ENTITY_ATTACHMENT = register("living_entity_attachment", LivingEntityAttachment.CODEC);
  public static final AttachmentType<PlayerEntityAttachment> PLAYER_ENTITY_ATTACHMENT = register("player_entity_attachment", PlayerEntityAttachment.CODEC);

  private static <T> AttachmentType<T> register(String id, Codec<T> codec) {
    return AttachmentRegistry.create(UnderDistantMoons.identifierOf(id), builder -> builder.persistent(codec));
  }

  public static void initialize() {}
}
