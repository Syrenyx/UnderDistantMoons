package syrenyx.distantmoons.content.menu.block.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class LockedSlot extends Slot {

  private final int maxStackSize;

  public LockedSlot(Container container, int index, int x, int y, int maxStackSize) {
    super(container, index, x, y);
    this.maxStackSize = maxStackSize;
  }

  @Override
  public boolean mayPlace(@NonNull ItemStack stack) {
    return false;
  }

  @Override
  public boolean mayPickup(@NonNull Player player) {
    return false;
  }

  @Override
  public int getMaxStackSize() {
    return this.maxStackSize;
  }
}
