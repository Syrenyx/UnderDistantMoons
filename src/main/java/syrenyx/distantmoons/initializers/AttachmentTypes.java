package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.attached_data.ActiveAfflictionsAttachment;

public class AttachmentTypes {

  public static AttachmentType<ActiveAfflictionsAttachment> ACTIVE_AFFLICTIONS = AttachmentRegistry.create(
      UnderDistantMoons.identifierOf("active_afflictions"),
      builder -> builder
          .initializer(() -> ActiveAfflictionsAttachment.DEFAULT)
          .persistent(ActiveAfflictionsAttachment.CODEC)
          .syncWith(ActiveAfflictionsAttachment.PACKET_CODEC, AttachmentSyncPredicate.all())
  );

  public static void initialize() {}
}
