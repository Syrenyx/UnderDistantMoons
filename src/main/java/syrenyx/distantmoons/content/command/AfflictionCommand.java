package syrenyx.distantmoons.content.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import org.jetbrains.annotations.Nullable;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.content.affliction.AfflictionManager;
import syrenyx.distantmoons.references.DistantMoonsRegistryKeys;

import java.util.Collection;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class AfflictionCommand {

  public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess) {
    dispatcher.register(Commands
        .literal(UnderDistantMoons.withPrefixedNamespace("affliction"))
        .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
        .then(Commands
            .literal("add")
            .then(Commands
                .argument("targets", EntityArgument.entities())
                .then(Commands
                    .argument("affliction", ResourceArgument.resource(registryAccess, DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .then(Commands
                        .argument("stage", IntegerArgumentType.integer(1, Affliction.MAX_STAGE))
                        .executes(context -> executeAdd(
                            context.getSource(),
                            EntityArgument.getEntities(context, "targets"),
                            new AfflictionInstance(
                                ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
                                IntegerArgumentType.getInteger(context, "stage")
                            )
                        ))
                        .then(Commands
                            .argument("progression", FloatArgumentType.floatArg(0.0F, Affliction.MAX_PROGRESSION))
                            .executes(context -> executeAdd(
                                context.getSource(),
                                EntityArgument.getEntities(context, "targets"),
                                new AfflictionInstance(
                                    ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
                                    IntegerArgumentType.getInteger(context, "stage"),
                                    FloatArgumentType.getFloat(context, "progression")
                                )
                            ))
                        )
                    )
                )
            )
        )
        .then(Commands
            .literal("clear")
            .executes(context -> executeClear(
                context.getSource(),
                ImmutableList.of(context.getSource().getEntityOrException()),
                null)
            )
            .then(Commands
                .argument("targets", EntityArgument.entities())
                .executes(context -> executeClear(
                    context.getSource(),
                    EntityArgument.getEntities(context, "targets"),
                    null
                ))
                .then(Commands
                    .argument("affliction", ResourceArgument.resource(registryAccess, DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .executes(context -> executeClear(
                        context.getSource(),
                        EntityArgument.getEntities(context, "targets"),
                        ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY)
                    ))
                )
            )
        )
        .then(Commands
            .literal("get")
            .then(Commands
                .argument("target", EntityArgument.entity())
                .then(Commands
                    .argument("affliction", ResourceArgument.resource(registryAccess, DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .then(Commands
                        .literal("stage")
                        .executes(context -> executeGet(
                            context.getSource(),
                            EntityArgument.getEntity(context, "target"),
                            ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
                            true
                        ))
                    )
                    .then(Commands
                        .literal("progression")
                        .executes(context -> executeGet(
                            context.getSource(),
                            EntityArgument.getEntity(context, "target"),
                            ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
                            false
                        ))
                    )
                )
            )
        )
        .then(Commands
            .literal("give")
            .then(Commands
                .argument("targets", EntityArgument.entities())
                .then(Commands
                    .argument("affliction", ResourceArgument.resource(registryAccess, DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .executes(context -> executeGive(
                        context.getSource(),
                        EntityArgument.getEntities(context, "targets"),
                        new AfflictionInstance(
                            ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY)
                        )
                    ))
                    .then(Commands
                        .argument("stage", IntegerArgumentType.integer(1, Affliction.MAX_STAGE))
                        .executes(context -> executeGive(
                            context.getSource(),
                            EntityArgument.getEntities(context, "targets"),
                            new AfflictionInstance(
                                ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
                                IntegerArgumentType.getInteger(context, "stage")
                            )
                        ))
                        .then(Commands
                            .argument("progression", FloatArgumentType.floatArg(0.0F, Affliction.MAX_PROGRESSION))
                            .executes(context -> executeGive(
                                context.getSource(),
                                EntityArgument.getEntities(context, "targets"),
                                new AfflictionInstance(
                                    ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
                                    IntegerArgumentType.getInteger(context, "stage"),
                                    FloatArgumentType.getFloat(context, "progression")
                                )
                            ))
                        )
                    )
                )
            )
        )
        .then(Commands
            .literal("set")
            .then(Commands
                .argument("targets", EntityArgument.entities())
                .then(Commands
                    .argument("affliction", ResourceArgument.resource(registryAccess, DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY))
                    .then(Commands
                        .argument("stage", IntegerArgumentType.integer(1, Affliction.MAX_STAGE))
                        .executes(context -> executeSet(
                            context.getSource(),
                            EntityArgument.getEntities(context, "targets"),
                            new AfflictionInstance(
                                ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
                                IntegerArgumentType.getInteger(context, "stage")
                            )
                        ))
                        .then(Commands
                            .argument("progression", FloatArgumentType.floatArg(0.0F, Affliction.MAX_PROGRESSION))
                            .executes(context -> executeSet(
                                context.getSource(),
                                EntityArgument.getEntities(context, "targets"),
                                new AfflictionInstance(
                                    ResourceArgument.getResource(context, "affliction", DistantMoonsRegistryKeys.AFFLICTION_REGISTRY_KEY),
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

  private static final SimpleCommandExceptionType ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.distant-moons.affliction.add.failed"));
  private static final SimpleCommandExceptionType CLEAR_EVERYTHING_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.distant-moons.affliction.clear.everything.failed"));
  private static final SimpleCommandExceptionType CLEAR_SPECIFIC_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.distant-moons.affliction.clear.specific.failed"));
  private static final SimpleCommandExceptionType GET_FAILED_AFFLICTION_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.distant-moons.affliction.get.failed.affliction"));
  private static final SimpleCommandExceptionType GET_FAILED_ENTITY_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.distant-moons.affliction.get.failed.entity"));
  private static final SimpleCommandExceptionType GIVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.distant-moons.affliction.give.failed"));
  private static final SimpleCommandExceptionType SET_FAILED_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.distant-moons.affliction.set.failed"));

  private static int executeAdd(
      CommandSourceStack source,
      Collection<? extends Entity> targets,
      AfflictionInstance affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity livingEntity && AfflictionManager.addToAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw ADD_FAILED_EXCEPTION.create();
    if (targets.size() == 1) source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.add.success.single", affliction.affliction().value().description(), targets.iterator().next().getDisplayName()), true);
    else source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.add.success.multiple", affliction.affliction().value().description(), targets.size()), true);
    return result;
  }

  private static int executeClear(
      CommandSourceStack source,
      Collection<? extends Entity> targets,
      @Nullable Holder<Affliction> affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity livingEntity && AfflictionManager.clearAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw affliction == null ? CLEAR_EVERYTHING_FAILED_EXCEPTION.create() : CLEAR_SPECIFIC_FAILED_EXCEPTION.create();
    if (targets.size() == 1 && affliction == null) source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.clear.everything.success.single", targets.iterator().next().getDisplayName()), true);
    else if (targets.size() == 1) source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.clear.specific.success.single", affliction.value().description(), targets.iterator().next().getDisplayName()), true);
    else if (affliction == null) source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.clear.everything.success.multiple", targets.size()), true);
    else source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.clear.specific.success.multiple", affliction.value().description(), targets.size()), true);
    return result;
  }

  private static int executeGet(
      CommandSourceStack source,
      Entity target,
      Holder<Affliction> affliction,
      boolean stage
  ) throws CommandSyntaxException {
    if (!(target instanceof LivingEntity livingEntity)) throw GET_FAILED_ENTITY_EXCEPTION.create();
    AfflictionInstance afflictionInstance = AfflictionManager.getAffliction(livingEntity, affliction);
    if (afflictionInstance == null) throw GET_FAILED_AFFLICTION_EXCEPTION.create();
    if (stage) source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.get.success.stage", affliction.value().description(), afflictionInstance.stage()), true);
    else source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.get.success.progression", affliction.value().description(), afflictionInstance.progression()), true);
    return stage ? afflictionInstance.stage() : (int) afflictionInstance.progression();
  }

  private static int executeGive(
      CommandSourceStack source,
      Collection<? extends Entity> targets,
      AfflictionInstance affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity livingEntity && AfflictionManager.giveAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw GIVE_FAILED_EXCEPTION.create();
    if (targets.size() == 1) source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.give.success.single", affliction.affliction().value().description(), targets.iterator().next().getDisplayName()), true);
    else source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.give.success.multiple", affliction.affliction().value().description(), targets.size()), true);
    return result;
  }

  private static int executeSet(
      CommandSourceStack source,
      Collection<? extends Entity> targets,
      AfflictionInstance affliction
  ) throws CommandSyntaxException {
    int result = 0;
    for (Entity target : targets) {
      if (target instanceof LivingEntity livingEntity && AfflictionManager.setAffliction(livingEntity, affliction)) result++;
    }
    if (result == 0) throw SET_FAILED_EXCEPTION.create();
    if (targets.size() == 1) source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.set.success.single", affliction.affliction().value().description(), targets.iterator().next().getDisplayName()), true);
    else source.sendSuccess(() -> Component.translatable("commands.distant-moons.affliction.set.success.multiple", affliction.affliction().value().description(), targets.size()), true);
    return result;
  }
}
