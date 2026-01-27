package syrenyx.distantmoons.content.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.LargeBlastFurnaceBlock;
import syrenyx.distantmoons.content.block.block_state_enums.BlockCorner;
import syrenyx.distantmoons.initializers.DistantMoonsBlockEntityTypes;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;

public class LargeBlastFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {

  private static final Component DEFAULT_NAME = Component.translatable("container.distant-moons.blast_furnace");
  private boolean updateController;
  private Controller controller = null;
  private final boolean fuelAccess;
  private static final int[] MATERIAL_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
  private static final int[] FUEL_SLOTS = {16, 17, 18, 19, 20};
  private static final int CONTAINER_SIZE = MATERIAL_SLOTS.length + FUEL_SLOTS.length;

  public LargeBlastFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(DistantMoonsBlockEntityTypes.LARGE_BLAST_FURNACE, blockPos, blockState);
    if (blockState.getValue(LargeBlastFurnaceBlock.CORNER) == BlockCorner.TOP_NORTH_EAST) {
      this.controller = new Controller(blockState.getValue(LargeBlastFurnaceBlock.MIRRORED));
    } else this.updateController = true;
    this.fuelAccess = LargeBlastFurnaceBlock.hasFuelAccess(blockState);
  }

  @Override
  protected @NonNull Component getDefaultName() {
    return DEFAULT_NAME;
  }

  @Override
  protected @NonNull NonNullList<ItemStack> getItems() {
    return this.controller.items;
  }

  @Override
  protected void setItems(@NonNull NonNullList<ItemStack> items) {
    this.controller.items = items;
  }

  @Override
  public void setItem(int slot, @NonNull ItemStack itemStack) {
    itemStack.limitSize(this.getMaxStackSize());
    this.controller.items.set(slot, itemStack);
  }

  @Override
  public int getMaxStackSize() {
    return 1;
  }

  @Override
  protected @NonNull AbstractContainerMenu createMenu(int i, @NonNull Inventory inventory) {
    return null;
  }

  @Override
  public int getContainerSize() {
    return CONTAINER_SIZE;
  }

  @Override
  public int @NonNull [] getSlotsForFace(@NonNull Direction direction) {
    if (!this.fuelAccess) return MATERIAL_SLOTS;
    if (direction != Direction.DOWN) return FUEL_SLOTS;
    return new int[0];
  }

  @Override
  public boolean canPlaceItemThroughFace(int slot, @NonNull ItemStack itemStack, @Nullable Direction direction) {
    return !this.fuelAccess && slot <= MATERIAL_SLOTS[MATERIAL_SLOTS.length - 1]
        || itemStack.has(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL) && slot >= FUEL_SLOTS[0] && direction != Direction.DOWN;
  }

  @Override
  public boolean canTakeItemThroughFace(int slot, @NonNull ItemStack itemStack, @NonNull Direction direction) {
    return !this.fuelAccess && direction == Direction.DOWN && slot <= MATERIAL_SLOTS[MATERIAL_SLOTS.length - 1];
  }

  @Override
  public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder) {

  }

  @Override
  public @Nullable RecipeHolder<?> getRecipeUsed() {
    return null;
  }

  @Override
  public void fillStackedContents(@NonNull StackedItemContents stackedItemContents) {
    for(ItemStack itemStack : this.controller.items) {
      stackedItemContents.accountStack(itemStack);
    }
  }

  public int getAnalogOutputSignal() {
    if (this.fuelAccess) return this.controller.heat;
    int signal = 0;
    for (int slot : MATERIAL_SLOTS) {
      if (!this.getItem(slot).isEmpty()) signal++;
    }
    return signal;
  }

  public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, LargeBlastFurnaceBlockEntity blockEntity) {
    if (blockEntity.updateController) blockEntity.findController((ServerLevel) level, blockPos, blockState);
  }

  private void findController(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState) {
    this.updateController = false;
    if (!LargeBlastFurnaceBlock.shapeIntegrityCheck(serverLevel, blockState, blockPos)) return;
    BlockEntity blockEntity = serverLevel.getBlockEntity(blockState.getValue(LargeBlastFurnaceBlock.CORNER).getTopNorthEastPos(blockPos));
    if (!(blockEntity instanceof LargeBlastFurnaceBlockEntity largeBlastFurnaceBlockEntity)) return;
    this.controller = largeBlastFurnaceBlockEntity.controller;
  }

  @Override
  protected void loadAdditional(@NonNull ValueInput valueInput) {
    super.loadAdditional(valueInput);
    this.controller.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    ContainerHelper.loadAllItems(valueInput, this.controller.items);
    this.controller.fuelBurnTime = valueInput.getShortOr("fuel_burn_time", (short) 0);
    this.controller.fuelHeatValue = valueInput.getShortOr("fuel_heat_value", (short) 0);
    this.controller.heat = valueInput.getShortOr("heat", (short) 0);
  }

  @Override
  protected void saveAdditional(@NonNull ValueOutput valueOutput) {
    super.saveAdditional(valueOutput);
    ContainerHelper.saveAllItems(valueOutput, this.controller.items);
    valueOutput.putShort("fuel_burn_time", (short) this.controller.fuelBurnTime);
    valueOutput.putShort("fuel_heat_value", (short) this.controller.fuelHeatValue);
    valueOutput.putShort("heat", (short) this.controller.heat);
  }

  private static class Controller {

    protected final ContainerData dataAccess;
    private final boolean mirrored;
    protected int fuelBurnTime = 0;
    protected int fuelHeatValue = 0;
    protected int heat = 0;
    protected NonNullList<ItemStack> items;

    public Controller(boolean mirrored) {
      this.mirrored = mirrored;
      this.items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
      this.dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
          return switch (index) {
            case 0 -> Controller.this.fuelBurnTime;
            case 1 -> Controller.this.fuelHeatValue;
            case 2 -> Controller.this.heat;
            default -> 0;
          };
        }

        @Override
        public void set(int index, int value) {
          switch (index) {
            case 0 -> Controller.this.fuelBurnTime = value;
            case 1 -> Controller.this.fuelHeatValue = value;
            case 2 -> Controller.this.heat = value;
          }
        }

        @Override
        public int getCount() {
          return 3;
        }
      };
    }
  }
}
