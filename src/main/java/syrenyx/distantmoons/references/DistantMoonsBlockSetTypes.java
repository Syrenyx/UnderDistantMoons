package syrenyx.distantmoons.references;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import syrenyx.distantmoons.UnderDistantMoons;

public abstract class DistantMoonsBlockSetTypes {

  public static final int WOODEN_BUTTON_PRESSED_DURATION = 30;
  public static final BlockSetType ABYSS_TEAR = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .build(UnderDistantMoons.identifierOf("abyss_tear"));
  public static final BlockSetType WROUGHT_IRON = BlockSetTypeBuilder.copyOf(BlockSetType.IRON)
      .openableByHand(true)
      .build(UnderDistantMoons.identifierOf("wrought_iron"));
}
