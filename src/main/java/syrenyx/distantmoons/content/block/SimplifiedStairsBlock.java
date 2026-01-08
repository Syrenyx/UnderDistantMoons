package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import org.jspecify.annotations.NonNull;

public class SimplifiedStairsBlock extends StairBlock {

  private static final MapCodec<SimplifiedStairsBlock> CODEC = simpleCodec(SimplifiedStairsBlock::new);

  public SimplifiedStairsBlock(Properties settings) {
    super(Blocks.AIR.defaultBlockState(), settings);
  }

  @Override
  public @NonNull MapCodec<? extends StairBlock> codec() {
    return CODEC;
  }

  @Override
  public float getExplosionResistance() {
    return this.explosionResistance;
  }
}
