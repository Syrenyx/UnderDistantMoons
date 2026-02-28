package syrenyx.distantmoons.content.rendering.item.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.initializers.DistantMoonsEnvironmentAttributes;

public record UnderworldDimension() implements ConditionalItemModelProperty {

  public static final MapCodec<UnderworldDimension> MAP_CODEC = MapCodec.unit(new UnderworldDimension());

  @Override
  public @NonNull MapCodec<? extends ConditionalItemModelProperty> type() {
    return MAP_CODEC;
  }

  @Override
  public boolean get(@NonNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, @NonNull ItemDisplayContext itemDisplayContext) {
    return clientLevel != null && livingEntity != null && clientLevel.environmentAttributes().getValue(DistantMoonsEnvironmentAttributes.UNDERWORLD, livingEntity.position());
  }
}
