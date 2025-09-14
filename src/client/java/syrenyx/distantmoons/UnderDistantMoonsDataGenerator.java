package syrenyx.distantmoons;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import syrenyx.distantmoons.datagen.DistantMoonsRecipeProvider;
import syrenyx.distantmoons.datagen.lang.en_us;

public class UnderDistantMoonsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(DistantMoonsRecipeProvider::new);
    pack.addProvider(en_us::new);
	}
}
