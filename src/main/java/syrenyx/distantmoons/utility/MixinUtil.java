package syrenyx.distantmoons.utility;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public abstract class MixinUtil {

  public static void returnBoolean(boolean value, CallbackInfoReturnable<Boolean> callbackInfo) {
    callbackInfo.cancel();
    callbackInfo.setReturnValue(value);
  }
}
