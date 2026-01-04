package syrenyx.distantmoons.content.affliction.effect.entity;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.permissions.LevelBasedPermissionSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.command.PermissionLevel;

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
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos) {
    MinecraftServer minecraftServer = world.getServer();
    ServerFunctionManager commandFunctionManager = minecraftServer.getFunctions();
    Optional<CommandFunction<CommandSourceStack>> function = commandFunctionManager.get(this.function);
    if (function.isEmpty()) {
      UnderDistantMoons.LOGGER.error("Affliction run_function effect failed for non-existent function {}", this.function);
      return;
    }
    commandFunctionManager.execute(
        function.get(),
        minecraftServer
            .createCommandSourceStack()
            .withPermission(LevelBasedPermissionSet.GAMEMASTER)
            .withSuppressedOutput()
            .withEntity(target)
            .withLevel(world)
            .withPosition(pos)
            .withRotation(target.getRotationVector())
    );
  }
}
