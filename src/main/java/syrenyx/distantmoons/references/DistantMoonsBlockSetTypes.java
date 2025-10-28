package syrenyx.distantmoons.references;

import net.minecraft.block.BlockSetType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public abstract class DistantMoonsBlockSetTypes {

  public static final BlockSetType WROUGHT_IRON = new BlockSetType(
      "wrought_iron",
      true,
      false,
      false,
      BlockSetType.ActivationRule.EVERYTHING,
      BlockSoundGroup.IRON,
      SoundEvents.BLOCK_IRON_DOOR_CLOSE,
      SoundEvents.BLOCK_IRON_DOOR_OPEN,
      SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE,
      SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
      SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF,
      SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON,
      SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,
      SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON
  );
}
