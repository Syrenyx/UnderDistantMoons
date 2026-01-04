package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.command.permission.LeveledPermissionPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class RunFunctionEntry extends LeafEntry {

  public static final MapCodec<RunFunctionEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("function").forGetter(entry -> entry.function),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("target", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality)
      )
      .and(addConditionsField(instance).t1())
      .apply(instance, RunFunctionEntry::new)
  );
  protected final Identifier function;
  protected final OptionalEffectPoolEntryTarget target;

  protected RunFunctionEntry(Identifier function, OptionalEffectPoolEntryTarget target, int weight, int quality, List<LootCondition> conditions) {
    super(weight, quality, conditions, Collections.emptyList());
    this.function = function;
    this.target = target;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.RUN_FUNCTION;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    ServerWorld world = context.getWorld();
    MinecraftServer minecraftServer = world.getServer();
    CommandFunctionManager commandFunctionManager = minecraftServer.getCommandFunctionManager();
    Optional<CommandFunction<ServerCommandSource>> function = commandFunctionManager.getFunction(this.function);
    if (function.isEmpty()) {
      UnderDistantMoons.LOGGER.error("Loot Table run_function pool entry failed for non-existent function {}", this.function);
      return true;
    }
    Entity target = this.target.tryGettingEntityFromContext(context);
    commandFunctionManager.execute(
        function.get(),
        minecraftServer
            .getCommandSource()
            .withPermissions(LeveledPermissionPredicate.GAMEMASTERS)
            .withSilent()
            .withEntity(target)
            .withWorld(world)
            .withPosition(context.hasParameter(LootContextParameters.ORIGIN) ? context.get(LootContextParameters.ORIGIN) : minecraftServer.getCommandSource().getPosition())
            .withRotation(target != null ? target.getRotationClient() : minecraftServer.getCommandSource().getRotation())
    );
    return true;
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
