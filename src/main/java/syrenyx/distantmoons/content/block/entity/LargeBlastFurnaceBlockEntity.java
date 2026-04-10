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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
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
  public void setChanged() {
    if (this.controller != null) this.controller.setChanged();
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
    if (this.controller == null) return;
    this.controller.items = items;
    this.setChanged();
  }

  @Override
  public void setItem(int slot, @NonNull ItemStack itemStack) {
    if (this.controller == null) return;
    itemStack.limitSize(this.getMaxStackSize());
    this.controller.items.set(slot, itemStack);
    this.setChanged();
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
  public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder) {}

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
    this.setChanged();
  }

  public int getAnalogOutputSignal() {
    if (this.controller == null) return 0;
    if (this.fuelAccess) return Math.round(this.controller.heat / 100);
    int signal = 0;
    for (int slot : MATERIAL_SLOTS) {
      if (!this.controller.items.get(slot).isEmpty()) signal++;
    }
    return signal;
  }

  public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, LargeBlastFurnaceBlockEntity blockEntity) {
    if (blockEntity.updateController) blockEntity.findController((ServerLevel) level, blockPos, blockState);
    if (blockEntity.controller == null) {
      updateBlockState(level, blockPos, blockState, 0, false);
      return;
    }
    blockEntity.controller.serverTick(level);
    updateBlockState(level, blockPos, blockState, Mth.ceil(blockEntity.controller.heat / 400), blockEntity.controller.soulFuel);
  }

  private void findController(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState) {
    this.updateController = false;
    if (!LargeBlastFurnaceBlock.shapeIntegrityCheck(serverLevel, blockState, blockPos)) return;
    BlockEntity blockEntity = serverLevel.getBlockEntity(blockState.getValue(LargeBlastFurnaceBlock.CORNER).getTopNorthEastPos(blockPos));
    if (!(blockEntity instanceof LargeBlastFurnaceBlockEntity largeBlastFurnaceBlockEntity)) return;
    this.controller = largeBlastFurnaceBlockEntity.controller;
  }

  private static void updateBlockState(Level level, BlockPos blockPos, BlockState blockState, int heat, boolean soulFuel) {
    if (blockState.getValue(LargeBlastFurnaceBlock.HEAT) == heat && blockState.getValue(LargeBlastFurnaceBlock.SOUL_FIRE) == soulFuel) return;
    level.setBlock(
        blockPos,
        blockState
            .setValue(LargeBlastFurnaceBlock.HEAT, Mth.clamp(heat, LargeBlastFurnaceBlock.MIN_HEAT, LargeBlastFurnaceBlock.MAX_HEAT))
            .setValue(LargeBlastFurnaceBlock.SOUL_FIRE, soulFuel),
        Block.UPDATE_ALL
    );
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
    this.controller.soulFuel = valueInput.getBooleanOr("soul_fuel", false);
    this.controller.blastCharge = valueInput.getFloatOr("blast_charge", 0.0F);
    this.controller.blastingSteps = valueInput.getIntArray("blasting_steps").orElse(Controller.EMPTY_BLASTING_STEPS.clone());
    this.controller.requiredBlastingSteps = valueInput.getIntArray("required_blasting_steps").orElse(Controller.EMPTY_BLASTING_STEPS.clone());
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
    valueOutput.putBoolean("soul_fuel", this.controller.soulFuel);
    valueOutput.putFloat("blast_charge", this.controller.blastCharge);
    valueOutput.putIntArray("blasting_steps", this.controller.blastingSteps);
    valueOutput.putIntArray("required_blasting_steps", this.controller.requiredBlastingSteps);
  }

  public static class Controller {

    public static final int DATA_COUNT = 36;
    private static final float EXPLOSION_RADIUS = 7.0F;
    public static final int BLAST_CHARGE_INTERVAL = 100;
    private static final int BLAST_CHARGE_HEAT_THRESHOLD = 15;
    public static final int DANGEROUS_HEAT = 900;
    public static final int MAX_HEAT = 1600;
    private static final double FIRE_RADIUS = 6.0;
    private static final double FIRE_RADIUS_SQUARED = FIRE_RADIUS * FIRE_RADIUS;
    private static final float FIRE_CHANCE_PER_TICK = 0.1F;
    private static final int FIRE_TICKS = 200;
    protected static final RecipeManager.CachedCheck<SingleRecipeInput, LargeBlastFurnaceRecipe> QUICK_CHECK = RecipeManager.createCheck(DistantMoonsRecipeTypes.BLASTING);
    protected static final int[] EMPTY_BLASTING_STEPS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private Level level;
    private final BlockPos setterPos;
    protected final ContainerData dataAccess;
    private final boolean mirrored;
    private final Vec3 center;
    private final AABB fireArea;
    protected int fuelBurnTime = 0;
    protected int fuelBurnTimer = 0;
    protected int fuelHeatValue = 0;
    protected boolean soulFuel = false;
    protected float heat = 0.0F;
    protected float blastCharge = 0.0F;
    protected NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private long previousGameTime = 0L;
    private int[] blastingSteps = EMPTY_BLASTING_STEPS.clone();
    private int[] requiredBlastingSteps = EMPTY_BLASTING_STEPS.clone();

    public Controller(BlockPos blockPos, BlockState blockState) {
      this.setterPos = blockPos;
      this.mirrored = blockState.getValue(LargeBlastFurnaceBlock.MIRRORED);
      this.center = LargeBlastFurnaceBlock.getCenter(blockPos, blockState);
      this.fireArea = new AABB(new Vec3(this.center.x, this.center.y, this.center.z).add(FIRE_RADIUS), new Vec3(this.center.x, this.center.y, this.center.z).subtract(FIRE_RADIUS));
      this.dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
          return switch (index) {
            case 0 -> Controller.this.fuelBurnTime;
            case 1 -> Controller.this.fuelBurnTimer;
            case 2 -> Controller.this.fuelHeatValue;
            case 3 -> Math.round(Controller.this.heat);
            case 4 -> Controller.this.soulFuel ? 1 : 0;
            case 5 -> Math.round(Controller.this.blastCharge);
            case 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 -> Controller.this.blastingSteps[index - 6];
            case 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35 -> Controller.this.requiredBlastingSteps[index - 21];
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
            case 4 -> Controller.this.soulFuel = value >= 1;
            case 5 -> Controller.this.blastCharge = value;
            case 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 -> Controller.this.blastingSteps[index - 6] = value;
            case 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35 -> Controller.this.requiredBlastingSteps[index - 21] = value;
          }
        }

        @Override
        public int getCount() {
          return DATA_COUNT;
        }
      };
    }

    private void setChanged() {
      if (this.level == null) return;
      BlockState blockState = this.level.getBlockState(setterPos);
      if (!(blockState.getBlock() instanceof LargeBlastFurnaceBlock)) return;
      for (BlockPos pos : blockState.getValue(LargeBlastFurnaceBlock.CORNER).getCornersForPositionsInBlock(this.setterPos).values()) {
        BlockEntity blockEntity = this.level.getBlockEntity(pos);
        if (blockEntity != null) BlockEntity.setChanged(this.level, pos, this.level.getBlockState(pos));
      }
    }

    private void serverTick(Level level) {
      if (this.level == null || level == null) this.level = level;
      if (this.previousGameTime == this.level.getGameTime()) return;
      this.previousGameTime = this.level.getGameTime();
      this.updateFuel();
      this.updateHeat();
      this.updateBlastCharges();
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
        this.soulFuel = false;
        fuelStack.setCount(0);
        return;
      }
      BlastFurnaceFuelComponent fuelComponent = fuelStack.get(DistantMoonsDataComponentTypes.BLAST_FURNACE_FUEL);
      this.fuelHeatValue = fuelComponent.heat();
      this.fuelBurnTime = fuelComponent.burnTime();
      this.fuelBurnTimer = fuelComponent.burnTime();
      this.soulFuel = fuelComponent.soulFuel();
      fuelStack.setCount(0);
    }

    private void updateHeat() {
      float heatDifference = this.fuelHeatValue - this.heat;
      if (heatDifference != 0.0F) this.setChanged();
      this.heat += heatDifference / (this.fuelHeatValue == 0 ? 32 : 128);
      if (Mth.abs(heatDifference) <= 0.01F) this.heat = this.fuelHeatValue;
      else if (this.heat < 0) this.heat = 0.0F;
      if (this.heat >= DANGEROUS_HEAT) {
        this.level.getEntitiesOfClass(
            Entity.class,
            this.fireArea,
            entity -> !entity.fireImmune() && entity.distanceToSqr(this.center) <= FIRE_RADIUS_SQUARED
        ).forEach(entity -> {
          if (this.level.getRandom().nextFloat() <= FIRE_CHANCE_PER_TICK) entity.setRemainingFireTicks(FIRE_TICKS);
        });
      }
    }

    private void updateBlastCharges() {

      //Reset Blasting Steps
      for (int slot : MATERIAL_SLOTS) {
        ItemStack itemStack = this.items.get(slot);
        if (itemStack.isEmpty()) {
          this.blastingSteps[slot] = 0;
          this.requiredBlastingSteps[slot] = 0;
          continue;
        }
        Optional<RecipeHolder<LargeBlastFurnaceRecipe>> recipe = QUICK_CHECK.getRecipeFor(new SingleRecipeInput(itemStack), (ServerLevel) this.level);
        if (recipe.isEmpty()) {
          this.blastingSteps[slot] = 0;
          this.requiredBlastingSteps[slot] = 0;
          continue;
        }
        this.requiredBlastingSteps[slot] = recipe.get().value().blastingSteps();
      }

      //Update Blast Charge
      this.blastCharge += (this.heat - BLAST_CHARGE_HEAT_THRESHOLD) / (this.heat < BLAST_CHARGE_HEAT_THRESHOLD ? 16F : 1600F);
      if (this.blastCharge < 0) this.blastCharge = 0;

      //Process Steps and Materials
      if (this.blastCharge >= BLAST_CHARGE_INTERVAL) {
        this.blastCharge %= BLAST_CHARGE_INTERVAL;
        if (Math.round(this.heat) >= MAX_HEAT) {
          LargeBlastFurnaceBlock.breakBlocks(this.level, this.setterPos);
          this.level.explode(null, this.center.x(), this.center.y(), this.center.z(), EXPLOSION_RADIUS, Level.ExplosionInteraction.BLOCK);
          return;
        }
        this.level.playSound(null, this.center.x(), this.center.y(), this.center.z(), DistantMoonsSoundEvents.LARGE_BLAST_FURNACE_BLAST, SoundSource.BLOCKS, 2.0F, 0.6F);
        for (int slot : MATERIAL_SLOTS) {
          if (this.requiredBlastingSteps[slot] == 0) continue;
          SingleRecipeInput recipeInput = new SingleRecipeInput(this.items.get(slot));
          RecipeHolder<LargeBlastFurnaceRecipe> recipe = QUICK_CHECK.getRecipeFor(recipeInput, (ServerLevel) this.level).orElse(null);
          if (recipe == null) {
            this.blastingSteps[slot] = 0;
            continue;
          }
          Optional<Boolean> soulFuelRequirement = recipe.value().soulFuelRequirement();
          if (soulFuelRequirement.isPresent() && soulFuelRequirement.get() != this.soulFuel) continue;
          this.blastingSteps[slot]++;
          if (this.blastingSteps[slot] < this.requiredBlastingSteps[slot]) continue;
          this.blastingSteps[slot] = 0;
          this.items.set(slot, recipe.value().assemble(recipeInput));
        }
      }
    }
  }
}
