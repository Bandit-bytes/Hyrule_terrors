package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.StalmoblinAnimator;
import net.bandit.hyrule_terrors.entity.client.renderer.layer.StalmoblinItemLayer;
import net.bandit.hyrule_terrors.entity.mobs.Stalmoblin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StalmoblinRenderer extends AzEntityRenderer<Stalmoblin> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/entity/stalmoblin.geo.json"
    );

    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
        "textures/entity/stalmoblin.png"
    );

    public StalmoblinRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<Stalmoblin>builder(MODEL, TEXTURE)
                .setAnimatorProvider(StalmoblinAnimator::new)
                .addRenderLayer(new StalmoblinItemLayer())
                .setShadowRadius(0.7F)
                .build(),
            context
        );
    }
}
