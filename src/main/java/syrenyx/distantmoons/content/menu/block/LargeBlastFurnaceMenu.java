package syrenyx.distantmoons.content.menu.block;

import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.block.entity.LargeBlastFurnaceBlockEntity;
import syrenyx.distantmoons.content.menu.block.slot.LockedSlot;
import syrenyx.distantmoons.initializers.DistantMoonsMenuTypes;

public class LargeBlastFurnaceMenu extends AbstractContainerMenu {

  private final Container container;
  private final ContainerData containerData;
  public final boolean mirrored;

  public LargeBlastFurnaceMenu(int id, Inventory inventory, boolean mirrored) {
    this(id, inventory, new SimpleContainer(LargeBlastFurnaceBlockEntity.CONTAINER_SIZE), new SimpleContainerData(LargeBlastFurnaceBlockEntity.Controller.DATA_COUNT), mirrored);
  }

  public LargeBlastFurnaceMenu(int id, Inventory inventory, Container container, ContainerData containerData, boolean mirrored) {
    super(mirrored ? DistantMoonsMenuTypes.LARGE_BLAST_FURNACE_MIRRORED : DistantMoonsMenuTypes.LARGE_BLAST_FURNACE_DEFAULT, id);
    this.container = container;
    this.containerData = containerData;
    this.mirrored = mirrored;
    this.addFuelSlots();
    this.addMaterialSlots();
    this.addStandardInventorySlots(inventory, 8, 149);
    this.addDataSlots(containerData);
  }

  private void addFuelSlots() {
    for (int row = 0; row < 5; row++) {
      this.addSlot(new LockedSlot(
          this.container,
          LargeBlastFurnaceBlockEntity.FUEL_SLOTS[row],
          this.mirrored ? 44 : 116, 18 + row * 18,
          1
      ));
    }
  }

  private void addMaterialSlots() {
    for (int row = 0; row < 5; row++) {
      for (int column = 0; column < 3; column++) {
        this.addSlot(new LockedSlot(
            this.container,
            LargeBlastFurnaceBlockEntity.MATERIAL_SLOTS[column + row * 3],
            (this.mirrored ? 80 : 44) + column * 18, 18 + row * 18,
            1
        ));
      }
    }
  }

  @Override
  public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slot) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean stillValid(Player player) {
    return this.container.stillValid(player);
  }

  public int getFuelBurnTime() {
    return this.containerData.get(0);
  }

  public int getFuelBurnTimer() {
    return this.containerData.get(1);
  }

  public float getFuelBurnProgress() {
    int total = this.getFuelBurnTime();
    if (total == 0) return 1.0F;
    return Mth.clamp((float) this.getFuelBurnTimer() / total, 0.0F, 1.0F);
  }

  public int getFuelHeatValue() {
    return this.containerData.get(2);
  }

  public int getHeat() {
    return this.containerData.get(3);
  }

  public int getBlastCharge() {
    return this.containerData.get(4);
  }

  public int getBlastingStepAtSlot(int slot) {
    return this.containerData.get(slot + 5);
  }

  public int getRequiredBlastingStepAtSlot(int slot) {
    return this.containerData.get(slot + 20);
  }
}