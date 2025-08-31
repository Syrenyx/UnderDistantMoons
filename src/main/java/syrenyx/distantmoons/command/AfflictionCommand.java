package syrenyx.distantmoons.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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

public class AfflictionCommand {

  public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
    dispatcher.register(CommandManager
        .literal("affliction")
        .requires(source -> source.hasPermissionLevel(PermissionLevel.GAMEMASTER.get()))
        .then(CommandManager
            .literal("clear")
            .executes(context -> executeClear(context.getSource(), ImmutableList.of(context.getSource().getEntityOrThrow())))
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .executes(context -> executeClear(context.getSource(), EntityArgumentType.getEntities(context, "targets")))
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION))
                    .executes(context -> executeClear(
                        context.getSource(),
                        EntityArgumentType.getEntities(context, "targets"),
                        RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION)
                    ))
                )
            )
        )
        .then(CommandManager
            .literal("set")
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION))
                    .executes(context -> executeUpdate(
                        context.getSource(),
                        EntityArgumentType.getEntities(context, "targets"),
                        new AfflictionInstance(RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION), 1, 1.0F),
                        true
                    ))
                    .then(CommandManager
                        .argument("stage", IntegerArgumentType.integer(1, Affliction.MAX_STAGE))
                        .executes(context -> executeUpdate(
                            context.getSource(),
                            EntityArgumentType.getEntities(context, "targets"),
                            new AfflictionInstance(RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION), IntegerArgumentType.getInteger(context, "stage"), 1.0F),
                            true
                        ))
                        .then(CommandManager
                            .argument("progression", FloatArgumentType.floatArg(0.0F, Affliction.MAX_PROGRESSION_LIMIT))
                            .executes(context -> executeUpdate(
                                context.getSource(),
                                EntityArgumentType.getEntities(context, "targets"),
                                new AfflictionInstance(RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION), IntegerArgumentType.getInteger(context, "stage"), FloatArgumentType.getFloat(context, "progression")),
                                true
                            ))
                        )
                    )
                )
            )
        )
        .then(CommandManager
            .literal("add")
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION))
                    .executes(context -> executeUpdate(
                        context.getSource(),
                        EntityArgumentType.getEntities(context, "targets"),
                        new AfflictionInstance(RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION), 1, 1.0F),
                        false
                    ))
                    .then(CommandManager
                        .argument("stage", IntegerArgumentType.integer(-Affliction.MAX_STAGE, Affliction.MAX_STAGE))
                        .executes(context -> executeUpdate(
                            context.getSource(),
                            EntityArgumentType.getEntities(context, "targets"),
                            new AfflictionInstance(RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION), IntegerArgumentType.getInteger(context, "stage"), 1.0F),
                            false
                        ))
                        .then(CommandManager
                            .argument("progression", FloatArgumentType.floatArg(-Affliction.MAX_PROGRESSION_LIMIT, Affliction.MAX_PROGRESSION_LIMIT))
                            .executes(context -> executeUpdate(
                                context.getSource(),
                                EntityArgumentType.getEntities(context, "targets"),
                                new AfflictionInstance(RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION), IntegerArgumentType.getInteger(context, "stage"), FloatArgumentType.getFloat(context, "progression")),
                                false
                            ))
                        )
                    )
                )
            )
        )
    );
  }

  private static int executeClear(ServerCommandSource source, Collection<? extends Entity> targets) {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity entity && AfflictionManager.clearAfflictions(entity)) result++;
    }
    return result;
  }

  private static int executeClear(ServerCommandSource source, Collection<? extends Entity> targets, RegistryEntry<Affliction> affliction) {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity entity && AfflictionManager.clearAffliction(entity, affliction)) result++;
    }
    return result;
  }

  private static int executeUpdate(ServerCommandSource source, Collection<? extends Entity> targets, AfflictionInstance affliction, boolean replace) {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity entity) {
        if (replace) AfflictionManager.setAffliction(entity, affliction);
        else AfflictionManager.addAffliction(entity, affliction);
        result++;
      }
    }
    return result;
  }
}
