package syrenyx.distantmoons.content.block;

import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import syrenyx.distantmoons.content.block.block_state_enums.BalustradeSideState;
import syrenyx.distantmoons.content.block.block_state_enums.EndCappedState;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class WideBalustradeBlock extends AbstractBalustradeBlock {

  private static final VoxelShape CENTER_SHAPE = Shapes.or(
      Block.box(6.0, 3.0, 6.0, 10.0, 13.0, 10.0),
      Block.box(4.0, 0.0, 4.0, 12.0, 3.0, 12.0),
      Block.box(4.0, 5.0, 4.0, 12.0, 11.0, 12.0),
      Block.box(4.0, 13.0, 4.0, 12.0, 16.0, 12.0)
  );
  private static final VoxelShape BOTTOM_CAP_SHAPE = Block.box(4.0, 0.0, 0.0, 12.0, 3.0, 4.0);
  private static final VoxelShape TOP_CAP_SHAPE = Block.box(4.0, 13.0, 0.0, 12.0, 16.0, 4.0);
  private static final VoxelShape DOUBLE_CAP_SHAPE = Shapes.or(BOTTOM_CAP_SHAPE, TOP_CAP_SHAPE);
  private static final Map<Direction, VoxelShape> BOTTOM_CAP_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(BOTTOM_CAP_SHAPE);
  private static final Map<Direction, VoxelShape> TOP_CAP_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(TOP_CAP_SHAPE);
  private static final Map<Direction, VoxelShape> DOUBLE_CAP_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(DOUBLE_CAP_SHAPE);

  private static final VoxelShape CENTER_COLLISION_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 24.0, 12.0);
  private static final Map<Direction, VoxelShape> SIDE_COLLISION_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(Block.box(4.0, 0.0, 0.0, 12.0, 24.0, 4.0));

  public WideBalustradeBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected TagKey<Block> balustradeType() {
    return DistantMoonsBlockTags.WIDE_BALUSTRADE;
  }

  @Override
  protected TagKey<Block> alwaysConnectsToHorizontally() {
    return DistantMoonsBlockTags.WIDE_BALUSTRADE_ALWAYS_CONNECTS_TO_HORIZONTALLY;
  }

  @Override
  protected TagKey<Block> alwaysConnectsToVertically() {
    return DistantMoonsBlockTags.WIDE_BALUSTRADE_ALWAYS_CONNECTS_TO_VERTICALLY;
  }

  @Override
  protected boolean canConnectToBalustradeCap() {
    return false;
  }

  @Override
  protected TagKey<Block> neverConnectsToHorizontally() {
    return DistantMoonsBlockTags.WIDE_BALUSTRADE_NEVER_CONNECTS_TO_HORIZONTALLY;
  }

  @Override
  protected TagKey<Block> neverConnectsToVertically() {
    return DistantMoonsBlockTags.WIDE_BALUSTRADE_NEVER_CONNECTS_TO_VERTICALLY;
  }

  @Override
  protected VoxelShape getCenterShape(EndCappedState state) {
    return CENTER_SHAPE;
  }

  @Override
  protected VoxelShape getSideShape(BalustradeSideState state, Direction direction) {
    return switch (state) {
      case BOTTOM_CAPPED -> BOTTOM_CAP_SHAPES_BY_DIRECTION.get(direction);
      case DOUBLE_CAPPED -> DOUBLE_CAP_SHAPES_BY_DIRECTION.get(direction);
      case TOP_CAPPED -> TOP_CAP_SHAPES_BY_DIRECTION.get(direction);
      default -> Shapes.empty();
    };
  }

  @Override
  protected VoxelShape getCenterCollisionShape() {
    return CENTER_COLLISION_SHAPE;
  }

  @Override
  protected VoxelShape getSideCollisionShape(Direction direction) {
    return SIDE_COLLISION_SHAPES_BY_DIRECTION.get(direction);
  }
}
