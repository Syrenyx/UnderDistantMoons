package syrenyx.distantmoons.affliction.effect.miscellaneous;

import com.mojang.serialization.Codec;

public record DamageImmunityEffect() {

  public static final DamageImmunityEffect INSTANCE = new DamageImmunityEffect();
  public static final Codec<DamageImmunityEffect> CODEC = Codec.unit(() -> INSTANCE);
}
