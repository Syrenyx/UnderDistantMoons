package syrenyx.distantmoons.content.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.initializers.DistantMoonsEnvironmentAttributes;
import syrenyx.distantmoons.references.DistantMoonsLoggerMessages;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.references.tag.DistantMoonsStructureTags;

import java.util.Optional;

public class UnderworldCompassItem extends Item {

  public static final int SEARCH_RADIUS = 100;
  private Component sealedName;

  public UnderworldCompassItem(Properties properties) {
    super(properties);
  }

  @Override
  public @NonNull Component getName(@NonNull ItemStack itemStack) {
    Component name = super.getName(itemStack);
    if (!(name.getContents() instanceof TranslatableContents contents)) return name;
    LodestoneTracker dataComponent = itemStack.get(DataComponents.LODESTONE_TRACKER);
    if (this.sealedName == null) this.sealedName = Component.translatable(contents.getKey() + ".sealed");
    return dataComponent != null && dataComponent.tracked() ? this.sealedName : name;
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    LodestoneTracker dataComponent = itemStack.get(DataComponents.LODESTONE_TRACKER);
    return dataComponent != null && dataComponent.tracked() || super.isFoil(itemStack);
  }

  @Override
  public void inventoryTick(@NonNull ItemStack itemStack, @NonNull ServerLevel serverLevel, @NonNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
    if (serverLevel.getGameTime() % 50 != 0 || !serverLevel.environmentAttributes().getValue(DistantMoonsEnvironmentAttributes.UNDERWORLD, entity.position())) return;
    LodestoneTracker dataComponent = itemStack.get(DataComponents.LODESTONE_TRACKER);
    if (dataComponent == null || dataComponent.target().isEmpty()) return;
    GlobalPos target = dataComponent.target().get();
    if (serverLevel.dimension() != target.dimension()) return;
    Vec3 position = entity.position();
    serverLevel.playLocalSound(
        position.x(), position.y(), position.z(),
        SoundEvents.ALLAY_AMBIENT_WITH_ITEM,
        entity.getSoundSource(),
        1.0F,
        1.0F,
        true
    );
  }

  @Override
  public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand interactionHand) {
    ItemStack itemStack = player.getItemInHand(interactionHand);
    LodestoneTracker dataComponent = itemStack.get(DataComponents.LODESTONE_TRACKER);
    if ((dataComponent != null && dataComponent.tracked()) || !(level instanceof ServerLevel serverLevel)) return InteractionResult.CONSUME;
    BlockPos target = serverLevel.findNearestMapStructure(
        DistantMoonsStructureTags.UNDERWORLD_COMPASS_TARGET,
        player.blockPosition(),
        SEARCH_RADIUS,
        false
    );
    if (target != null) this.setTarget(level, player, itemStack, target, false);
    else UnderDistantMoons.LOGGER.info(DistantMoonsLoggerMessages.UNDERWORLD_COMPASS_UNABLE_TO_LOCATE_TARGET);
    player.awardStat(Stats.ITEM_USED.get(this));
    return InteractionResult.SUCCESS;
  }

  @Override
  public @NonNull InteractionResult useOn(@NonNull UseOnContext useOnContext) {
    Player player = useOnContext.getPlayer();
    if (player == null) return super.useOn(useOnContext);
    BlockPos blockPos = useOnContext.getClickedPos();
    Level level = useOnContext.getLevel();
    if (!level.getBlockState(blockPos).is(DistantMoonsBlockTags.UNDERWORLD_COMPASS_TARGET)) return super.useOn(useOnContext);
    this.setTarget(level, player, useOnContext.getItemInHand(), blockPos, true);
    player.awardStat(Stats.ITEM_USED.get(this));
    return InteractionResult.SUCCESS;
  }

  private void setTarget(Level level, Player player, ItemStack itemStack, BlockPos blockPos, boolean locked) {
    LodestoneTracker dataComponent = new LodestoneTracker(Optional.of(GlobalPos.of(level.dimension(), blockPos)), locked);
    if (!player.hasInfiniteMaterials() && itemStack.getCount() == 1 || !locked) {
      itemStack.set(DataComponents.LODESTONE_TRACKER, dataComponent);
      return;
    }
    ItemStack lockedCompassStack = itemStack.transmuteCopy(this, 1);
    itemStack.consume(1, player);
    lockedCompassStack.set(DataComponents.LODESTONE_TRACKER, dataComponent);
    if (!player.getInventory().add(lockedCompassStack)) player.drop(lockedCompassStack, false);
  }
}
