package syrenyx.distantmoons.datagen.utility;

import net.minecraft.client.render.model.json.MultipartModelConditionBuilder;
import net.minecraft.state.property.Properties;
import org.jetbrains.annotations.Nullable;

public abstract class ModelProviderUtil {

  public static MultipartModelConditionBuilder directionalMultipartCondition(@Nullable Boolean north, @Nullable Boolean east, @Nullable Boolean south, @Nullable Boolean west) {
    MultipartModelConditionBuilder builder = new MultipartModelConditionBuilder();
    if (north != null) builder.put(Properties.NORTH, north);
    if (east != null) builder.put(Properties.EAST, east);
    if (south != null) builder.put(Properties.SOUTH, south);
    if (west != null) builder.put(Properties.WEST, west);
    return builder;
  }
}
