package syrenyx.distantmoons.data.networking;

import syrenyx.distantmoons.UnderDistantMoons;

import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ActiveAfflictionsPayload(List<AfflictionPacket> afflictions) implements CustomPacketPayload {

  public static final Type<ActiveAfflictionsPayload> ID = new Type<>(UnderDistantMoons.identifierOf("active_afflictions_payload"));
  public static final StreamCodec<RegistryFriendlyByteBuf, ActiveAfflictionsPayload> CODEC = StreamCodec.composite(
      AfflictionPacket.PACKET_CODEC.apply(ByteBufCodecs.list()), ActiveAfflictionsPayload::afflictions,
      ActiveAfflictionsPayload::new
  );

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return ID;
  }
}
