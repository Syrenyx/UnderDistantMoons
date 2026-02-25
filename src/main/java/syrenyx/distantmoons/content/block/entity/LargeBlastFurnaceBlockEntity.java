package syrenyx.distantmoons.content.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.LargeBlastFurnaceBlock;
import syrenyx.distantmoons.content.block.block_state_enums.BlockCorner;
import syrenyx.distantmoons.content.data_component.BlastFurnaceFuelComponent;
import syrenyx.distantmoons.content.menu.block.LargeBlastFurnaceMenu;
import syrenyx.distantmoons.content.recipe.LargeBlastFurnaceRecipe;
import syrenyx.distantmoons.initializers.DistantMoonsBlockEntityTypes;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;
import syrenyx.distantmoons.initializers.DistantMoonsRecipeTypes;
import syrenyx.distantmoons.initializers.DistantMoonsSoundEvents;

import java.util.Optional;

public class LargeBlastFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {

  private static final Component DEFAULT_NAME = Component.translatable("container.distant-moons.blast_furnace");
  public static final int[] MATERIAL_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
  public static final int FIRST_FUEL_SLOT = 15;
  private static final int ACTIVE_FUEL_SLOT = 19;
  public static final int[] FUEL_SLOTS = {FIRST_FUEL_SLOT, 16, 17, 18, ACTIVE_FUEL_SLOT};
  public static final int[] ACCESSIBLE_FUEL_SLOTS = {FIRST_FUEL_SLOT};
  public static final int CONTAINER_SIZE = MATERIAL_SLOTS.length + FUEL_SLOTS.length;
  private boolean updateController;
  private Controller controller = null;
  private final boolean fuelAccess;

  public LargeBlastFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(DistantMoonsBlockEntityTypes.LARGE_BLAST_FURNACE, blockPos, blockState);
    if (blockState.getValue(LargeBlastFurnaceBlock.CORNER) == BlockCorner.TOP_NORTH_EAST) {
      this.controller = new Controller(blockPos, blockState);
    } else this.updateController = true;
    this.fuelAccess = LargeBlastFurnaceBlock.hasFuelAccess(blockState);
  }

  @Override
  protected @NonNull Component getDefaultName() {
    return DEFAULT_NAME;
  }

  @Override
  protected @NonNull NonNullList<ItemStack> getItems() {
    return this.controller != null ? this.controller.items : NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
  }

  @Override
  protected void setItems(@NonNull NonNullList<ItemStack> items) {
    if (this.controller != null) this.controller.items = items;
  }

  @Override
  public void setItem(int slot, @NonNull ItemStack itemStack) {
    if (this.controller == null) return;
    itemStack.limitSize(this.getMaxStackSize());
    this.controller.items.set(slot, itemStack);
  }

  @Override
  public int getMaxStackSize() {
    return 1;
  }

  @Override
  protected @NonNull AbstractContainerMenu createMenu(int i, @NonNull Inventory inventory) {
    return new LargeBlastFurnaceMenu(i, inventory, this, this.controller.dataAccess, this.controller.mirrored);
  }

  @Override
  public int getContainerSize() {
    return CONTAINER_SIZE;
  }

  @Override
  public int @NonNull [] getSlotsForFace(@NonNull Direction direction) {
    if (!this.fuelAccess) return MATERIAL_SLOTS;
    if (direction != Direction.DOWN) return ACCESSIBLE_FUEL_SLOTS;
    return new int[0];
  }

  @Override
  public boolean canPlaceItemThroughFace(int slot, @NonNull ItemStack itemStack, @Nullable Direction direction) {
    if (this.controller == null || !this.controller.items.get(slot).isEmpty()) return false;
    return !this.fuelAccess && slot <= MATERIAL_SLOTS[MATERIAL_SLOTS.length - 1]
        || itemStack.has(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL) && slot == FIRST_FUEL_SLOT && direction != Direction.DOWN;
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
    if (this.controller == null) return;
    for(ItemStack itemStack : this.controller.items) {
      stackedItemContents.accountStack(itemStack);
    }
  }

  public int getAnalogOutputSignal() {
    if (this.controller == null) return 0;
    if (this.fuelAccess) return Math.round(this.controller.heat / 100);
    int signal = 0;
    for (ItemStack itemStack : this.controller.items) {
      if (!itemStack.isEmpty()) signal++;
    }
    return signal;
  }

  public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, LargeBlastFurnaceBlockEntity blockEntity) {
    if (blockEntity.updateController) blockEntity.findController((ServerLevel) level, blockPos, blockState);
    if (blockEntity.controller == null) {
      updateBlockHeat(level, blockPos, blockState, 0);
      return;
    }
    blockEntity.controller.serverTick(level, blockPos, blockState);
    updateBlockHeat(level, blockPos, blockState, Mth.floor(blockEntity.controller.heat / 400));
  }

  private void findController(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState) {
    this.updateController = false;
    if (!LargeBlastFurnaceBlock.shapeIntegrityCheck(serverLevel, blockState, blockPos)) return;
    BlockEntity blockEntity = serverLevel.getBlockEntity(blockState.getValue(LargeBlastFurnaceBlock.CORNER).getTopNorthEastPos(blockPos));
    if (!(blockEntity instanceof LargeBlastFurnaceBlockEntity largeBlastFurnaceBlockEntity)) return;
    this.controller = largeBlastFurnaceBlockEntity.controller;
  }

  private static void updateBlockHeat(Level level, BlockPos blockPos, BlockState blockState, int heat) {
    if (blockState.getValue(LargeBlastFurnaceBlock.HEAT) != heat) LargeBlastFurnaceBlock.setHeat(level, blockPos, blockState, heat);
  }

  @Override
  protected void loadAdditional(@NonNull ValueInput valueInput) {
    super.loadAdditional(valueInput);
    if (this.controller == null) return;
    this.controller.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    ContainerHelper.loadAllItems(valueInput, this.controller.items);
    this.controller.fuelBurnTime = valueInput.getIntOr("fuel_burn_time", 0);
    this.controller.fuelBurnTimer = valueInput.getIntOr("fuel_burn_timer", 0);
    this.controller.fuelHeatValue = valueInput.getIntOr("fuel_heat_value", 0);
    this.controller.heat = valueInput.getFloatOr("heat", 0.0F);
    this.controller.blastCharge = valueInput.getFloatOr("blast_charge", 0.0F);
    this.controller.blastingSteps = valueInput.getIntArray("blasting_steps").orElse(Controller.EMPTY_BLASTING_STEPS);
    this.controller.requiredBlastingSteps = valueInput.getIntArray("required_blasting_steps").orElse(Controller.EMPTY_BLASTING_STEPS);
  }

  @Override
  protected void saveAdditional(@NonNull ValueOutput valueOutput) {
    super.saveAdditional(valueOutput);
    if (this.controller == null) return;
    ContainerHelper.saveAllItems(valueOutput, this.controller.items);
    valueOutput.putInt("fuel_burn_time", this.controller.fuelBurnTime);
    valueOutput.putInt("fuel_burn_timer", this.controller.fuelBurnTimer);
    valueOutput.putInt("fuel_heat_value", this.controller.fuelHeatValue);
    valueOutput.putFloat("heat", this.controller.heat);
    valueOutput.putFloat("blast_charge", this.controller.blastCharge);
    valueOutput.putIntArray("blasting_steps", this.controller.blastingSteps);
    valueOutput.putIntArray("required_blasting_steps", this.controller.requiredBlastingSteps);
  }

  public static class Controller {

    public static final int DATA_COUNT = 35;
    private static final float EXPLOSION_RADIUS = 7.0F;
    private static final int BLAST_CHARGE_INTERVAL = 100;
    public static final int MAX_HEAT = 1600;
    protected static final RecipeManager.CachedCheck<SingleRecipeInput, LargeBlastFurnaceRecipe> QUICK_CHECK = RecipeManager.createCheck(DistantMoonsRecipeTypes.BLASTING);
    protected static final int[] EMPTY_BLASTING_STEPS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    protected final ContainerData dataAccess;
    private final boolean mirrored;
    private final Vec3 center;
    protected int fuelBurnTime = 0;
    protected int fuelBurnTimer = 0;
    protected int fuelHeatValue = 0;
    protected float heat = 0.0F;
    protected float blastCharge = 0.0F;
    protected NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private long previousGameTime = 0L;
    private int[] blastingSteps = EMPTY_BLASTING_STEPS;
    private int[] requiredBlastingSteps = EMPTY_BLASTING_STEPS;

    public Controller(BlockPos blockPos, BlockState blockState) {
      this.mirrored = blockState.getValue(LargeBlastFurnaceBlock.MIRRORED);
      this.center = LargeBlastFurnaceBlock.getCenter(blockPos, blockState);
      this.dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
          return switch (index) {
            case 0 -> Controller.this.fuelBurnTime;
            case 1 -> Controller.this.fuelBurnTimer;
            case 2 -> Controller.this.fuelHeatValue;
            case 3 -> Math.round(Controller.this.heat);
            case 4 -> Math.round(Controller.this.blastCharge);
            case 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 -> Controller.this.blastingSteps[index - 5];
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34 -> Controller.this.requiredBlastingSteps[index - 20];
            default -> 0;
          };
        }

        @Override
        public void set(int index, int value) {
          switch (index) {
            case 0 -> Controller.this.fuelBurnTime = value;
            case 1 -> Controller.this.fuelBurnTimer = value;
            case 2 -> Controller.this.fuelHeatValue = value;
            case 3 -> Controller.this.heat = value;
            case 4 -> Controller.this.blastCharge = value;
            case 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 -> Controller.this.blastingSteps[index - 5] = value;
            case 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34 -> Controller.this.requiredBlastingSteps[index - 20] = value;
          }
        }

        @Override
        public int getCount() {
          return DATA_COUNT;
        }
      };
    }

    private void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
      if (this.previousGameTime == level.getGameTime()) return;
      this.previousGameTime = level.getGameTime();
      for (int slot : MATERIAL_SLOTS) {
        ItemStack itemStack = this.items.get(slot);
        if (itemStack.isEmpty()) {
          this.blastingSteps[slot] = 0;
          this.requiredBlastingSteps[slot] = 0;
          continue;
        }
        Optional<RecipeHolder<LargeBlastFurnaceRecipe>> recipe = QUICK_CHECK.getRecipeFor(new SingleRecipeInput(itemStack), (ServerLevel) level);
        if (recipe.isEmpty()) {
          this.blastingSteps[slot] = 0;
          this.requiredBlastingSteps[slot] = 0;
          continue;
        }
        this.requiredBlastingSteps[slot] = recipe.get().value().blastingSteps();
      }
      this.updateFuel();
      float heatDifference = this.fuelHeatValue - this.heat;
      this.heat += heatDifference / 64;
      if (Mth.abs(heatDifference) <= 0.0001F) this.heat = this.fuelHeatValue;
      else if (this.heat < 0) this.heat = 0.0F;
      this.blastCharge += this.heat / 1600F;
      if (this.blastCharge >= BLAST_CHARGE_INTERVAL) {
        if (Math.round(this.heat) >= MAX_HEAT) {
          LargeBlastFurnaceBlock.breakBlocks(level, blockPos, blockState);
          level.explode(null, this.center.x(), this.center.y(), this.center.z(), EXPLOSION_RADIUS, Level.ExplosionInteraction.BLOCK);
          return;
        }
        level.playSound(null, this.center.x(), this.center.y(), this.center.z(), DistantMoonsSoundEvents.LARGE_BLAST_FURNACE_BLAST, SoundSource.BLOCKS, 2.0F, 0.6F);
        this.processMaterials(level);
      }
      this.blastCharge %= BLAST_CHARGE_INTERVAL;
    }

    private void updateFuel() {

      //Slide Down Fuel Stacks
      for (int slot = FUEL_SLOTS[FUEL_SLOTS.length - 2]; slot >= FIRST_FUEL_SLOT; slot--) {
        ItemStack stack = this.items.get(slot);
        if (stack.isEmpty() || !this.items.get(slot + 1).isEmpty()) continue;
        this.items.set(slot + 1, stack);
        this.items.set(slot, Items.AIR.getDefaultInstance());
      }

      //Burn Active Fuel Slot
      if (this.fuelBurnTimer > 0) this.fuelBurnTimer--;
      if (this.fuelBurnTimer != 0) return;
      ItemStack fuelStack = this.items.get(ACTIVE_FUEL_SLOT);
      if (this.items.get(ACTIVE_FUEL_SLOT).isEmpty() || !fuelStack.has(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL)) {
        this.fuelBurnTime = 0;
        this.fuelHeatValue = 0;
        fuelStack.setCount(0);
        return;
      }
      BlastFurnaceFuelComponent fuelComponent = fuelStack.get(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL);
      this.fuelHeatValue = fuelComponent.heat();
      this.fuelBurnTime = fuelComponent.burnTime();
      this.fuelBurnTimer = fuelComponent.burnTime();
      fuelStack.setCount(0);
    }

    private void processMaterials(Level level) {
      for (int slot : MATERIAL_SLOTS) {
        if (this.requiredBlastingSteps[slot] == 0) continue;
        SingleRecipeInput recipeInput = new SingleRecipeInput(this.items.get(slot));
        RecipeHolder<LargeBlastFurnaceRecipe> recipe = QUICK_CHECK.getRecipeFor(recipeInput, (ServerLevel) level).orElse(null);
        if (recipe == null) {
          this.blastingSteps[slot] = 0;
          continue;
        }
        this.blastingSteps[slot]++;
        if (this.blastingSteps[slot] < this.requiredBlastingSteps[slot]) continue;
        this.blastingSteps[slot] = 0;
        this.items.set(slot, recipe.value().assemble(recipeInput, level.registryAccess()));
      }
    }
  }
}
