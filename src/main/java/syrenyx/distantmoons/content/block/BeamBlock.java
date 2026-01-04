package syrenyx.distantmoons.content.block;

import net.minecraft.block.*;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.DirectionTransformation;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class BeamBlock extends PoleBlock {

  public static final VoxelShape EXTENSION_SHAPE = Block.createCuboidShape(4.0, 16.0, 4.0, 12.0, 20.0, 12.0);
  private static final Map<Direction.Axis, VoxelShape> CENTER_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0));
  private static final Map<Direction.Axis, VoxelShape> UP_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(EXTENSION_SHAPE);
  private static final Map<Direction.Axis, VoxelShape> DOWN_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(VoxelShapes.transform(EXTENSION_SHAPE, DirectionTransformation.ROT_180_FACE_XY, VoxelShapeUtil.BLOCK_CENTER_ANCHOR));

  public BeamBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    VoxelShape shape = CENTER_SHAPES_BY_AXIS.get(state.get(AXIS));
    if (state.get(UP)) shape = VoxelShapes.union(shape, UP_SHAPES_BY_AXIS.get(state.get(AXIS)));
    if (state.get(DOWN)) shape = VoxelShapes.union(shape, DOWN_SHAPES_BY_AXIS.get(state.get(AXIS)));
    return shape;
  }

  @Override
  protected boolean canConnectTo(BlockView world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.offset(direction));
    if (state.isIn(DistantMoonsBlockTags.BEAM_NEVER_CONNECTS_TO)) return false;
    if (state.isIn(DistantMoonsBlockTags.BEAM_ALWAYS_CONNECTS_TO)) return true;
    Direction.Axis axis = direction.getAxis();
    if (state.getBlock() instanceof BeamBlock && state.get(AXIS) != axis) return true;
    if (axis != Direction.Axis.Y && state.getBlock() instanceof WallBlock && state.get(WallBlock.UP)) return true;
    return false;
  }
}
