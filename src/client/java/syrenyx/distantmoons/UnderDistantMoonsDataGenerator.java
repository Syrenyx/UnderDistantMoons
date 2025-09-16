package syrenyx.distantmoons;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import syrenyx.distantmoons.datagen.DistantMoonsModelProvider;
import syrenyx.distantmoons.datagen.DistantMoonsRecipeProvider;
import syrenyx.distantmoons.datagen.lang.en_us;
import syrenyx.distantmoons.datagen.loot_table.DistantMoonsBlockLootTableProvider;
import syrenyx.distantmoons.datagen.tag.DistantMoonsBlockTagProvider;

public class UnderDistantMoonsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

    //MISCELLANEOUS
    pack.addProvider(DistantMoonsModelProvider::new);
    pack.addProvider(DistantMoonsRecipeProvider::new);
    pack.addProvider(DistantMoonsBlockLootTableProvider::new);

    //TAGS
    pack.addProvider(DistantMoonsBlockTagProvider::new);

    //LANGUAGES
    pack.addProvider(en_us::new);
	}
}
