package syrenyx.distantmoons.initializers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.entity.SittingSpotEntity;

public class DistantMoonsEntityTypes {

  public static final EntityType<SittingSpotEntity> SITTING_SPOT = register("sitting_spot", EntityType.Builder.create(SittingSpotEntity::new, SpawnGroup.MISC).dimensions(0.5F, 0.5F));

  private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
    RegistryKey<EntityType<?>> key = UnderDistantMoons.registryKeyOf(id, RegistryKeys.ENTITY_TYPE);
    return Registry.register(Registries.ENTITY_TYPE, key, builder.build(key));
  }

  public static void initialize() {}
}
