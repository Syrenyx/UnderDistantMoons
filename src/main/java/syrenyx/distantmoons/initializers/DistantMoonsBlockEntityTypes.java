package syrenyx.distantmoons.initializers;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.block.entity.*;

public abstract class DistantMoonsBlockEntityTypes {

  public static final BlockEntityType<LargeBlastFurnaceBlockEntity> LARGE_BLAST_FURNACE = register("large_blast_furnace", LargeBlastFurnaceBlockEntity::new,
      DistantMoonsBlocks.BLAST_FURNACE
  );
  public static final BlockEntityType<UnderworldConfluxBlockEntity> UNDERWORLD_CONFLUX = register("underworld_conflux", UnderworldConfluxBlockEntity::new,
      DistantMoonsBlocks.UNDERWORLD_CONFLUX
  );

  static {
    BlockEntityType.HANGING_SIGN.addValidBlock(DistantMoonsBlocks.ABYSS_TEAR_HANGING_SIGN);
    BlockEntityType.HANGING_SIGN.addValidBlock(DistantMoonsBlocks.ABYSS_TEAR_WALL_HANGING_SIGN);
    BlockEntityType.SHELF.addValidBlock(DistantMoonsBlocks.ABYSS_TEAR_SHELF);
    BlockEntityType.SIGN.addValidBlock(DistantMoonsBlocks.ABYSS_TEAR_SIGN);
  }

  private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<? extends T> blockEntityFactory, Block... blocks) {
    return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, UnderDistantMoons.identifierOf(id), FabricBlockEntityTypeBuilder.<T>create(blockEntityFactory, blocks).build());
  }

  public static void initialize() {}
}
