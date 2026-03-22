package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import syrenyx.distantmoons.content.block.entity.LargeBlastFurnaceBlockEntity;
import syrenyx.distantmoons.content.block.entity.UnderworldConfluxBlockEntity;

public abstract class DistantMoonsBlockEntityTypes {

  public static final BlockEntityType<LargeBlastFurnaceBlockEntity> LARGE_BLAST_FURNACE = register("large_blast_furnace", LargeBlastFurnaceBlockEntity::new, DistantMoonsBlocks.BLAST_FURNACE);
  public static final BlockEntityType<UnderworldConfluxBlockEntity> UNDERWORLD_CONFLUX = register("underworld_conflux", UnderworldConfluxBlockEntity::new, DistantMoonsBlocks.UNDERWORLD_CONFLUX);

  private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<? extends T> blockEntityFactory, Block... blocks) {
    return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(blockEntityFactory, blocks).build());
  }

  public static void initialize() {}
}
