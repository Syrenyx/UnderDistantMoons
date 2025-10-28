package syrenyx.distantmoons.initializers;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsSoundEvents {

  //AMBIENT

  //BLOCK

  //ENTITY
  public static final SoundEvent BEGIN_CURING_CURSE_OF_THE_MOON = register("entity.begin_curing_curse_of_the_night");
  public static final SoundEvent FINISH_CURING_CURSE_OF_THE_MOON = register("entity.finish_curing_curse_of_the_night");

  //ITEM

  //MUSIC

  //MISCELLANEOUS

  private static SoundEvent register(String id) {
    Identifier identifier = UnderDistantMoons.identifierOf(id);
    return Registry.register(
        Registries.SOUND_EVENT,
        identifier,
        SoundEvent.of(identifier)
    );
  }

  public static void initialize() {}
}
