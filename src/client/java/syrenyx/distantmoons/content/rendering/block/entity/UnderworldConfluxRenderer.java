package syrenyx.distantmoons.content.rendering.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syrenyx.distantmoons.content.block.entity.UnderworldConfluxBlockEntity;
import syrenyx.distantmoons.content.data_component.DimensionKeystoneComponent;
import syrenyx.distantmoons.initializers.DistantMoonsDataComponentTypes;

public class UnderworldConfluxRenderer implements BlockEntityRenderer<UnderworldConfluxBlockEntity, UnderworldConfluxRenderer.RenderState> {

  private final ItemModelResolver itemModelResolver;

  public UnderworldConfluxRenderer(BlockEntityRendererProvider.Context context) {
    this.itemModelResolver = context.itemModelResolver();
  }

  @Override
  public RenderState createRenderState() {
    return new RenderState();
  }

  @Override
  public void extractRenderState(
      UnderworldConfluxBlockEntity blockEntity,
      UnderworldConfluxRenderer.RenderState renderState,
      float f,
      @NonNull Vec3 position,
      ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay
  ) {
    BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, f, position, crumblingOverlay);
    ItemStack itemStack = blockEntity.getItem();
    if (itemStack.isEmpty()) return;
    DimensionKeystoneComponent component = itemStack.get(DistantMoonsDataComponentTypes.DIMENSION_KEYSTONE);
    if (component != null) itemStack.set(DataComponents.ITEM_MODEL, component.slottedItemModel());
    ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
    this.itemModelResolver.updateForTopItem(itemStackRenderState, itemStack, ItemDisplayContext.NONE, blockEntity.level(), null, (int) blockEntity.getBlockPos().asLong());
    renderState.item = itemStackRenderState;
  }

  @Override
  public void submit(RenderState renderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
    if (renderState.item == null) return;
    poseStack.pushPose();
    poseStack.translate(0.5F, 0.5F, 0.5F);
    renderState.item.submit(poseStack, submitNodeCollector, 0, 0, 0);
    poseStack.popPose();
  }

  public static class RenderState extends BlockEntityRenderState {

    public ItemStackRenderState item = null;
  }
}
