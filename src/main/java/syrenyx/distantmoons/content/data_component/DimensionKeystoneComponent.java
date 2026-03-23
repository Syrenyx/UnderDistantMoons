package syrenyx.distantmoons.content.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import syrenyx.distantmoons.content.block.UnderworldBlock;

public record DimensionKeystoneComponent(Identifier dimension, int color, Identifier slottedItemModel) {

  public static final int ABYSS_COLOR = 4101631;
  public static final int NETHER_COLOR = 16738622;

  public static final Codec<DimensionKeystoneComponent> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("dimension").forGetter(DimensionKeystoneComponent::dimension),
          ExtraCodecs.RGB_COLOR_CODEC.optionalFieldOf("color", UnderworldBlock.DEFAULT_COLOR).forGetter(DimensionKeystoneComponent::color),
          Identifier.CODEC.fieldOf("slotted_item_model").forGetter(DimensionKeystoneComponent::dimension)
      )
      .apply(instance, DimensionKeystoneComponent::new)
  );
}
