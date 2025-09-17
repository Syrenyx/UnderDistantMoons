package syrenyx.distantmoons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class MetalLadderBlock extends LadderBlock {

  private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
  public static final Map<Direction, VoxelShape> SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(SHAPE);

  public MetalLadderBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPES_BY_DIRECTION.get(state.get(FACING));
  }
}
