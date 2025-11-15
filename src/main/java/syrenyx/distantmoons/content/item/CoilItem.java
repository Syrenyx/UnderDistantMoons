package syrenyx.distantmoons.content.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import syrenyx.distantmoons.content.data_component.CoiledBlockComponent;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;

public class CoilItem extends IndependentBlockItem {

  public CoilItem(Block block, Settings settings) {
    super(block, settings);
  }

  protected boolean place(ItemPlacementContext context, BlockState state) {
    CoiledBlockComponent component = context.getStack().getComponents().getOrDefault(
        DistantMoonsDataComponentTypes.COILED_BLOCK,
        new CoiledBlockComponent(1)
    );
    BlockState placedState = component.block().isPresent()
        ? Registries.BLOCK.get(component.block().get()).getPlacementState(context) : state;
    if (placedState == null) return false;
    World world = context.getWorld();
    int placed = 0;
    BlockPos pos = context.getBlockPos();
    Direction direction = component.direction();
    boolean waterloggable = state.getBlock().getStateManager().getProperties().contains(Properties.WATERLOGGED);
    while (placed < component.amount()) {
      BlockPos offsetPos = pos.offset(direction, placed);
      BlockState currentState = world.getBlockState(offsetPos);
      if (!currentState.isReplaceable() || !waterloggable && currentState.getBlock() instanceof FluidBlock) break;
      boolean underwater = currentState.isOf(Blocks.WATER);
      if (waterloggable && currentState.getBlock() instanceof FluidBlock && !underwater) break;
      if (!world.setBlockState(offsetPos, underwater ? placedState.with(Properties.WATERLOGGED, true) : placedState, Block.NOTIFY_ALL_AND_REDRAW)) break;
      placed++;
    }
    if (placed == 0) return false;
    PlayerEntity player = context.getPlayer();
    ItemStack remainderStack = placedState.getBlock().asItem().getDefaultStack().copyWithCount(component.amount() - placed);
    if (remainderStack.isEmpty()) return true;
    if (player != null) {
      if(!player.isCreative()) player.giveOrDropStack(remainderStack);
    }
    else {
      ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), remainderStack);
      item.setToDefaultPickupDelay();
      world.spawnEntity(item);
    }
    return true;
  }
}
