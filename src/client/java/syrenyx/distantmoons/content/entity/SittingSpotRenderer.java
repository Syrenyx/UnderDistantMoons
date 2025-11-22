package syrenyx.distantmoons.content.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;

public class SittingSpotRenderer extends EntityRenderer<SittingSpotEntity, EntityRenderState> {

  public SittingSpotRenderer(EntityRendererFactory.Context context) {
    super(context);
  }

  @Override
  public EntityRenderState createRenderState() {
    return new EntityRenderState();
  }

  @Override
  public boolean shouldRender(SittingSpotEntity entity, Frustum frustum, double x, double y, double z) {
    return false;
  }
}
