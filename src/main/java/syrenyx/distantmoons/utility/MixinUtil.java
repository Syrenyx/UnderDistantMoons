package syrenyx.distantmoons.utility;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public abstract class MixinUtil {

  public static <T> void cancelAndReturnValue(T value, CallbackInfoReturnable<T> callbackInfo) {
    callbackInfo.cancel();
    callbackInfo.setReturnValue(value);
  }
}
