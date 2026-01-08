package syrenyx.distantmoons.content.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.initializers.DistantMoonsEnvironmentAttributes;

public class UnderworldLanternBlock extends LanternBlock{

  public static BooleanProperty LIT = BlockStateProperties.LIT;

  public UnderworldLanternBlock(Properties properties) {
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
    BlockState state = super.getStateForPlacement(context);
    if (state == null) return null;
    return state.setValue(LIT, context.getLevel().environmentAttributes().getDimensionValue(DistantMoonsEnvironmentAttributes.UNDERWORLD));
  }
}
