package syrenyx.distantmoons.content.block.block_state_enums;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.content.block.UnderworldBlock;

public enum UnderworldConfluxState implements StringRepresentable {
  ACTIVE("active"),
  LIT("lit"),
  UNLIT("unlit");

  private final String id;

  UnderworldConfluxState(final String id) {
    this.id = id;
  }

  @Override
  public @NonNull String getSerializedName() {
    return this.id;
  }

  public boolean lit() {
    return this != UNLIT;
  }

  public static UnderworldConfluxState getAtPosition(Level level, BlockPos blockPos) {
    return UnderworldBlock.inUnderworld(level, blockPos) ? LIT : UNLIT;
  }
}
