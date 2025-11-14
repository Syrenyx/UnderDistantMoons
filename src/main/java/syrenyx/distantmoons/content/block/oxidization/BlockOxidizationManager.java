package syrenyx.distantmoons.content.block.oxidization;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
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
          Oxidizable.OxidationLevel.UNAFFECTED,
          true,
          UNAFFECTED_OXIDIZATION_CHANCE_MULTIPLIER,
          state -> DistantMoonsBlocks.EXPOSED_IRON_BLOCK.getDefaultState(),
          null,
          state -> DistantMoonsBlocks.WAXED_IRON_BLOCK.getDefaultState()
      )),
      Map.entry(DistantMoonsBlocks.EXPOSED_IRON_BLOCK, new BlockOxidizationDefinition(
          Oxidizable.OxidationLevel.EXPOSED,
          true,
          DEFAULT_OXIDIZATION_CHANCE_MULTIPLIER,
          state -> DistantMoonsBlocks.WEATHERED_IRON_BLOCK.getDefaultState(),
          state -> Blocks.IRON_BLOCK.getDefaultState(),
          state -> DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK.getDefaultState()
      )),
      Map.entry(DistantMoonsBlocks.WEATHERED_IRON_BLOCK, new BlockOxidizationDefinition(
          Oxidizable.OxidationLevel.WEATHERED,
          true,
          DEFAULT_OXIDIZATION_CHANCE_MULTIPLIER,
          state -> DistantMoonsBlocks.RUSTED_IRON_BLOCK.getDefaultState(),
          state -> DistantMoonsBlocks.EXPOSED_IRON_BLOCK.getDefaultState(),
          state -> DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK.getDefaultState()
      )),
      Map.entry(DistantMoonsBlocks.RUSTED_IRON_BLOCK, new BlockOxidizationDefinition(
          Oxidizable.OxidationLevel.OXIDIZED,
          true,
          DEFAULT_OXIDIZATION_CHANCE_MULTIPLIER,
          null,
          state -> DistantMoonsBlocks.WEATHERED_IRON_BLOCK.getDefaultState(),
          state -> DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK.getDefaultState()
      ))
  );

  public static final Map<Block, Function<BlockState, BlockState>> WAXED_BLOCK_SCRAPING_MAP = Map.ofEntries(
      Map.entry(DistantMoonsBlocks.WAXED_IRON_BLOCK, state -> Blocks.IRON_BLOCK.getDefaultState()),
      Map.entry(DistantMoonsBlocks.WAXED_EXPOSED_IRON_BLOCK, state -> DistantMoonsBlocks.EXPOSED_IRON_BLOCK.getDefaultState()),
      Map.entry(DistantMoonsBlocks.WAXED_WEATHERED_IRON_BLOCK, state -> DistantMoonsBlocks.WEATHERED_IRON_BLOCK.getDefaultState()),
      Map.entry(DistantMoonsBlocks.WAXED_RUSTED_IRON_BLOCK, state -> DistantMoonsBlocks.RUSTED_IRON_BLOCK.getDefaultState())
  );

  public static Optional<BlockState> tryToOxidize(Block block, BlockState state, ServerWorld world, BlockPos pos, Random random) {
    BlockOxidizationDefinition oxidizationRules = BLOCK_OXIDIZATION_MAP.get(block);
    int thisLevel;
    float oxidizationChanceMultiplier;
    Optional<BlockState> resultingState;
    if (oxidizationRules != null) {
      thisLevel = oxidizationRules.level().ordinal();
      oxidizationChanceMultiplier = oxidizationRules.oxidizationChanceMultiplier();
      resultingState = oxidizationRules.getOxidizedStateOf(state);
    }
    else if (block instanceof Degradable<?> degradableBlock) {
      thisLevel = degradableBlock.getDegradationLevel().ordinal();
      oxidizationChanceMultiplier = degradableBlock.getDegradationChanceMultiplier();
      resultingState = degradableBlock.getDegradationResult(state);
    }
    else return Optional.empty();
    int higherLevelCount = 0;
    int sameLevelCount = 0;

    for (BlockPos neighboringPos : BlockPos.iterateOutwards(pos, RADIUS, RADIUS, RADIUS)) {
      int distance = neighboringPos.getManhattanDistance(pos);
      if (distance > 4) break;
      Block neighboringBlock = world.getBlockState(neighboringPos).getBlock();
      if (neighboringPos.equals(pos) || (
          !BLOCK_OXIDIZATION_MAP.containsKey(neighboringBlock)
              && !(neighboringBlock instanceof Degradable<?> degradable && degradable.getDegradationLevel().getClass() == Oxidizable.OxidationLevel.class)
      )) continue;
      int neighboringLevel = neighboringBlock instanceof Degradable<?> degradable
          ? degradable.getDegradationLevel().ordinal()
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
