package syrenyx.distantmoons.content.loot.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import syrenyx.distantmoons.initializers.DistantMoonsLootPoolEntryTypes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class SummonEntityEntry extends LeafEntry {

  public static final MapCodec<SummonEntityEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          RegistryCodecs.entryList(RegistryKeys.ENTITY_TYPE).fieldOf("entity").forGetter(entry -> entry.entityType),
          Codec.INT.optionalFieldOf("weight", LeafEntry.DEFAULT_WEIGHT).forGetter(entry -> entry.weight),
          Codec.INT.optionalFieldOf("quality", LeafEntry.DEFAULT_QUALITY).forGetter(entry -> entry.quality),
          LootCondition.CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(entry -> entry.conditions)
      )
      .apply(instance, SummonEntityEntry::new)
  );
  protected final RegistryEntryList<EntityType<?>> entityType;

  protected SummonEntityEntry(
      RegistryEntryList<EntityType<?>> entityType,
      int weight,
      int quality,
      List<LootCondition> conditions
  ) {
    super(weight, quality, conditions, Collections.emptyList());
    this.entityType = entityType;
  }

  @Override
  public LootPoolEntryType getType() {
    return DistantMoonsLootPoolEntryTypes.SUMMON_ENTITY;
  }

  @Override
  public boolean expand(LootContext context, Consumer<LootChoice> consumer) {
    if (!this.test(context)) return false;
    Vec3d pos = context.get(LootContextParameters.ORIGIN);
    if (pos == null) return true;
    BlockPos blockPos = BlockPos.ofFloored(pos);
    if (!World.isValid(blockPos)) return true;
    ServerWorld world = context.getWorld();
    Optional<RegistryEntry<EntityType<?>>> optional = this.entityType.getRandom(world.getRandom());
    if (optional.isEmpty()) return true;
    Entity entity = optional.get().value().spawn(world, blockPos, SpawnReason.TRIGGERED);
    if (entity == null) return true;
    entity.refreshPositionAndAngles(pos.x, pos.y, pos.z, entity.getYaw(), entity.getPitch());
    return true;
  }

  @Override
  protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {}
}
