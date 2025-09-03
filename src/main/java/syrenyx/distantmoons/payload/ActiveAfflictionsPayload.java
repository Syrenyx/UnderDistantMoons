package syrenyx.distantmoons.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.AfflictionPacket;

import java.util.List;

public record ActiveAfflictionsPayload(List<AfflictionPacket> afflictions) implements CustomPayload {

  public static final CustomPayload.Id<ActiveAfflictionsPayload> ID = new CustomPayload.Id<>(UnderDistantMoons.identifierOf("active_afflictions_payload"));
  public static final PacketCodec<RegistryByteBuf, ActiveAfflictionsPayload> CODEC = PacketCodec.tuple(
      AfflictionPacket.PACKET_CODEC.collect(PacketCodecs.toList()), ActiveAfflictionsPayload::afflictions,
      ActiveAfflictionsPayload::new
  );

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }
}
