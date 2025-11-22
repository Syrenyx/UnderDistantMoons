package syrenyx.distantmoons.content.worldgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;
import java.util.function.Predicate;

public class OreGeodeFeature extends Feature<OreGeodeFeature.Config> {

  public OreGeodeFeature(Codec<Config> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean generate(FeatureContext<Config> context) {
    Config config = context.getConfig();
    Random random = context.getRandom();
    if (random.nextFloat() > config.generationChance) return false;
    BlockPos blockPos = context.getOrigin();
    StructureWorldAccess structureWorldAccess = context.getWorld();
    int minGenOffset = config.minGenOffset;
    int maxGenOffset = config.maxGenOffset;
    List<Pair<BlockPos, Integer>> list = Lists.newLinkedList();
    int distributionPoint = config.distributionPoints.get(random);
    ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(structureWorldAccess.getSeed()));
    DoublePerlinNoiseSampler doublePerlinNoiseSampler = DoublePerlinNoiseSampler.create(chunkRandom, -4, 1.0);
    double distributionDistance = (double) distributionPoint / config.outerWallDistance.getMax();
    Config.LayerConfig layerConfig = config.layerConfig;
    double fillingThreshold = 1.0 / Math.sqrt(layerConfig.fillingSize);
    double innerLayerThreshold = 1.0 / Math.sqrt(layerConfig.innerLayerThreshold + distributionDistance);
    double outerLayerThreshold = 1.0 / Math.sqrt(layerConfig.outerLayerThreshold + distributionDistance);

    int i = 0;
    for (int n = 0; n < distributionPoint; n++) {
      BlockPos testPos = blockPos.add(config.outerWallDistance.get(random), config.outerWallDistance.get(random), config.outerWallDistance.get(random));
      BlockState testState = structureWorldAccess.getBlockState(testPos);
      if ((testState.isAir() || testState.isIn(layerConfig.invalidBlocks)) && ++i > config.invalidBlocksThreshold) return false;
      list.add(Pair.of(testPos, config.pointOffset.get(random)));
    }

    Predicate<BlockState> predicate = notInBlockTagPredicate(config.layerConfig.cannotReplace);

    for (BlockPos blockPos3 : BlockPos.iterate(blockPos.add(minGenOffset, minGenOffset, minGenOffset), blockPos.add(maxGenOffset, maxGenOffset, maxGenOffset))) {
      double noiseValue = doublePerlinNoiseSampler.sample(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ()) * config.noiseMultiplier;
      double inverseDistance = 0.0;
      for (Pair<BlockPos, Integer> pair : list) inverseDistance += MathHelper.inverseSqrt(blockPos3.getSquaredDistance(pair.getFirst()) + pair.getSecond()) + noiseValue;
      if (inverseDistance < outerLayerThreshold) continue;
      if (inverseDistance >= fillingThreshold) this.setBlockStateIf(structureWorldAccess, blockPos3, layerConfig.fillingProvider.get(random, blockPos3), predicate);
      else if (inverseDistance >= innerLayerThreshold) this.setBlockStateIf(structureWorldAccess, blockPos3, layerConfig.innerLayerProvider.get(random, blockPos3), predicate);
      else if (inverseDistance >= outerLayerThreshold) this.setBlockStateIf(structureWorldAccess, blockPos3, layerConfig.outerLayerProvider.get(random, blockPos3), predicate);
    }

    return true;
  }

  public record Config(
      LayerConfig layerConfig,
      IntProvider outerWallDistance,
      IntProvider distributionPoints,
      IntProvider pointOffset,
      int minGenOffset,
      int maxGenOffset,
      double noiseMultiplier,
      int invalidBlocksThreshold,
      float generationChance
  ) implements FeatureConfig {

    public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            LayerConfig.CODEC.fieldOf("layers").forGetter(Config::layerConfig),
            IntProvider.createValidatingCodec(1, 20).optionalFieldOf("outer_wall_distance", UniformIntProvider.create(4, 5)).forGetter(config -> config.outerWallDistance),
            IntProvider.createValidatingCodec(1, 20).optionalFieldOf("distribution_points", UniformIntProvider.create(3, 4)).forGetter(config -> config.distributionPoints),
            IntProvider.createValidatingCodec(0, 10).optionalFieldOf("point_offset", UniformIntProvider.create(1, 2)).forGetter(config -> config.pointOffset),
            Codec.INT.optionalFieldOf("min_gen_offset", -16).forGetter(config -> config.minGenOffset),
            Codec.INT.optionalFieldOf("max_gen_offset", 16).forGetter(config -> config.maxGenOffset),
            Codec.doubleRange(0.0, 1.0).optionalFieldOf("noise_multiplier", 0.05).forGetter(config -> config.noiseMultiplier),
            Codec.INT.fieldOf("invalid_blocks_threshold").forGetter(config -> config.invalidBlocksThreshold),
            Codec.FLOAT.fieldOf("generation_chance").forGetter(config -> config.generationChance)
        )
        .apply(instance, Config::new)
    );

    private record LayerConfig(
        BlockStateProvider fillingProvider,
        BlockStateProvider innerLayerProvider,
        BlockStateProvider outerLayerProvider,
        float fillingSize,
        float innerLayerThreshold,
        float outerLayerThreshold,
        TagKey<Block> cannotReplace,
        TagKey<Block> invalidBlocks
    ) {

      public static final Codec<LayerConfig> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(
              BlockStateProvider.TYPE_CODEC.fieldOf("filling_provider").forGetter(config -> config.fillingProvider),
              BlockStateProvider.TYPE_CODEC.fieldOf("inner_layer_provider").forGetter(config -> config.innerLayerProvider),
              BlockStateProvider.TYPE_CODEC.fieldOf("outer_layer_provider").forGetter(config -> config.outerLayerProvider),
              Codec.floatRange(0.01F, 50.0F).fieldOf("filling_size").orElse(1.7F).forGetter(config -> config.fillingSize),
              Codec.floatRange(0.01F, 50.0F).fieldOf("inner_layer_threshold").orElse(2.2F).forGetter(config -> config.innerLayerThreshold),
              Codec.floatRange(0.01F, 50.0F).fieldOf("outer_layer_threshold").orElse(3.2F).forGetter(config -> config.outerLayerThreshold),
              TagKey.codec(RegistryKeys.BLOCK).fieldOf("cannot_replace").forGetter(config -> config.cannotReplace),
              TagKey.codec(RegistryKeys.BLOCK).fieldOf("invalid_blocks").forGetter(config -> config.invalidBlocks)
          )
          .apply(instance, LayerConfig::new)
      );
    }
  }
}
