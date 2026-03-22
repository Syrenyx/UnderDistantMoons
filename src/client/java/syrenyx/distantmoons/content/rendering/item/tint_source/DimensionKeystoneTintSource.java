package syrenyx.distantmoons.content.rendering.item.tint_source;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.data_component.DimensionKeystoneComponent;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;

public record DimensionKeystoneTintSource(int defaultColor) implements ItemTintSource {

  public static final MapCodec<DimensionKeystoneTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(DimensionKeystoneTintSource::defaultColor)
      )
      .apply(instance, DimensionKeystoneTintSource::new)
  );

  @Override
  public @NonNull MapCodec<? extends ItemTintSource> type() {
    return MAP_CODEC;
  }

  @Override
  public int calculate(@NonNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
    DimensionKeystoneComponent component = itemStack.get(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE);
    return ARGB.opaque(component != null ? component.color() : this.defaultColor);
  }
}
