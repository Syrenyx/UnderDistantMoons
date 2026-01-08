package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class SummonEntityEntry extends LootPoolSingletonContainer {

  public static final MapCodec<SummonEntityEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("entity").forGetter(entry -> entry.entityType),
          Codec.INT.optionalFieldOf("weight", LootPoolSingletonContainer.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LootPoolSingletonContainer.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, SummonEntityEntry::new)
  );
  protected final HolderSet<EntityType<?>> entityType;

  protected SummonEntityEntry(
      HolderSet<EntityType<?>> entityType,
      int weight,
      int quality,
      List<LootItemCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.entityType = entityType;
  }

  @Override
  public @NonNull LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.SUMMON_ENTITY;
  }

  @Override
  public boolean expand(@NonNull LootContext context, @NonNull Consumer<LootPoolEntry> consumer) {
    if (!this.canRun(context)) return false;
    Vec3 pos = context.getOptionalParameter(LootContextParams.ORIGIN);
    if (pos == null) return true;
    BlockPos blockPos = BlockPos.containing(pos);
    if (!Level.isInSpawnableBounds(blockPos)) return true;
    ServerLevel world = context.getLevel();
    Optional<Holder<EntityType<?>>> optional = this.entityType.getRandomElement(world.getRandom());
    if (optional.isEmpty()) return true;
    Entity entity = optional.get().value().spawn(world, blockPos, EntitySpawnReason.TRIGGERED);
    if (entity == null) return true;
    entity.snapTo(pos.x, pos.y, pos.z, entity.getYRot(), entity.getXRot());
    return true;
  }

  @Override
  protected void createItemStack(@NonNull Consumer<ItemStack> lootConsumer, @NonNull LootContext context) {}
}
