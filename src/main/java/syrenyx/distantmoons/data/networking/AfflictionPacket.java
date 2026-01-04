package syrenyx.distantmoons.data.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;

public record AfflictionPacket(Identifier id, int stage, float progression) {
  public static final StreamCodec<RegistryFriendlyByteBuf, AfflictionPacket> PACKET_CODEC = StreamCodec.composite(
      Identifier.STREAM_CODEC, AfflictionPacket::id,
      ByteBufCodecs.INT, AfflictionPacket::stage,
      ByteBufCodecs.FLOAT, AfflictionPacket::progression,
      AfflictionPacket::new
  );

  public static AfflictionPacket fromInstance(AfflictionInstance instance) {
    return new AfflictionPacket(instance.affliction().unwrapKey().get().identifier(), instance.stage(), instance.progression());
  }
}
