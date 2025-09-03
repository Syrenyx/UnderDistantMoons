package syrenyx.distantmoons.mixin.client;

import com.google.common.collect.Ordering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;

import java.util.Collection;

@Mixin(StatusEffectsDisplay.class)
public abstract class StatusEffectsDisplayMixin {

  @Unique
  private static final int MIN_SIZE = 32;
  @Unique
  private static final int FULL_SIZE = 120;
  @Unique
  private static final Identifier LARGE_BACKGROUND_TEXTURE = Identifier.ofVanilla("container/inventory/effect_background_large");
  @Unique
  private static final Identifier SMALL_BACKGROUND_TEXTURE = Identifier.ofVanilla("container/inventory/effect_background_small");

  @Final @Shadow
  private HandledScreen<?> parent;
  @Final @Shadow
  private MinecraftClient client;
  @Shadow
  private StatusEffectInstance hoveredStatusEffect;

  @Inject(at = @At("HEAD"), cancellable = true, method = "drawStatusEffects")
  public void drawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo callbackInfo) {
    if (this.client.player == null) callbackInfo.cancel();
    StatusEffectsDisplay display = (StatusEffectsDisplay) (Object) this;
    HandledScreenAccessor parentAccessor = (HandledScreenAccessor) this.parent;
    this.hoveredStatusEffect = null;
    int horizontalPosition = parentAccessor.x() + parentAccessor.backgroundWidth() + 2;
    int height = parent.width - horizontalPosition;
    Collection<AfflictionInstance> activeAfflictions = ClientPlayerAttachment.getOrCreate(this.client.player).activeAfflictions().stream().filter(affliction -> affliction.affliction().value().display().isPresent()).toList();
    Collection<StatusEffectInstance> statusEffects = this.client.player.getStatusEffects();
    if (height < MIN_SIZE || statusEffects.isEmpty() && activeAfflictions.isEmpty()) callbackInfo.cancel();
    boolean wide = height >= FULL_SIZE;
    int amount = statusEffects.size() + activeAfflictions.size();
    int widgetHeight = amount > 5 ? 132 / (amount - 1) : 33;
    Iterable<AfflictionInstance> iterableAfflictions = Ordering.natural().sortedCopy(activeAfflictions);
    Iterable<StatusEffectInstance> iterableEffects = Ordering.natural().sortedCopy(statusEffects);
    this.drawBackgrounds(context, horizontalPosition, parentAccessor.y(), widgetHeight, amount, wide);
    this.drawSprites(context, horizontalPosition, parentAccessor.y(), widgetHeight, iterableAfflictions, iterableEffects, wide);
    if (wide) {
      this.drawDescriptions(context, horizontalPosition, parentAccessor.y(), widgetHeight, iterableAfflictions, iterableEffects);
      callbackInfo.cancel();
    }
    callbackInfo.cancel();
  }

  @Unique
  private void drawBackgrounds(
      DrawContext context,
      int x,
      int y,
      int height,
      int amount,
      boolean wide
  ) {
    for (int i = 0; i < amount; i++) {
      if (wide) context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, LARGE_BACKGROUND_TEXTURE, x, y, FULL_SIZE, MIN_SIZE);
      else context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SMALL_BACKGROUND_TEXTURE, x, y, MIN_SIZE, MIN_SIZE);
      y += height;
    }
  }

  @Unique
  private void drawDescriptions(
      DrawContext context,
      int x,
      int y ,
      int height,
      Iterable<AfflictionInstance> afflictions,
      Iterable<StatusEffectInstance> statusEffects
  ) {
    for (AfflictionInstance affliction : afflictions) {
      Text descriptionText = this.getDescription(affliction);
      context.drawTextWithShadow(this.parent.getTextRenderer(), descriptionText, x + 28, y + 6, Colors.WHITE);
      y += height;
    }
    for (StatusEffectInstance statusEffect : statusEffects) {
      Text descriptionText = this.getDescription(statusEffect);
      Text durationText = StatusEffectUtil.getDurationText(statusEffect, 1.0F, this.client.world.getTickManager().getTickRate());
      context.drawTextWithShadow(this.parent.getTextRenderer(), descriptionText, x + 28, y + 6, Colors.WHITE);
      context.drawTextWithShadow(this.parent.getTextRenderer(), durationText, x + 28, y + 16, -8421505);
      y += height;
    }
  }

  @Unique
  private void drawSprites(
      DrawContext context,
      int x,
      int y,
      int height,
      Iterable<AfflictionInstance> afflictions,
      Iterable<StatusEffectInstance> statusEffects,
      boolean wide
  ) {
    for (AfflictionInstance affliction : afflictions) {
      context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getIcon(affliction), x + (wide ? 6 : 7), y + 7, 18, 18);
      y += height;
    }
    for (StatusEffectInstance statusEffect : statusEffects) {
      Identifier identifier = InGameHud.getEffectTexture(statusEffect.getEffectType());
      context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier, x + (wide ? 6 : 7), y + 7, 18, 18);
      y += height;
    }
  }

  @Unique
  private Text getDescription(AfflictionInstance affliction) {
    MutableText text = affliction.affliction().value().description().copy();
    if (affliction.affliction().value().maxStage() > 1) text.append(ScreenTexts.SPACE).append(Text.translatable("enchantment.level." + affliction.stage()));
    return text;
  }

  @Unique
  private Text getDescription(StatusEffectInstance statusEffect) {
    MutableText text = statusEffect.getEffectType().value().getName().copy();
    if (statusEffect.getAmplifier() >= 1) text.append(ScreenTexts.SPACE).append(Text.translatable("enchantment.level." + (statusEffect.getAmplifier() + 1)));
    return text;
  }

  @Unique
  private Identifier getIcon(AfflictionInstance affliction) {
    Identifier identifier = affliction.getSprite();
    if (identifier == null) return MissingSprite.getMissingSpriteId();
    else return identifier.withPrefixedPath("mob_effect/");
  }
}
