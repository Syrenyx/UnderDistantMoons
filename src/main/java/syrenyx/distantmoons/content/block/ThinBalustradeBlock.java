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

public class ThinBalustradeBlock extends AbstractBalustradeBlock {

  private static final VoxelShape BOTTOM_CAP_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0);
  private static final VoxelShape TOP_CAP_CENTER_SHAPE = Block.box(5.0, 14.0, 5.0, 11.0, 16.0, 11.0);
  private static final VoxelShape UNCAPPED_CENTER_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
  private static final VoxelShape BOTTOM_CAPPED_CENTER_SHAPE = Shapes.or(UNCAPPED_CENTER_SHAPE, BOTTOM_CAP_SHAPE);
  private static final VoxelShape DOUBLE_CAPPED_CENTER_SHAPE = Shapes.or(UNCAPPED_CENTER_SHAPE, BOTTOM_CAP_SHAPE, TOP_CAP_CENTER_SHAPE);
  private static final VoxelShape TOP_CAPPED_CENTER_SHAPE = Shapes.or(UNCAPPED_CENTER_SHAPE, TOP_CAP_CENTER_SHAPE);
  private static final VoxelShape BOTTOM_CAP_SIDE_SHAPE = Block.box(5.0, 0.0, 0.0, 11.0, 2.0, 5.0);
  private static final VoxelShape TOP_CAP_SIDE_SHAPE = Block.box(5.0, 14.0, 0.0, 11.0, 16.0, 5.0);
  private static final VoxelShape UNCAPPED_SIDE_SHAPE = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 2.0);
  private static final Map<Direction, VoxelShape> UNCAPPED_SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(UNCAPPED_SIDE_SHAPE);
  private static final Map<Direction, VoxelShape> BOTTOM_CAPPED_SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(Shapes.or(UNCAPPED_SIDE_SHAPE, BOTTOM_CAP_SIDE_SHAPE));
  private static final Map<Direction, VoxelShape> DOUBLE_CAPPED_SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(Shapes.or(UNCAPPED_SIDE_SHAPE, BOTTOM_CAP_SIDE_SHAPE, TOP_CAP_SIDE_SHAPE));
  private static final Map<Direction, VoxelShape> TOP_CAPPED_SIDE_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(Shapes.or(UNCAPPED_SIDE_SHAPE, TOP_CAP_SIDE_SHAPE));

  private static final VoxelShape CENTER_COLLISION_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 24.0, 11.0);
  private static final Map<Direction, VoxelShape> SIDE_COLLISION_SHAPES_BY_DIRECTION = VoxelShapeUtil.createHorizontalDirectionShapeMap(Block.box(5.0, 0.0, 0.0, 11.0, 24.0, 4.0));

  public ThinBalustradeBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected TagKey<Block> balustradeType() {
    return DistantMoonsBlockTags.THIN_BALUSTRADE;
  }

  @Override
  protected TagKey<Block> alwaysConnectsToHorizontally() {
    return DistantMoonsBlockTags.THIN_BALUSTRADE_ALWAYS_CONNECTS_TO_HORIZONTALLY;
  }

  @Override
  protected TagKey<Block> alwaysConnectsToVertically() {
    return DistantMoonsBlockTags.THIN_BALUSTRADE_ALWAYS_CONNECTS_TO_VERTICALLY;
  }

  @Override
  protected TagKey<Block> neverConnectsToHorizontally() {
    return DistantMoonsBlockTags.THIN_BALUSTRADE_NEVER_CONNECTS_TO_HORIZONTALLY;
  }

  @Override
  protected TagKey<Block> neverConnectsToVertically() {
    return DistantMoonsBlockTags.THIN_BALUSTRADE_NEVER_CONNECTS_TO_VERTICALLY;
  }

  @Override
  protected VoxelShape getCenterShape(EndCappedState state) {
    return switch (state) {
      case BOTTOM_CAPPED -> BOTTOM_CAPPED_CENTER_SHAPE;
      case DOUBLE_CAPPED -> DOUBLE_CAPPED_CENTER_SHAPE;
      case TOP_CAPPED -> TOP_CAPPED_CENTER_SHAPE;
      case UNCAPPED -> UNCAPPED_CENTER_SHAPE;
    };
  }

  @Override
  protected VoxelShape getSideShape(BalustradeSideState state, Direction direction) {
    return switch (state) {
      case NONE -> Shapes.empty();
      case BOTTOM_CAPPED -> BOTTOM_CAPPED_SIDE_SHAPES_BY_DIRECTION.get(direction);
      case DOUBLE_CAPPED -> DOUBLE_CAPPED_SIDE_SHAPES_BY_DIRECTION.get(direction);
      case TOP_CAPPED -> TOP_CAPPED_SIDE_SHAPES_BY_DIRECTION.get(direction);
      case UNCAPPED -> UNCAPPED_SIDE_SHAPES_BY_DIRECTION.get(direction);
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
