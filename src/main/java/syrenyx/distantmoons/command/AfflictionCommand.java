package syrenyx.distantmoons.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
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
            .literal("add")
            .then(CommandManager
                .argument("targets", EntityArgumentType.entities())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .then(CommandManager
                        .argument("stage", IntegerArgumentType.integer(1, Affliction.MAX_STAGE))
                        .executes(context -> executeAdd(
                            context.getSource(),
                            EntityArgumentType.getEntities(context, "targets"),
                            new AfflictionInstance(
                                RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                                IntegerArgumentType.getInteger(context, "stage")
                            )
                        ))
                        .then(CommandManager
                            .argument("progression", FloatArgumentType.floatArg(0.0F, Affliction.MAX_PROGRESSION))
                            .executes(context -> executeAdd(
                                context.getSource(),
                                EntityArgumentType.getEntities(context, "targets"),
                                new AfflictionInstance(
                                    RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                                    IntegerArgumentType.getInteger(context, "stage"),
                                    FloatArgumentType.getFloat(context, "progression")
                                )
                            ))
                        )
                    )
                )
            )
        )
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
            .literal("get")
            .then(CommandManager
                .argument("target", EntityArgumentType.entity())
                .then(CommandManager
                    .argument("affliction", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .then(CommandManager
                        .literal("stage")
                        .executes(context -> executeGet(
                            context.getSource(),
                            EntityArgumentType.getEntity(context, "target"),
                            RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                            true
                        ))
                    )
                    .then(CommandManager
                        .literal("progression")
                        .executes(context -> executeGet(
                            context.getSource(),
                            EntityArgumentType.getEntity(context, "target"),
                            RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                            false
                        ))
                    )
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
                        .then(CommandManager
                            .argument("progression", FloatArgumentType.floatArg(0.0F, Affliction.MAX_PROGRESSION))
                            .executes(context -> executeGive(
                                context.getSource(),
                                EntityArgumentType.getEntities(context, "targets"),
                                new AfflictionInstance(
                                    RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                                    IntegerArgumentType.getInteger(context, "stage"),
                                    FloatArgumentType.getFloat(context, "progression")
                                )
                            ))
                        )
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
                        .then(CommandManager
                            .argument("progression", FloatArgumentType.floatArg(0.0F, Affliction.MAX_PROGRESSION))
                            .executes(context -> executeSet(
                                context.getSource(),
                                EntityArgumentType.getEntities(context, "targets"),
                                new AfflictionInstance(
                                    RegistryEntryReferenceArgumentType.getRegistryEntry(context, "affliction", RegistryKeys.AFFLICTION_REGISTRY_KEY),
                                    IntegerArgumentType.getInteger(context, "stage"),
                                    FloatArgumentType.getFloat(context, "progression")
                                )
                            ))
                        )
                    )
                )
            )
        )
    );
  }

  private static final SimpleCommandExceptionType ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.add.failed"));
  private static final SimpleCommandExceptionType CLEAR_EVERYTHING_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.clear.everything.failed"));
  private static final SimpleCommandExceptionType CLEAR_SPECIFIC_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.clear.specific.failed"));
  private static final SimpleCommandExceptionType GET_FAILED_AFFLICTION_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.get.failed.affliction"));
  private static final SimpleCommandExceptionType GET_FAILED_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.get.failed.entity"));
  private static final SimpleCommandExceptionType GIVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.give.failed"));
  private static final SimpleCommandExceptionType SET_FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.affliction.set.failed"));

  private static int executeAdd(
      ServerCommandSource source,
      Collection<? extends Entity> targets,
      AfflictionInstance affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity livingEntity && AfflictionManager.addToAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw ADD_FAILED_EXCEPTION.create();
    if (targets.size() == 1) source.sendFeedback(() -> Text.translatable("commands.affliction.add.success.single", affliction.affliction().value().description(), targets.iterator().next().getDisplayName()), true);
    else source.sendFeedback(() -> Text.translatable("commands.affliction.add.success.multiple", affliction.affliction().value().description(), targets.size()), true);
    return result;
  }

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

  private static int executeGet(
      ServerCommandSource source,
      Entity target,
      RegistryEntry<Affliction> affliction,
      boolean stage
  ) throws CommandSyntaxException {
    if (!(target instanceof LivingEntity livingEntity)) throw GET_FAILED_ENTITY_EXCEPTION.create();
    AfflictionInstance afflictionInstance = AfflictionManager.getAffliction(livingEntity, affliction);
    if (afflictionInstance == null) throw GET_FAILED_AFFLICTION_EXCEPTION.create();
    if (stage) source.sendFeedback(() -> Text.translatable("commands.affliction.get.success.stage", affliction.value().description(), afflictionInstance.stage()), true);
    else source.sendFeedback(() -> Text.translatable("commands.affliction.get.success.progression", affliction.value().description(), afflictionInstance.progression()), true);
    return stage ? afflictionInstance.stage() : (int) afflictionInstance.progression();
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
      if (target instanceof LivingEntity livingEntity && AfflictionManager.setAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw SET_FAILED_EXCEPTION.create();
    if (targets.size() == 1) source.sendFeedback(() -> Text.translatable("commands.affliction.set.success.single", affliction.affliction().value().description(), targets.iterator().next().getDisplayName()), true);
    else source.sendFeedback(() -> Text.translatable("commands.affliction.set.success.multiple", affliction.affliction().value().description(), targets.size()), true);
    return result;
  }
}
