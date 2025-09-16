package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;
import syrenyx.distantmoons.references.DistantMoonsTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsBlockTagProvider extends FabricTagProvider.BlockTagProvider {

  public DistantMoonsBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

    //BLOCK GROUPS
    this.valueLookupBuilder(DistantMoonsTags.SPIKED_FENCE)
        .add(DistantMoonsBlocks.IRON_FENCE, DistantMoonsBlocks.REFINED_DEEP_IRON_FENCE, DistantMoonsBlocks.WROUGHT_IRON_FENCE);

    //CONNECTION TARGETS
    this.valueLookupBuilder(DistantMoonsTags.SPIKED_FENCE_ALWAYS_CONNECTS_TO)
        .addTag(DistantMoonsTags.SPIKED_FENCE);
    this.valueLookupBuilder(DistantMoonsTags.SPIKED_FENCE_NEVER_CONNECTS_TO);

    //IMMUNITIES
    this.valueLookupBuilder(DistantMoonsTags.IMMUNE_TO_DRAGON);
    this.valueLookupBuilder(DistantMoonsTags.IMMUNE_TO_WITHER);

    //MINING TOOL GROUPS
    this.valueLookupBuilder(DistantMoonsTags.MINING_TIER_DIAMOND);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TIER_IRON);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TIER_STONE);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_AXE);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_HOE);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_PICKAXE);
    this.valueLookupBuilder(DistantMoonsTags.MINING_TYPE_SHOVEL);

    //MISCELLANEOUS
    this.valueLookupBuilder(DistantMoonsTags.CLIMBABLE)
        .add(DistantMoonsBlocks.IRON_LADDER, DistantMoonsBlocks.REFINED_DEEP_IRON_LADDER, DistantMoonsBlocks.WROUGHT_IRON_LADDER);

    //VANILLA TAG REDIRECTS
    this.valueLookupBuilder(BlockTags.AXE_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_AXE);
    this.valueLookupBuilder(BlockTags.CLIMBABLE).addTag(DistantMoonsTags.CLIMBABLE);
    this.valueLookupBuilder(BlockTags.DRAGON_IMMUNE).addTag(DistantMoonsTags.IMMUNE_TO_DRAGON);
    this.valueLookupBuilder(BlockTags.HOE_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_HOE);
    this.valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL).addTag(DistantMoonsTags.MINING_TIER_DIAMOND);
    this.valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL).addTag(DistantMoonsTags.MINING_TIER_IRON);
    this.valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL).addTag(DistantMoonsTags.MINING_TIER_STONE);
    this.valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_PICKAXE);
    this.valueLookupBuilder(BlockTags.SHOVEL_MINEABLE).addTag(DistantMoonsTags.MINING_TYPE_SHOVEL);
    this.valueLookupBuilder(BlockTags.WITHER_IMMUNE).addTag(DistantMoonsTags.IMMUNE_TO_WITHER);
  }
}
