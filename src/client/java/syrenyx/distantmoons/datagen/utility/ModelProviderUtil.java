package syrenyx.distantmoons.datagen.utility;

import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public abstract class ModelProviderUtil {

  public static ConditionBuilder directionalMultipartCondition(@Nullable Boolean north, @Nullable Boolean east, @Nullable Boolean south, @Nullable Boolean west) {
    ConditionBuilder builder = new ConditionBuilder();
    if (north != null) builder.term(BlockStateProperties.NORTH, north);
    if (east != null) builder.term(BlockStateProperties.EAST, east);
    if (south != null) builder.term(BlockStateProperties.SOUTH, south);
    if (west != null) builder.term(BlockStateProperties.WEST, west);
    return builder;
  }
}
