package syrenyx.distantmoons.content.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import syrenyx.distantmoons.utility.VectorUtil;

public class BranchingOreVeinFeature extends Feature<BranchingOreVeinFeature.Config> {

  private static final int BLOB_RADIUS = 3;
  private static final float BRANCH_CHANCE = 0.175F;
  private static final float BRANCH_DISTANCE = 5;
  private static final int MAX_DEPTH = 15;

  public BranchingOreVeinFeature(Codec<Config> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean generate(FeatureContext<Config> context) {
    generateBranch(0, context.getOrigin().mutableCopy(), VectorUtil.randomPointOnSphere(context.getRandom(), BRANCH_DISTANCE), context);
    return true;
  }

  private void generateBranch(int depth, BlockPos pos, Vec3d branchVector, FeatureContext<Config> context) {
    if (depth >= MAX_DEPTH) return;
    generateBlob(pos, BLOB_RADIUS, context);
    if (context.getRandom().nextFloat() <= BRANCH_CHANCE) {
      Vec3d newBranchVector = VectorUtil.randomCrossProductVector(context.getRandom(), branchVector, branchVector.length());
      generateBranch(depth + 1, pos.mutableCopy().add(VectorUtil.toVec3i(newBranchVector)), newBranchVector, context);
    }
    generateBranch(depth + 1, pos.add(VectorUtil.toVec3i(branchVector)), branchVector, context);
  }

  private void generateBlob(BlockPos pos, int radius, FeatureContext<Config> context) {
    StructureWorldAccess world = context.getWorld();
    Config config = context.getConfig();
    int weightSum = config.oreWeight() + config.rawOreBlockWeight() + config.stoneWeight();
    BlockPos.iterate(
        pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
        pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius
    ).forEach(iteratedPos -> {
      if (!iteratedPos.isWithinDistance(pos, radius)) return;
      int weight = context.getRandom().nextBetween(1, weightSum);
      if (weight <= config.oreWeight()) world.setBlockState(iteratedPos, context.getConfig().ore().getDefaultState(), Block.NOTIFY_LISTENERS);
      if (weight <= config.rawOreBlockWeight() - config.oreWeight()) world.setBlockState(iteratedPos, context.getConfig().rawOreBlock().getDefaultState(), Block.NOTIFY_LISTENERS);
      world.setBlockState(iteratedPos, context.getConfig().stone().getDefaultState(), Block.NOTIFY_LISTENERS);
    });
  }

  public record Config(
      Block ore,
      Block rawOreBlock,
      Block stone,
      int oreWeight,
      int rawOreBlockWeight,
      int stoneWeight
  ) implements FeatureConfig {

    private static final int DEFAULT_ORE_WEIGHT = 4;
    private static final int DEFAULT_RAW_ORE_BLOCK_WEIGHT = 1;
    private static final int DEFAULT_STONE_WEIGHT = 15;

    public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Registries.BLOCK.getCodec().fieldOf("ore").forGetter(Config::ore),
            Registries.BLOCK.getCodec().fieldOf("raw_ore_block").forGetter(Config::rawOreBlock),
            Registries.BLOCK.getCodec().fieldOf("stone").forGetter(Config::stone),
            Codec.INT.optionalFieldOf("ore_weight", DEFAULT_ORE_WEIGHT).forGetter(Config::oreWeight),
            Codec.INT.optionalFieldOf("raw_ore_block_weight", DEFAULT_RAW_ORE_BLOCK_WEIGHT).forGetter(Config::rawOreBlockWeight),
            Codec.INT.optionalFieldOf("stone_weight", DEFAULT_STONE_WEIGHT).forGetter(Config::stoneWeight)
        )
        .apply(instance, Config::new)
    );
  }
}
