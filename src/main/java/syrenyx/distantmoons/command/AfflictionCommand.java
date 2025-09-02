package syrenyx.distantmoons.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.affliction.AfflictionManager;
import syrenyx.distantmoons.references.RegistryKeys;

import java.util.Collection;

public abstract class AfflictionCommand {

  public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
    dispatcher.register(CommandManager
        .literal("affliction")
        .requires(source -> source.hasPermissionLevel(PermissionLevel.GAMEMASTER.get()))
        .then(CommandManager
            .literal("clear")
            .executes(context -> executeClear(
                context.getSource(),
                ImmutableList.of(context.getSource().getEntityOrThrow()),
                null)
            )
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .executes(context -> executeClear(
                    context.getSource(),
                    EntityArgumentType.getEntities(context, "targets"),
                    null
                ))
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .executes(context -> executeClear(
                        context.getSource(),
                        EntityArgumentType.getEntities(context, "targets"),
                        RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY)
                    ))
                )
            )
        )
        .then(CommandManager
            .literal("set")
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .executes(context -> executeSet(
                        context.getSource(),
                        EntityArgumentType.getEntities(context, "targets"),
                        new AfflictionInstance(
                            RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY)
                        )
                    ))
                )
            )
        )
        .then(CommandManager
            .literal("give")
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .executes(context -> executeGive(
                        context.getSource(),
                        EntityArgumentType.getEntities(context, "targets"),
                        new AfflictionInstance(
                            RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY)
                        )
                    ))
                )
            )
        )
    );
  }

  private static int executeClear(ServerCommandSource source, Collection<? extends Entity> targets, RegistryEntry<Affliction> affliction) {
    int result = 0;
    for (Entity target : targets) {
      if (!(target instanceof LivingEntity livingEntity)) continue;
      AfflictionManager.clearAffliction(livingEntity, affliction);
    }
    return result;
  }

  private static int executeSet(ServerCommandSource source, Collection<? extends Entity> targets, AfflictionInstance affliction) {
    int result = 0;
    for (Entity target : targets) {
      if (!(target instanceof LivingEntity livingEntity)) continue;
      AfflictionManager.setAffliction(livingEntity, affliction);
    }
    return result;
  }

  private static int executeGive(ServerCommandSource source, Collection<? extends Entity> targets, AfflictionInstance affliction) {
    int result = 0;
    for (Entity target : targets) {
      if (!(target instanceof LivingEntity livingEntity)) continue;
      AfflictionManager.giveAffliction(livingEntity, affliction);
    }
    return result;
  }
}
