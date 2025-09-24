package syrenyx.distantmoons.datagen.loot_table;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBlockLootTableProvider extends FabricBlockLootTableProvider {

  public DistantMoonsBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {
    this.addDrop(DistantMoonsBlocks.CHARCOAL_BLOCK);
    this.addDrop(DistantMoonsBlocks.COKE_BLOCK);
    this.addDrop(DistantMoonsBlocks.CRUDE_DEEP_IRON_BLOCK);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_BARS);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_FENCE);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.DEEP_IRON_TRAPDOOR);
    this.addDrop(DistantMoonsBlocks.FIXED_DEEP_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.FIXED_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.FIXED_WROUGHT_IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.IRON_FENCE);
    this.addDrop(DistantMoonsBlocks.IRON_LADDER);
    this.addDrop(DistantMoonsBlocks.RAW_DEEP_IRON_BLOCK);
    this.addDrop(DistantMoonsBlocks.REFINED_DEEP_IRON_BLOCK);
    this.addDrop(DistantMoonsBlocks.WROUGHT_IRON_BARS);
    this.addDrop(DistantMoonsBlocks.WROUGHT_IRON_FENCE);
    this.addDrop(DistantMoonsBlocks.WROUGHT_IRON_LADDER);
  }
}
