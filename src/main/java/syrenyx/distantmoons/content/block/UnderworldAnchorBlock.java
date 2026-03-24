package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.particle.UnderworldParticleOptions;
import syrenyx.distantmoons.initializers.DistantMoonsPointOfInterestTypes;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VectorUtil;

import java.util.Optional;

public class UnderworldAnchorBlock extends Block implements UnderworldBlock {

  public static final int SEARCH_RADIUS = 64;
  private static final int ANCHOR_CHAMBER_RADIUS = 3;
  private static final int ANCHOR_CHAMBER_PLACEMENT_ATTEMPTS = 10;
  private static final int ANCHOR_CHAMBER_PLACEMENT_SEARCH_RADIUS = 32;

  public static final BooleanProperty LIT = BlockStateProperties.LIT;

  public UnderworldAnchorBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState()
        .setValue(LIT, true)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(LIT);
  }

  @Nullable @Override
  public BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    return UnderworldBlock.getStateForPlacement(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
  }

  @Override
  protected void randomTick(@NonNull BlockState blockState, @NonNull ServerLevel serverLevel, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    UnderworldBlock.randomTick(blockState, serverLevel, blockPos);
  }

  @Override
  public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    if (!state.getValue(LIT) || randomSource.nextInt(2) != 0) return;
    Vec3 anchor = blockPos.getCenter();
    Vec3 particlePosition = anchor.add(VectorUtil.randomPointOnSphere(randomSource, ((float) randomSource.nextInt(3, 10) / 5) + 1));
    level.addParticle(
        new UnderworldParticleOptions(anchor, UnderworldBlock.DEFAULT_COLOR),
        particlePosition.x(), particlePosition.y(), particlePosition.z(),
        0.0F, 0.0F, 0.0F
    );
  }

  protected static boolean locateOrConstructAnchorChamber(ServerLevel serverLevel, BlockPos originPos, ResourceKey<ConfiguredFeature<?, ?>> anchorChamberFeature) {
    if (locateExistingUnderworldAnchor(serverLevel, originPos).isPresent()) return true;
    Optional<BlockPos> anchorPos = findSuitableAnchorChamberLocation(serverLevel, originPos);
    if (anchorPos.isEmpty()) return false;
    serverLevel
        .registryAccess()
        .lookupOrThrow(Registries.CONFIGURED_FEATURE)
        .getOrThrow(anchorChamberFeature)
        .value()
        .place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.getRandom(), anchorPos.get());
    return true;
  }

  protected static Optional<BlockPos> locateExistingUnderworldAnchor(ServerLevel serverLevel, BlockPos blockPos) {
    PoiManager poiManager = serverLevel.getPoiManager();
    poiManager.ensureLoadedAndValid(serverLevel, blockPos, SEARCH_RADIUS);
    return poiManager
        .getInSquare(
            poi -> poi.value() == DistantMoonsPointOfInterestTypes.UNDERWORLD_ANCHOR,
            blockPos,
            SEARCH_RADIUS,
            PoiManager.Occupancy.ANY
        )
        .map(PoiRecord::getPos)
        .filter(pos -> serverLevel.getWorldBorder().isWithinBounds(pos) && !serverLevel.isOutsideBuildHeight(pos))
        .findFirst();
  }

  private static Optional<BlockPos> findSuitableAnchorChamberLocation(ServerLevel serverLevel, BlockPos blockPos) {
    int bestScore = Integer.MAX_VALUE;
    BlockPos bestPosition = null;
    potentialAnchorPlacementLoop: for (BlockPos anchorPos : BlockPos.randomBetweenClosed(
        serverLevel.getRandom(), ANCHOR_CHAMBER_PLACEMENT_ATTEMPTS,
        blockPos.getX() + ANCHOR_CHAMBER_PLACEMENT_SEARCH_RADIUS, blockPos.getY() + ANCHOR_CHAMBER_PLACEMENT_SEARCH_RADIUS, blockPos.getZ() + ANCHOR_CHAMBER_PLACEMENT_SEARCH_RADIUS,
        blockPos.getX() - ANCHOR_CHAMBER_PLACEMENT_SEARCH_RADIUS, blockPos.getY() - ANCHOR_CHAMBER_PLACEMENT_SEARCH_RADIUS, blockPos.getZ() - ANCHOR_CHAMBER_PLACEMENT_SEARCH_RADIUS
    )) {
      int score = 0;
      for (BlockPos pos : BlockPos.betweenClosed(
          anchorPos.getX() + ANCHOR_CHAMBER_RADIUS, anchorPos.getY() + ANCHOR_CHAMBER_RADIUS, anchorPos.getZ() + ANCHOR_CHAMBER_RADIUS,
          anchorPos.getX() - ANCHOR_CHAMBER_RADIUS, anchorPos.getY() - ANCHOR_CHAMBER_RADIUS, anchorPos.getZ() - ANCHOR_CHAMBER_RADIUS
      )) {
        if (!serverLevel.getWorldBorder().isWithinBounds(pos) || serverLevel.isOutsideBuildHeight(pos)) continue potentialAnchorPlacementLoop;
        BlockState blockState = serverLevel.getBlockState(pos);
        if (blockState.is(DistantMoonsBlockTags.WORLDGEN_IRREPLACEABLE)) continue potentialAnchorPlacementLoop;
        if (!blockState.is(DistantMoonsBlockTags.WORLDGEN_ANCHOR_CHAMBER_IGNORES)) score++;
      }
      if (bestScore <= score) continue;
      if (score == 0) return Optional.of(anchorPos);
      bestScore = score;
      bestPosition = anchorPos;
    }
    return Optional.ofNullable(bestPosition);
  }

  public static int lightLevel(BlockState blockState) {
    return blockState.getValue(UnderworldAnchorBlock.LIT) ? 15 : 0;
  }

  public static MapColor mapColor(BlockState blockState) {
    return blockState.getValue(UnderworldAnchorBlock.LIT) ? MapColor.COLOR_ORANGE : MapColor.COLOR_BLACK;
  }
}
