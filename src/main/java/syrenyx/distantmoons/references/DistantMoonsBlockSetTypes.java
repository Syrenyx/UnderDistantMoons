package syrenyx.distantmoons.references;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public abstract class DistantMoonsBlockSetTypes {

  public static final BlockSetType WROUGHT_IRON = new BlockSetType(
      "wrought_iron",
      true,
      false,
      false,
      BlockSetType.PressurePlateSensitivity.EVERYTHING,
      SoundType.IRON,
      SoundEvents.IRON_DOOR_CLOSE,
      SoundEvents.IRON_DOOR_OPEN,
      SoundEvents.IRON_TRAPDOOR_CLOSE,
      SoundEvents.IRON_TRAPDOOR_OPEN,
      SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
      SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
      SoundEvents.STONE_BUTTON_CLICK_OFF,
      SoundEvents.STONE_BUTTON_CLICK_ON
  );
}
