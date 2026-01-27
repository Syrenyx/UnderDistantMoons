package syrenyx.distantmoons.initializers;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.data_component.BlastFurnaceFuelComponent;
import syrenyx.distantmoons.content.data_component.CoiledBlockComponent;

public abstract class DistantMoonsDataComponentTypes {

  public static final DataComponentType<BlastFurnaceFuelComponent> BLAST_FURNACE_FUEL = register("blast_furnace_fuel", BlastFurnaceFuelComponent.CODEC);
  public static final DataComponentType<CoiledBlockComponent> COILED_BLOCK = register("coiled_block", CoiledBlockComponent.CODEC);

  private static <T> DataComponentType<T> register(String id, Codec<T> codec) {
    return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, UnderDistantMoons.identifierOf(id), DataComponentType.<T>builder().persistent(codec).build());
  }

  public static void initialize() {}
}
