package syrenyx.distantmoons.content.predicate.location;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.CampfireBlock;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.FluidPredicate;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public record LocationPredicate(
    Optional<PositionRangePredicate> position,
    Optional<RegistryEntryList<Biome>> biomes,
    Optional<RegistryEntryList<Structure>> structures,
    Optional<RegistryKey<World>> dimension,
    Optional<Boolean> smokey,
    Optional<LightPredicate> light,
    Optional<BlockPredicate> block,
    Optional<FluidPredicate> fluid,
    Optional<Boolean> canSeeSky
) {

  public static final Codec<LocationPredicate> CODEC = RecordCodecBuilder.create(instance -> instance
      .group(
          PositionRangePredicate.CODEC.optionalFieldOf("position").forGetter(LocationPredicate::position),
          RegistryCodecs.entryList(RegistryKeys.BIOME).optionalFieldOf("biomes").forGetter(LocationPredicate::biomes),
          RegistryCodecs.entryList(RegistryKeys.STRUCTURE).optionalFieldOf("structures").forGetter(LocationPredicate::structures),
          RegistryKey.createCodec(RegistryKeys.WORLD).optionalFieldOf("dimension").forGetter(LocationPredicate::dimension),
          Codec.BOOL.optionalFieldOf("smokey").forGetter(LocationPredicate::smokey),
          LightPredicate.CODEC.optionalFieldOf("light").forGetter(LocationPredicate::light),
          BlockPredicate.CODEC.optionalFieldOf("block").forGetter(LocationPredicate::block),
          FluidPredicate.CODEC.optionalFieldOf("fluid").forGetter(LocationPredicate::fluid),
          Codec.BOOL.optionalFieldOf("can_see_sky").forGetter(LocationPredicate::canSeeSky)
      )
      .apply(instance, LocationPredicate::new)
  );

  public boolean test(ServerWorld world, double x, double y, double z) {
    BlockPos blockPos = BlockPos.ofFloored(x, y, z);
    boolean positionLoaded = world.isPosLoaded(blockPos);
    if (this.position.isPresent() && !this.position.get().test(x, y, z)) return false;
    if (this.biomes.isPresent() && (!positionLoaded || !this.biomes.get().contains(world.getBiome(blockPos)))) return false;
    if (this.structures.isPresent() && (!positionLoaded || !world.getStructureAccessor().getStructureContaining(blockPos, this.structures.get()).hasChildren())) return false;
    if (this.dimension.isPresent() && this.dimension.get() != world.getRegistryKey()) return false;
    if (this.smokey.isPresent() && (!positionLoaded || this.smokey.get() != CampfireBlock.isLitCampfireInRange(world, blockPos))) return false;
    if (this.light.isPresent() && !this.light.get().test(world, blockPos)) return false;
    if (this.block.isPresent() && !this.block.get().test(world, blockPos)) return false;
    if (this.fluid.isPresent() && !this.fluid.get().test(world, blockPos)) return false;
    return this.canSeeSky.orElse(true);
  }
}
