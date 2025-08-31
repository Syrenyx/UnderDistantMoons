package syrenyx.distantmoons.affliction.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public record AfflictionAttributeEffect(
    Identifier id,
    RegistryEntry<EntityAttribute> attribute,
    float amount,
    EntityAttributeModifier.Operation operation
) {

  public static final MapCodec<AfflictionAttributeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("id").forGetter(AfflictionAttributeEffect::id),
          EntityAttribute.CODEC.fieldOf("attribute").forGetter(AfflictionAttributeEffect::attribute),
          Codec.FLOAT.fieldOf("amount").forGetter(AfflictionAttributeEffect::amount),
          EntityAttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(AfflictionAttributeEffect::operation)
      )
      .apply(instance, AfflictionAttributeEffect::new)
  );
}
