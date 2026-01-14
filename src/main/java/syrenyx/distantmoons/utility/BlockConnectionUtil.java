package syrenyx.distantmoons.utility;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StairsShape;

public abstract class BlockConnectionUtil {

  public static boolean isStairBlockFacePartiallySturdy(BlockState state, Direction direction) {
    if (direction.getAxis() == Direction.Axis.Y) return true;
    StairsShape shape = state.getValue(StairBlock.SHAPE);
    if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) return true;
    Direction facing = state.getValue(StairBlock.FACING);
    if (shape == StairsShape.STRAIGHT) return facing != direction.getOpposite();
    if (facing == direction) return true;
    if (shape == StairsShape.OUTER_LEFT) return facing == direction.getClockWise();
    else return facing == direction.getCounterClockWise();
  }
}
