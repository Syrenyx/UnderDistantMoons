package syrenyx.distantmoons.initializers.client;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;

@SuppressWarnings("UnstableApiUsage")
public abstract class AttachedData {
  public static final AttachmentType<ClientPlayerAttachment> CLIENT_PLAYER_ATTACHMENT = register("client_player_attachment", ClientPlayerAttachment.CODEC);

  private static <T> AttachmentType<T> register(String id, Codec<T> codec) {
    return AttachmentRegistry.create(
        UnderDistantMoons.identifierOf(id),
        builder -> builder.persistent(codec)
    );
  }

  public static void initialize() {}
}
