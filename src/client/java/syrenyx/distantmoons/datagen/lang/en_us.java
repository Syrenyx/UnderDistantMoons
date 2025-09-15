package syrenyx.distantmoons.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

import java.util.concurrent.CompletableFuture;

public class en_us extends FabricLanguageProvider {

  public en_us(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, "en_us", registryLookup);
  }

  @Override
  public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
    builder.add("affliction.distant-moons.curse_of_the_night", "Curse of the Night");
    builder.add("affliction.distant-moons.curse_of_the_night_waning", "Waning Curse of the Night");
    builder.add("affliction.distant-moons.curse_of_the_underworld", "Curse of the Underworld");

    builder.add("affliction.progression_tooltip", "%s / %s");

    builder.add(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE, "Blackstone Deep Iron Ore");
    builder.add(DistantMoonsBlocks.CHARCOAL_BLOCK, "Block of Charcoal");
    builder.add(DistantMoonsBlocks.COKE_BLOCK, "Block of Coke");
    builder.add(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK, "Block of Crude Deep Iron");
    builder.add(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE, "Deepslate Deep Iron Ore");
    builder.add(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE, "Netherrack Deep Iron Ore");
    builder.add(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK, "Block of Raw Deep Iron");
    builder.add(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK, "Block of Refined Deep Iron");

    builder.add("commands.affliction.add.failed", "Unable to apply this affliction (target is either immune to afflictions, has something stronger, or the operation produced an invalid affliction instance)");
    builder.add("commands.affliction.add.success.multiple", "Added affliction %s to %s targets");
    builder.add("commands.affliction.add.success.single", "Applied affliction %s to %s");
    builder.add("commands.affliction.clear.everything.failed", "Target has no afflictions to remove");
    builder.add("commands.affliction.clear.everything.success.multiple", "Removed every affliction from %s targets");
    builder.add("commands.affliction.clear.everything.success.single", "Removed every affliction from %s");
    builder.add("commands.affliction.clear.specific.failed", "Target doesn't have the requested afflictions");
    builder.add("commands.affliction.clear.specific.success.multiple", "Removed affliction %s from %s targets");
    builder.add("commands.affliction.clear.specific.success.single", "Removed affliction %s from %s");
    builder.add("commands.affliction.get.failed.affliction", "Unable to get this affliction (target does not have this affliction)");
    builder.add("commands.affliction.get.failed.entity", "Unable to get this affliction (target is immune to afflictions)");
    builder.add("commands.affliction.get.success.progression", "Affliction %s has progressed to %s on target");
    builder.add("commands.affliction.get.success.stage", "Affliction %s has progressed to stage %s on target");
    builder.add("commands.affliction.give.failed", "Unable to apply this affliction (target is either immune to afflictions, or has something stronger)");
    builder.add("commands.affliction.give.success.multiple", "Applied affliction %s to %s targets");
    builder.add("commands.affliction.give.success.single", "Applied affliction %s to %s");
    builder.add("commands.affliction.set.failed", "Target is immune to afflictions");
    builder.add("commands.affliction.set.success.multiple", "Applied affliction %s to %s targets");
    builder.add("commands.affliction.set.success.single", "Applied affliction %s to %s");

    builder.add(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, "Crude Deep Iron Chunk");
    builder.add(DistantMoonsItems.COKE, "Coke");
    builder.add(DistantMoonsItems.IRON_ROD, "Iron Rod");
    builder.add(DistantMoonsItems.RAW_DEEP_IRON, "Raw Deep Iron");
    builder.add(DistantMoonsItems.REFINED_DEEP_IRON_INGOT, "Refined Deep Iron Ingot");
    builder.add(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, "Refined Deep Iron Nugget");
    builder.add(DistantMoonsItems.REFINED_DEEP_IRON_ROD, "Refined Deep Iron Rod");
    builder.add(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, "Roasted Brown Mushroom");
    builder.add(DistantMoonsItems.UNDERWORLD_DUST, "Underworld Dust");

    EnchantmentLevelUtil.generateEnchantmentLevels(builder, 11, 255);
  }
}
