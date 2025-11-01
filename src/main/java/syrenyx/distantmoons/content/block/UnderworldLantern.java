package syrenyx.distantmoons.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class UnderworldLantern extends LanternBlock {

  public static final BooleanProperty LIT = BooleanProperty.of("lit");

  public UnderworldLantern(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState()
        .with(LIT, true)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(LIT);
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    BlockState state = super.getPlacementState(context);
    if (state == null) return null;
    return state.with(LIT, context.getWorld().getDimension().hasCeiling());
  }
}
