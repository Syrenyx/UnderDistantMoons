package syrenyx.distantmoons.content.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
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
  public boolean generate(FeatureContext<Config> context) {
    Vec3d initialVector = VectorUtil.randomPointOnSphere(context.getRandom(), BRANCH_DISTANCE);
    generateBranch(0, context.getOrigin().mutableCopy().add(VectorUtil.toVec3i(initialVector)), initialVector, context);
    generateBranch(0, context.getOrigin().mutableCopy(), initialVector.multiply(-1.0), context);
    return true;
  }

  private void generateBranch(int depth, BlockPos pos, Vec3d branchVector, FeatureContext<Config> context) {
    if (depth >= MAX_DEPTH) return;
    Random random = context.getRandom();
    generateBlob(pos, RandomUtil.nextFloatBetween(MIN_BLOB_RADIUS, MAX_BLOB_RADIUS, random), context);
    if (depth < MAX_DEPTH - 1 && random.nextFloat() <= BRANCH_CHANCE) {
      Vec3d newBranchVector = VectorUtil.randomCrossProductVector(random, branchVector, branchVector.length());
      generateBranch(
          Math.min(depth * 2, MAX_DEPTH - 1),
          pos.mutableCopy().add(VectorUtil.toVec3i(newBranchVector)),
          VectorUtil.randomOffsetVector(random, newBranchVector, random.nextBetween(0, MAX_OFFSET)),
          context
      );
    }
    generateBranch(
        depth + 1,
        pos.add(VectorUtil.toVec3i(branchVector)),
        VectorUtil.randomOffsetVector(random, branchVector, random.nextBetween(0, MAX_OFFSET)),
        context
    );
  }

  private void generateBlob(BlockPos pos, float radius, FeatureContext<Config> context) {
    StructureWorldAccess world = context.getWorld();
    Config config = context.getConfig();
    int weightSum = config.oreWeight() + config.rawOreBlockWeight() + config.stoneWeight();
    int iterationRadius = MathHelper.ceil(radius);
    BlockPos.iterate(
        pos.getX() - iterationRadius, pos.getY() - iterationRadius, pos.getZ() - iterationRadius,
        pos.getX() + iterationRadius, pos.getY() + iterationRadius, pos.getZ() + iterationRadius
    ).forEach(iteratedPos -> {
      if (!iteratedPos.isWithinDistance(pos, radius)) return;
      int weight = context.getRandom().nextBetween(1, weightSum);
      if (weight <= config.oreWeight()) world.setBlockState(iteratedPos, context.getConfig().ore().getDefaultState(), Block.NOTIFY_LISTENERS);
      else if (weight <= config.rawOreBlockWeight() + config.oreWeight()) world.setBlockState(iteratedPos, context.getConfig().rawOreBlock().getDefaultState(), Block.NOTIFY_LISTENERS);
      else world.setBlockState(iteratedPos, context.getConfig().stone().getDefaultState(), Block.NOTIFY_LISTENERS);
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
