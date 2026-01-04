package syrenyx.distantmoons.data.persistent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import syrenyx.distantmoons.UnderDistantMoons;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersistentStateManager extends SavedData {

  public static final Codec<PersistentStateManager> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Codec.unboundedMap(UUIDUtil.AUTHLIB_CODEC, PlayerData.CODEC).fieldOf("players").forGetter(PersistentStateManager::players)
      )
      .apply(instance, PersistentStateManager::new)
  );
  private static final SavedDataType<PersistentStateManager> TYPE = new SavedDataType<>(
      UnderDistantMoons.MOD_ID,
      PersistentStateManager::new,
      CODEC,
      null
  );
  public HashMap<UUID, PlayerData> players;


  private PersistentStateManager() {
    this.players = new HashMap<>();
  }

  private PersistentStateManager(Map<UUID, PlayerData> players) {
    this.players = new HashMap<>(players);
  }

  public HashMap<UUID, PlayerData> players() {
    return this.players;
  }

  public static PersistentStateManager getServerState(MinecraftServer server) {
    ServerLevel world = server.getLevel(ServerLevel.OVERWORLD);
    assert world != null;
    PersistentStateManager state = world.getDataStorage().computeIfAbsent(TYPE);
    state.setDirty();
    return state;
  }

  public static PlayerData getPlayerState(LivingEntity player) {
    if (!(player instanceof ServerPlayer serverPlayer)) throw new IllegalArgumentException();
    PersistentStateManager stateManager = getServerState(serverPlayer.level().getServer());
    return stateManager.players.computeIfAbsent(player.getUUID(), uuid -> PlayerData.newDefault());
  }
}
