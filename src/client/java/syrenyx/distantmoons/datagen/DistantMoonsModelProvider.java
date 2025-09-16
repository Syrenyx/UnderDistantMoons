package syrenyx.distantmoons.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.initializers.DistantMoonsItems;

public class DistantMoonsModelProvider extends FabricModelProvider {

  public DistantMoonsModelProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator generator) {
    generator.registerSimpleCubeAll(DistantMoonsBlocks.BLACKSTONE_DEEP_IRON_ORE);
    generator.registerSimpleCubeAll(DistantMoonsBlocks.CHARCOAL_BLOCK);
    generator.registerSimpleCubeAll(DistantMoonsBlocks.COKE_BLOCK);
    generator.registerSimpleCubeAll(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
    generator.registerSimpleCubeAll(DistantMoonsBlocks.DEEPSLATE_DEEP_IRON_ORE);
    generator.registerSimpleCubeAll(DistantMoonsBlocks.NETHERRACK_DEEP_IRON_ORE);
    generator.registerSimpleCubeAll(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
    generator.registerSimpleCubeAll(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {
    generator.register(DistantMoonsItems.COKE, Models.GENERATED);
    generator.register(DistantMoonsItems.CRUDE_DEEP_IRON_CHUNK, Models.GENERATED);
    generator.register(DistantMoonsItems.IRON_ROD, Models.HANDHELD);
    generator.register(DistantMoonsItems.RAW_DEEP_IRON, Models.GENERATED);
    generator.register(DistantMoonsItems.REFINED_DEEP_IRON_INGOT, Models.GENERATED);
    generator.register(DistantMoonsItems.REFINED_DEEP_IRON_NUGGET, Models.GENERATED);
    generator.register(DistantMoonsItems.REFINED_DEEP_IRON_ROD, Models.HANDHELD);
    generator.register(DistantMoonsItems.ROASTED_BROWN_MUSHROOM, Models.HANDHELD);
    generator.register(DistantMoonsItems.UNDERWORLD_DUST, Models.GENERATED);
    generator.register(DistantMoonsItems.WROUGHT_IRON_ROD, Models.HANDHELD);
  }
}
