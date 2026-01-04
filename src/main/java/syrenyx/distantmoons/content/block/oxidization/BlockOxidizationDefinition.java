package syrenyx.distantmoons.content.block.oxidization;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public record BlockOxidizationDefinition(
    WeatheringCopper.WeatherState level,
    boolean rust,
    float oxidizationChanceMultiplier,
    @Nullable Function<BlockState, BlockState> oxidizationStateTransformation,
    @Nullable Function<BlockState, BlockState> scrapingStateTransformation,
    @Nullable Function<BlockState, BlockState> waxingStateTransformation
) {

  public BlockOxidizationDefinition(
      WeatheringCopper.WeatherState level,
      boolean rust,
      float oxidizationChanceMultiplier,
      @Nullable Block oxidizedBlock,
      @Nullable Block scrapedBlock,
      @Nullable Block waxedBlock
  ) {
    this(
        level, rust, oxidizationChanceMultiplier,
        oxidizedBlock != null ? state -> oxidizedBlock.defaultBlockState() : null,
        scrapedBlock != null ? state -> scrapedBlock.defaultBlockState() : null,
        waxedBlock != null ? state -> waxedBlock.defaultBlockState() : null
    );
  }

  public BlockOxidizationDefinition(
      WeatheringCopper.WeatherState level,
      boolean rust,
      float oxidizationChanceMultiplier,
      @Nullable BlockState oxidizedBlockState,
      @Nullable BlockState scrapedBlockState,
      @Nullable BlockState waxedBlockState
  ) {
    this(
        level, rust, oxidizationChanceMultiplier,
        oxidizedBlockState != null ? state -> oxidizedBlockState : null,
        scrapedBlockState != null ? state -> scrapedBlockState : null,
        waxedBlockState != null ? state -> waxedBlockState : null
    );
  }

  public Optional<BlockState> getOxidizedStateOf(BlockState state) {
    return this.canOxidize() ? Optional.of(this.oxidizationStateTransformation.apply(state)) : Optional.empty();
  }

  public Optional<BlockState> getScrapedStateOf(BlockState state) {
    return this.canBeScraped() ? Optional.of(this.scrapingStateTransformation.apply(state)) : Optional.empty();
  }

  public Optional<BlockState> getWaxedStateOf(BlockState state) {
    return this.canBeWaxed() ? Optional.of(this.waxingStateTransformation.apply(state)) : Optional.empty();
  }

  public boolean canOxidize() {
    return this.oxidizationStateTransformation != null;
  }

  public boolean canBeScraped() {
    return this.scrapingStateTransformation != null;
  }

  public boolean canBeWaxed() {
    return this.waxingStateTransformation != null;
  }
}
