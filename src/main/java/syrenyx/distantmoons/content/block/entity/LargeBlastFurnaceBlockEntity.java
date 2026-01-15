package syrenyx.distantmoons.content.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class LargeBlastFurnaceBlockEntity extends BaseContainerBlockEntity {

  private static final Component DEFAULT_NAME = Component.translatable("container.distant-moons.blast_furnace");

  protected LargeBlastFurnaceBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
    super(blockEntityType, blockPos, blockState);
  }

  @Override
  protected @NonNull Component getDefaultName() {
    return DEFAULT_NAME;
  }

  @Override
  protected @NonNull NonNullList<ItemStack> getItems() {
    return null;
  }

  @Override
  protected void setItems(@NonNull NonNullList<ItemStack> nonNullList) {

  }

  @Override
  protected @NonNull AbstractContainerMenu createMenu(int i, @NonNull Inventory inventory) {
    return null;
  }

  @Override
  public int getContainerSize() {
    return 0;
  }
}
