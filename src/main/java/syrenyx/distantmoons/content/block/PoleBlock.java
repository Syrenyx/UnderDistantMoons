package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;
import com.mojang.math.OctahedralGroup;
import java.util.Map;

public class PoleBlock extends AbstractPoleBlock {

  private static final VoxelShape EXTENSION_SHAPE = Block.box(6.0, 16.0, 6.0, 10.0, 22.0, 10.0);
  private static final Map<Direction.Axis, VoxelShape> CENTER_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0));
  private static final Map<Direction.Axis, VoxelShape> UP_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(EXTENSION_SHAPE);
  private static final Map<Direction.Axis, VoxelShape> DOWN_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Shapes.rotate(EXTENSION_SHAPE, OctahedralGroup.ROT_180_FACE_XY, VoxelShapeUtil.BLOCK_CENTER_ANCHOR));

  public PoleBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
    VoxelShape shape = CENTER_SHAPES_BY_AXIS.get(state.getValue(AXIS));
    if (state.getValue(UP)) shape = Shapes.or(shape, UP_SHAPES_BY_AXIS.get(state.getValue(AXIS)));
    if (state.getValue(DOWN)) shape = Shapes.or(shape, DOWN_SHAPES_BY_AXIS.get(state.getValue(AXIS)));
    return shape;
  }

  @Override
  protected boolean canConnectTo(BlockGetter world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.relative(direction));
    if (state.is(DistantMoonsBlockTags.POLE_NEVER_CONNECTS_TO)) return false;
    if (state.is(DistantMoonsBlockTags.POLE_ALWAYS_CONNECTS_TO)) return true;
    Direction.Axis axis = direction.getAxis();
    if (axis != Direction.Axis.Y && state.is(DistantMoonsBlockTags.POLE_ALWAYS_CONNECTS_TO_HORIZONTALLY)) return true;
    if (state.getBlock() instanceof AbstractBranchBlock && state.getValue(AbstractBranchBlock.ATTACHMENT).getAxis() != axis) return true;
    if (state.getBlock() instanceof AbstractPoleBlock && state.getValue(AXIS) != axis) return true;
    return false;
  }
}
