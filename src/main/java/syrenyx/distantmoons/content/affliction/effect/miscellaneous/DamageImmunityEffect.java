package syrenyx.distantmoons.content.affliction.effect.miscellaneous;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public record DamageImmunityEffect() {

  public static final DamageImmunityEffect INSTANCE = new DamageImmunityEffect();
  public static final Codec<DamageImmunityEffect> CODEC = MapCodec.unitCodec(INSTANCE);
}
