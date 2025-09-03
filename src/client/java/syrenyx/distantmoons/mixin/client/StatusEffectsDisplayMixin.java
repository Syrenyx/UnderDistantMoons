package syrenyx.distantmoons.mixin.client;

import com.google.common.collect.Ordering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.tick.TickManager;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.affliction.Affliction;
import syrenyx.distantmoons.affliction.AfflictionDisplay;
import syrenyx.distantmoons.affliction.AfflictionInstance;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Debug(export = true)
@Mixin(StatusEffectsDisplay.class)
public abstract class StatusEffectsDisplayMixin {

  @Unique private static final int MIN_SIZE = 32;
  @Unique private static final int FULL_SIZE = 120;
  @Unique private static final int PROGRESSION_BAR_HEIGHT = 5;
  @Unique private static final int PROGRESSION_BAR_WIDTH = 80;

  @Unique private static final Identifier LARGE_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/affliction_background_large");
  @Unique private static final Identifier SMALL_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/affliction_background_small");
  @Unique private static final Identifier LARGE_EFFECT_BACKGROUND_TEXTURE = Identifier.ofVanilla("container/inventory/effect_background_large");
  @Unique private static final Identifier SMALL_EFFECT_BACKGROUND_TEXTURE = Identifier.ofVanilla("container/inventory/effect_background_small");
  @Unique private static final Identifier LARGE_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_background_large");
  @Unique private static final Identifier SMALL_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_background_small");

  @Unique private static final Identifier AFFLICTION_PROGRESSION_BACKGROUND = UnderDistantMoons.identifierOf("container/inventory/affliction_progression_background");
  @Unique private static final Identifier AFFLICTION_PROGRESSION_BAR = UnderDistantMoons.identifierOf("container/inventory/affliction_progression_bar");
  @Unique private static final Identifier PERSISTENT_AFFLICTION_PROGRESSION_BACKGROUND = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_progression_background");
  @Unique private static final Identifier PERSISTENT_AFFLICTION_PROGRESSION_BAR = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_progression_bar");

  @Final @Shadow
  private HandledScreen<?> parent;
  @Final @Shadow
  private MinecraftClient client;
  @Shadow
  private StatusEffectInstance hoveredStatusEffect;
  @Unique
  private AfflictionInstance hoveredAffliction;

  @Inject(at = @At("HEAD"), cancellable = true, method = "drawStatusEffects")
  public void drawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo callbackInfo) {
    callbackInfo.cancel();
    if (this.client.player == null) return;
    HandledScreenAccessor parentAccessor = (HandledScreenAccessor) this.parent;
    this.hoveredAffliction = null;
    this.hoveredStatusEffect = null;
    int horizontalPosition = parentAccessor.x() + parentAccessor.backgroundWidth() + 2;
    int height = parent.width - horizontalPosition;
    Collection<AfflictionInstance> activeAfflictions = ClientPlayerAttachment.getOrCreate(this.client.player).activeAfflictions().stream().filter(affliction -> affliction.affliction().value().display().isPresent()).toList();
    Collection<StatusEffectInstance> statusEffects = this.client.player.getStatusEffects();
    if (height < MIN_SIZE || statusEffects.isEmpty() && activeAfflictions.isEmpty()) return;
    boolean wide = height >= FULL_SIZE;
    int amount = statusEffects.size() + activeAfflictions.size();
    int widgetSpacing = amount > 5 ? 132 / (amount - 1) : 33;
    Iterable<AfflictionInstance> iterableAfflictions = Ordering.natural().sortedCopy(activeAfflictions);
    Iterable<StatusEffectInstance> iterableEffects = Ordering.natural().sortedCopy(statusEffects);
    int y = parentAccessor.y();
    for (AfflictionInstance affliction : iterableAfflictions) {
      drawAfflictionWidget(context, horizontalPosition, y, wide, affliction, this.parent.getTextRenderer());
      y += widgetSpacing;
    }
    for (var statusEffect : iterableEffects) {
      drawStatusEffectWidget(context, horizontalPosition, y, wide, statusEffect, this.parent.getTextRenderer(), this.client.world.getTickManager());
      y += widgetSpacing;
    }
    if (wide || !(mouseX >= horizontalPosition && mouseX <= horizontalPosition + 33)) return;
    y = parentAccessor.y();
    for (AfflictionInstance afflictionInstance : iterableAfflictions) {
      if (mouseY >= y && mouseY <= y + widgetSpacing) this.hoveredAffliction = afflictionInstance;
      y += widgetSpacing;
    }
    for (StatusEffectInstance statusEffectInstance : iterableEffects) {
      if (mouseY >= y && mouseY <= y + widgetSpacing) this.hoveredStatusEffect = statusEffectInstance;
      y += widgetSpacing;
    }
  }

  @Inject(at = @At("HEAD"), cancellable = true, method = "drawStatusEffectTooltip")
  public void drawStatusEffectTooltip(DrawContext context, int mouseX, int mouseY, CallbackInfo callbackInfo) {
    callbackInfo.cancel();
    if (this.hoveredAffliction != null) {
      List<Text> text = List.of(
          getDescription(this.hoveredAffliction),
          getProgressionText(this.hoveredAffliction)
      );
      Optional<Identifier> tooltipStyle = this.hoveredAffliction.affliction().value().display().flatMap(AfflictionDisplay::tooltipStyle);
      if (tooltipStyle.isPresent()) context.drawTooltip(this.parent.getTextRenderer(), text, Optional.empty(), mouseX, mouseY, tooltipStyle.get());
      else context.drawTooltip(this.parent.getTextRenderer(), text, Optional.empty(), mouseX, mouseY);
    } else if (this.hoveredStatusEffect != null) {
      List<Text> text = List.of(
          getDescription(this.hoveredStatusEffect),
          StatusEffectUtil.getDurationText(this.hoveredStatusEffect, 1.0F, this.client.world.getTickManager().getTickRate())
      );
      context.drawTooltip(this.parent.getTextRenderer(), text, Optional.empty(), mouseX, mouseY);
    }
  }

  @Unique
  private static void drawAfflictionWidget(DrawContext context, int x, int y, boolean wide, AfflictionInstance affliction, TextRenderer textRenderer) {
    Identifier texture = affliction.affliction().value().persistent()
        ? (wide ? LARGE_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE : SMALL_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE)
        : (wide ? LARGE_AFFLICTION_BACKGROUND_TEXTURE : SMALL_AFFLICTION_BACKGROUND_TEXTURE);
    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, texture, x, y, wide ? FULL_SIZE : MIN_SIZE, MIN_SIZE);
    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getIcon(affliction), x + (wide ? 6 : 7), y + 7, 18, 18);
    if (wide) {
      context.drawTextWithShadow(textRenderer, getDescription(affliction), x + 28, y + 6, Colors.WHITE);
      context.drawGuiTexture(
          RenderPipelines.GUI_TEXTURED,
          affliction.affliction().value().persistent() ? PERSISTENT_AFFLICTION_PROGRESSION_BACKGROUND : AFFLICTION_PROGRESSION_BACKGROUND,
          x + 28, y + 19, PROGRESSION_BAR_WIDTH, PROGRESSION_BAR_HEIGHT
      );
      int progression = MathHelper.ceil((affliction.progression() / 100) * PROGRESSION_BAR_WIDTH);
      context.drawGuiTexture(
          RenderPipelines.GUI_TEXTURED,
          affliction.affliction().value().persistent() ? PERSISTENT_AFFLICTION_PROGRESSION_BAR : AFFLICTION_PROGRESSION_BAR,
          PROGRESSION_BAR_WIDTH, PROGRESSION_BAR_HEIGHT,
          0, 0,
          x + 28, y + 19,
          progression, PROGRESSION_BAR_HEIGHT
      );
    }
  }

  @Unique
  private static void drawStatusEffectWidget(DrawContext context, int x, int y, boolean wide, StatusEffectInstance statusEffect, TextRenderer textRenderer, TickManager tickManager) {
    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, wide ? LARGE_EFFECT_BACKGROUND_TEXTURE : SMALL_EFFECT_BACKGROUND_TEXTURE, x, y, wide ? FULL_SIZE : MIN_SIZE, MIN_SIZE);
    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, InGameHud.getEffectTexture(statusEffect.getEffectType()), x + (wide ? 6 : 7), y + 7, 18, 18);
    if (wide) {
      context.drawTextWithShadow(textRenderer, getDescription(statusEffect), x + 28, y + 6, Colors.WHITE);
      context.drawTextWithShadow(textRenderer, StatusEffectUtil.getDurationText(statusEffect, 1.0F, tickManager.getTickRate()), x + 28, y + 16, -8421505);
    }
  }

  @Unique
  private static Text getDescription(AfflictionInstance affliction) {
    MutableText text = affliction.affliction().value().description().copy();
    if (affliction.affliction().value().maxStage() > 1) text.append(ScreenTexts.SPACE).append(Text.translatable("enchantment.level." + affliction.stage()));
    return text;
  }

  @Unique
  private static Text getDescription(StatusEffectInstance statusEffect) {
    MutableText text = statusEffect.getEffectType().value().getName().copy();
    if (statusEffect.getAmplifier() >= 1) text.append(ScreenTexts.SPACE).append(Text.translatable("enchantment.level." + (statusEffect.getAmplifier() + 1)));
    return text;
  }

  @Unique
  private static Identifier getIcon(AfflictionInstance affliction) {
    Identifier identifier = affliction.getSprite();
    if (identifier == null) return MissingSprite.getMissingSpriteId();
    else return identifier.withPrefixedPath("mob_effect/");
  }

  @Unique
  private static Text getProgressionText(AfflictionInstance affliction) {
    if (affliction.affliction().value().tickProgression().isEmpty()) return Text.translatable("effect.duration.infinite");
    return Text.translatable("affliction.progression_tooltip", String.format("%.1f", affliction.progression()), Affliction.MAX_PROGRESSION);
  }
}
