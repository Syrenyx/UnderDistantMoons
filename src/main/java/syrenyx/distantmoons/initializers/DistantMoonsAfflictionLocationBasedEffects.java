package syrenyx.distantmoons.initializers;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.Registry;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.effect.location_based.AfflictionLocationBasedEffect;
import syrenyx.distantmoons.affliction.effect.entity.*;
import syrenyx.distantmoons.affliction.effect.location_based.AllOfLocationBasedEffect;
import syrenyx.distantmoons.affliction.effect.location_based.AttributeEffect;

public abstract class DistantMoonsAfflictionLocationBasedEffects {

  static {
    register("all_of", AllOfLocationBasedEffect.CODEC);
    register("apply_mob_effect", ApplyMobEffectEffect.CODEC);
    register("attribute", AttributeEffect.CODEC);
    register("change_affliction", ChangeAfflictionEffect.CODEC);
    register("damage_entity", DamageEntityEffect.CODEC);
    register("explode", ExplodeEffect.CODEC);
    register("ignite", IgniteEffect.CODEC);
    register("play_sound", PlaySoundEffect.CODEC);
    register("replace_block", ReplaceBlockEffect.CODEC);
    register("replace_disk", ReplaceDiskEffect.CODEC);
    register("run_function", RunFunctionEffect.CODEC);
    register("set_block_properties", SetBlockPropertiesEffect.CODEC);
    register("spawn_particles", SpawnParticlesEffect.CODEC);
    register("summon_entity", SummonEntityEffect.CODEC);
  }

  private static <T extends AfflictionLocationBasedEffect> void register(String id, MapCodec<T> codec) {
    Registry.register(Registries.AFFLICTION_LOCATION_BASED_EFFECT_REGISTRY, UnderDistantMoons.identifierOf(id), codec);
  }

  public static void initialize() {}
}
