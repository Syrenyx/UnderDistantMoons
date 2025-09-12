package syrenyx.distantmoons.screen.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;
import syrenyx.distantmoons.data.persistent.PersistentStateManager;

public class HeartInventory implements Inventory {

  public final PlayerEntity player;
  private ItemStack itemStack;

  public HeartInventory(PlayerEntity player) {
    this.player = player;
    this.itemStack = this.getHeartItem();
  }

  @Override
  public int size() {
    return 1;
  }

  @Override
  public boolean isEmpty() {
    return itemStack.isEmpty();
  }

  @Override
  public ItemStack getStack(int slot) {
    return slot == 0 ? this.itemStack : ItemStack.EMPTY;
  }

  @Override
  public ItemStack removeStack(int slot, int amount) {
    if (slot != 0 || amount < 1) return ItemStack.EMPTY;
    return itemStack.split(amount);
  }

  @Override
  public ItemStack removeStack(int slot) {
    return this.removeStack(slot, 1);
  }

  @Override
  public void setStack(int slot, ItemStack stack) {
    if (slot != 0) return;
    this.itemStack = stack;
  }

  @Override
  public void markDirty() {}

  @Override
  public boolean canPlayerUse(PlayerEntity player) {
    return true;
  }

  @Override
  public void clear() {
    this.itemStack = ItemStack.EMPTY;
  }

  private ItemStack getHeartItem() {
    return this.player instanceof ServerPlayerEntity
        ? PersistentStateManager.getPlayerState(this.player).heartItem()
        : ClientPlayerAttachment.getOrCreate(this.player).heartItem();
  }
}
