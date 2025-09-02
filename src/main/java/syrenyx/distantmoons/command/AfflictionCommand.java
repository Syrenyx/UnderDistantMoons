package syrenyx.distantmoons.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
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
            .literal("give")
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .executes(context -> executeGive(
                        context.getSource(),
                        EntityArgumentType.getEntities(context, "targets"),
                        new AfflictionInstance(
                            RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                            Affliction.DEFAULT_STAGE
                        )
                    ))
                    .then(CommandManager
                        .argument("stage", IntegerArgumentType.integer(1, Affliction.MAX_STAGE))
                        .executes(context -> executeGive(
                            context.getSource(),
                            EntityArgumentType.getEntities(context, "targets"),
                            new AfflictionInstance(
                                RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                                IntegerArgumentType.getInteger(context, "stage")
                            )
                        ))
                    )
                )
            )
        )
        .then(CommandManager
            .literal("set")
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .then(CommandManager
                        .argument("stage", IntegerArgumentType.integer(1, Affliction.MAX_STAGE))
                        .executes(context -> executeSet(
                            context.getSource(),
                            EntityArgumentType.getEntities(context, "targets"),
                            new AfflictionInstance(
                                RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                                IntegerArgumentType.getInteger(context, "stage")
                            )
                        ))
                    )
                )
            )
        )
    );
  }

  private static final SimpleCommandExceptionType CLEAR_EVERYTHING_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.clear.everything.failed"));
  private static final SimpleCommandExceptionType CLEAR_SPECIFIC_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.clear.specific.failed"));
  private static final SimpleCommandExceptionType GIVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.give.failed"));
  private static final SimpleCommandExceptionType SET_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.set.failed"));

  private static int executeClear(
      ServerCommandSource source,
      Collection<? extends Entity> targets,
      @Nullable RegistryEntry<Affliction> affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity livingEntity && AfflictionManager.clearAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw affliction == null ? CLEAR_EVERYTHING_FAILED_EXCEPTION.create() : CLEAR_SPECIFIC_FAILED_EXCEPTION.create();
    if (targets.size() == 1 && affliction == null) source.sendFeedback(() -> Text.translatable("commands.affliction.clear.everything.success.single", targets.iterator().next().getDisplayName()), true);
    else if (targets.size() == 1) source.sendFeedback(() -> Text.translatable("commands.affliction.clear.specific.success.single", affliction.value().description(), targets.iterator().next().getDisplayName()), true);
    else if (affliction == null) source.sendFeedback(() -> Text.translatable("commands.affliction.clear.everything.success.multiple", targets.size()), true);
    else source.sendFeedback(() -> Text.translatable("commands.affliction.clear.specific.success.multiple", affliction.value().description(), targets.size()), true);
    return result;
  }

  private static int executeGive(
      ServerCommandSource source,
      Collection<? extends Entity> targets,
      AfflictionInstance affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity livingEntity && AfflictionManager.giveAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw GIVE_FAILED_EXCEPTION.create();
    if (targets.size() == 1) source.sendFeedback(() -> Text.translatable("commands.affliction.give.success.single", affliction.affliction().value().description(), targets.iterator().next().getDisplayName()), true);
    else source.sendFeedback(() -> Text.translatable("commands.affliction.give.success.multiple", affliction.affliction().value().description(), targets.size()), true);
    return result;
  }

  private static int executeSet(
      ServerCommandSource source,
      Collection<? extends Entity> targets,
      AfflictionInstance affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (!(target instanceof LivingEntity livingEntity)) continue;
      AfflictionManager.setAffliction(livingEntity, affliction);
      result++;
    }
    if (result == 0) throw SET_FAILED_EXCEPTION.create();
    if (targets.size() == 1) source.sendFeedback(() -> Text.translatable("commands.affliction.set.success.single", affliction.affliction().value().description(), targets.iterator().next().getDisplayName()), true);
    else source.sendFeedback(() -> Text.translatable("commands.affliction.set.success.multiple", affliction.affliction().value().description(), targets.size()), true);
    return result;
  }
}
