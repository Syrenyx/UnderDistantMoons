package syrenyx.distantmoons.content.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.Vec3;
import syrenyx.distantmoons.utility.RandomUtil;
import syrenyx.distantmoons.utility.VectorUtil;

public class BranchingOreVeinFeature extends Feature<BranchingOreVeinFeature.Config> {

  private static final float MIN_BLOB_RADIUS = 3;
  private static final float MAX_BLOB_RADIUS = 4.5F;
  private static final float BRANCH_CHANCE = 0.1F;
  private static final float BRANCH_DISTANCE = 5;
  private static final int MAX_DEPTH = 3;
  private static final int MAX_OFFSET = 5;

  public BranchingOreVeinFeature(Codec<Config> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean place(FeaturePlaceContext<Config> context) {
    Vec3 initialVector = VectorUtil.randomPointOnSphere(context.random(), BRANCH_DISTANCE);
    generateBranch(0, context.origin().mutable().offset(VectorUtil.toVec3i(initialVector)), initialVector, context);
    generateBranch(0, context.origin().mutable(), initialVector.scale(-1.0), context);
    return true;
  }

  private void generateBranch(int depth, BlockPos pos, Vec3 branchVector, FeaturePlaceContext<Config> context) {
    if (depth >= MAX_DEPTH) return;
    RandomSource random = context.random();
    generateBlob(pos, RandomUtil.nextFloatBetween(MIN_BLOB_RADIUS, MAX_BLOB_RADIUS, random), context);
    if (depth < MAX_DEPTH - 1 && random.nextFloat() <= BRANCH_CHANCE) {
      Vec3 newBranchVector = VectorUtil.randomCrossProductVector(random, branchVector, branchVector.length());
      generateBranch(
          Math.min(depth * 2, MAX_DEPTH - 1),
          pos.mutable().offset(VectorUtil.toVec3i(newBranchVector)),
          VectorUtil.randomOffsetVector(random, newBranchVector, random.nextIntBetweenInclusive(0, MAX_OFFSET)),
          context
      );
    }
    generateBranch(
        depth + 1,
        pos.offset(VectorUtil.toVec3i(branchVector)),
        VectorUtil.randomOffsetVector(random, branchVector, random.nextIntBetweenInclusive(0, MAX_OFFSET)),
        context
    );
  }

  private void generateBlob(BlockPos pos, float radius, FeaturePlaceContext<Config> context) {
    WorldGenLevel world = context.level();
    Config config = context.config();
    int weightSum = config.oreWeight() + config.rawOreBlockWeight() + config.stoneWeight();
    int iterationRadius = Mth.ceil(radius);
    BlockPos.betweenClosed(
        pos.getX() - iterationRadius, pos.getY() - iterationRadius, pos.getZ() - iterationRadius,
        pos.getX() + iterationRadius, pos.getY() + iterationRadius, pos.getZ() + iterationRadius
    ).forEach(iteratedPos -> {
      if (!iteratedPos.closerThan(pos, radius)) return;
      int weight = context.random().nextIntBetweenInclusive(1, weightSum);
      if (weight <= config.oreWeight()) world.setBlock(iteratedPos, context.config().ore().defaultBlockState(), Block.UPDATE_CLIENTS);
      else if (weight <= config.rawOreBlockWeight() + config.oreWeight()) world.setBlock(iteratedPos, context.config().rawOreBlock().defaultBlockState(), Block.UPDATE_CLIENTS);
      else world.setBlock(iteratedPos, context.config().stone().defaultBlockState(), Block.UPDATE_CLIENTS);
    });
  }

  public record Config(
      Block ore,
      Block rawOreBlock,
      Block stone,
      int oreWeight,
      int rawOreBlockWeight,
      int stoneWeight
  ) implements FeatureConfiguration {

    private static final int DEFAULT_ORE_WEIGHT = 4;
    private static final int DEFAULT_RAW_ORE_BLOCK_WEIGHT = 1;
    private static final int DEFAULT_STONE_WEIGHT = 15;

    public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("ore").forGetter(Config::ore),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("raw_ore_block").forGetter(Config::rawOreBlock),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("stone").forGetter(Config::stone),
            Codec.INT.optionalFieldOf("ore_weight", DEFAULT_ORE_WEIGHT).forGetter(Config::oreWeight),
            Codec.INT.optionalFieldOf("raw_ore_block_weight", DEFAULT_RAW_ORE_BLOCK_WEIGHT).forGetter(Config::rawOreBlockWeight),
            Codec.INT.optionalFieldOf("stone_weight", DEFAULT_STONE_WEIGHT).forGetter(Config::stoneWeight)
        )
        .apply(instance, Config::new)
    );
  }
}
