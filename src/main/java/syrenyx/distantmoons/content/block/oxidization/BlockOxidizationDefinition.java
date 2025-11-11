package syrenyx.distantmoons.content.block.oxidization;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public record BlockOxidizationDefinition(
    Oxidizable.OxidationLevel level,
    float oxidizationChanceMultiplier,
    @Nullable Function<BlockState, BlockState> oxidizationStateTransformation,
    @Nullable Function<BlockState, BlockState> scrapingStateTransformation,
    @Nullable Function<BlockState, BlockState> waxingStateTransformation
) {

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
