package syrenyx.distantmoons.affliction.effect.location_based;

import com.google.common.collect.HashMultimap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import syrenyx.distantmoons.affliction.AfflictionInstance;

public record AttributeEffect(
    Identifier id,
    RegistryEntry<EntityAttribute> attribute,
    EnchantmentLevelBasedValue amount,
    EntityAttributeModifier.Operation operation
) implements AfflictionLocationBasedEffect {

  public static final MapCodec<AttributeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
      .group(
          Identifier.CODEC.fieldOf("id").forGetter(AttributeEffect::id),
          EntityAttribute.CODEC.fieldOf("attribute").forGetter(AttributeEffect::attribute),
          EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(AttributeEffect::amount),
          EntityAttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(AttributeEffect::operation)
      )
      .apply(instance, AttributeEffect::new)
  );

  @Override
  public MapCodec<? extends AfflictionLocationBasedEffect> getCodec() {
    return CODEC;
  }

  @Override
  public void apply(ServerWorld world, int stage, Entity target, Vec3d pos, AfflictionInstance instance) {
    if (!(target instanceof LivingEntity livingEntity)) return;
    if (instance.activeLocationBasedEffects.contains(this)) return;
    livingEntity.getAttributes().addTemporaryModifiers(this.getModifiers(stage));
    instance.activeLocationBasedEffects.add(this);
  }

  @Override
  public void remove(ServerWorld world, int stage, Entity target, Vec3d pos, AfflictionInstance instance) {
    if (target instanceof LivingEntity livingEntity) {
      livingEntity.getAttributes().removeModifiers(this.getModifiers(stage));
      instance.activeLocationBasedEffects.remove(this);
    }
  }

  private HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(int level) {
    HashMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> hashMultimap = HashMultimap.create();
    hashMultimap.put(this.attribute, new EntityAttributeModifier(this.id.withSuffixedPath("/affliction"), this.amount().getValue(level), this.operation()));
    return hashMultimap;
  }
}
