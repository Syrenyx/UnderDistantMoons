package syrenyx.distantmoons.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.AfflictionEntityEffect;
import syrenyx.distantmoons.command.PermissionLevel;

import java.util.Optional;

public record RunFunctionEffect(Identifier function) implements AfflictionEntityEffect {

  public static final MapCodec<RunFunctionEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("function").forGetter(RunFunctionEffect::function)
      )
      .apply(instance, RunFunctionEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionEntityEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos) {
    MinecraftServer minecraftServer = world.getServer();
    CommandFunctionManager commandFunctionManager = minecraftServer.getCommandFunctionManager();
    Optional<CommandFunction<ServerCommandSource>> function = commandFunctionManager.getFunction(this.function);
    if (function.isEmpty()) {
      UnderDistantMoons.LOGGER.error("Affliction run_function effect failed for non-existent function {}", this.function);
      return;
    }
    commandFunctionManager.execute(
        function.get(),
        minecraftServer
            .getCommandSource()
            .withLevel(PermissionLevel.GAMEMASTER.get())
            .withSilent()
            .withEntity(target)
            .withWorld(world)
            .withPosition(pos)
            .withRotation(target.getRotationClient())
    );
  }
}
