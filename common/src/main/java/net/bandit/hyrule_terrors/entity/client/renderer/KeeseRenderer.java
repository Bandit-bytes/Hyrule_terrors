package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.KeeseAnimator;
import net.bandit.hyrule_terrors.entity.mobs.Keese;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KeeseRenderer extends AzEntityRenderer<Keese> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
            "geo/entity/keese.geo.json"
    );
    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
            "textures/entity/keese.png"
    );

    public KeeseRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<Keese>builder(MODEL, TEXTURE)
                        .setAnimatorProvider(KeeseAnimator::new)
                        .setDeathMaxRotation(0.0F)
                        .setShadowRadius(0.25F)
                        .build(),
                context
        );
    }
}
