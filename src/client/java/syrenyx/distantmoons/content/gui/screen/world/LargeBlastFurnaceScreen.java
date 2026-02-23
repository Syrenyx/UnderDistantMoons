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
import syrenyx.distantmoons.content.menu.block.LargeBlastFurnaceMenu;

public class LargeBlastFurnaceScreen extends AbstractContainerScreen<LargeBlastFurnaceMenu> {

  private static final Identifier DEFAULT_TEXTURE = UnderDistantMoons.identifierOf("textures/gui/container/large_blast_furnace/default.png");
  private static final Identifier MIRRORED_TEXTURE = UnderDistantMoons.identifierOf("textures/gui/container/large_blast_furnace/mirrored.png");
  private static final Identifier FUEL_BURNING_PROGRESS_SPRITE = UnderDistantMoons.identifierOf("container/large_blast_furnace/fuel_burning_progress");
  private static final int BACKGROUND_TEXTURE_SIZE = 256;
  private static final int FUEL_BURNING_PROGRESS_SPRITE_SIZE = 14;

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
      int heightReduction = Mth.ceil(fuelBurnProgress * (FUEL_BURNING_PROGRESS_SPRITE_SIZE - 1)) + 1;
      guiGraphics.blitSprite(
          RenderPipelines.GUI_TEXTURED,
          FUEL_BURNING_PROGRESS_SPRITE,
          FUEL_BURNING_PROGRESS_SPRITE_SIZE, FUEL_BURNING_PROGRESS_SPRITE_SIZE,
          0, FUEL_BURNING_PROGRESS_SPRITE_SIZE - heightReduction,
          this.leftPos + 117, this.topPos + 110 + FUEL_BURNING_PROGRESS_SPRITE_SIZE - heightReduction,
          FUEL_BURNING_PROGRESS_SPRITE_SIZE, heightReduction
      );
    }
  }

  @Override
  public void render(@NonNull GuiGraphics guiGraphics, int cursorX, int cursorY, float delta) {
    super.render(guiGraphics, cursorX, cursorY, delta);
    this.renderTooltip(guiGraphics, cursorX, cursorY);
  }
}
