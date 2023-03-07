package smacho.macho.content.inits;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderInit {

    // Subscribe to the EntityRenderersEvent
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Register the ThrownItemRenderer for the BulletEntity type
        event.registerEntityRenderer(EntityTypeInit.BULLET_ENTITY_TYPE.get(), ThrownItemRenderer::new);
    }

}
