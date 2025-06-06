package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.BokoblinAnimator;
import net.bandit.hyrule_terrors.entity.mobs.Bokoblin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BokoblinRenderer extends AzEntityRenderer<Bokoblin> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/entity/bokoblin.geo.json"
    );

    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
        "textures/entity/bokoblin.png"
    );

    public BokoblinRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<Bokoblin>builder(MODEL, TEXTURE)
                .setAnimatorProvider(BokoblinAnimator::new)
                .setShadowRadius(0.5F)
                .build(),
            context
        );
    }

}
