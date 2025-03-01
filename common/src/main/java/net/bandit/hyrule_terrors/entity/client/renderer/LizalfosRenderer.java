package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.LizalfosAnimator;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LizalfosRenderer extends AzEntityRenderer<Lizalfos> {

    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID, "geo/entity/lizalfos.geo.json"
    );
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID, "textures/entity/lizalfos.png"
    );

    public LizalfosRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<Lizalfos>builder(MODEL, TEXTURE)
                        .setAnimatorProvider(LizalfosAnimator::new)
                        .setDeathMaxRotation(0.0F)
                        .build(),
                context
        );
        this.shadowRadius = 0.55f;
    }
}
