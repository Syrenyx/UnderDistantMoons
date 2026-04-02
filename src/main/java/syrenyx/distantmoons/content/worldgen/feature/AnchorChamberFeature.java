package syrenyx.distantmoons.content.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;

public class AnchorChamberFeature extends Feature<AnchorChamberFeature.Config> {

  public static final int CHAMBER_RADIUS = 3;

  public AnchorChamberFeature(Codec<Config> configCodec) {
    super(configCodec);
  }

  @Override
  public boolean place(FeaturePlaceContext<Config> context) {
    Config config = context.config();
    BlockPos blockPos = context.origin();
    BlockPos maxPos = blockPos.offset(CHAMBER_RADIUS, CHAMBER_RADIUS, CHAMBER_RADIUS);
    BlockPos minPos = blockPos.offset(-CHAMBER_RADIUS, -CHAMBER_RADIUS, -CHAMBER_RADIUS);
    WorldGenLevel level = context.level();
    RandomSource randomSource = context.random();
    for (BlockPos pos : BlockPos.betweenClosed(
        minPos.getX(), minPos.getY(), minPos.getZ(),
        maxPos.getX(), maxPos.getY(), maxPos.getZ()
    )) {
      if (level.getBlockState(pos).is(DistantMoonsBlockTags.WORLDGEN_IRREPLACEABLE)) continue;
      int edges = 0;
      if (Math.abs(pos.getX()) == CHAMBER_RADIUS) edges++;
      if (Math.abs(pos.getY()) == CHAMBER_RADIUS) edges++;
      if (Math.abs(pos.getZ()) == CHAMBER_RADIUS) edges++;
      level.setBlock(pos, switch (edges) {
        case 1 -> config.wall().getState(level, randomSource, pos);
        case 2 -> config.edge().getState(level, randomSource, pos);
        case 3 -> config.corner().getState(level, randomSource, pos);
        default -> Blocks.AIR.defaultBlockState();
      }, Block.UPDATE_SUPPRESS_DROPS);
    }
    level.setBlock(blockPos, config.anchor().getState(level, randomSource, blockPos), Block.UPDATE_SUPPRESS_DROPS);
    return true;
  }

  public record Config(
      BlockStateProvider anchor,
      BlockStateProvider corner,
      BlockStateProvider edge,
      BlockStateProvider wall
  ) implements FeatureConfiguration {

    public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            BlockStateProvider.CODEC.fieldOf("anchor").forGetter(config -> config.anchor),
            BlockStateProvider.CODEC.fieldOf("corner").forGetter(config -> config.corner),
            BlockStateProvider.CODEC.fieldOf("edge").forGetter(config -> config.edge),
            BlockStateProvider.CODEC.fieldOf("wall").forGetter(config -> config.wall)
        )
        .apply(instance, Config::new)
    );
  }
}
