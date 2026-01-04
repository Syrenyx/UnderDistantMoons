package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class RunFunctionEntry extends LootPoolSingletonContainer {

  public static final MapCodec<RunFunctionEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("function").forGetter(entry -> entry.function),
          OptionalEffectPoolEntryTarget.CODEC.optionalFieldOf("target", OptionalEffectPoolEntryTarget.NONE).forGetter(entry -> entry.target),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality)
      )
      .and(commonFields(instance).t1())
      .apply(instance, RunFunctionEntry::new)
  );
  protected final Identifier function;
  protected final OptionalEffectPoolEntryTarget target;

  protected RunFunctionEntry(Identifier function, OptionalEffectPoolEntryTarget target, int weight, int quality, List<LootItemCondition> conditions) {
    super(weight, quality, conditions, Collections.emptyList());
    this.function = function;
    this.target = target;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.RUN_FUNCTION;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    ServerLevel world = context.getLevel();
    MinecraftServer minecraftServer = world.getServer();
    ServerFunctionManager commandFunctionManager = minecraftServer.getFunctions();
    Optional<CommandFunction<CommandSourceStack>> function = commandFunctionManager.get(this.function);
    if (function.isEmpty()) {
      UnderDistantMoons.LOGGER.error("Loot Table run_function pool entry failed for non-existent function {}", this.function);
      return true;
    }
    Entity target = this.target.tryGettingEntityFromContext(context);
    commandFunctionManager.execute(
        function.get(),
        minecraftServer
            .createCommandSourceStack()
            .withPermission(LevelBasedPermissionSet.GAMEMASTER)
            .withSuppressedOutput()
            .withEntity(target)
            .withLevel(world)
            .withPosition(context.hasParameter(LootContextParams.ORIGIN) ? context.getOptionalParameter(LootContextParams.ORIGIN) : minecraftServer.createCommandSourceStack().getPosition())
            .withRotation(target != null ? target.getRotationVector() : minecraftServer.createCommandSourceStack().getRotation())
    );
    return true;
  }

  @Override
  protected void createItemStack(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
