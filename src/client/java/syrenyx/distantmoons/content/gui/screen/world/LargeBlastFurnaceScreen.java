package syrenyx.distantmoons.content.gui.screen.world;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;
import syrenyx.distantmoons.UnderDistantMoons;
import syrenyx.distantmoons.content.block.LargeBlastFurnaceBlock;
import syrenyx.distantmoons.content.block.entity.LargeBlastFurnaceBlockEntity;
import syrenyx.distantmoons.content.menu.block.LargeBlastFurnaceMenu;

public class LargeBlastFurnaceScreen extends AbstractContainerScreen<LargeBlastFurnaceMenu> {

  private static final Identifier DEFAULT_TEXTURE = UnderDistantMoons.identifierOf("textures/gui/container/large_blast_furnace/default.png");
  private static final Identifier MIRRORED_TEXTURE = UnderDistantMoons.identifierOf("textures/gui/container/large_blast_furnace/mirrored.png");
  private static final Identifier FUEL_BURNING_PROGRESS_SPRITE = UnderDistantMoons.identifierOf("container/large_blast_furnace/fuel_burning_progress");
  private static final Identifier HEAT_SPRITE = UnderDistantMoons.identifierOf("container/large_blast_furnace/heat");
  private static final int BACKGROUND_TEXTURE_SIZE = 256;
  private static final int SMALL_SPRITE_SIZE = 14;
  private static final int HEAT_SPRITE_HEIGHT = 86;

  public LargeBlastFurnaceScreen(LargeBlastFurnaceMenu abstractContainerMenu, Inventory inventory, Component component) {
    super(abstractContainerMenu, inventory, component);
    this.imageHeight += 65;
    this.inventoryLabelY = this.imageHeight - 94;
  }

  @Override
  public void init() {
    super.init();
    this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
  }

  @Override
  protected void renderBg(@NonNull GuiGraphics guiGraphics, float delta, int cursorX, int cursorY) {
    guiGraphics.blit(
        RenderPipelines.GUI_TEXTURED,
        this.menu.mirrored ? MIRRORED_TEXTURE : DEFAULT_TEXTURE,
        this.leftPos, this.topPos,
        0.0F, 0.0F,
        this.imageWidth, this.imageHeight,
        BACKGROUND_TEXTURE_SIZE, BACKGROUND_TEXTURE_SIZE
    );
    float fuelBurnProgress = this.menu.getFuelBurnProgress();
    if (fuelBurnProgress < 1.0F) {
      int heightReduction = Mth.ceil(fuelBurnProgress * (SMALL_SPRITE_SIZE - 1)) + 1;
      guiGraphics.blitSprite(
          RenderPipelines.GUI_TEXTURED,
          FUEL_BURNING_PROGRESS_SPRITE,
          SMALL_SPRITE_SIZE, SMALL_SPRITE_SIZE,
          0, SMALL_SPRITE_SIZE - heightReduction,
          this.leftPos + (this.menu.mirrored ? 45 : 117), this.topPos + 110 + SMALL_SPRITE_SIZE - heightReduction,
          SMALL_SPRITE_SIZE, heightReduction
      );
    }
    int heat = this.menu.getHeat();
    if (heat > 0) {
      int heightReduction = Mth.ceil(Mth.clamp((float) heat / LargeBlastFurnaceBlockEntity.Controller.MAX_HEAT, 0.0F, 1.0F) * (HEAT_SPRITE_HEIGHT - 1)) + 1;
      guiGraphics.blitSprite(
          RenderPipelines.GUI_TEXTURED,
          HEAT_SPRITE,
          SMALL_SPRITE_SIZE, HEAT_SPRITE_HEIGHT,
          0, HEAT_SPRITE_HEIGHT - heightReduction,
          this.leftPos + (this.menu.mirrored ? 27 : 135), this.topPos + 19 + HEAT_SPRITE_HEIGHT - heightReduction,
          SMALL_SPRITE_SIZE, heightReduction
      );
    }
  }

  @Override
  public void render(@NonNull GuiGraphics guiGraphics, int cursorX, int cursorY, float delta) {
    super.render(guiGraphics, cursorX, cursorY, delta);
    this.renderTooltip(guiGraphics, cursorX, cursorY);
  }
}
