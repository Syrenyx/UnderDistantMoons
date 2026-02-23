package syrenyx.distantmoons.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsEntityTypes;
import syrenyx.distantmoons.initializers.DistantMoonsItems;
import syrenyx.distantmoons.initializers.DistantMoonsStats;
import syrenyx.distantmoons.references.tag.DistantMoonsItemTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class de_de extends FabricLanguageProvider {

  private static final Map<DyeColor, String> FEMININE_COLOR_ADJECTIVES = Map.ofEntries(
      Map.entry(DyeColor.WHITE, "Weiße"),
      Map.entry(DyeColor.LIGHT_GRAY, "Hellgraue"),
      Map.entry(DyeColor.GRAY, "Graue"),
      Map.entry(DyeColor.BLACK, "Schwarze"),
      Map.entry(DyeColor.BROWN, "Braune"),
      Map.entry(DyeColor.RED, "Rote"),
      Map.entry(DyeColor.ORANGE, "Orange"),
      Map.entry(DyeColor.YELLOW, "Gelbe"),
      Map.entry(DyeColor.LIME, "Hellgrüne"),
      Map.entry(DyeColor.GREEN, "Grüne"),
      Map.entry(DyeColor.CYAN, "Türkise"),
      Map.entry(DyeColor.LIGHT_BLUE, "Hellblaue"),
      Map.entry(DyeColor.BLUE, "Blaue"),
      Map.entry(DyeColor.PURPLE, "Violette"),
      Map.entry(DyeColor.MAGENTA, "Magenta"),
      Map.entry(DyeColor.PINK, "Rosa")
  );
  private static final Map<DyeColor, String> MASCULINE_COLOR_ADJECTIVES = Map.ofEntries(
      Map.entry(DyeColor.WHITE, "Weißer"),
      Map.entry(DyeColor.LIGHT_GRAY, "Hellgrauer"),
      Map.entry(DyeColor.GRAY, "Grauer"),
      Map.entry(DyeColor.BLACK, "Schwarzer"),
      Map.entry(DyeColor.BROWN, "Brauner"),
      Map.entry(DyeColor.RED, "Roter"),
      Map.entry(DyeColor.ORANGE, "Oranger"),
      Map.entry(DyeColor.YELLOW, "Gelber"),
      Map.entry(DyeColor.LIME, "Hellgrüner"),
      Map.entry(DyeColor.GREEN, "Grüner"),
      Map.entry(DyeColor.CYAN, "Türkiser"),
      Map.entry(DyeColor.LIGHT_BLUE, "Hellblauer"),
      Map.entry(DyeColor.BLUE, "Blauer"),
      Map.entry(DyeColor.PURPLE, "Violetter"),
      Map.entry(DyeColor.MAGENTA, "Magenta"),
      Map.entry(DyeColor.PINK, "Rosa")
  );
  private static final Map<DyeColor, String> OBJECTIVE_COLOR_ADJECTIVES = Map.ofEntries(
      Map.entry(DyeColor.WHITE, "Weißes"),
      Map.entry(DyeColor.LIGHT_GRAY, "Hellgraues"),
      Map.entry(DyeColor.GRAY, "Graues"),
      Map.entry(DyeColor.BLACK, "Schwarzes"),
      Map.entry(DyeColor.BROWN, "Braunes"),
      Map.entry(DyeColor.RED, "Rotes"),
      Map.entry(DyeColor.ORANGE, "Oranges"),
      Map.entry(DyeColor.YELLOW, "Gelbes"),
      Map.entry(DyeColor.LIME, "Hellgrünes"),
      Map.entry(DyeColor.GREEN, "Grünes"),
      Map.entry(DyeColor.CYAN, "Türkises"),
      Map.entry(DyeColor.LIGHT_BLUE, "Hellblaues"),
      Map.entry(DyeColor.BLUE, "Blaues"),
      Map.entry(DyeColor.PURPLE, "Violettes"),
      Map.entry(DyeColor.MAGENTA, "Magenta"),
      Map.entry(DyeColor.PINK, "Rosa")
  );

  public de_de(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, "de_de", registryLookup);
  }

  @Override
  public void generateTranslations(HolderLookup.@NonNull Provider provider, @NonNull TranslationBuilder builder) {

    LangHelper helper = new LangHelper(builder);

    builder.add("affliction.distant-moons.curse_of_the_night", "Fluch der Nacht");
    builder.add("affliction.distant-moons.curse_of_the_night_waning", "Abnehmender Fluch der Nacht");
    builder.add("affliction.distant-moons.curse_of_the_underworld", "Fluch der Unterwelt");

    builder.add("affliction.progression_tooltip", "%s / %s");

    builder.add(DistantMoonsBlocks.ACACIA_BEAM, "Akazienholzbalken");
    builder.add(DistantMoonsBlocks.ACACIA_POLE, "Akazienholzpfosten");
    builder.add(DistantMoonsBlocks.ACACIA_WALL_SLAB, "Gestufte Akazienholzwand");
    builder.add(DistantMoonsBlocks.ANDESITE_WALL_SLAB, "Gestufte Andesitwand");
    builder.add(DistantMoonsBlocks.BAMBOO_POLE, "Bambuspfosten");
    builder.add(DistantMoonsBlocks.BAMBOO_MOSAIC_WALL_SLAB, "Gestufte Bambusmosaikwand");
    builder.add(DistantMoonsBlocks.BAMBOO_WALL_SLAB, "Gestufte Bambuswand");
    builder.add(DistantMoonsBlocks.BIRCH_BEAM, "Birkenholzbalken");
    builder.add(DistantMoonsBlocks.BIRCH_POLE, "Birkenholzpfosten");
    builder.add(DistantMoonsBlocks.BIRCH_WALL_SLAB, "Gestufte Birkenholzwand");
    builder.add(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE, "Schwarzstein-Tiefeisenerz");
    builder.add(DistantMoonsBlocks.BLACKSTONE_WALL_SLAB, "Gestufte Schwarzsteinwand");
    builder.add(DistantMoonsBlocks.BLAST_FURNACE, "Hochofen");
    builder.add(DistantMoonsBlocks.BRICK_WALL_SLAB, "Gestufte Ziegelwand");
    builder.add(DistantMoonsBlocks.CHARCOAL_BLOCK, "Holzkohleblock");
    builder.add(DistantMoonsBlocks.CHERRY_BEAM, "Kirschholzbalken");
    builder.add(DistantMoonsBlocks.CHERRY_POLE, "Kirschholzpfosten");
    builder.add(DistantMoonsBlocks.CHERRY_WALL_SLAB, "Gestufte Kirschholzwand");
    builder.add(DistantMoonsBlocks.COBBLED_DEEPSLATE_WALL_SLAB, "Gestufte Bruchtiefenschieferwand");
    builder.add(DistantMoonsBlocks.COBBLESTONE_WALL_SLAB, "Gestufte Bruchsteinwand");
    builder.add(DistantMoonsBlocks.COKE_BLOCK, "Koksblock");
    builder.add(DistantMoonsBlocks.CRIMSON_BEAM, "Karmesinbalken");
    builder.add(DistantMoonsBlocks.CRIMSON_POLE, "Karmesinpfosten");
    builder.add(DistantMoonsBlocks.CRIMSON_WALL_SLAB, "Gestufte Karmesinwand");
    builder.add(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK, "Grober Tiefeisenblock");
    builder.add(DistantMoonsBlocks.CUT_ACACIA_LOG, "Geschnittener Akazienstamm");
    builder.add(DistantMoonsBlocks.CUT_ACACIA_WOOD, "Geschnittenes Akazienholz");
    builder.add(DistantMoonsBlocks.CUT_BAMBOO_BLOCK, "Geschnittener Bambusblock");
    builder.add(DistantMoonsBlocks.CUT_BASALT, "Geschnittener Basalt");
    builder.add(DistantMoonsBlocks.CUT_BIRCH_LOG, "Geschnittener Birkenstamm");
    builder.add(DistantMoonsBlocks.CUT_BIRCH_WOOD, "Geschnittenes Birkenholz");
    builder.add(DistantMoonsBlocks.CUT_BONE_BLOCK, "Geschnittener Knochenblock");
    builder.add(DistantMoonsBlocks.CUT_CHERRY_LOG, "Geschnittener Kirschstamm");
    builder.add(DistantMoonsBlocks.CUT_CHERRY_WOOD, "Geschnittenes Kirschholz");
    builder.add(DistantMoonsBlocks.CUT_CRIMSON_HYPHAE, "Geschnittene Karmesinhyphen");
    builder.add(DistantMoonsBlocks.CUT_CRIMSON_STEM, "Geschnittener Karmesinstiel");
    builder.add(DistantMoonsBlocks.CUT_DARK_OAK_LOG, "Geschnittener Schwarzeichenstamm");
    builder.add(DistantMoonsBlocks.CUT_DARK_OAK_WOOD, "Geschnittenes Schwarzeichenholz");
    builder.add(DistantMoonsBlocks.CUT_DEEPSLATE, "Geschnittemer Tiefenschiefer");
    builder.add(DistantMoonsBlocks.CUT_JUNGLE_LOG, "Geschnittener Tropenbaumstamm");
    builder.add(DistantMoonsBlocks.CUT_JUNGLE_WOOD, "Geschnittenes Tropenholz");
    builder.add(DistantMoonsBlocks.CUT_MANGROVE_LOG, "Geschnittener Mangrovenstamm");
    builder.add(DistantMoonsBlocks.CUT_MANGROVE_WOOD, "Geschnittenes Mangrovenholz");
    builder.add(DistantMoonsBlocks.CUT_OAK_LOG, "Geschnittener Eichenstamm");
    builder.add(DistantMoonsBlocks.CUT_OAK_WOOD, "Geschnittenes Eichenholz");
    builder.add(DistantMoonsBlocks.CUT_PALE_OAK_LOG, "Geschnittener Blasseichenstamm");
    builder.add(DistantMoonsBlocks.CUT_PALE_OAK_WOOD, "Geschnittenes Blasseichenholz");
    builder.add(DistantMoonsBlocks.CUT_PURPUR_PILLAR, "Geschnittene Purpursäule");
    builder.add(DistantMoonsBlocks.CUT_QUARTZ_PILLAR, "Geschnittene Quarzsäule");
    builder.add(DistantMoonsBlocks.CUT_RED_SANDSTONE_WALL_SLAB, "Gestufte rote Sandsteinwand");
    builder.add(DistantMoonsBlocks.CUT_SANDSTONE_WALL_SLAB, "Gestufte Sandsteinwand");
    builder.add(DistantMoonsBlocks.CUT_SPRUCE_LOG, "Geschnittener Fichtenstamm");
    builder.add(DistantMoonsBlocks.CUT_SPRUCE_WOOD, "Geschnittenes Fichtenholz");
    builder.add(DistantMoonsBlocks.CUT_WARPED_HYPHAE, "Geschnittene Wirrhyphen");
    builder.add(DistantMoonsBlocks.CUT_WARPED_STEM, "Geschnittener Wirrstiel");
    builder.add(DistantMoonsBlocks.DARK_OAK_BEAM, "Schwarzeichenholzbalken");
    builder.add(DistantMoonsBlocks.DARK_OAK_POLE, "Schwarzeichenholzpfosten");
    builder.add(DistantMoonsBlocks.DARK_OAK_WALL_SLAB, "Gestufte Schwarzeichenholzwand");
    builder.add(DistantMoonsBlocks.DARK_PRISMARINE_WALL_SLAB, "Gestufte dunkle Prismarinewand");
    builder.add(DistantMoonsBlocks.DEEP_IRON_BAR_DOOR, "Tiefeisengittertür");
    builder.add(DistantMoonsBlocks.DEEP_IRON_BARS, "Tiefeisengitter");
    builder.add(DistantMoonsBlocks.DEEP_IRON_CHAIN, "Tiefeisenkette");
    builder.add(DistantMoonsBlocks.DEEP_IRON_DOOR, "Tiefeisentür");
    builder.add(DistantMoonsBlocks.DEEP_IRON_FENCE, "Tiefeisenzaun");
    builder.add(DistantMoonsBlocks.DEEP_IRON_LADDER, "Tiefeisenleiter");
    builder.add(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR, "Tiefeisenfalltür");
    builder.add(DistantMoonsBlocks.DEEPSLATE_BRICK_WALL_SLAB, "Gestufte Tiefenschieferziegelwand");
    builder.add(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, "Tiefenschiefer-Tiefeisenerz");
    builder.add(DistantMoonsBlocks.DEEPSLATE_TILE_WALL_SLAB, "Gestufte Tiefenschieferfliesenwand");
    builder.add(DistantMoonsBlocks.DIORITE_WALL_SLAB, "Gestufte Dioritwand");
    builder.add(DistantMoonsBlocks.END_STONE_BRICK_WALL_SLAB, "Gestufte Endsteinziegelwand");
    builder.add(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER, "Feste Tiefeisenleiter");
    builder.add(DistantMoonsBlocks.FIXED_IRON_LADDER, "Feste Eisenleiter");
    builder.add(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER, "Feste Schmiedeeisenleiter");
    builder.add(DistantMoonsBlocks.GRANITE_WALL_SLAB, "Gestufte Granitwand");
    builder.add(DistantMoonsBlocks.GRAY_PRISMARINE, "Grauer Primsarin");
    builder.add(DistantMoonsBlocks.GRAY_PRISMARINE_SLAB, "Graue Prismarinstufe");
    builder.add(DistantMoonsBlocks.GRAY_PRISMARINE_STAIRS, "Graue Prismarintreppe");
    builder.add(DistantMoonsBlocks.GRAY_PRISMARINE_WALL_SLAB, "Gestufte graue Prismarinwand");
    builder.add(DistantMoonsBlocks.INFESTED_CHISELED_DEEPSLATE, "Befallener gemeißelter Bruchtiefenschiefer");
    builder.add(DistantMoonsBlocks.INFESTED_COBBLED_DEEPSLATE, "Befallener Bruchtiefenschiefer");
    builder.add(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_BRICKS, "Befallene rissige Tiefenschieferziegel");
    builder.add(DistantMoonsBlocks.INFESTED_CRACKED_DEEPSLATE_TILES, "Befallene rissige Tiefenschieferfliesen");
    builder.add(DistantMoonsBlocks.INFESTED_DEEPSLATE_BRICKS, "Befallene Tiefenschieferziegel");
    builder.add(DistantMoonsBlocks.INFESTED_DEEPSLATE_TILES, "Befallene Tiefenschieferfliesen");
    builder.add(DistantMoonsBlocks.INFESTED_MOSSY_COBBLESTONE, "Befallener bemooster Bruchstein");
    builder.add(DistantMoonsBlocks.INFESTED_POLISHED_DEEPSLATE, "Befallener polierter Tiefenschiefer");
    builder.add(DistantMoonsBlocks.INFESTED_SMOOTH_STONE, "Befallener glatter Stein");
    builder.add(DistantMoonsBlocks.IRON_BAR_DOOR, "Eisengittertür");
    builder.add(DistantMoonsBlocks.IRON_FENCE, "Eisenzaun");
    builder.add(DistantMoonsBlocks.IRON_LADDER, "Eisenleiter");
    builder.add(DistantMoonsBlocks.JUNGLE_BEAM, "Tropenholzbalken");
    builder.add(DistantMoonsBlocks.JUNGLE_POLE, "Tropenholzpfosten");
    builder.add(DistantMoonsBlocks.JUNGLE_WALL_SLAB, "Gestufte Tropenholzwand");
    builder.add(DistantMoonsBlocks.MANGROVE_BEAM, "Mangrovenholzbalken");
    builder.add(DistantMoonsBlocks.MANGROVE_POLE, "Mangrovenholzpfosten");
    builder.add(DistantMoonsBlocks.MANGROVE_WALL_SLAB, "Gestufte Mangrovenholzwand");
    builder.add(DistantMoonsBlocks.MOSSY_COBBLESTONE_WALL_SLAB, "Gestufte bemooste Bruchsteinwand");
    builder.add(DistantMoonsBlocks.MOSSY_STONE_BRICK_WALL_SLAB, "Gestufte bemooste Steinziegelwand");
    builder.add(DistantMoonsBlocks.MUD_BRICK_WALL_SLAB, "Gestufte Schlammziegelwand");
    builder.add(DistantMoonsBlocks.NETHER_BRICK_WALL_SLAB, "Gestufte Netherziegelwand");
    builder.add(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, "Nethertiefeisenerz");
    builder.add(DistantMoonsBlocks.OAK_BEAM, "Eichenholzbalken");
    builder.add(DistantMoonsBlocks.OAK_POLE, "Eichenholzpfosten");
    builder.add(DistantMoonsBlocks.OAK_WALL_SLAB, "Gestufte Eichenholzwand");
    builder.add(DistantMoonsBlocks.PALE_OAK_BEAM, "Blasseichenholzbalken");
    builder.add(DistantMoonsBlocks.PALE_OAK_POLE, "Blasseichenholzpfosten");
    builder.add(DistantMoonsBlocks.PALE_OAK_WALL_SLAB, "Gestufte Blasseichenholzwand");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE, "Bleicher Prismarin");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_SLAB, "Bleiche Prismarinziegelstufe");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_STAIRS, "Bleiche Prismarinziegeltreppe");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_BRICK_WALL_SLAB, "Gestufte bleiche Prismarinziegelwand");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_BRICKS, "Bleiche Prismarinziegel");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_SLAB, "Bleiche Prismarinstufe");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_STAIRS, "Bleiche Prismarintreppe");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_TILE_SLAB, "Bleiche Prismarinfliesenstufe");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_TILE_STAIRS, "Bleiche Prismarinfliesentreppe");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_TILE_WALL_SLAB, "Gestufte bleiche Prismarinfliesenwand");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_TILES, "Bleiche Prismarinfliesen");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_WALL, "Bleiche Prismarinmauer");
    builder.add(DistantMoonsBlocks.PALE_PRISMARINE_WALL_SLAB, "Gestufte bleiche Prismarinwand");
    builder.add(DistantMoonsBlocks.PALE_SEA_LANTERN, "Bleiche Seelaterne");
    builder.add(DistantMoonsBlocks.POLISHED_ANDESITE_WALL_SLAB, "Gestufte polierte Andesitwand");
    builder.add(DistantMoonsBlocks.POLISHED_BLACKSTONE_BRICK_WALL_SLAB, "Gestufte polierte Schwarzsteinziegelwand");
    builder.add(DistantMoonsBlocks.POLISHED_BLACKSTONE_WALL_SLAB, "Gestufte polierte Schwarzsteinwand");
    builder.add(DistantMoonsBlocks.POLISHED_CUT_BASALT, "Polierter Geschnittener Basalt");
    builder.add(DistantMoonsBlocks.POLISHED_DEEPSLATE_WALL_SLAB, "Gestufte polierte Tiefenschieferwand");
    builder.add(DistantMoonsBlocks.POLISHED_DIORITE_WALL_SLAB, "Gestufte polierte Dioritwand");
    builder.add(DistantMoonsBlocks.POLISHED_GRANITE_WALL_SLAB, "Gestufte polierte Granitwand");
    builder.add(DistantMoonsBlocks.POLISHED_TUFF_WALL_SLAB, "Gestufte polierte Tuffwand");
    builder.add(DistantMoonsBlocks.PRISMARINE_BRICK_WALL_SLAB, "Gestufte Prismarinziegelwand");
    builder.add(DistantMoonsBlocks.PRISMARINE_TILE_SLAB, "Prismarinfliesenstufe");
    builder.add(DistantMoonsBlocks.PRISMARINE_TILE_STAIRS, "Prismarinfliesentreppe");
    builder.add(DistantMoonsBlocks.PRISMARINE_TILE_WALL_SLAB, "Gestufte Prismarinfliesenwand");
    builder.add(DistantMoonsBlocks.PRISMARINE_TILES, "Prismarinfliesen");
    builder.add(DistantMoonsBlocks.PRISMARINE_WALL_SLAB, "Gestufte Prismarinwand");
    builder.add(DistantMoonsBlocks.PURPUR_WALL_SLAB, "Gestufte Purpurwand");
    builder.add(DistantMoonsBlocks.QUARTZ_WALL_SLAB, "Gestufte Quarzwand");
    builder.add(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK, "Rohtiefeisenblock");
    builder.add(DistantMoonsBlocks.RED_NETHER_BRICK_WALL_SLAB, "Gestufte rote Netherziegelwand");
    builder.add(DistantMoonsBlocks.RED_SANDSTONE_WALL_SLAB, "Gestufte rote Sandsteinwand");
    builder.add(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK, "Feiner Tiefeisenblock");
    builder.add(DistantMoonsBlocks.RESIN_BRICK_WALL_SLAB, "Gestufte Harzziegelwand");
    builder.add(DistantMoonsBlocks.ROPE_LADDER, "Strickleiter");
    builder.add(DistantMoonsBlocks.SANDSTONE_WALL_SLAB, "Gestufte Sandsteinwand");
    builder.add(DistantMoonsBlocks.SMOOTH_QUARTZ_WALL_SLAB, "Gestufte glatte Quarzwand");
    builder.add(DistantMoonsBlocks.SMOOTH_RED_SANDSTONE_WALL_SLAB, "Gestufte glatte rote Sandsteinwand");
    builder.add(DistantMoonsBlocks.SMOOTH_SANDSTONE_WALL_SLAB, "Gestufte glatte Sandsteinwand");
    builder.add(DistantMoonsBlocks.SMOOTH_STONE_WALL_SLAB, "Gestufte glatte Steinwand");
    builder.add(DistantMoonsBlocks.SPRUCE_BEAM, "Fichtenholzbalken");
    builder.add(DistantMoonsBlocks.SPRUCE_POLE, "Fichtenholzpfosten");
    builder.add(DistantMoonsBlocks.SPRUCE_WALL_SLAB, "Gestufte Fichtenholzwand");
    builder.add(DistantMoonsBlocks.STONE_BRICK_WALL_SLAB, "Gestufte Steinziegelwand");
    builder.add(DistantMoonsBlocks.STONE_WALL_SLAB, "Gestufte Steinwand");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_LOG, "Entrindeter geschnittener Akazienstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_ACACIA_WOOD, "Entrindetes geschnittenes Akazienholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_BAMBOO_BLOCK, "Geschälter geschnittener Bambusblock");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_LOG, "Entrindeter geschnittener Birkenstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_BIRCH_WOOD, "Entrindetes geschnittenes Birkenholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_LOG, "Entrindeter geschnittener Kirschstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_CHERRY_WOOD, "Entrindetes geschnittenes Kirschholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_HYPHAE, "Geschälte geschnittene Karmesinhyphen");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_CRIMSON_STEM, "Geschälter geschnittener Karmesinstiel");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_LOG, "Entrindeter geschnittener Schwarzeichenstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_DARK_OAK_WOOD, "Entrindetes geschnittenes Schwarzeichenholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_LOG, "Entrindeter geschnittener Tropenbaumstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_JUNGLE_WOOD, "Entrindetes geschnittenes Tropenholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_LOG, "Entrindeter geschnittener Mangrovenstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_MANGROVE_WOOD, "Entrindetes geschnittenes Mangrovenholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_OAK_LOG, "Entrindeter geschnittener Eichenstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_OAK_WOOD, "Entrindetes geschnittenes Eichenholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_LOG, "Entrindeter geschnittener Blasseichenstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_PALE_OAK_WOOD, "Entrindetes geschnittenes Blasseichenholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_LOG, "Entrindeter geschnittener Fichtenstamm");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_SPRUCE_WOOD, "Entrindetes geschnittenes Fichtenholz");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_WARPED_HYPHAE, "Geschälte geschnittene Wirrhyphen");
    builder.add(DistantMoonsBlocks.STRIPPED_CUT_WARPED_STEM, "Geschälter geschnittener Wirrstiel");
    builder.add(DistantMoonsBlocks.TUFF_BRICK_WALL_SLAB, "Gestufte Tuffziegelwand");
    builder.add(DistantMoonsBlocks.TUFF_WALL_SLAB, "Gestufte Tuffwand");
    builder.add(DistantMoonsBlocks.UNDERWORLD_LANTERN, "Unterweltlaterne");
    builder.add(DistantMoonsBlocks.WARPED_BEAM, "Wirrbalken");
    builder.add(DistantMoonsBlocks.WARPED_POLE, "Wirrpfosten");
    builder.add(DistantMoonsBlocks.WARPED_WALL_SLAB, "Gestufte Wirrwand");
    builder.add(DistantMoonsBlocks.WROUGHT_IRON_BAR_DOOR, "Schmiedeeisengittertür");
    builder.add(DistantMoonsBlocks.WROUGHT_IRON_BARS, "Schmiedeeisengitter");
    builder.add(DistantMoonsBlocks.WROUGHT_IRON_FENCE, "Schmiedeeisenzaun");
    builder.add(DistantMoonsBlocks.WROUGHT_IRON_LADDER, "Schmiedeeisenleiter");

    helper.generateDyedBlockNames(DistantMoonsBlocks.DYED_PILLOWS, "%c Kissen", OBJECTIVE_COLOR_ADJECTIVES);

    builder.add(DistantMoonsBlocks.CUT_COPPER_WALL_SLAB, "Gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.EXPOSED_CUT_COPPER_WALL_SLAB, "Angelaufene gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.WEATHERED_CUT_COPPER_WALL_SLAB, "Verwitterte gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.OXIDIZED_CUT_COPPER_WALL_SLAB, "Oxidierte gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.WAXED_CUT_COPPER_WALL_SLAB, "Gewachste gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.WAXED_EXPOSED_CUT_COPPER_WALL_SLAB, "Gewachste angelaufene gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.WAXED_WEATHERED_CUT_COPPER_WALL_SLAB, "Gewachste verwitterte gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL_SLAB, "Gewachste oxidierte gestufte geschnittene Kupferwand");
    builder.add(DistantMoonsBlocks.EXPOSED_IRON_BLOCK, "Angelaufener Eisenblock");
    builder.add(DistantMoonsBlocks.WEATHERED_IRON_BLOCK, "Verwitterter Eisenblock");
    builder.add(DistantMoonsBlocks.RUSTED_IRON_BLOCK, "Verrosteter Eisenblock");
    builder.add(DistantMoonsBlocks.WAXED_IRON_BLOCK, "Gewachster Eisenblock");
    builder.add(DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK, "Gewachster angelaufener Eisenblock");
    builder.add(DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK, "Gewachster verwitterter Eisenblock");
    builder.add(DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK, "Gewachster verrosteter Eisenblock");

    builder.add("commands.distant-moons.affliction.add.failed", "Gebrechen konnte nicht angewandt werden (das Ziel ist resistent, besitzt eine stärkere Stufe oder die Operation hat ein unzulässiges Gebrechen erzäugt)");
    builder.add("commands.distant-moons.affliction.add.success.multiple", "Gebrechen %s wurde zu %s Zielen hinzugefügt");
    builder.add("commands.distant-moons.affliction.add.success.single", "Gebrechen %s wurde zu %s hinzugefügt");
    builder.add("commands.distant-moons.affliction.clear.everything.failed", "Ziel besitzt keine zu entfernenden Gebrechen");
    builder.add("commands.distant-moons.affliction.clear.everything.success.multiple", "Alle Gebrechen wurden von %s Zielen entfernt");
    builder.add("commands.distant-moons.affliction.clear.everything.success.single", "Alle Gebrechen wurden von %s entfernt");
    builder.add("commands.distant-moons.affliction.clear.specific.failed", "Ziel besitzt das zu entfernende Gebrechen nicht");
    builder.add("commands.distant-moons.affliction.clear.specific.success.multiple", "Gebrechen %s wurde von %s Zielen entfernt");
    builder.add("commands.distant-moons.affliction.clear.specific.success.single", "Gebrechen %s wurde von %s entfernt");
    builder.add("commands.distant-moons.affliction.get.failed.affliction", "Gebrechen kann nicht abgefragt werden (Ziel ist resistent gegen Gebrechen)");
    builder.add("commands.distant-moons.affliction.get.failed.entity", "Gebrechen kann nicht abgefragt werden (target is immune to afflictions)");
    builder.add("commands.distant-moons.affliction.get.success.progression", "Gebrechen %s ist zu %s auf dem Ziel vorangeschritten");
    builder.add("commands.distant-moons.affliction.get.success.stage", "Gebrechen %s ist zur Stufe %s auf dem Ziel vorangeschritten");
    builder.add("commands.distant-moons.affliction.give.failed", "Gebrechen konnte nicht angewandt werden (das Ziel ist entweder resistent oder besitzt eine stärkere Stufe)");
    builder.add("commands.distant-moons.affliction.give.success.multiple", "Gebrechen %s wurde auf %s Ziele angewandt");
    builder.add("commands.distant-moons.affliction.give.success.single", "Gebrechen %s wurde auf %s angewandt");
    builder.add("commands.distant-moons.affliction.set.failed", "Ziel ist resistent gegen Gebrechen");
    builder.add("commands.distant-moons.affliction.set.success.multiple", "Gebrechen %s wurde auf %s Ziele angewandt");
    builder.add("commands.distant-moons.affliction.set.success.single", "Gebrechen %s wurde auf %s angewandt");

    builder.add("container.distant-moons.blast_furnace", "Hochofen");

    builder.add(DistantMoonsEntityTypes.SITTING_SPOT, "Sitzplatz");

    builder.add(DistantMoonsItems.COILED_ROPE_LADDER, "Aufgerollte Strickleiter");
    builder.add(DistantMoonsItems.COKE, "Koks");
    builder.add(DistantMoonsItems.COPPER_ROD, "Kupferstange");
    builder.add(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, "Grober Tiefeisenklumpen");
    builder.add(DistantMoonsItems.DEEP_IRON_AXE, "Tiefeisenaxt");
    builder.add(DistantMoonsItems.DEEP_IRON_BOOTS, "Tiefeisenstiefel");
    builder.add(DistantMoonsItems.DEEP_IRON_CHESTPLATE, "Tiefeisenharnisch");
    builder.add(DistantMoonsItems.DEEP_IRON_HELMET, "Tiefeisenhelm");
    builder.add(DistantMoonsItems.DEEP_IRON_HOE, "Tiefeisenhacke");
    builder.add(DistantMoonsItems.DEEP_IRON_HORSE_ARMOR, "Tiefeisener Rossharnisch");
    builder.add(DistantMoonsItems.DEEP_IRON_LEGGINGS, "Tiefeisenbeinschutz");
    builder.add(DistantMoonsItems.DEEP_IRON_PICKAXE, "Tiefeisenspitzhacke");
    builder.add(DistantMoonsItems.DEEP_IRON_SHOVEL, "Tiefeisenschaufel");
    builder.add(DistantMoonsItems.DEEP_IRON_SWORD, "Tiefeisenschwert");
    builder.add(DistantMoonsItems.IRON_ROD, "Eisenstange");
    builder.add(DistantMoonsItems.PALE_PRISMARINE_SHARD, "Bleiche Prismarinscherbe");
    builder.add(DistantMoonsItems.RAW_DEEP_IRON, "Rohtiefeisen");
    builder.add(DistantMoonsItems.REFINED_DEEP_IRON_INGOT, "Feiner Tiefeisenbarren");
    builder.add(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, "Feiner Tiefeisenklumpen");
    builder.add(DistantMoonsItems.REFINED_DEEP_IRON_ROD, "Feine Tiefeisenstange");
    builder.add(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, "Gerösteter brauner Pilz");
    builder.add(DistantMoonsItems.ROTTEN_FISH, "Verrotteter Fisch");
    builder.add(DistantMoonsItems.UNDERWORLD_DUST, "Unterweltstaub");
    builder.add(DistantMoonsItems.WROUGHT_IRON_ROD, "Schmiedeeisenstange");


    builder.add("subtitles.distant-moons.block.large_blast_furnace.blast", "Hochofen brüllt");
    builder.add("subtitles.distant-moons.entity.begin_curing_curse", "Fluch schwindet");
    builder.add("subtitles.distant-moons.entity.finish_curing_curse", "Fluch vergeht");

    builder.add(DistantMoonsStats.INTERACT_WITH_LARGE_BLAST_FURNACE, "Hochöfen benutzt");

    builder.add(DistantMoonsItemTags.DYED_PILLOW, "Gefärbter Polster");
    builder.add(DistantMoonsItemTags.REPAIRS_DEEP_IRON_EQUIPMENT, "Repariert Tiefeisenausrüstung");
    builder.add(DistantMoonsItemTags.SMELTING_FUEL_WOOD_BLOCK, "Holzblock Brennstoff");
    builder.add(DistantMoonsItemTags.SMELTING_FUEL_WOOD_HALF_BLOCK, "Halber Holzblock Brennstoff");
    builder.add(DistantMoonsItemTags.SWORD, "Schwert");

    helper.generateEnchantmentLevels(11, 255);
  }
}
