package syrenyx.distantmoons.data.persistent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import syrenyx.distantmoons.UnderDistantMoons;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersistentStateManager extends PersistentState {

  public static final Codec<PersistentStateManager> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.unboundedMap(Uuids.CODEC, PlayerData.CODEC).fieldOf("players").forGetter(PersistentStateManager::players)
      )
      .apply(instance, PersistentStateManager::new)
  );
  private static final PersistentStateType<PersistentStateManager> TYPE = new PersistentStateType<>(
      UnderDistantMoons.MOD_ID,
      PersistentStateManager::new,
      CODEC,
      null
  );
  public HashMap<UUID, PlayerData> players = new HashMap<>();


  private PersistentStateManager() {}

  private PersistentStateManager(Map<UUID, PlayerData> players) {
    this.players = new HashMap<>(players);
  }

  public HashMap<UUID, PlayerData> players() {
    return this.players;
  }

  public static PersistentStateManager getServerState(MinecraftServer server) {
    ServerWorld world = server.getWorld(ServerWorld.OVERWORLD);
    assert world != null;
    PersistentStateManager state = world.getPersistentStateManager().getOrCreate(TYPE);
    state.markDirty();
    return state;
  }

  public static PlayerData getPlayerState(LivingEntity player) {
    MinecraftServer server = player.getServer();
    assert server != null;
    PersistentStateManager stateManager = getServerState(server);
    return stateManager.players.computeIfAbsent(player.getUuid(), uuid -> PlayerData.newDefault());
  }
}
