package syrenyx.distantmoons.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;

public class SimplifiedStairsBlock extends StairsBlock {

  private static final MapCodec<SimplifiedStairsBlock> CODEC = createCodec(SimplifiedStairsBlock::new);

  public SimplifiedStairsBlock(Settings settings) {
    super(Blocks.AIR.getDefaultState(), settings);
  }

  @Override
  public MapCodec<? extends StairsBlock> getCodec() {
    return CODEC;
  }

  @Override
  public float getBlastResistance() {
    return this.resistance;
  }
}
