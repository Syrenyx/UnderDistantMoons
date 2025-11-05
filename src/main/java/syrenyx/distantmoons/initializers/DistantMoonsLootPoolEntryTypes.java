package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.loot.entry.RunFunctionEntry;

public class DistantMoonsLootPoolEntryTypes {

  public static final LootPoolEntryType RUN_FUNCTION = register("run_function", RunFunctionEntry.CODEC);

  private static LootPoolEntryType register(String id, MapCodec<? extends LootPoolEntry> codec) {
    return Registry.register(Registries.LOOT_POOL_ENTRY_TYPE, UnderDistantMoons.identifierOf(id), new LootPoolEntryType(codec));
  }

  public static void initialize() {}
}
