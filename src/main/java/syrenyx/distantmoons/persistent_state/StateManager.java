package syrenyx.distantmoons.persistent_state;

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

public class StateManager extends PersistentState {

  public static final Codec<StateManager> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.unboundedMap(Uuids.CODEC, PlayerData.CODEC).fieldOf("players").forGetter(StateManager::getPlayers)
      )
      .apply(instance, StateManager::new)
  );
  private static final PersistentStateType<StateManager> type = new PersistentStateType<>(
      UnderDistantMoons.MOD_ID,
      StateManager::new,
      CODEC,
      null
  );
  public HashMap<UUID, PlayerData> players = new HashMap<>();

  private StateManager() {}

  private StateManager(Map<UUID, PlayerData> players) {
    this.players = new HashMap<>(players);
  }

  public HashMap<UUID, PlayerData> getPlayers() {
    return this.players;
  }

  public static StateManager getServerState(MinecraftServer server) {
    ServerWorld world = server.getWorld(ServerWorld.OVERWORLD);
    assert world != null;
    StateManager state = world.getPersistentStateManager().getOrCreate(type);
    state.markDirty();
    return state;
  }

  public static PlayerData getPlayerState(LivingEntity player) {
    MinecraftServer server = player.getServer();
    assert server != null;
    StateManager stateManager = getServerState(server);
    PlayerData playerData = stateManager.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
    return playerData;
  }
}
