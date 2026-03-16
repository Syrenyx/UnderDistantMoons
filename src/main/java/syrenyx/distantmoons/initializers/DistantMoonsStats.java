package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsStats {

  public static final Identifier INTERACT_WITH_LARGE_BLAST_FURNACE = registerCustomStat("interact_with_large_blast_furnace", StatFormatter.DEFAULT);

  private static Identifier registerCustomStat(String string, StatFormatter statFormatter) {
    Identifier identifier = UnderDistantMoons.identifierOf(string);
    Registry.register(BuiltInRegistries.CUSTOM_STAT, string, identifier);
    Stats.CUSTOM.get(identifier, statFormatter);
    return identifier;
  }

  public static void initialize() {}
}
