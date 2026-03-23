package syrenyx.distantmoons.content.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.UnderworldConfluxBlock;
import syrenyx.distantmoons.content.data_component.DimensionKeystoneComponent;
import syrenyx.distantmoons.initializers.DistantMoonsBlockEntityTypes;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;

public class UnderworldConfluxBlockEntity extends BlockEntity implements Container, ItemOwner {

  private static final String KEYSTONE_DATA_KEY = "keystone";

  private ItemStack keystoneStack = ItemStack.EMPTY;

  public UnderworldConfluxBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(DistantMoonsBlockEntityTypes.UNDERWORLD_CONFLUX, blockPos, blockState);
  }

  @Override
  public @NonNull Level level() {
    return this.level;
  }

  @Override
  public @NonNull Vec3 position() {
    return this.getBlockPos().getCenter();
  }

  @Override
  public float getVisualRotationYInDegrees() {
    return 0;
  }

  @Override
  public int getContainerSize() {
    return 1;
  }

  @Override
  public boolean isEmpty() {
    return keystoneStack.isEmpty();
  }

  public @NonNull ItemStack getItem() {
    return this.getItem(0);
  }

  @Override
  public @NonNull ItemStack getItem(int slot) {
    return this.keystoneStack;
  }

  public @NonNull ItemStack removeItem() {
    return this.removeItem(0, 1);
  }

  @Override
  public @NonNull ItemStack removeItem(int slot, int amount) {
    if (amount == 0) return ItemStack.EMPTY;
    this.setChanged();
    UnderworldConfluxBlock.onKeystoneChanged(this.level, this.worldPosition);
    return this.keystoneStack.copyAndClear();
  }

  @Override
  public @NonNull ItemStack removeItemNoUpdate(int slot) {
    return this.keystoneStack.copyAndClear();
  }

  public void setItem(@NonNull ItemStack itemStack) {
    this.setItem(0, itemStack);
  }

  @Override
  public void setItem(int i, @NonNull ItemStack itemStack) {
    itemStack.limitSize(1);
    this.setChanged();
    UnderworldConfluxBlock.onKeystoneChanged(this.level, this.worldPosition);
    this.keystoneStack = itemStack;
  }

  @Override
  public void clearContent() {
    this.keystoneStack = ItemStack.EMPTY;
    this.setChanged();
    UnderworldConfluxBlock.onKeystoneChanged(this.level, this.worldPosition);
  }

  @Override
  public int getMaxStackSize() {
    return 1;
  }

  @Override
  public boolean stillValid(@NonNull Player player) {
    return false;
  }

  @Override
  public boolean canPlaceItem(int slot, ItemStack itemStack) {
    return itemStack.has(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE);
  }

  @Override
  public @Nullable Object getRenderData() {
    return (this.keystoneStack.get(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE) instanceof DimensionKeystoneComponent component) ? component.color() : null;
  }

  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider provider) {
    CompoundTag compoundTag = super.getUpdateTag(provider);
    if (!this.keystoneStack.isEmpty()) compoundTag.store(KEYSTONE_DATA_KEY, ItemStack.SINGLE_ITEM_CODEC, this.keystoneStack);
    return compoundTag;
  }

  @Override
  protected void loadAdditional(@NonNull ValueInput valueInput) {
    super.loadAdditional(valueInput);
    this.keystoneStack = valueInput.read(KEYSTONE_DATA_KEY, ItemStack.SINGLE_ITEM_CODEC).orElse(Items.AIR.getDefaultInstance());
    if (this.level != null) UnderworldConfluxBlock.onKeystoneChanged(this.level, this.worldPosition);
  }

  @Override
  protected void saveAdditional(@NonNull ValueOutput valueOutput) {
    super.saveAdditional(valueOutput);
    if (!this.keystoneStack.isEmpty()) valueOutput.store(KEYSTONE_DATA_KEY, ItemStack.SINGLE_ITEM_CODEC, this.keystoneStack);
  }
}
