package syrenyx.distantmoons.mixin.client;

import com.google.common.collect.Ordering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.affliction.Affliction;
import syrenyx.distantmoons.content.affliction.AfflictionInstance;
import syrenyx.distantmoons.content.affliction.ProgressionBarStyle;
import syrenyx.distantmoons.data.attachment.ClientPlayerAttachment;

import java.util.Collection;

//TODO: Rewrite entirely

@Mixin(EffectsInInventory.class)
public abstract class EffectsInInventoryMixin {

  @Unique private static final int MIN_SIZE = 32;
  @Unique private static final int FULL_SIZE = 120;
  @Unique private static final int PROGRESSION_BAR_HEIGHT = 5;
  @Unique private static final int PROGRESSION_BAR_WIDTH = 84;
  @Unique private static final int WIDGET_SPACING = 33;

  @Unique private static final Identifier LARGE_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/affliction_background_large");
  @Unique private static final Identifier SMALL_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/affliction_background_small");
  @Unique private static final Identifier LARGE_EFFECT_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("container/inventory/effect_background_large");
  @Unique private static final Identifier SMALL_EFFECT_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("container/inventory/effect_background_small");
  @Unique private static final Identifier LARGE_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_background_large");
  @Unique private static final Identifier SMALL_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_background_small");

  @Unique private static final Identifier AFFLICTION_PROGRESSION_BACKGROUND = UnderDistantMoons.identifierOf("container/inventory/affliction_progression_background");
  @Unique private static final Identifier AFFLICTION_PROGRESSION_BAR = UnderDistantMoons.identifierOf("container/inventory/affliction_progression_bar");
  @Unique private static final Identifier AFFLICTION_PROGRESSION_INFINITE = UnderDistantMoons.identifierOf("container/inventory/affliction_progression_infinite");
  @Unique private static final Identifier PERSISTENT_AFFLICTION_PROGRESSION_BACKGROUND = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_progression_background");
  @Unique private static final Identifier PERSISTENT_AFFLICTION_PROGRESSION_BAR = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_progression_bar");
  @Unique private static final Identifier PERSISTENT_AFFLICTION_PROGRESSION_INFINITE = UnderDistantMoons.identifierOf("container/inventory/persistent_affliction_progression_infinite");

  @Final @Shadow private AbstractContainerScreen<?> screen;
  @Final @Shadow private Minecraft minecraft;
  @Unique private MobEffectInstance hoveredStatusEffect;
  @Unique private AfflictionInstance hoveredAffliction;

  @Inject(at = @At("HEAD"), cancellable = true, method = "renderEffects")
  public void distantMoons$drawStatusEffects(GuiGraphics context, Collection<MobEffectInstance> effects, int x, int height, int mouseX, int mouseY, int width, CallbackInfo callbackInfo) {
    callbackInfo.cancel();
    if (this.minecraft.player == null) return;
    HandledScreenAccessor parentAccessor = (HandledScreenAccessor) this.screen;
    this.hoveredAffliction = null;
    this.hoveredStatusEffect = null;
    int horizontalPosition = parentAccessor.x() + parentAccessor.backgroundWidth() + 2;
    //int height = parent.width - horizontalPosition;
    Collection<AfflictionInstance> activeAfflictions = ClientPlayerAttachment.getOrCreate(this.minecraft.player).activeAfflictions().stream().filter(AfflictionInstance::isVisible).toList();
    Collection<MobEffectInstance> statusEffects = this.minecraft.player.getActiveEffects();
    if (height < MIN_SIZE || statusEffects.isEmpty() && activeAfflictions.isEmpty()) return;
    boolean wide = height >= FULL_SIZE && activeAfflictions.size() + statusEffects.size() < 6;
    Iterable<AfflictionInstance> iterableAfflictions = Ordering.natural().sortedCopy(activeAfflictions);
    Iterable<MobEffectInstance> iterableEffects = Ordering.natural().sortedCopy(statusEffects);
    //int x = horizontalPosition;
    int y = parentAccessor.y();
    for (AfflictionInstance affliction : iterableAfflictions) {
      distantMoons$drawAfflictionWidget(context, x, y, wide, affliction, this.screen.getFont());
      y += WIDGET_SPACING;
      if (y - parentAccessor.y() > WIDGET_SPACING * 4) {
        y = parentAccessor.y();
        x += WIDGET_SPACING;
      }
    }
    for (var statusEffect : iterableEffects) {
      assert this.minecraft.level != null;
      distantMoons$drawStatusEffectWidget(context, x, y, wide, statusEffect, this.screen.getFont(), this.minecraft.level.tickRateManager());
      y += WIDGET_SPACING;
      if (y - parentAccessor.y() > WIDGET_SPACING * 4) {
        y = parentAccessor.y();
        x += WIDGET_SPACING;
      }
    }
    x = horizontalPosition;
    y = parentAccessor.y();
    for (AfflictionInstance afflictionInstance : iterableAfflictions) {
      if (mouseY >= y && mouseY <= y + MIN_SIZE - 1 && mouseX >= x && mouseX <= x + (wide ? FULL_SIZE : MIN_SIZE) - 1) this.hoveredAffliction = afflictionInstance;
      y += WIDGET_SPACING;
      if (y - parentAccessor.y() > WIDGET_SPACING * 4) {
        y = parentAccessor.y();
        x += WIDGET_SPACING;
      }
    }
    for (MobEffectInstance statusEffectInstance : iterableEffects) {
      if (mouseY >= y && mouseY <= y + MIN_SIZE - 1 && mouseX >= x && mouseX <= x + (wide ? FULL_SIZE : MIN_SIZE) - 1) this.hoveredStatusEffect = statusEffectInstance;
      y += WIDGET_SPACING;
      if (y - parentAccessor.y() > WIDGET_SPACING * 4) {
        y = parentAccessor.y();
        x += WIDGET_SPACING;
      }
    }
  }

  /*
  @Inject(at = @At("HEAD"), cancellable = true, method = "drawStatusEffectTooltip")
  public void distantMoons$drawStatusEffectTooltip(DrawContext context, int mouseX, int mouseY, CallbackInfo callbackInfo) {
    callbackInfo.cancel();
    if (this.hoveredAffliction != null) {
      List<Text> text = List.of(this.hoveredAffliction.getDescription(), distantMoons$getProgressionText(this.hoveredAffliction));
      Identifier tooltipStyle = this.hoveredAffliction.getTooltipStyle();
      if (tooltipStyle != null) context.drawTooltip(this.parent.getTextRenderer(), text, Optional.empty(), mouseX, mouseY, tooltipStyle);
      else context.drawTooltip(this.parent.getTextRenderer(), text, Optional.empty(), mouseX, mouseY);
    } else if (this.hoveredStatusEffect != null) {
      assert this.client.world != null;
      List<Text> text = List.of(
          distantMoons$getDescription(this.hoveredStatusEffect),
          StatusEffectUtil.getDurationText(this.hoveredStatusEffect, 1.0F, this.client.world.getTickManager().getTickRate())
      );
      context.drawTooltip(this.parent.getTextRenderer(), text, Optional.empty(), mouseX, mouseY);
    }
  }
   */

  @Unique
  private static void distantMoons$drawAfflictionWidget(GuiGraphics context, int x, int y, boolean wide, AfflictionInstance afflictionInstance, Font textRenderer) {
    Affliction affliction = afflictionInstance.affliction().value();
    Identifier texture = affliction.persistent()
        ? (wide ? LARGE_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE : SMALL_PERSISTENT_AFFLICTION_BACKGROUND_TEXTURE)
        : (wide ? LARGE_AFFLICTION_BACKGROUND_TEXTURE : SMALL_AFFLICTION_BACKGROUND_TEXTURE);
    context.blitSprite(RenderPipelines.GUI_TEXTURED, texture, x, y, wide ? FULL_SIZE : MIN_SIZE, MIN_SIZE);
    context.blitSprite(RenderPipelines.GUI_TEXTURED, distantMoons$getIcon(afflictionInstance), x + (wide ? 6 : 7), y + 7, 18, 18);
    if (!wide) return;
    context.drawString(textRenderer, afflictionInstance.getDescription(), x + 28, y + 6, CommonColors.WHITE);
    switch (afflictionInstance.getProgressionBarStyle()) {
      case DEFAULT -> {
        context.blitSprite(
            RenderPipelines.GUI_TEXTURED,
            affliction.persistent() ? PERSISTENT_AFFLICTION_PROGRESSION_BACKGROUND : AFFLICTION_PROGRESSION_BACKGROUND,
            x + 28, y + 19, PROGRESSION_BAR_WIDTH, PROGRESSION_BAR_HEIGHT
        );
        int progression = Mth.ceil((afflictionInstance.progression() / 100) * PROGRESSION_BAR_WIDTH);
        context.blitSprite(
            RenderPipelines.GUI_TEXTURED,
            affliction.persistent() ? PERSISTENT_AFFLICTION_PROGRESSION_BAR : AFFLICTION_PROGRESSION_BAR,
            PROGRESSION_BAR_WIDTH, PROGRESSION_BAR_HEIGHT,
            0, 0,
            x + 28, y + 19,
            progression, PROGRESSION_BAR_HEIGHT
        );
      }
      case EMPTY -> context.blitSprite(
          RenderPipelines.GUI_TEXTURED,
          affliction.persistent() ? PERSISTENT_AFFLICTION_PROGRESSION_BACKGROUND : AFFLICTION_PROGRESSION_BACKGROUND,
          x + 28, y + 19, PROGRESSION_BAR_WIDTH, PROGRESSION_BAR_HEIGHT
      );
      case FULL -> context.blitSprite(
          RenderPipelines.GUI_TEXTURED,
          affliction.persistent() ? PERSISTENT_AFFLICTION_PROGRESSION_BAR : AFFLICTION_PROGRESSION_BAR,
          x + 28, y + 19, PROGRESSION_BAR_WIDTH, PROGRESSION_BAR_HEIGHT
      );
      case INFINITE -> context.blitSprite(
          RenderPipelines.GUI_TEXTURED,
          affliction.persistent() ? PERSISTENT_AFFLICTION_PROGRESSION_INFINITE : AFFLICTION_PROGRESSION_INFINITE,
          x + 28, y + 17, PROGRESSION_BAR_WIDTH, PROGRESSION_BAR_HEIGHT + 4
      );
    }
  }

  @Unique
  private static void distantMoons$drawStatusEffectWidget(GuiGraphics context, int x, int y, boolean wide, MobEffectInstance statusEffect, Font textRenderer, TickRateManager tickManager) {
    context.blitSprite(RenderPipelines.GUI_TEXTURED, wide ? LARGE_EFFECT_BACKGROUND_TEXTURE : SMALL_EFFECT_BACKGROUND_TEXTURE, x, y, wide ? FULL_SIZE : MIN_SIZE, MIN_SIZE);
    context.blitSprite(RenderPipelines.GUI_TEXTURED, Gui.getMobEffectSprite(statusEffect.getEffect()), x + (wide ? 6 : 7), y + 7, 18, 18);
    if (wide) {
      context.drawString(textRenderer, distantMoons$getDescription(statusEffect), x + 28, y + 6, CommonColors.WHITE);
      context.drawString(textRenderer, MobEffectUtil.formatDuration(statusEffect, 1.0F, tickManager.tickrate()), x + 28, y + 16, -8421505);
    }
  }

  @Unique
  private static Component distantMoons$getDescription(MobEffectInstance statusEffect) {
    MutableComponent text = statusEffect.getEffect().value().getDisplayName().copy();
    if (statusEffect.getAmplifier() >= 1) text.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + (statusEffect.getAmplifier() + 1)));
    return text;
  }

  @Unique
  private static Identifier distantMoons$getIcon(AfflictionInstance afflictionInstance) {
    Identifier icon = afflictionInstance.getIcon();
    if (icon == null) return MissingTextureAtlasSprite.getLocation();
    return icon.withPath("mob_effect/");
  }

  @Unique
  private static Component distantMoons$getProgressionText(AfflictionInstance affliction) {
    if (affliction.getProgressionBarStyle() == ProgressionBarStyle.INFINITE) return Component.translatable("effect.duration.infinite");
    float progression = switch (affliction.getProgressionBarStyle()) {
      case EMPTY -> 0.0F;
      case FULL -> 100.0F;
      default -> affliction.progression();
    };
    return Component.translatable("affliction.progression_tooltip", String.format(progression == 0.0F || progression == 100.0F ? "%f" : "%.1f", affliction.progression()), Affliction.MAX_PROGRESSION);
  }
}
