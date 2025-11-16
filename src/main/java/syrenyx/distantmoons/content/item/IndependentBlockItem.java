package syrenyx.distantmoons.content.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TypedEntityData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class IndependentBlockItem extends Item {

  private final Block block;

  public IndependentBlockItem(Block block, Settings settings) {
    super(settings);
    this.block = block;
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    ActionResult actionResult = this.place(new ItemPlacementContext(context));
    return !actionResult.isAccepted() && context.getStack().contains(DataComponentTypes.CONSUMABLE)
        ? super.use(context.getWorld(), context.getPlayer(), context.getHand())
        : actionResult;
  }

  public ActionResult place(ItemPlacementContext context) {
    if (context == null || !context.canPlace()) return ActionResult.FAIL;
    BlockState blockState = this.getPlacementState(context);
    if (blockState == null || !this.place(context, blockState)) return ActionResult.FAIL;
    BlockPos pos = context.getBlockPos();
    World world = context.getWorld();
    PlayerEntity player = context.getPlayer();
    ItemStack itemStack = context.getStack();
    BlockState placedState = world.getBlockState(pos);
    if (placedState.isOf(blockState.getBlock())) {
      placedState = this.placeFromNbt(pos, world, itemStack, placedState);
      this.postPlacement(pos, world, player, itemStack, placedState);
      copyComponentsToBlockEntity(world, pos, itemStack);
      placedState.getBlock().onPlaced(world, pos, placedState, player, itemStack);
      if (player instanceof ServerPlayerEntity) Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, itemStack);
    }
    BlockSoundGroup blockSoundGroup = placedState.getSoundGroup();
    world.playSound(
        player,
        pos,
        placedState.getSoundGroup().getPlaceSound(),
        SoundCategory.BLOCKS,
        (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
        blockSoundGroup.getPitch() * 0.8F
    );
    world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(player, placedState));
    itemStack.decrementUnlessCreative(1, player);
    return ActionResult.SUCCESS;
  }

  private static void copyComponentsToBlockEntity(World world, BlockPos pos, ItemStack stack) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity == null) return;
    blockEntity.readComponents(stack);
    blockEntity.markDirty();
  }

  protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
    return writeNbtToBlockEntity(world, player, pos, stack);
  }

  @Nullable
  protected BlockState getPlacementState(ItemPlacementContext context) {
    BlockState blockState = this.block.getPlacementState(context);
    return blockState != null && this.canPlace(context, blockState) ? blockState : null;
  }

  private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
    BlockStateComponent blockStateComponent = stack.getOrDefault(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT);
    if (blockStateComponent.isEmpty()) return state;
    BlockState blockState = blockStateComponent.applyToState(state);
    if (blockState != state) world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
    return blockState;
  }

  protected boolean canPlace(ItemPlacementContext context, BlockState state) {
    return (!this.checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos()))
        && context.getWorld().canPlace(state, context.getBlockPos(), ShapeContext.ofPlacement(context.getPlayer()));
  }

  protected boolean checkStatePlacement() {
    return true;
  }

  protected boolean place(ItemPlacementContext context, BlockState state) {
    return context.getWorld().setBlockState(context.getBlockPos(), state, Block.NOTIFY_ALL_AND_REDRAW);
  }

  public static boolean writeNbtToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack) {
    if (world.isClient()) return false;
    TypedEntityData<BlockEntityType<?>> typedEntityData = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
    if (typedEntityData == null) return false;
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity == null) return false;
    BlockEntityType<?> blockEntityType = blockEntity.getType();
    if (blockEntityType != typedEntityData.getType()) return false;
    if (!blockEntityType.canPotentiallyExecuteCommands() || player != null && player.isCreativeLevelTwoOp()) {
      return typedEntityData.applyToBlockEntity(blockEntity, world.getRegistryManager());
    }
    return false;
  }

  @Override
  public boolean shouldShowOperatorBlockWarnings(ItemStack stack, @Nullable PlayerEntity player) {
    if (player == null || player.getPermissionLevel() < 2) return false;
    TypedEntityData<BlockEntityType<?>> typedEntityData = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
    if (typedEntityData == null) return false;
    return typedEntityData.getType().canPotentiallyExecuteCommands();
  }

  @Override
  public boolean canBeNested() {
    return !(this.block instanceof ShulkerBoxBlock);
  }

  @Override
  public void onItemEntityDestroyed(ItemEntity entity) {
    ContainerComponent containerComponent = entity.getStack().set(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);
    if (containerComponent != null) ItemUsage.spawnItemContents(entity, containerComponent.iterateNonEmptyCopy());
  }

  @Override
  public FeatureSet getRequiredFeatures() {
    return this.block.getRequiredFeatures();
  }
}
