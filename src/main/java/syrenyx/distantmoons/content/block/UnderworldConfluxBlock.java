package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.block_state_enums.UnderworldConfluxState;
import syrenyx.distantmoons.content.block.entity.UnderworldConfluxBlockEntity;
import syrenyx.distantmoons.content.particle.UnderworldConfluxEffectOptions;
import syrenyx.distantmoons.content.particle.UnderworldParticleOptions;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;
import syrenyx.distantmoons.references.DistantMoonsBlockStateProperties;
import syrenyx.distantmoons.references.tag.DistantMoonsEntityTypeTags;
import syrenyx.distantmoons.utility.VectorUtil;

import java.util.Optional;

public class UnderworldConfluxBlock extends BaseEntityBlock implements UnderworldBlock {

  public static final EnumProperty<UnderworldConfluxState> STATE = DistantMoonsBlockStateProperties.UNDERWORLD_CONFLUX_STATE;

  private final ResourceKey<ConfiguredFeature<?, ?>> anchorChamberFeature;

  public UnderworldConfluxBlock(Properties properties, ResourceKey<ConfiguredFeature<?, ?>> anchorChamberFeature) {
    super(properties);
    this.anchorChamberFeature = anchorChamberFeature;
    this.registerDefaultState(this.defaultBlockState()
        .setValue(STATE, UnderworldConfluxState.LIT)
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(STATE);
  }

  @Override
  protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
    return simpleCodec(properties -> new UnderworldConfluxBlock(properties, this.anchorChamberFeature));
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
    return new UnderworldConfluxBlockEntity(blockPos, blockState);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
    BlockState blockState = super.getStateForPlacement(context);
    if (blockState == null) return null;
    return blockState.setValue(STATE, UnderworldConfluxState.getAtPosition(context.getLevel(), context.getClickedPos()));
  }

  @Override
  protected void tick(@NonNull BlockState blockState, @NonNull ServerLevel serverLevel, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    attemptToTeleport(serverLevel, blockPos);
    serverLevel.setBlock(blockPos, blockState.setValue(STATE, UnderworldBlock.inUnderworld(serverLevel, blockPos) ? UnderworldConfluxState.LIT : UnderworldConfluxState.UNLIT), Block.UPDATE_ALL);
  }

  @Override
  protected void randomTick(@NonNull BlockState blockState, @NonNull ServerLevel serverLevel, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    UnderworldConfluxState state = blockState.getValue(STATE);
    if (state == UnderworldConfluxState.ACTIVE) return;
    boolean inUnderworld = UnderworldBlock.inUnderworld(serverLevel, blockPos);
    if (inUnderworld == state.lit()) return;
    serverLevel.setBlock(blockPos, blockState.setValue(STATE, inUnderworld ? UnderworldConfluxState.LIT : UnderworldConfluxState.UNLIT), Block.UPDATE_ALL);
  }

  @Override
  public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull RandomSource randomSource) {
    if (!state.getValue(STATE).lit()) return;
    Vec3 anchor = blockPos.getCenter();
    int color = getKeystoneColor(level, blockPos);
    spawnUnderworldConfluxParticles(level, anchor, color, randomSource.nextInt(1, 3), false, randomSource);
    if (randomSource.nextInt(10) != 0) return;
    Vec3 particlePosition = anchor.add(VectorUtil.randomPointOnSphere(randomSource, ((float) randomSource.nextInt(2, 4) / 5) + 1));
    level.addParticle(
        new UnderworldParticleOptions(anchor, color),
        particlePosition.x(), particlePosition.y(), particlePosition.z(),
        0.0F, 0.0F, 0.0F
    );
  }

  @Override
  protected @NonNull InteractionResult useWithoutItem(
      @NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull Player player, @NonNull BlockHitResult blockHitResult
  ) {
    if (blockState.getValue(STATE) == UnderworldConfluxState.ACTIVE) return InteractionResult.PASS;
    if (player.isCrouching()) return swapKeystone(level, ItemStack.EMPTY, blockPos, player, InteractionHand.MAIN_HAND) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    return this.attemptToInitiateTeleport(level, blockState, blockPos) ? InteractionResult.SUCCESS : InteractionResult.PASS;
  }

  @Override
  protected @NonNull InteractionResult useItemOn(
      @NonNull ItemStack itemStack, @NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull Player player, @NonNull InteractionHand interactionHand, @NonNull BlockHitResult blockHitResult
  ) {
    if (blockState.getValue(STATE) == UnderworldConfluxState.ACTIVE) return InteractionResult.PASS;
    if (itemStack.has(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE)) {
      swapKeystone(level, itemStack, blockPos, player, interactionHand);
      return InteractionResult.SUCCESS;
    }
    if (player.isCrouching()) return InteractionResult.TRY_WITH_EMPTY_HAND;
    return this.attemptToInitiateTeleport(level, blockState, blockPos) ? InteractionResult.SUCCESS : InteractionResult.TRY_WITH_EMPTY_HAND;
  }

  private static boolean swapKeystone(Level level, ItemStack newKeystoneStack, BlockPos blockPos, Player player, InteractionHand interactionHand) {
    if (!(level.getBlockEntity(blockPos) instanceof UnderworldConfluxBlockEntity blockEntity)) return false;
    ItemStack oldKeystoneStack = blockEntity.removeItem();
    if (newKeystoneStack.has(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE)) blockEntity.setItem(player.hasInfiniteMaterials() ? newKeystoneStack.copyWithCount(1) : newKeystoneStack.split(1));
    else blockEntity.setItem(ItemStack.EMPTY);
    if (oldKeystoneStack.isEmpty()) return true;
    if (newKeystoneStack.isEmpty()) {
      player.setItemInHand(interactionHand, oldKeystoneStack);
      return true;
    }
    else if (player.getInventory().add(oldKeystoneStack)) return true;
    Vec3 position = blockPos.getCenter();
    float rawDeltaValue = level.getRandom().nextFloat() * (float) (Math.PI * 2);
    float reductionFactor = level.getRandom().nextFloat() * 0.5F;
    level.addFreshEntity(new ItemEntity(
        level,
        position.x(), position.y() + 0.5F, position.z(),
        oldKeystoneStack,
        -Mth.sin(rawDeltaValue) * reductionFactor, 0.2F, Mth.cos(rawDeltaValue) * reductionFactor
    ));
    return true;
  }

  private boolean attemptToInitiateTeleport(Level level, BlockState blockState, BlockPos blockPos) {
    if (blockState.getValue(STATE) != UnderworldConfluxState.LIT || !(level.getBlockEntity(blockPos) instanceof UnderworldConfluxBlockEntity blockEntity) || !blockEntity.getItem().has(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE)) return false;
    ResourceKey<Level> targetDimension = ResourceKey.create(Registries.DIMENSION, blockEntity.getItem().get(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE).dimension());
    if (!(level instanceof ServerLevel serverLevel) || serverLevel.dimension() == targetDimension || !UnderworldAnchorBlock.locateOrConstructAnchorChamber(serverLevel.getServer().getLevel(targetDimension), blockPos, this.anchorChamberFeature) || !level.setBlock(blockPos, blockState.setValue(STATE, UnderworldConfluxState.ACTIVE), Block.UPDATE_ALL)) return false;
    level.scheduleTick(blockPos, blockState.getBlock(), 20);
    spawnUnderworldConfluxParticles(level, blockPos.getCenter(), getKeystoneColor(level, blockPos), 50, true, level.getRandom());
    return true;
  }

  private static boolean attemptToTeleport(ServerLevel serverLevel, BlockPos blockPos) {
    if (!(serverLevel.getBlockEntity(blockPos) instanceof UnderworldConfluxBlockEntity blockEntity) || !blockEntity.getItem().has(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE)) return false;
    ResourceKey<Level> targetDimension = ResourceKey.create(Registries.DIMENSION, blockEntity.getItem().get(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE).dimension());
    if (serverLevel.dimension() == targetDimension) return false;
    ServerLevel dimensionLevel = serverLevel.getServer().getLevel(targetDimension);
    Optional<BlockPos> optionalAnchorPos = UnderworldAnchorBlock.locateExistingUnderworldAnchor(dimensionLevel, blockPos);
    if (optionalAnchorPos.isEmpty()) return false;
    BlockPos anchorPos = optionalAnchorPos.get();
    if (dimensionLevel.isOutsideBuildHeight(anchorPos) || !dimensionLevel.getWorldBorder().isWithinBounds(anchorPos)) return false;
    for (Entity entity : serverLevel.getEntitiesOfClass(
        Entity.class,
        new AABB(blockPos.getX() + 2, blockPos.getY() + 2, blockPos.getZ() + 2, blockPos.getX() - 2, blockPos.getY() - 2, blockPos.getZ() - 2),
        entity -> !entity.isPassenger() && !entity.is(DistantMoonsEntityTypeTags.IGNORED_BY_UNDERWORLD_CONFLUX)
    )) {
      entity.teleport(new TeleportTransition(
          dimensionLevel,
          entity.position().subtract(blockPos.getCenter()).add(anchorPos.getCenter()),
          Vec3.ZERO,
          entity.getYRot(), entity.getXRot(),
          TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
      ));
    }
    return true;
  }

  private static void spawnUnderworldConfluxParticles(Level level, Vec3 center, int color, int amount, boolean inverted, RandomSource randomSource) {
    for (int i = 0; i < amount; i++) {
      Vec3 outerPosition = center.add(VectorUtil.randomPointOnSphere(randomSource, 5));
      level.addParticle(
          new UnderworldConfluxEffectOptions(
              Mth.HALF_PI * randomSource.nextInt(0, 4),
              !inverted,
              color,
              inverted ? center : outerPosition
          ),
          inverted ? outerPosition.x() : center.x(),
          inverted ? outerPosition.y() : center.y(),
          inverted ? outerPosition.z() : center.z(),
          0.0F, 0.0F, 0.0F
      );
    }
  }

  private static int getKeystoneColor(@NonNull Level level, @NonNull BlockPos blockPos) {
    if (!(level.getBlockEntity(blockPos) instanceof UnderworldConfluxBlockEntity blockEntity)) return UnderworldBlock.DEFAULT_COLOR;
    ItemStack itemStack = blockEntity.getItem();
    if (itemStack.isEmpty() || !itemStack.has(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE)) return UnderworldBlock.DEFAULT_COLOR;
    return itemStack.get(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE).color();
  }

  public static void onKeystoneChanged(Level level, BlockPos blockPos) {
    BlockState blockState = level.getBlockState(blockPos);
    level.sendBlockUpdated(blockPos, blockState, blockState, 0);
  }

  public static int lightLevel(BlockState blockState) {
    return switch (blockState.getValue(STATE)) {
      case ACTIVE -> 15;
      case LIT -> 10;
      case UNLIT -> 0;
    };
  }

  public static MapColor mapColor(BlockState blockState) {
    return blockState.getValue(STATE).lit() ? MapColor.COLOR_ORANGE : MapColor.COLOR_BLACK;
  }
}
