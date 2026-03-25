package syrenyx.distantmoons.initializers;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.data_component.BlastFurnaceFuelComponent;
import syrenyx.distantmoons.content.data_component.CoiledBlockComponent;
import syrenyx.distantmoons.content.data_component.DimensionKeystoneComponent;

public abstract class DistantMoonsDataComponentTypes {

  public static final DataComponentType<BlastFurnaceFuelComponent> BLAST_FURNACE_FUEL = register("blast_furnace_fuel", BlastFurnaceFuelComponent.CODEC);
  public static final DataComponentType<CoiledBlockComponent> COILED_BLOCK = register("coiled_block", CoiledBlockComponent.CODEC);
  public static final DataComponentType<DimensionKeystoneComponent> DIMENSION_KEYSTONE = register("dimension_keystone", DimensionKeystoneComponent.CODEC);

  private static <T> DataComponentType<T> register(String id, Codec<T> codec) {
    return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, UnderDistantMoons.identifierOf(id), DataComponentType.<T>builder().persistent(codec).build());
  }

  private static <T> DataComponentType<T> register(String id, Codec<T> codec, StreamCodec<ByteBuf, T> streamCodec) {
    return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, UnderDistantMoons.identifierOf(id), DataComponentType.<T>builder().persistent(codec).networkSynchronized(streamCodec).build());
  }

  public static void initialize() {}
}
