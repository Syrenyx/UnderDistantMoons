package syrenyx.distantmoons.initializers.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceKey;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;
import syrenyx.distantmoons.data.networking.ActiveAfflictionsPayload;
import syrenyx.distantmoons.references.DistantMoonsRegistryKeys;

import java.util.List;

public abstract class DistantMoonsNetworking {

  static {
    ClientPlayNetworking.registerGlobalReceiver(
        ActiveAfflictionsPayload.ID,
        (payload, context) -> context.client().execute(
            () -> {
              List<AfflictionInstance> currentAfflictions = ClientPlayerAttachment.getOrCreate(context.player()).activeAfflictions();
              currentAfflictions.clear();
              currentAfflictions.addAll(payload.afflictions().stream().map(packet ->
                  new AfflictionInstance(
                      context.player().registryAccess().getOrThrow(ResourceKey.create(DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY, packet.id())),
                      packet.stage(),
                      packet.progression()
                  )
              ).toList());
            }
        )
    );
  }

  public static void initialize() {}
}
