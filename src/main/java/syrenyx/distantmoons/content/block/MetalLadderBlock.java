package syrenyx.distantmoons.content.block;

import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MetalLadderBlock extends LadderBlock {

  private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
  public static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(SHAPE);

  public MetalLadderBlock(Properties settings) {
    super(settings);
  }

  @Override
  protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    return SHAPES_BY_DIRECTION.get(state.getValue(FACING));
  }
}
