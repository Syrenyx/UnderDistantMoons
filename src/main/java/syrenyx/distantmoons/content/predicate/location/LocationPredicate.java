package syrenyx.distantmoons.content.predicate.location;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.FluidPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.levelgen.structure.Structure;

public record LocationPredicate(
    Optional<PositionRangePredicate> position,
    Optional<HolderSet<Biome>> biomes,
    Optional<HolderSet<Structure>> structures,
    Optional<ResourceKey<Level>> dimension,
    Optional<Boolean> smokey,
    Optional<LightPredicate> light,
    Optional<BlockPredicate> block,
    Optional<FluidPredicate> fluid,
    Optional<Boolean> canSeeSky
) {

  public static final Codec<LocationPredicate> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          PositionRangePredicate.CODEC.optionalFieldOf("position").forGetter(LocationPredicate::position),
          RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(LocationPredicate::biomes),
          RegistryCodecs.homogeneousList(Registries.STRUCTURE).optionalFieldOf("structures").forGetter(LocationPredicate::structures),
          ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("dimension").forGetter(LocationPredicate::dimension),
          Codec.BOOL.optionalFieldOf("smokey").forGetter(LocationPredicate::smokey),
          LightPredicate.CODEC.optionalFieldOf("light").forGetter(LocationPredicate::light),
          BlockPredicate.CODEC.optionalFieldOf("block").forGetter(LocationPredicate::block),
          FluidPredicate.CODEC.optionalFieldOf("fluid").forGetter(LocationPredicate::fluid),
          Codec.BOOL.optionalFieldOf("can_see_sky").forGetter(LocationPredicate::canSeeSky)
      )
      .apply(instance, LocationPredicate::new)
  );

  public boolean test(ServerLevel world, double x, double y, double z) {
    BlockPos blockPos = BlockPos.containing(x, y, z);
    boolean positionLoaded = world.isLoaded(blockPos);
    if (this.position.isPresent() && !this.position.get().test(x, y, z)) return false;
    if (this.biomes.isPresent() && (!positionLoaded || !this.biomes.get().contains(world.getBiome(blockPos)))) return false;
    if (this.structures.isPresent() && (!positionLoaded || !world.structureManager().getStructureWithPieceAt(blockPos, this.structures.get()).isValid())) return false;
    if (this.dimension.isPresent() && this.dimension.get() != world.dimension()) return false;
    if (this.smokey.isPresent() && (!positionLoaded || this.smokey.get() != CampfireBlock.isSmokeyPos(world, blockPos))) return false;
    if (this.light.isPresent() && !this.light.get().test(world, blockPos)) return false;
    if (this.block.isPresent() && !this.block.get().matches(world, blockPos)) return false;
    if (this.fluid.isPresent() && !this.fluid.get().matches(world, blockPos)) return false;
    return this.canSeeSky.orElse(true);
  }
}
