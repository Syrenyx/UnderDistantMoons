package syrenyx.distantmoons.content.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import syrenyx.distantmoons.content.data_component.CoiledBlockComponent;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;

public class CoilItem extends IndependentBlockItem {

  public CoilItem(Block block, Properties settings) {
    super(block, settings);
  }

  protected boolean place(BlockPlaceContext context, BlockState state) {
    CoiledBlockComponent component = context.getItemInHand().getComponents().getOrDefault(
        DistantMoonsDataComponentTypes.COILED_BLOCK,
        new CoiledBlockComponent(1)
    );
    BlockState placedState = component.block().isPresent()
        ? BuiltInRegistries.BLOCK.getValue(component.block().get()).getStateForPlacement(context) : state;
    if (placedState == null) return false;
    Level world = context.getLevel();
    int placed = 0;
    BlockPos pos = context.getClickedPos();
    Direction direction = component.direction();
    boolean waterloggable = state.getBlock().getStateDefinition().getProperties().contains(BlockStateProperties.WATERLOGGED);
    while (placed < component.amount()) {
      BlockPos offsetPos = pos.relative(direction, placed);
      BlockState currentState = world.getBlockState(offsetPos);
      if (!currentState.canBeReplaced() || !waterloggable && currentState.getBlock() instanceof LiquidBlock && placed != 0) break;
      boolean underwater = currentState.is(Blocks.WATER);
      if (waterloggable && currentState.getBlock() instanceof LiquidBlock && !underwater) break;
      if (!world.setBlock(offsetPos, underwater && waterloggable ? placedState.setValue(BlockStateProperties.WATERLOGGED, true) : placedState, Block.UPDATE_ALL_IMMEDIATE)) break;
      placed++;
    }
    if (placed == 0) return false;
    Player player = context.getPlayer();
    ItemStack remainderStack = placedState.getBlock().asItem().getDefaultInstance().copyWithCount(component.amount() - placed);
    if (remainderStack.isEmpty()) return true;
    if (player != null) {
      if(!player.isCreative()) player.handleExtraItemsCreatedOnUse(remainderStack);
    }
    else {
      ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), remainderStack);
      item.setDefaultPickUpDelay();
      world.addFreshEntity(item);
    }
    return true;
  }
}
