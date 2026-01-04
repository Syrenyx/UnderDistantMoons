package syrenyx.distantmoons.content.entity;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jspecify.annotations.NonNull;

public class SittingSpotRenderer extends EntityRenderer<SittingSpotEntity, EntityRenderState> {

  public SittingSpotRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public EntityRenderState createRenderState() {
    return new EntityRenderState();
  }

  @Override
  public boolean shouldRender(SittingSpotEntity entity, @NonNull Frustum frustum, double x, double y, double z) {
    return false;
  }
}
