package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.loot.entry.*;

public class DistantMoonsLootPoolEntryTypes {

  static {
    register("apply_mob_effect", ApplyMobEffectEntry.CODEC);
    register("change_affliction", ChangeAfflictionEntry.CODEC);
    register("damage_entity", DamageEntityEntry.CODEC);
    register("explode", ExplodeEntry.CODEC);
    register("ignite", IgniteEntry.CODEC);
    register("play_sound", PlaySoundEntry.CODEC);
    register("run_function", RunFunctionEntry.CODEC);
    register("spawn_particles", SpawnParticlesEntry.CODEC);
    register("summon_entity", SummonEntityEntry.CODEC);
  }

  private static void register(String id, MapCodec<? extends LootPoolEntryContainer> codec) {
    Registry.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
