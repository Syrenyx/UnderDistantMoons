package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class UnderworldConfluxBlock extends BaseEntityBlock implements UnderworldBlock {

  public UnderworldConfluxBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
    return null;
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
    return null;
  }
}
