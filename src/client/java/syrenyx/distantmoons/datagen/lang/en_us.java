package syrenyx.distantmoons.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class en_us extends FabricLanguageProvider {

  public en_us(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, "en_us", registryLookup);
  }

  @Override
  public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
    builder.add("affliction.distant-moons.curse", "Curse");
    builder.add("affliction.distant-moons.illness", "Illness");

    builder.add("affliction.progression_tooltip", "%s / %s");

    builder.add("commands.affliction.add.failed", "Unable to apply this affliction (target is either immune to afflictions, has something stronger, or the operation produced an invalid affliction)");
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

    builder.add("enchantment.distant-moons.curse", "Curse");

    EnchantmentLevelUtil.generateEnchantmentLevels(builder, 11, 255);
  }
}
