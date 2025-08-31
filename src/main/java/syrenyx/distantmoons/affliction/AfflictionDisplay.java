package syrenyx.distantmoons.affliction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

public class AfflictionDisplay {

  public static final Codec<AfflictionDisplay> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          TextCodecs.CODEC.fieldOf("description").forGetter(AfflictionDisplay::getDescription),
          Identifier.CODEC.fieldOf("icon").forGetter(AfflictionDisplay::getIcon),
          Codecs.RGB.fieldOf("color").forGetter(AfflictionDisplay::getColor)
      )
      .apply(instance, AfflictionDisplay::new)
  );
  private final Text description;
  private final Identifier icon;
  private final int color;

  public AfflictionDisplay(
      Text description,
      Identifier icon,
      int color
  ) {
    this.description = description;
    this.icon = icon;
    this.color = color;
  }

  public Text getDescription() {
    return this.description;
  }

  public Identifier getIcon() {
    return this.icon;
  }

  public int getColor() {
    return this.color;
  }
}
