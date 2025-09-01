package syrenyx.distantmoons.attached_data;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionInstance;

import java.util.Map;

public record ActiveAfflictionsAttachment(
    Map<RegistryEntry<Affliction>, AfflictionInstance> activeAfflictions
) {

  public static Codec<ActiveAfflictionsAttachment> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.unboundedMap(Affliction.FIXED_ENTRY_CODEC, AfflictionInstance.CODEC).fieldOf("activeAfflictions").forGetter(ActiveAfflictionsAttachment::activeAfflictions)
      )
      .apply(instance, ActiveAfflictionsAttachment::new)
  );
  public static PacketCodec<ByteBuf, ActiveAfflictionsAttachment> PACKET_CODEC = PacketCodecs.codec(CODEC);
  public static ActiveAfflictionsAttachment DEFAULT = new ActiveAfflictionsAttachment(Maps.newHashMap());
}
