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
  public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
    translationBuilder.add("affliction.distant-moons.curse", "Curse");
    translationBuilder.add("affliction.distant-moons.illness", "Illness");

    translationBuilder.add("commands.affliction.clear.everything.failed", "Target has no afflictions to remove");
    translationBuilder.add("commands.affliction.clear.everything.success.multiple", "Removed every affliction from %s targets");
    translationBuilder.add("commands.affliction.clear.everything.success.single", "Removed every affliction from %s");
    translationBuilder.add("commands.affliction.clear.specific.failed", "Target doesn't have the requested afflictions");
    translationBuilder.add("commands.affliction.clear.specific.success.multiple", "Removed affliction %s from %s targets");
    translationBuilder.add("commands.affliction.clear.specific.success.single", "Removed affliction %s from %s");
    translationBuilder.add("commands.affliction.give.failed", "Unable to apply this affliction (target is either immune to afflictions, or has something stronger)");
    translationBuilder.add("commands.affliction.give.success.multiple", "Applied affliction %s to %s targets");
    translationBuilder.add("commands.affliction.give.success.single", "Applied affliction %s to %s");
    translationBuilder.add("commands.affliction.set.failed", "Target is immune to afflictions");
    translationBuilder.add("commands.affliction.set.success.multiple", "Applied affliction %s to %s targets");
    translationBuilder.add("commands.affliction.set.success.single", "Applied affliction %s to %s");
  }
}
