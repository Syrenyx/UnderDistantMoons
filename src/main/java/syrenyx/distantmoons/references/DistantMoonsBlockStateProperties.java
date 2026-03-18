package syrenyx.distantmoons.references;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import syrenyx.distantmoons.content.block.block_state_enums.*;

public abstract class DistantMoonsBlockStateProperties {

  //BOOLEAN PROPERTIES
  public static final BooleanProperty DOUBLE = BooleanProperty.create("double");
  public static final BooleanProperty LEFT_CAPPED = BooleanProperty.create("left_capped");
  public static final BooleanProperty MIRRORED = BooleanProperty.create("mirrored");
  public static final BooleanProperty RIGHT_CAPPED = BooleanProperty.create("right_capped");
  public static final BooleanProperty SOUL_FIRE = BooleanProperty.create("soul_fire");
  public static final BooleanProperty TOP = BooleanProperty.create("top");

  //ENUM PROPERTIES
  public static final EnumProperty<BlockCorner> CORNER = EnumProperty.create("corner", BlockCorner.class);
  public static final EnumProperty<FixedLadderSideShape> FIXED_LADDER_LEFT_SHAPE = EnumProperty.create("left_shape", FixedLadderSideShape.class);
  public static final EnumProperty<FixedLadderSideShape> FIXED_LADDER_RIGHT_SHAPE = EnumProperty.create("right_shape", FixedLadderSideShape.class);
  public static final EnumProperty<RopeLadderDirection> ROPE_LADDER_DIRECTION = EnumProperty.create("direction", RopeLadderDirection.class);
  public static final EnumProperty<SpikedFenceShape> SPIKED_FENCE_SHAPE_NORTH = EnumProperty.create("north", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> SPIKED_FENCE_SHAPE_EAST = EnumProperty.create("east", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> SPIKED_FENCE_SHAPE_SOUTH = EnumProperty.create("south", SpikedFenceShape.class);
  public static final EnumProperty<SpikedFenceShape> SPIKED_FENCE_SHAPE_WEST = EnumProperty.create("west", SpikedFenceShape.class);
  public static final EnumProperty<WallSlabShape> WALL_SLAB_SHAPE = EnumProperty.create("shape", WallSlabShape.class);

  //INTEGER PROPERTIES
  public static final IntegerProperty HEAT_3 = IntegerProperty.create("heat", 0, 3);
}
