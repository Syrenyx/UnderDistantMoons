package syrenyx.distantmoons.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.references.tag.DistantMoonsDamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class DistantMoonsDamageTypeTagProvider extends FabricTagProvider<DamageType> {

  public DistantMoonsDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, Registries.DAMAGE_TYPE, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.@NonNull Provider provider) {

    this.builder(DistantMoonsDamageTypeTags.IS_EXPLOSION_OR_FIRE)
        .forceAddTag(DamageTypeTags.IS_EXPLOSION)
        .forceAddTag(DamageTypeTags.IS_FIRE);
  }
}
