package syrenyx.distantmoons.content.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class IndependentBlockItem extends Item {

  private final Block block;

  public IndependentBlockItem(Block block, Properties settings) {
    super(settings);
    this.block = block;
  }

  @Override
  public @NonNull InteractionResult useOn(@NonNull UseOnContext context) {
    InteractionResult actionResult = this.place(new BlockPlaceContext(context));
    return !actionResult.consumesAction() && context.getItemInHand().has(DataComponents.CONSUMABLE)
        ? super.use(context.getLevel(), context.getPlayer(), context.getHand())
        : actionResult;
  }

  public InteractionResult place(BlockPlaceContext context) {
    if (context == null || !context.canPlace()) return InteractionResult.FAIL;
    BlockState blockState = this.getPlacementState(context);
    if (blockState == null || !this.place(context, blockState)) return InteractionResult.FAIL;
    BlockPos pos = context.getClickedPos();
    Level world = context.getLevel();
    Player player = context.getPlayer();
    ItemStack itemStack = context.getItemInHand();
    BlockState placedState = world.getBlockState(pos);
    if (placedState.is(blockState.getBlock())) {
      placedState = this.placeFromNbt(pos, world, itemStack, placedState);
      this.postPlacement(pos, world, player, itemStack, placedState);
      copyComponentsToBlockEntity(world, pos, itemStack);
      placedState.getBlock().setPlacedBy(world, pos, placedState, player, itemStack);
      if (player instanceof ServerPlayer) CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
    }
    SoundType blockSoundGroup = placedState.getSoundType();
    world.playSound(
        player,
        pos,
        placedState.getSoundType().getPlaceSound(),
        SoundSource.BLOCKS,
        (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
        blockSoundGroup.getPitch() * 0.8F
    );
    world.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, placedState));
    itemStack.consume(1, player);
    return InteractionResult.SUCCESS;
  }

  private static void copyComponentsToBlockEntity(Level world, BlockPos pos, ItemStack stack) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity == null) return;
    blockEntity.applyComponentsFromItemStack(stack);
    blockEntity.setChanged();
  }

  protected boolean postPlacement(BlockPos pos, Level world, @Nullable Player player, ItemStack stack, BlockState state) {
    return writeNbtToBlockEntity(world, player, pos, stack);
  }

  @Nullable
  protected BlockState getPlacementState(BlockPlaceContext context) {
    BlockState blockState = this.block.getStateForPlacement(context);
    return blockState != null && this.canPlace(context, blockState) ? blockState : null;
  }

  private BlockState placeFromNbt(BlockPos pos, Level world, ItemStack stack, BlockState state) {
    BlockItemStateProperties blockStateComponent = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
    if (blockStateComponent.isEmpty()) return state;
    BlockState blockState = blockStateComponent.apply(state);
    if (blockState != state) world.setBlock(pos, blockState, Block.UPDATE_CLIENTS);
    return blockState;
  }

  protected boolean canPlace(BlockPlaceContext context, BlockState state) {
    return (!this.checkStatePlacement() || state.canSurvive(context.getLevel(), context.getClickedPos()))
        && context.getLevel().isUnobstructed(state, context.getClickedPos(), CollisionContext.placementContext(context.getPlayer()));
  }

  protected boolean checkStatePlacement() {
    return true;
  }

  protected boolean place(BlockPlaceContext context, BlockState state) {
    return context.getLevel().setBlock(context.getClickedPos(), state, Block.UPDATE_ALL_IMMEDIATE);
  }

  public static boolean writeNbtToBlockEntity(Level world, @Nullable Player player, BlockPos pos, ItemStack stack) {
    if (world.isClientSide()) return false;
    TypedEntityData<BlockEntityType<?>> typedEntityData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
    if (typedEntityData == null) return false;
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity == null) return false;
    BlockEntityType<?> blockEntityType = blockEntity.getType();
    if (blockEntityType != typedEntityData.type()) return false;
    if (!blockEntityType.onlyOpCanSetNbt() || player != null && player.canUseGameMasterBlocks()) {
      return typedEntityData.loadInto(blockEntity, world.registryAccess());
    }
    return false;
  }

  @Override
  public boolean shouldPrintOpWarning(@NonNull ItemStack stack, @Nullable Player player) {
    if (player == null || !player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) return false;
    TypedEntityData<BlockEntityType<?>> typedEntityData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
    if (typedEntityData == null) return false;
    return typedEntityData.type().onlyOpCanSetNbt();
  }

  @Override
  public boolean canFitInsideContainerItems() {
    return !(this.block instanceof ShulkerBoxBlock);
  }

  @Override
  public void onDestroyed(ItemEntity entity) {
    ItemContainerContents containerComponent = entity.getItem().set(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    if (containerComponent != null) ItemUtils.onContainerDestroyed(entity, containerComponent.nonEmptyItemsCopy());
  }

  @Override
  public @NonNull FeatureFlagSet requiredFeatures() {
    return this.block.requiredFeatures();
  }
}
