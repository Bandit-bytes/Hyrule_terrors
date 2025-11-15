package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.LizalfosAnimator;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LizalfosRenderer extends AzEntityRenderer<Lizalfos> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/entity/lizalfos.geo.json"
    );

    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
        "textures/entity/lizalfos.png"
    );

    public LizalfosRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<Lizalfos>builder(MODEL, TEXTURE)
                .setAnimatorProvider(LizalfosAnimator::new)
                .setDeathMaxRotation(0.5F)
                .setShadowRadius(0.75F)
                .setScale(1.4F)
                .build(),
            context
        );
    }
}
