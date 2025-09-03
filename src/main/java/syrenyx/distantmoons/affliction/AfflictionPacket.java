package syrenyx.distantmoons.affliction;


import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public record AfflictionPacket(Identifier id, int stage, float progression) {
  public static final PacketCodec<RegistryByteBuf, AfflictionPacket> PACKET_CODEC = PacketCodec.tuple(
      Identifier.PACKET_CODEC, AfflictionPacket::id,
      PacketCodecs.INTEGER, AfflictionPacket::stage,
      PacketCodecs.FLOAT, AfflictionPacket::progression,
      AfflictionPacket::new
  );

  public static AfflictionPacket fromInstance(AfflictionInstance instance) {
    return new AfflictionPacket(instance.affliction().getKey().get().getValue(), instance.stage(), instance.progression());
  }
}
