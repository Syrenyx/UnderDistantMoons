package syrenyx.distantmoons.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;
import com.mojang.math.OctahedralGroup;
import java.util.Map;

public class BeamBlock extends PoleBlock {

  public static final VoxelShape EXTENSION_SHAPE = Block.box(4.0, 16.0, 4.0, 12.0, 20.0, 12.0);
  private static final Map<Direction.Axis, VoxelShape> CENTER_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0));
  private static final Map<Direction.Axis, VoxelShape> UP_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(EXTENSION_SHAPE);
  private static final Map<Direction.Axis, VoxelShape> DOWN_SHAPES_BY_AXIS = VoxelShapeUtil.createAxisShapeMap(Shapes.rotate(EXTENSION_SHAPE, OctahedralGroup.ROT_180_FACE_XY, VoxelShapeUtil.BLOCK_CENTER_ANCHOR));

  public BeamBlock(Properties settings) {
    super(settings);
  }

  @Override
  protected @NonNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    VoxelShape shape = CENTER_SHAPES_BY_AXIS.get(state.getValue(AXIS));
    if (state.getValue(UP)) shape = Shapes.or(shape, UP_SHAPES_BY_AXIS.get(state.getValue(AXIS)));
    if (state.getValue(DOWN)) shape = Shapes.or(shape, DOWN_SHAPES_BY_AXIS.get(state.getValue(AXIS)));
    return shape;
  }

  @Override
  protected boolean canConnectTo(BlockGetter world, BlockPos pos, Direction direction) {
    BlockState state = world.getBlockState(pos.relative(direction));
    if (state.is(DistantMoonsBlockTags.BEAM_NEVER_CONNECTS_TO)) return false;
    if (state.is(DistantMoonsBlockTags.BEAM_ALWAYS_CONNECTS_TO)) return true;
    Direction.Axis axis = direction.getAxis();
    if (state.getBlock() instanceof BeamBlock && state.getValue(AXIS) != axis) return true;
    if (axis != Direction.Axis.Y && state.getBlock() instanceof WallBlock && state.getValue(WallBlock.UP)) return true;
    return false;
  }
}
