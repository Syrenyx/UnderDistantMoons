package syrenyx.distantmoons.content.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import syrenyx.distantmoons.content.entity.SittingSpotEntity;
import syrenyx.distantmoons.initializers.DistantMoonsEntityTypes;

public class PillowBlock extends SlabBlock {

  public PillowBlock(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (hit.getSide() == Direction.DOWN || player.isSneaking()) return super.onUse(state, world, pos, player, hit);
    if (world.isClient()) return ActionResult.SUCCESS;
    if (!world.getEntitiesByType(DistantMoonsEntityTypes.SITTING_SPOT, new Box(pos), sittingSpot -> true).isEmpty()) return ActionResult.PASS;
    SittingSpotEntity entity = DistantMoonsEntityTypes.SITTING_SPOT.spawn((ServerWorld) world, pos, SpawnReason.TRIGGERED);
    if (entity == null) return ActionResult.FAIL;
    if (state.get(SlabBlock.TYPE) != SlabType.BOTTOM) entity.setPos(entity.getX(), entity.getY() + 0.5, entity.getZ());
    player.startRiding(entity);
    return ActionResult.SUCCESS;
  }
}
