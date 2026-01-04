package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.entity.SittingSpotEntity;

public class DistantMoonsEntityTypes {

  public static final EntityType<SittingSpotEntity> SITTING_SPOT = register("sitting_spot", EntityType.Builder.of(SittingSpotEntity::new, MobCategory.MISC).sized(0.5F, 0.5F));

  private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
    ResourceKey<EntityType<?>> key = UnderDistantMoons.registryKeyOf(id, Registries.ENTITY_TYPE);
    return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
  }

  public static void initialize() {}
}
