package syrenyx.distantmoons.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class HeartSlot extends Slot {

  public HeartSlot(Inventory inventory, int index, int x, int y) {
    super(inventory, index, x, y);
  }

  @Override
  public int getMaxItemCount() {
    return 1;
  }

  @Override
  public boolean canInsert(ItemStack stack) {
    return false;
  }

  @Override
  public boolean canTakeItems(PlayerEntity playerEntity) {
    return false;
  }

  @Override
  public void setStack(ItemStack stack, ItemStack previousStack) {
    //TODO: Update Heart Effects
    super.setStack(stack, previousStack);
  }}
