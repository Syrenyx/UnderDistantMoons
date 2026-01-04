package syrenyx.distantmoons.content.affliction.effect.location_based;

import com.google.common.collect.HashMultimap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.phys.Vec3;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;

public record AttributeEffect(
    Identifier id,
    Holder<Attribute> attribute,
    LevelBasedValue amount,
    AttributeModifier.Operation operation
) implements AfflictionLocationBasedEffect {

  public static final MapCodec<AttributeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("id").forGetter(AttributeEffect::id),
          Attribute.CODEC.fieldOf("attribute").forGetter(AttributeEffect::attribute),
          LevelBasedValue.CODEC.fieldOf("amount").forGetter(AttributeEffect::amount),
          AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(AttributeEffect::operation)
      )
      .apply(instance, AttributeEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionLocationBasedEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerLevel world, int stage, Entity target, Vec3 pos, AfflictionInstance instance) {
    if (!(target instanceof LivingEntity livingEntity) || instance.activeAttributeEffects.contains(this)) return;
    livingEntity.getAttributes().addTransientAttributeModifiers(this.getModifiers(stage, instance.affliction().getRegisteredName()));
    instance.activeAttributeEffects.add(this);
  }

  @Override
  public void remove(ServerLevel world, int stage, Entity target, Vec3 pos, AfflictionInstance instance) {
    if (target instanceof LivingEntity livingEntity) {
      livingEntity.getAttributes().removeAttributeModifiers(this.getModifiers(stage, instance.affliction().getRegisteredName()));
      instance.activeAttributeEffects.remove(this);
    }
  }

  private HashMultimap<Holder<Attribute>, AttributeModifier> getModifiers(int level, String afflictionId) {
    HashMultimap<Holder<Attribute>, AttributeModifier> hashMultimap = HashMultimap.create();
    AttributeModifier modifier = new AttributeModifier(
        this.id.withSuffix("/affliction/" + afflictionId.replace(":", "/")),
        this.amount().calculate(level),
        this.operation()
    );
    hashMultimap.put(this.attribute, modifier);
    return hashMultimap;
  }
}
