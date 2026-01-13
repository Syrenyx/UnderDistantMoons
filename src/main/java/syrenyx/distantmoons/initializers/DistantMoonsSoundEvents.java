package syrenyx.distantmoons.initializers;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsSoundEvents {

  //AMBIENT

  //BLOCK

  //ENTITY
  public static final SoundEvent BEGIN_CURING_CURSE_OF_THE_NIGHT = register("entity.begin_curing_curse_of_the_night");
  public static final SoundEvent FINISH_CURING_CURSE_OF_THE_NIGHT = register("entity.finish_curing_curse_of_the_night");

  //ITEM

  //MUSIC

  //MISCELLANEOUS

  private static SoundEvent register(String id) {
    Identifier identifier = UnderDistantMoons.identifierOf(id);
    return Registry.register(
        BuiltInRegistries.SOUND_EVENT,
        identifier,
        SoundEvent.createVariableRangeEvent(identifier)
    );
  }

  public static void initialize() {}
}
