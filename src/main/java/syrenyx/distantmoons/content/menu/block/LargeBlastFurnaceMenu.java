package syrenyx.distantmoons.content.menu.block;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.block.entity.LargeBlastFurnaceBlockEntity;
import syrenyx.distantmoons.initializers.DistantMoonsMenuTypes;

public class LargeBlastFurnaceMenu extends AbstractContainerMenu {

  private final ContainerData containerData;
  private final boolean mirrored;

  public LargeBlastFurnaceMenu(int i, Inventory inventory) {
    this(i, inventory, new SimpleContainerData(LargeBlastFurnaceBlockEntity.Controller.DATA_COUNT), false);
  }

  public LargeBlastFurnaceMenu(int i, Inventory inventory, ContainerData containerData, boolean mirrored) {
    super(DistantMoonsMenuTypes.LARGE_BLAST_FURNACE, i);
    this.containerData = containerData;
    this.mirrored = mirrored;
  }

  @Override
  public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slot) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean stillValid(@NonNull Player player) {
    return false;
  }
}