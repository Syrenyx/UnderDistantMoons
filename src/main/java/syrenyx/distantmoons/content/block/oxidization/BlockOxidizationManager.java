package syrenyx.distantmoons.content.block.oxidization;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import syrenyx.distantmoons.initializers.DistantMoonsBlocks;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class BlockOxidizationManager {

  public static final float OXIDIZATION_CHANCE = 0.05688889F;
  public static final float DEFAULT_OXIDIZATION_CHANCE_MULTIPLIER = 1.0F;
  public static final float UNAFFECTED_OXIDIZATION_CHANCE_MULTIPLIER = 0.75F;
  public static final int RADIUS = 4;
  public static final int WAX_OFF_WORLD_EVENT = 3004;
  public static final int SCRAPE_WORLD_EVENT = 3005;

  public static final Map<Block, BlockOxidizationDefinition> BLOCK_OXIDIZATION_MAP = Map.ofEntries(
      Map.entry(Blocks.IRON_BLOCK, new BlockOxidizationDefinition(
          WeatheringCopper.WeatherState.UNAFFECTED,
          true,
          UNAFFECTED_OXIDIZATION_CHANCE_MULTIPLIER,
          DistantMoonsBlocks.EXPOSED_IRON_BLOCK,
          null,
          DistantMoonsBlocks.WAXED_IRON_BLOCK
      )),
      Map.entry(DistantMoonsBlocks.EXPOSED_IRON_BLOCK, new BlockOxidizationDefinition(
          WeatheringCopper.WeatherState.EXPOSED,
          true,
          DEFAULT_OXIDIZATION_CHANCE_MULTIPLIER,
          DistantMoonsBlocks.WEATHERED_IRON_BLOCK,
          Blocks.IRON_BLOCK,
          DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK
      )),
      Map.entry(DistantMoonsBlocks.WEATHERED_IRON_BLOCK, new BlockOxidizationDefinition(
          WeatheringCopper.WeatherState.WEATHERED,
          true,
          DEFAULT_OXIDIZATION_CHANCE_MULTIPLIER,
          DistantMoonsBlocks.RUSTED_IRON_BLOCK,
          DistantMoonsBlocks.EXPOSED_IRON_BLOCK,
          DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK
      )),
      Map.entry(DistantMoonsBlocks.RUSTED_IRON_BLOCK, new BlockOxidizationDefinition(
          WeatheringCopper.WeatherState.OXIDIZED,
          true,
          DEFAULT_OXIDIZATION_CHANCE_MULTIPLIER,
          null,
          DistantMoonsBlocks.WEATHERED_IRON_BLOCK,
          DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK
      ))
  );

  public static final Map<Block, Function<BlockState, BlockState>> WAXED_BLOCK_SCRAPING_MAP = Map.ofEntries(
      Map.entry(DistantMoonsBlocks.WAXED_IRON_BLOCK, state -> Blocks.IRON_BLOCK.defaultBlockState()),
      Map.entry(DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK, state -> DistantMoonsBlocks.EXPOSED_IRON_BLOCK.defaultBlockState()),
      Map.entry(DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK, state -> DistantMoonsBlocks.WEATHERED_IRON_BLOCK.defaultBlockState()),
      Map.entry(DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK, state -> DistantMoonsBlocks.RUSTED_IRON_BLOCK.defaultBlockState())
  );

  public static Optional<BlockState> tryToOxidize(Block block, BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
    BlockOxidizationDefinition oxidizationRules = BLOCK_OXIDIZATION_MAP.get(block);
    int thisLevel;
    float oxidizationChanceMultiplier;
    Optional<BlockState> resultingState;
    if (oxidizationRules != null) {
      thisLevel = oxidizationRules.level().ordinal();
      oxidizationChanceMultiplier = oxidizationRules.oxidizationChanceMultiplier();
      resultingState = oxidizationRules.getOxidizedStateOf(state);
    }
    else if (block instanceof ChangeOverTimeBlock<?> degradableBlock) {
      thisLevel = degradableBlock.getAge().ordinal();
      oxidizationChanceMultiplier = degradableBlock.getChanceModifier();
      resultingState = degradableBlock.getNext(state);
    }
    else return Optional.empty();
    int higherLevelCount = 0;
    int sameLevelCount = 0;

    for (BlockPos neighboringPos : BlockPos.withinManhattan(pos, RADIUS, RADIUS, RADIUS)) {
      int distance = neighboringPos.distManhattan(pos);
      if (distance > 4) break;
      Block neighboringBlock = world.getBlockState(neighboringPos).getBlock();
      if (neighboringPos.equals(pos) || (
          !BLOCK_OXIDIZATION_MAP.containsKey(neighboringBlock)
              && !(neighboringBlock instanceof ChangeOverTimeBlock<?> degradable && degradable.getAge().getClass() == WeatheringCopper.WeatherState.class)
      )) continue;
      int neighboringLevel = neighboringBlock instanceof ChangeOverTimeBlock<?> degradable
          ? degradable.getAge().ordinal()
          : BLOCK_OXIDIZATION_MAP.get(neighboringBlock).level().ordinal();
      if (neighboringLevel < thisLevel) return Optional.empty();
      if (neighboringLevel > thisLevel) higherLevelCount++;
      else sameLevelCount++;
    }

    float oxidizationChanceFactor = (float) (higherLevelCount + 1) / (higherLevelCount + sameLevelCount + 1);
    float oxidizationChance = oxidizationChanceFactor * oxidizationChanceFactor * oxidizationChanceMultiplier;
    return random.nextFloat() < oxidizationChance ? resultingState : Optional.empty();
  }
}
