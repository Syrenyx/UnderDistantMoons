package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.loot.entry.*;

public class DistantMoonsLootPoolEntryTypes {

  public static final LootPoolEntryType APPLY_MOB_EFFECT = register("apply_mob_effect", ApplyMobEffectEntry.CODEC);
  public static final LootPoolEntryType CHANGE_AFFLICTION = register("change_affliction", ChangeAfflictionEntry.CODEC);
  public static final LootPoolEntryType DAMAGE_ENTITY = register("damage_entity", DamageEntityEntry.CODEC);
  public static final LootPoolEntryType EXPLODE = register("explode", ExplodeEntry.CODEC);
  public static final LootPoolEntryType IGNITE = register("ignite", IgniteEntry.CODEC);
  public static final LootPoolEntryType PLAY_SOUND = register("play_sound", PlaySoundEntry.CODEC);
  public static final LootPoolEntryType RUN_FUNCTION = register("run_function", RunFunctionEntry.CODEC);
  public static final LootPoolEntryType SPAWN_PARTICLES = register("spawn_particles", SpawnParticlesEntry.CODEC);
  public static final LootPoolEntryType SUMMON_ENTITY = register("summon_entity", SummonEntityEntry.CODEC);

  private static LootPoolEntryType register(String id, MapCodec<? extends LootPoolEntryContainer> codec) {
    return Registry.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, UnderDistantMoons.identifierOf(id), new LootPoolEntryType(codec));
  }

  public static void initialize() {}
}
