package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.entity.SittingSpotEntity;
import syrenyx.distantmoons.initializers.DistantMoonsEntityTypes;

public class PillowBlock extends SlabBlock {

  public PillowBlock(Properties settings) {
    super(settings);
  }

  @Override
  public @NonNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
    if (hit.getDirection() == Direction.DOWN || player.isShiftKeyDown()) return super.useWithoutItem(state, world, pos, player, hit);
    if (world.isClientSide()) return InteractionResult.SUCCESS;
    if (!world.getEntities(DistantMoonsEntityTypes.SITTING_SPOT, new AABB(pos), sittingSpot -> true).isEmpty()) return InteractionResult.PASS;
    SittingSpotEntity entity = DistantMoonsEntityTypes.SITTING_SPOT.spawn((ServerLevel) world, pos, EntitySpawnReason.TRIGGERED);
    if (entity == null) return InteractionResult.FAIL;
    if (state.getValue(SlabBlock.TYPE) != SlabType.BOTTOM) entity.setPosRaw(entity.getX(), entity.getY() + 0.5, entity.getZ());
    player.startRiding(entity);
    return InteractionResult.SUCCESS;
  }
}
