package syrenyx.distantmoons.content.worldgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import java.util.List;
import java.util.function.Predicate;

public class OreGeodeFeature extends Feature<OreGeodeFeature.Config> {

  public OreGeodeFeature(Codec<Config> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean place(FeaturePlaceContext<Config> context) {
    Config config = context.config();
    RandomSource random = context.random();
    if (random.nextFloat() > config.generationChance) return false;
    BlockPos blockPos = context.origin();
    WorldGenLevel structureWorldAccess = context.level();
    int minGenOffset = config.minGenOffset;
    int maxGenOffset = config.maxGenOffset;
    List<Pair<BlockPos, Integer>> list = Lists.newLinkedList();
    int distributionPoint = config.distributionPoints.sample(random);
    WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(structureWorldAccess.getSeed()));
    NormalNoise doublePerlinNoiseSampler = NormalNoise.create(chunkRandom, -4, 1.0);
    double distributionDistance = (double) distributionPoint / config.outerWallDistance.getMaxValue();
    Config.LayerConfig layerConfig = config.layerConfig;
    double fillingThreshold = 1.0 / Math.sqrt(layerConfig.fillingSize);
    double innerLayerThreshold = 1.0 / Math.sqrt(layerConfig.innerLayerThreshold + distributionDistance);
    double outerLayerThreshold = 1.0 / Math.sqrt(layerConfig.outerLayerThreshold + distributionDistance);

    int i = 0;
    for (int n = 0; n < distributionPoint; n++) {
      BlockPos testPos = blockPos.offset(config.outerWallDistance.sample(random), config.outerWallDistance.sample(random), config.outerWallDistance.sample(random));
      BlockState testState = structureWorldAccess.getBlockState(testPos);
      if ((testState.isAir() || testState.is(layerConfig.invalidBlocks)) && ++i > config.invalidBlocksThreshold) return false;
      list.add(Pair.of(testPos, config.pointOffset.sample(random)));
    }

    Predicate<BlockState> predicate = isReplaceable(config.layerConfig.cannotReplace);

    for (BlockPos blockPos3 : BlockPos.betweenClosed(blockPos.offset(minGenOffset, minGenOffset, minGenOffset), blockPos.offset(maxGenOffset, maxGenOffset, maxGenOffset))) {
      double noiseValue = doublePerlinNoiseSampler.getValue(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ()) * config.noiseMultiplier;
      double inverseDistance = 0.0;
      for (Pair<BlockPos, Integer> pair : list) inverseDistance += Mth.invSqrt(blockPos3.distSqr(pair.getFirst()) + pair.getSecond()) + noiseValue;
      if (inverseDistance < outerLayerThreshold) continue;
      if (inverseDistance >= fillingThreshold) this.safeSetBlock(structureWorldAccess, blockPos3, layerConfig.fillingProvider.getState(random, blockPos3), predicate);
      else if (inverseDistance >= innerLayerThreshold) this.safeSetBlock(structureWorldAccess, blockPos3, layerConfig.innerLayerProvider.getState(random, blockPos3), predicate);
      else if (inverseDistance >= outerLayerThreshold) this.safeSetBlock(structureWorldAccess, blockPos3, layerConfig.outerLayerProvider.getState(random, blockPos3), predicate);
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
  ) implements FeatureConfiguration {

    public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            LayerConfig.CODEC.fieldOf("layers").forGetter(Config::layerConfig),
            IntProvider.codec(1, 20).optionalFieldOf("outer_wall_distance", UniformInt.of(4, 5)).forGetter(config -> config.outerWallDistance),
            IntProvider.codec(1, 20).optionalFieldOf("distribution_points", UniformInt.of(3, 4)).forGetter(config -> config.distributionPoints),
            IntProvider.codec(0, 10).optionalFieldOf("point_offset", UniformInt.of(1, 2)).forGetter(config -> config.pointOffset),
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
              BlockStateProvider.CODEC.fieldOf("filling_provider").forGetter(config -> config.fillingProvider),
              BlockStateProvider.CODEC.fieldOf("inner_layer_provider").forGetter(config -> config.innerLayerProvider),
              BlockStateProvider.CODEC.fieldOf("outer_layer_provider").forGetter(config -> config.outerLayerProvider),
              Codec.floatRange(0.01F, 50.0F).fieldOf("filling_size").orElse(1.7F).forGetter(config -> config.fillingSize),
              Codec.floatRange(0.01F, 50.0F).fieldOf("inner_layer_threshold").orElse(2.2F).forGetter(config -> config.innerLayerThreshold),
              Codec.floatRange(0.01F, 50.0F).fieldOf("outer_layer_threshold").orElse(3.2F).forGetter(config -> config.outerLayerThreshold),
              TagKey.hashedCodec(Registries.BLOCK).fieldOf("cannot_replace").forGetter(config -> config.cannotReplace),
              TagKey.hashedCodec(Registries.BLOCK).fieldOf("invalid_blocks").forGetter(config -> config.invalidBlocks)
          )
          .apply(instance, LayerConfig::new)
      );
    }
  }
}
