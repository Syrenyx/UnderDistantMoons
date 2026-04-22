package syrenyx.distantmoons.content.block;

import com.google.common.collect.Maps;
import com.mojang.math.OctahedralGroup;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import syrenyx.distantmoons.references.tag.DistantMoonsBlockTags;
import syrenyx.distantmoons.utility.VoxelShapeUtil;

import java.util.Map;

public class ThinBranchBlock extends AbstractBranchBlock {

  private static final VoxelShape BOTTOM_EXTENSION_SHAPE = Block.box(6.0, -6.0, 6.0, 10.0, 0.0, 10.0);
  private static final VoxelShape CORNER_SHAPE = Shapes.or(
      Block.box(6.0, 6.0, 6.0, 10.0, 16.0, 10.0),
      Block.box(6.0, 6.0, 0.0, 10.0, 10.0, 6.0)
  );
  private static final VoxelShape SIDE_EXTENSION_SHAPE = Block.box(6.0, 6.0, -6.0, 10.0, 10.0, 0.0);
  private static final VoxelShape STRAIGHT_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
  private static final Map<Direction, VoxelShape> CENTER_SHAPES_BY_DIRECTION = createDirectionShapeMap(CORNER_SHAPE, STRAIGHT_SHAPE);
  private static final Map<Direction, VoxelShape> EXTENSION_SHAPES_BY_DIRECTION = createDirectionShapeMap(SIDE_EXTENSION_SHAPE, BOTTOM_EXTENSION_SHAPE);

  public ThinBranchBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected TagKey<Block> alwaysConnectsTo() {
    return DistantMoonsBlockTags.THIN_BRANCH_ALWAYS_CONNECTS_TO;
  }

  @Override
  protected TagKey<Block> extendsToHorizontally() {
    return DistantMoonsBlockTags.THIN_BRANCH_EXTENDS_TO_HORIZONTALLY;
  }

  @Override
  protected TagKey<Block> extendsToVertically() {
    return DistantMoonsBlockTags.THIN_BRANCH_EXTENDS_TO_VERTICALLY;
  }

  @Override
  protected TagKey<Block> neverConnectsTo() {
    return DistantMoonsBlockTags.THIN_BRANCH_NEVER_CONNECTS_TO;
  }

  @Override
  protected VoxelShape getCenterShapeByDirection(Direction direction) {
    return CENTER_SHAPES_BY_DIRECTION.get(direction);
  }

  @Override
  protected VoxelShape getExtensionShapeByDirection(Direction direction) {
    return EXTENSION_SHAPES_BY_DIRECTION.get(direction);
  }

  private static Map<Direction, VoxelShape> createDirectionShapeMap(VoxelShape sideShape, VoxelShape downShape) {
    return Maps.newEnumMap(Map.of(
        Direction.NORTH, sideShape,
        Direction.EAST, Shapes.rotate(sideShape, OctahedralGroup.ROT_90_Y_NEG, VoxelShapeUtil.BLOCK_CENTER_ANCHOR),
        Direction.SOUTH, Shapes.rotate(sideShape, OctahedralGroup.ROT_180_FACE_XZ, VoxelShapeUtil.BLOCK_CENTER_ANCHOR),
        Direction.WEST, Shapes.rotate(sideShape, OctahedralGroup.ROT_90_Y_POS, VoxelShapeUtil.BLOCK_CENTER_ANCHOR),
        Direction.DOWN, downShape
    ));
  }
}
